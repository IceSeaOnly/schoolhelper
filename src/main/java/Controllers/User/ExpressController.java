package Controllers.User;

import Entity.*;
import Entity.Manager.Manager;
import Entity.User.User;
import Service.CouponService;
import Service.ManagerService;
import Service.NoticeService;
import Service.UserService;
import Utils.ExpressOrderValidate;
import Utils.HttpUtils;
import Utils.MD5;
import Utils.TimeFormat;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Utils.ExpressOrderHelper.getExpress;
import static Utils.ExpressOrderHelper.getSendPart;

/**
 * Created by Administrator on 2016/8/31.
 */
@Controller
@RequestMapping("user")
public class ExpressController {

    @Resource
    ManagerService managerService;
    @Resource
    UserService userService;
    @Resource
    NoticeService noticeService;
    @Resource
    CouponService couponService;


    @RequestMapping("help_send_express")
    public String help_send_express(ModelMap map, HttpSession session) {
        User u = (User) session.getAttribute("user");
        ArrayList<SendExpress> expresses = userService.getAllSendExpresses(u.getSchoolId());

        if (expresses.size() == 0) {
            map.put("info", "更多快递正在接入中...");
            return "user/not_in_service";
        }

        map.put("expresses", expresses);
        String address = getSendPart(u.getPart(), userService.listAllParts(u.getSchoolId()), u.getSchoolId()).getPartName() + u.getBuilding();
        map.put("address", address);
        return "user/help_send_express";
    }

    @RequestMapping("submit_help_sendexpress")
    public String submit_help_sendexpress(
            @RequestParam int select_express,
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String address,
            HttpSession session,
            ModelMap map
    ) {
        User user = (User) session.getAttribute("user");
        ArrayList<SendExpress> expresses = userService.getAllSendExpresses(user.getSchoolId());
        SendExpress express = getSendExpress(select_express, expresses);
        if (express == null) return "errors/illegal";
        String orderKey = MD5.encryption(name + phone + address + System.currentTimeMillis());
        SendExpressOrder seo = new SendExpressOrder(express.getExpressname(), express.getPhone(), express.getOrderPrice(), false, new Date(System.currentTimeMillis()), orderKey,
                name, phone, address, user.getOpen_id(), user.getSchoolId());
        userService.sava(seo);
        session.setAttribute("seo", seo);
        return "user/readyto_pay_help_send_express";
    }

    @RequestMapping("pay_help_send_express")
    public String pay_help_send_express(@RequestParam String pay, HttpSession session, ModelMap map) {
        SendExpressOrder order = (SendExpressOrder) session.getAttribute("seo");
        if (pay.equals("vippay")) {
            User user = (User) session.getAttribute("user");
            user = userService.getUserByOpenId(user.getOpen_id());
            map.put("order_key", order.getOrderKey());

            if (order == null) return "errors/illegal";
            if (user.getMy_money() >= order.getShouldPay()) {
                user.setMy_money(user.getMy_money() - order.getShouldPay());
                order.setHaspay(true);
                userService.update(order);
                userService.update(user);
                session.setAttribute("user", user);

                JSONObject data = new JSONObject();
                data.put("where", order.getAddress());
                data.put("name", order.getName() + order.getPhone());
                //管理分红
                managerService.managerDividend(order.getSchoolId(), order.getShouldPay(), order.getId(), "代寄快递分红");
                //通知
                //noticeService.CommonSMSSend("SMS_46215163",order.getExpPhone(),data);
                noticeService.NoticeManagers(order.getSchoolId(), order.getExpress() + "代寄订单", order.getName() + "," + order.getAddress() + "," + order.getPhone(), "代寄订单", order.getShouldPay() / 100);
                noticeService.ReservationService(
                        "上门取件已接单",
                        order.getExpress() + "的服务人员将主动联系您",
                        "上门取件",
                        TimeFormat.format(System.currentTimeMillis()),
                        "快递小哥",
                        (double) order.getShouldPay() / 100 + "元",
                        "请耐心等待服务",
                        order.getOpen_id(),
                        "http://www.bigdata8.xin/user/index.do");
                return "user/vippay_success";
            } else {
                map.put("type", 3);
                return "user/vippay_failed";
            }
        }
        map.put("key", order.getOrderKey());
        return "user/pay_help_send_express";
    }

