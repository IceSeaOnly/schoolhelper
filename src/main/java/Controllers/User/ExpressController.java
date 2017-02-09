package Controllers.User;

import Entity.*;
import Entity.User.User;
import Service.UserService;
import Utils.ExpressOrderValidate;
import Utils.MD5;
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
    UserService userService;




    @RequestMapping("help_send_express")
    public String help_send_express(ModelMap map, HttpSession session) {
        User u = (User) session.getAttribute("user");
        ArrayList<SendExpress> expresses = userService.getAllSendExpresses(u.getSchoolId());

        if (expresses.size() == 0) {
            map.put("info", "更多快递正在接入中...");
            return "user/not_in_service";
        }

        map.put("expresses", expresses);
        String address = getSendPart(u.getPart(), userService.listAllParts(u.getSchoolId()),u.getSchoolId()).getPartName() + u.getBuilding();
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
        SendExpressOrder seo = new SendExpressOrder(express.getExpressname(), express.getOrderPrice(), false, new Date(System.currentTimeMillis()), orderKey,
                name, phone, address, user.getOpen_id(),user.getSchoolId());
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
                return "user/vippay_success";
            } else {
                map.put("type", 3);
                return "user/vippay_failed";
            }
        }
        map.put("key",order.getOrderKey());
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
    public String help_express(ModelMap map, HttpSession session) {

        /**
         * 2016.10.5 先检查信息是否完善
         * */
        User user = (User) session.getAttribute("user");
        user = userService.getUserByOpenId(user.getOpen_id());
        session.setAttribute("user", user);
        if (user.getUsername().equals("") && user.getPhone().equals("")) {
            map.put("should_complete_user_info", true);
            return "user/user_center";
        }

        // 查询是否正在服务中
        int state = userService.ExpressServiceRunning(user.getSchoolId());
        if (state != 0) {
            String info = userService.getServiceStopReason(state,user.getSchoolId());
            map.put("info", info);
            return "user/not_in_service";
        }

        ArrayList<Entity.Express> expresses = OutOfService(userService.listAllExpresses(user.getSchoolId()));
        if(expresses.size() == 0){
            map.put("info", "尚未接入快递点");
            return "user/not_in_service";
        }
        ArrayList<SendPart> parts = userService.listAllParts(user.getSchoolId());
        ArrayList<SendTime> sendTimes = userService.getAllSendTimes(user.getSchoolId());
        /** 对每一个配送时段确定剩余名额，没有名额的id置为-1 begin*/
        for (int i = 0; i < sendTimes.size(); i++) {
            Long cur = userService.getSendTimeCurrentSum(sendTimes.get(i).getId(),user.getSchoolId());
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
        map.put("order", order);
        if (order.isHas_pay() == false) return "user/ready_topay";
        return "user/order_detail";
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
            HttpSession session,
            ModelMap map) {

        /** 格式化用户输入*/
        String regEx="[^0-9]";
        Pattern p = Pattern.compile(regEx);
        building = p.matcher(building).toString();
        // 得到用户信息
        User user = (User) session.getAttribute("user");
        ArrayList<Entity.Express> expresses = (ArrayList<Express>) session.getAttribute("expresses");
        ArrayList<SendPart> parts = (ArrayList<SendPart>) session.getAttribute("parts");
        ArrayList<SendTime> sendTimes = (ArrayList<SendTime>) session.getAttribute("sendtimes");
        if (!ExpressOrderValidate.validate(express, part, expresses, parts)) {
            return "redirect:errors/illegal";
        }
        /** 确定该配送时段还有名额 begin*/
        Long cur_sum = userService.getSendTimeCurrentSum(sendtime_id,user.getSchoolId());
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

        Express ex = getExpress(express, expresses,user.getSchoolId());
        SendPart pa = getSendPart(part, parts,user.getSchoolId());
        // 计算费用 单位分
        int cost = ex.getSendPrice() + pa.getSendPrice();

        /**
         * 2016/9/19 关闭首单免费机制
         * 2017/2/7 可控制免费机制
         * */
        SchoolConfigs sc = userService.getSchoolConfBySchoolId(user.getSchoolId());
        if(sc.isFirstDiscount())
            cost = FirstDiscount(user, express_phone, sendto_phone, cost);

        /**
         * 2016/9/30 满十减一
         * 2017/2/7 可控制满十减一
         * */
        if(sc.isIfTenThenFree())
            cost = IF10THENFREE(user, cost, session);

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

        userService.sava(order);
        map.put("order", order);
        /**
         * 当前订单放置在session中
         * */
        session.setAttribute("cur_order", order);
        return "user/ready_topay";
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
        return userService.getFirstOrderCost(cost,user.getSchoolId());
    }

    @RequestMapping("schoolmove")
    public String schoolMove() {
        return "user/school_move";
    }

    @RequestMapping("pay_school_move")
    public String pay_school_move(@RequestParam String pay, HttpSession session, ModelMap map) {
        SchoolMoveOrder order = (SchoolMoveOrder) session.getAttribute("smo");
        if (pay.equals("vippay")) {
            /**
             * 会员卡支付
             * */
            User user = (User) session.getAttribute("user");
            user = userService.getUserByOpenId(user.getOpen_id());
            map.put("order_key", order.getOrderKey());
            if (order == null) return "errors/illegal";
            if (user.getMy_money() >= 500) {
                user.setMy_money(user.getMy_money() - 500);
                order.setHaspay(true);
                userService.update(order);
                userService.update(user);
                session.setAttribute("user", user);
                return "user/vippay_success";
            } else {
                map.put("type", 2);
                return "user/vippay_failed";
            }
        }
        map.put("key",order.getOrderKey());
        return "user/pay_schoolmove";
    }

    @RequestMapping("submit_schoolmove")
    public String submit_schoolmove(@RequestParam String movetime,
                                    @RequestParam String name,
                                    @RequestParam String phone,
                                    HttpSession session) {
        User user = (User) session.getAttribute("user");
        String key = MD5.encryption(phone + name + System.currentTimeMillis()).substring(25) + System.currentTimeMillis();
        SchoolMoveOrder smo = new SchoolMoveOrder(name, phone, movetime, user.getOpen_id(), key,user.getSchoolId());
        userService.sava(smo);
        session.setAttribute("smo", smo);
        return "user/ready_to_pay_schoolmove";
    }
}