    /**
     * 查询选择代取快递的快递
     */
    private SendExpress getSendExpress(int select_express, ArrayList<SendExpress> expresses) {
        for (int i = 0; i < expresses.size(); i++) {
            if (expresses.get(i).getId() == select_express) return expresses.get(i);
        }
        return null;
    }

    @RequestMapping("help_express")
    public String help_express(ModelMap map, Integer luggage, HttpSession session) {
        User user = (User) session.getAttribute("user");

        /**
         * 检查是否拉黑
         * */
        if (user.isBlackUser()) {
            map.put("is_url", false);
            map.put("notice", "微信环境异常");
            map.put("url", "");
            return "user/common_result";
        }

        /**
         * 2016.10.5 先检查信息是否完善
         * */
        user = userService.getUserByOpenId(user.getOpen_id());
        session.setAttribute("user", user);
        if (user.getUsername().equals("") && user.getPhone().equals("")) {
            map.put("is_url", true);
            map.put("notice", "请先完善信息~");
            map.put("url", "/user/change_my_school.do");
            return "user/common_result";
        }
        if (user.getDormitory() == null || user.getDormitory().equals("")) {
            map.put("is_url", true);
            map.put("notice", "请填写宿舍号以便送件上门哦~");
            map.put("url", "/user/change_my_school.do");
            return "user/common_result";
        }

        // 查询是否正在服务中
        int state = userService.ExpressServiceRunning(user.getSchoolId());
        if (state != 0) {
            String info = userService.getServiceStopReason(state, user.getSchoolId());
            map.put("info", info);
            return "user/not_in_service";
        }

        ArrayList<Entity.Express> expresses = OutOfService(userService.listAllExpresses(user.getSchoolId(), true));
        if (expresses.size() == 0) {
            map.put("info", "尚未接入快递点");
            return "user/not_in_service";
        }
        ArrayList<SendPart> parts = userService.listAllParts(user.getSchoolId());
        ArrayList<SendTime> sendTimes = userService.getAllSendTimes(user.getSchoolId(), true);
        if (sendTimes.size() == 0) {
            map.put("info", "尚未设置配送时段");
            return "user/not_in_service";
        }
        /** 对每一个配送时段确定剩余名额，没有名额的id置为-1 begin*/
        for (int i = 0; i < sendTimes.size(); i++) {
            Long cur = userService.getSendTimeCurrentSum(sendTimes.get(i).getId(), user.getSchoolId());
            sendTimes.get(i).setCurSum(cur);
            if (cur >= sendTimes.get(i).getS_limit())
                sendTimes.get(i).setId(-1);
        }
        /** 对每一个配送时段确定剩余名额，没有名额的id置为-1 end*/
        session.setAttribute("expresses", expresses);
        session.setAttribute("parts", parts);
        session.setAttribute("sendtimes", sendTimes);

        map.put("parts", parts);
        map.put("expresses", expresses);
        map.put("time_validate", System.currentTimeMillis());
        map.put("freeSum", couponService.howManyFreeIHave(user));
        if (null != luggage && luggage == 1) {
            map.put("isLuggage", true);
        } else {
            map.put("isLuggage", false);
        }
        return "user/help_express";
    }

    // 判断是否超时
    private ArrayList<Express> OutOfService(ArrayList<Express> expresses) {
        int H = new Date(System.currentTimeMillis()).getHours();
        int M = new Date(System.currentTimeMillis()).getMinutes();

        for (int i = 0; i < expresses.size(); i++) {
            boolean OutOfService = false;

            if (H > expresses.get(i).getHour_out_of_service()) OutOfService = true;
            if (H == expresses.get(i).getHour_out_of_service() && M > expresses.get(i).getMinute_out_of_service())
                OutOfService = true;

            if (OutOfService)
                expresses.get(i).setId(-100);
        }
        return expresses;
    }


    @RequestMapping("see_order_detail")
    public String see_order_detail(@RequestParam int id, ModelMap map, HttpSession session) {
        User user = (User) session.getAttribute("user");
        ExpressOrder order = userService.getExpressOrderById(user, id);
        if (order == null) return "errors/illegal";
        String riderPhone = order.getRider_id() > 0 ? managerService.getManagerById(order.getRider_id()).getPhone() : "暂未接单";
        map.put("riderPhone", riderPhone);
        map.put("order", order);
        if (order.isHas_pay() == false) return "user/ready_topay";
        if ((order.getOrder_state() == 0 || order.getOrder_state() == 1) && order.getOrderTimeStamp() < TimeFormat.getTimesmorning()) {
            map.put("title", "抱歉");
            map.put("msg", "您的订单因无人接单即将进入退款流程，再次表示歉意");
            map.put("btnmsg", "点击继续");
            map.put("url", "refund_outofdate_order.do?oid=" + id);
            return "errors/msg";
        }
        return "user/order_detail";
    }

    @RequestMapping("refund_outofdate_order")
    public String refund_outofdate_order(@RequestParam int oid, ModelMap map, HttpSession session) {
        User user = (User) session.getAttribute("user");
        ExpressOrder order = userService.getExpressOrderById(user, oid);
        if (order == null) return "errors/illegal";
        if (order.getUser_id() != user.getId()) return "errors/illegal";
        if (order.getOrderTimeStamp() > TimeFormat.getTimesmorning()) return "errors/illegal";
        if (order.getOrder_state() != 1 || order.getOrder_state() != 0) return "errors/illegal";
        if (!order.isHas_pay()) return "errors/illegal";
        if (order.getOrder_state() == -1) return "errors/illegal";

        String pass = session.getServletContext().getInitParameter("refund_pwd");
        String url = session.getServletContext().getInitParameter("refund_url");
        String validate = MD5.encryption(order.getOrderKey() + pass + order.getShouldPay());

        String rs = HttpUtils.sendGet(url, "out_trade_no=" + order.getOrderKey() + "&refund_fee=" + order.getShouldPay() + "&validate=" + validate);

        if (rs.contains("SUCCESS")) {
            order.setOrder_state(-1);
            managerService.update(order);
            managerService.orderSumCutOne(order.getUser_id());
            managerService.log(user.getId(), 11, order.getId() + "订单超时退款:" + rs);
            noticeService.refund(order.getId(), order.getUser_id(), order.getShouldPay(), "您的订单退款成功，按原路退回");
        } else if (rs.contains("订单不存在")) { //余额支付订单
            rs = "余额支付单，";
            order.setOrder_state(-1);
            managerService.update(order);
            managerService.orderSumCutOne(order.getUser_id());
            boolean res = managerService.refundVipPay(order.getUser_id(), order.getShouldPay());
            rs += (res ? "已退款至账户" : "但退款时发生错误");
            managerService.log(user.getId(), 11, order.getId() + "订单超时退款:" + rs);
            noticeService.refund(order.getId(), order.getUser_id(), order.getShouldPay(), "您的订单退款结果:" + (res ? "已退款至账户余额" : "退款时发生错误"));
        }
        map.put("title", "退款结果");
        map.put("msg", rs);
        map.put("btnmsg", "好的");
        map.put("url", "user_center.do");
        return "errors/msg";
    }

    @RequestMapping("go_to_pay")
    public String go_to_pay(@RequestParam String orderKey,
                            @RequestParam String pay, ModelMap map, HttpSession session) {
        /** 检查订单是否失效*/
        ExpressOrder eor = userService.getExpressOrderByOrderKey(orderKey);
        if (System.currentTimeMillis() - eor.getOrderTime().getTime() > 300000) {
            eor.setOrder_state(ExpressOrder.ORDER_CENCLE);
            userService.update(eor);
            map.put("msg", "订单超时已失效！");
            return "errors/error";
        }
        if (pay.equals("wxpay")) {
            map.put("key", orderKey);
            return "user/go_to_pay";
        } else if (pay.equals("vippay")) {
            /**
             * 会员卡支付
             * */
            User user = (User) session.getAttribute("user");
            user = userService.getUserByOpenId(user.getOpen_id());
            ExpressOrder order = (ExpressOrder) session.getAttribute("cur_order");
            if (order == null) return "errors/illegal";
            if (user.getMy_money() >= order.getShouldPay()) {
                /**
                 * 支付成功
                 * */
                income_add(user.getSchoolId(), order.getShouldPay());
                noticeService.paySuccess("筋斗云订单余额支付成功", (double) order.getShouldPay() / 100 + "元", "如有疑问或退款，请点我召唤客服", "代取快递", user.getOpen_id(), "http://www.bigdata8.xin/user/see_order_detail.do?id=" + order.getId(), order);
                user.setMy_money(user.getMy_money() - order.getShouldPay());
                order.setHas_pay(true);
                userService.update(order);
                userService.update(user);
                session.setAttribute("user", user);
                return "user/vippay_success";
            } else {
                /**
                 * 支付失败
                 * */
                map.put("order_key", orderKey);
                map.put("type", 0);
                return "user/vippay_failed";
            }
        }
        return "errors/illegal";
    }

    @RequestMapping(value = "take_help_express_order", method = RequestMethod.POST)
    public String take_help_express_order(
            @RequestParam int express,
            @RequestParam String sms,
            @RequestParam String arrive,
            @RequestParam String express_name,
            @RequestParam String express_phone,
            @RequestParam int part,
            @RequestParam int sendtime_id,
            @RequestParam String building,
            @RequestParam String sendto_name,
            @RequestParam String sendto_phone,
            @RequestParam String otherinfo,
            @RequestParam Integer luggage,
            HttpSession session,
            ModelMap map) {

        /** 格式化用户输入*/
//        String regEx="[^0-9]";
//        Pattern p = Pattern.compile(regEx);
//        building = p.matcher(building).toString();
        // 得到用户信息
        User user = (User) session.getAttribute("user");
        ArrayList<Entity.Express> expresses = (ArrayList<Express>) session.getAttribute("expresses");
        ArrayList<SendPart> parts = (ArrayList<SendPart>) session.getAttribute("parts");
        ArrayList<SendTime> sendTimes = (ArrayList<SendTime>) session.getAttribute("sendtimes");
        if (!ExpressOrderValidate.validate(express, part, expresses, parts)) {
            return "redirect:errors/illegal";
        }
        /** 确定该配送时段还有名额 begin*/
        Long cur_sum = userService.getSendTimeCurrentSum(sendtime_id, user.getSchoolId());
        boolean curtime_pass = false;
        for (int i = 0; i < sendTimes.size(); i++) {
            if (sendTimes.get(i).getId() == sendtime_id && sendTimes.get(i).getS_limit() > cur_sum)
                curtime_pass = true;
        }
        if (!curtime_pass) {
            map.put("msg", "对不起，该时段的配送名额已满，请选择其他时间段!");
            return "errors/error";
        }
        /** 确定该配送时段还有名额 end*/


        Express ex = getExpress(express, expresses, user.getSchoolId());
        SendPart pa = getSendPart(part, parts, user.getSchoolId());
        // 计算费用 单位分
        // 行李寄运10元/单
        int cost = (luggage == 1 ? 1000 : (ex.getSendPrice() + pa.getSendPrice()));
        boolean free_this = false;
        /** 20170224 免单检测 begin*/
        user = userService.getUserById(user.getId());
        SchoolConfigs sc = userService.getSchoolConfBySchoolId(user.getSchoolId());
        /**
         * 增加优惠券控制开关
         * */
        GiftRecord gift = null;
        if (sc.isEnableCoupon() && couponService.howManyFreeIHave(user) > 0) {
            cost = 1;
            free_this = true;
//            user.setFreeSum(user.getFreeSum()-1);
//            user.setOrder_sum(user.getOrder_sum()+1);
//            userService.update(user);
            couponService.consumeOneFreeGift(user);
        } else if (sc.isEnableCoupon() && luggage != 1 && (gift = couponService.consumeMaxCoupon(user)) != null) {

        } else if (sc.isEnableCoupon() && luggage == 1 && (gift = couponService.consumeOneByCtype(user,3)) != null){

        } else {
            /**
             * 2016/9/19 关闭首单免费机制
             * 2017/2/7 可控制免费机制
             * */
            if (sc.isFirstDiscount())
                cost = FirstDiscount(user, express_phone, sendto_phone, cost);

            /**
             * 2016/9/30 满十减一
             * 2017/2/7 可控制满十减一
             * */
            if (sc.isIfTenThenFree())
                cost = IF10THENFREE(user, cost, session);
        }
        /** 20170224 免单检测 end*/

        String orderKey = MD5.encryption(System.currentTimeMillis() + user.getId() + user.getPhone());

        Long TIMESTAMP = System.currentTimeMillis();
        ExpressOrder order = new ExpressOrder(
                user.getId(),
                cost,
                orderKey,
                ex.getExpressName(),
                express_name,
                express_phone,
                sms,
                pa.getPartName() + building,
                part,
                sendto_phone,
                sendto_name,
                ExpressOrder.NORMAL_STATE,
                new Date(TIMESTAMP),
                TIMESTAMP,
                arrive,
                otherinfo,
                sendtime_id,
                user.getSchoolId()
        );

        String title = (luggage == 1 ? "筋斗云行李寄运订单" : "筋斗云订单");

        if (free_this) {
            order.setHas_pay(true);
            noticeService.paySuccess("免单券支付成功", "0元", "该订单由免单券支付", title, user.getOpen_id(), "", order);
            userService.sava(order);
            return "redirect:my_orders.do";
        } else {
            // 2017.11.10新增立减优惠功能
            if (gift != null) {
                int after = order.getShouldPay() - gift.getClijian();
                noticeService.paySuccess("立减券使用成功", gift.getClijian() / 100.0 + "元", "立减" + gift.getClijian() / 100.0, title, user.getOpen_id(), "", null);
                order.setShouldPay(after > 0 ? after : 0);
                if (order.getShouldPay() == 0) {
                    order.setHas_pay(true);
                    noticeService.paySuccess("立减券全额支付成功", "0元", "该订单由立减券全额支付", title, user.getOpen_id(), "", order);
                    userService.sava(order);
                    return "redirect:my_orders.do";
                }
            }
            userService.sava(order);
            map.put("order", order);
            /**
             * 当前订单放置在session中
             * */
            session.setAttribute("cur_order", order);
            return "user/ready_topay";
        }
    }

    /**
     * 满十减一
     */
    private int IF10THENFREE(User user, int cost, HttpSession session) {
        if (user.getOrder_sum() > 0 && user.getOrder_sum() % 10 == 0) {
            Integer FLAG = (Integer) session.getAttribute("Free_Flag");
            if (FLAG == null || FLAG != user.getOrder_sum()) {
                /**
                 * 设置在这个订单数量下已经使用过了优惠
                 * */
                session.setAttribute("Free_Flag", user.getOrder_sum());
                session.setAttribute("user", user);
                return 1;
            }
        }
        return cost;
    }


    // 确定首单优惠
    private int FirstDiscount(User user, String express_phone, String sendto_phone, int cost) {
        if (user.getOrder_sum() > 0) return cost;

        if (userService.phoneExsit(express_phone, sendto_phone)) return cost;
        return userService.getFirstOrderCost(cost, user.getSchoolId());
    }

    @RequestMapping("schoolmove")
    public String schoolMove(Integer isLuggageOrder) {
        if (null != isLuggageOrder && isLuggageOrder == 1)
            return "user/luggage_move";
        return "user/school_move";
    }

    @RequestMapping("pay_school_move")
    public String pay_school_move(@RequestParam String pay, HttpSession session, ModelMap map) {
        User user = (User) session.getAttribute("user");

        SchoolMoveOrder order = (SchoolMoveOrder) session.getAttribute("smo");
        if (order.getCouponId() > 0) {
            GiftRecord giftRecord = couponService.findById(user.getId(), order.getCouponId());
            if (giftRecord == null || giftRecord.isUsed() || !giftRecord.isValid()) {
                order.setShouldPay(order.getShouldPay() + giftRecord.getClijian());
                order.setCouponId(-1);
            }
        }

        if (pay.equals("vippay")) {
            /**
             * 会员卡支付
             * */
            user = userService.getUserByOpenId(user.getOpen_id());
            map.put("order_key", order.getOrderKey());
            if (order == null) return "errors/illegal";
            if (user.getMy_money() >= order.getShouldPay()) {
                if (order.getCouponId() > 0) {
                    couponService.consumeOne(order.getCouponId());
                }
                user.setMy_money(user.getMy_money() - order.getShouldPay());
                order.setHaspay(true);
                userService.update(order);
                userService.update(user);
                session.setAttribute("user", user);
                income_add(user.getSchoolId(), order.getShouldPay());
                String title = order.getOrderKey().startsWith("lg_") ? "行李搬运订单" : "新校园搬运订单";
                noticeService.paySuccess("会员卡支付成功", order.getShouldPay() / 100 + "元", title + "支付成功", title, user.getOpen_id(), "http://www.bigdata8.xin/user/user_center.do", null);
                JSONObject data = new JSONObject();
                data.put("name", order.getName() + "," + order.getPhone());
                SchoolConfigs sc = userService.getSchoolConfBySchoolId(order.getSchoolId());
                //管理分红
                managerService.managerDividend(sc.getSchoolId(), order.getShouldPay(), order.getId(), title + "分红");
                //通知
                //noticeService.CommonSMSSend("SMS_47515056",String.valueOf(sc.getServicePhone()),data);
                noticeService.NoticeManagers(sc.getSchoolId(), title, order.getName() + "," + order.getMoveTime() + "," + order.getPhone(), title, order.getShouldPay() / 100);
                noticeService.ReservationService(
                        "服务人员已接单",
                        "筋斗云的服务人员将主动联系您",
                        title,
                        TimeFormat.format(System.currentTimeMillis()),
                        "筋斗云小哥",
                        "按实际收取",
                        "请耐心等待服务",
                        order.getOpen_id(),
                        "http://www.bigdata8.xin/user/index.do");
                return "user/vippay_success";
            } else {
                map.put("type", 2);
                return "user/vippay_failed";
            }
        }
        if (order.getCouponId() > 0) {
            couponService.consumeOne(order.getCouponId());
        }
        map.put("key", order.getOrderKey());
        return "user/pay_schoolmove";
    }

    @RequestMapping("submit_schoolmove")
    public String submit_schoolmove(@RequestParam String movetime,
                                    @RequestParam String name,
                                    @RequestParam String phone,
                                    Integer isLuggageOrder,
                                    HttpSession session) {
        User user = (User) session.getAttribute("user");
        String key = MD5.encryption(phone + name + System.currentTimeMillis()).substring(25) + System.currentTimeMillis();
        SchoolMoveOrder smo = new SchoolMoveOrder(name, phone, movetime, user.getOpen_id(), key, user.getSchoolId(), 500, -1);
        if (null != isLuggageOrder && isLuggageOrder == 1) {
            smo.setShouldPay(1000);
            smo.setOrderKey("lg_" + smo.getOrderKey());
            couponService.howManyCouponIHave(user.getId());
            ArrayList<GiftRecord> giftRecords = couponService.getMyCoupons(user.getId());
            for (GiftRecord record : giftRecords) {
                if (record.getCtype() == 3 && !record.isUsed() && record.isValid()) {
                    smo.setCouponId(record.getId());
                    smo.setShouldPay(smo.getShouldPay() - record.getClijian());
                    smo.setShouldPay(smo.getShouldPay() >= 1 ? smo.getShouldPay() : 1);
                }
            }
        }
        userService.sava(smo);
        session.setAttribute("smo", smo);
        return "user/ready_to_pay_schoolmove";
    }

    private void income_add(int sid, int many) {
        SchoolConfigs sc = userService.getSchoolConfBySchoolId(sid);
        sc.setSumIncome(sc.getSumIncome() + many);
        managerService.update(sc);
        managerService.log(-1, 11, sc.getId() + "学校总收入变为[vip pay]:" + sc.getSumIncome());
    }
}
