package Controllers.Manager;

import Entity.*;
import Entity.Manager.*;
import Entity.User.User;
import Service.ManagerService;
import Service.NoticeService;
import Service.UserService;
import Utils.HttpUtils;
import Utils.MD5;
import Utils.SuccessAnswer;
import Utils.TimeFormat;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
@Controller
@RequestMapping("app")
public class Business {
    @Resource
    ManagerService managerService;
    @Resource
    NoticeService noticeService;
    @Resource
    UserService userService;


    /**
     * 查看历史订单，包括今天
     */
    @RequestMapping("history_orders")
    public String histroy_orders(@RequestParam int managerId,
                                 @RequestParam int schoolId,
                                 @RequestParam String yyyy_MM_dd,
                                 String search,
                                 ModelMap map) {
        yyyy_MM_dd = yyyy_MM_dd.equals("/") ? TimeFormat.format2yyyy_MM_dd(System.currentTimeMillis()) : yyyy_MM_dd;
        Long TS = TimeFormat.data2Timestamp(yyyy_MM_dd);
        int year = TimeFormat.getThisYear(TS);
        int month = TimeFormat.getThisMonth(TS);
        int day = TimeFormat.getThisDay(TS);
        School school = managerService.getSchoolById(schoolId);
        ArrayList<ExpressOrder> orders = search == null ? managerService.commonOrderGet(-1, schoolId, -1, year, month, day, -1) : searchOrders(search, schoolId);
        FetchFailFirst(orders);
        ArrayList<Reason> reasons = managerService.listAllReasons(Reason.SEND_ERR);
        map.put("reasons", reasons);
        map.put("orders", orders);
        map.put("school", school);
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        map.put("yyyy_MM_dd", yyyy_MM_dd);
        map.put("max_date", TimeFormat.format2yyyy_MM_dd(System.currentTimeMillis()));
        return "manager/history_orders.do";
    }

    /**
     * 优先显示取件失败的订单
     * */
    private void FetchFailFirst(ArrayList<ExpressOrder> orders) {
        int len = orders.size();
        int cur = 0;
        for (int i = 0; i < len; i++) {
            if(orders.get(i).getOrder_state() == ExpressOrder.ORDER_NOT_EXIST){
                ExpressOrder t = orders.get(cur);
                orders.set(cur,orders.get(i));
                orders.set(i,t);
                cur++;
            }
        }
    }

    /**
     * 查询订单
     */
    private ArrayList<ExpressOrder> searchOrders(String search, int sid) {
        return managerService.searchOrders(search, sid);
    }


    /**
     * 管理员查看待接订单
     */
    @RequestMapping("waitting_to_take_orders")
    public String waitting2take_orders(@RequestParam int managerId,
                                       @RequestParam int schoolId,
                                       ModelMap map) {
        int year = TimeFormat.getThisYear(null);
        int month = TimeFormat.getThisMonth(null);
        int day = TimeFormat.getThisDay(null);
        int orderState = 0; // -1 as default
        int sendTime = -1;// -1 as default
        School school = managerService.getSchoolById(schoolId);
        ArrayList<ExpressOrder> orders =
                managerService.commonOrderGet(-1, schoolId, sendTime, year, month, day, orderState);
        Collections.sort(orders);
        map.put("orders", orders);
        map.put("school", school);
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        return "manager/waitting2take_orders";
    }


    /**
     * 管理员查看待取订单
     * waitting_to_fetch_orders.do?managerId=MANAGERID&token=TOKEN&schoolId=SCHOOLID
     * orderstatus = 1,-2
     */
    @RequestMapping("waitting_to_fetch_orders")
    public String waitting_to_fetch_orders(@RequestParam int managerId,
                                           @RequestParam int schoolId,
                                           String only,
                                           ModelMap map) {

        School school = managerService.getSchoolById(schoolId);
        ArrayList<ExpressOrder> orders = managerService.managerAccess2School(managerId, schoolId) ?
                managerService.getOrdersByStatus(managerId, new Integer[]{1, -2}) : new ArrayList<ExpressOrder>();
        Collections.sort(orders);
        ArrayList<Reason> reasons = managerService.listAllReasons(Reason.FETCH_ERR);
        ArrayList<Express> expresses = userService.listAllExpresses(schoolId, true);
        map.put("expresses", expresses);
        // 筛选生效
        map.put("orders", selectExrpess(orders,only));
        map.put("school", school);
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        map.put("reasons", reasons);
        return "manager/waitting_to_fetch_orders";
    }


    private ArrayList<ExpressOrder> selectExrpess(ArrayList<ExpressOrder>orders,String only){
        ArrayList<ExpressOrder> rs = new ArrayList<ExpressOrder>();
        try {
            if(only !=null)
            only = URLDecoder.decode(only,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (only != null) {
            if (!only.equals("all"))
                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).getExpress().equals(only)) {
                        rs.add(orders.get(i));
                    }
                }
        }else return orders;
        return rs;
    }
    /**
     * 管理员配送订单
     * send_express_order.do?managerId=MANAGERID&token=TOKEN&schoolId=SCHOOLID&config=xxx
     */
    @RequestMapping("send_express_order")
    public String send_express_order(@RequestParam int managerId,
                                     @RequestParam int schoolId,
                                     @RequestParam String config,
                                     ModelMap map) {
        School school = managerService.getSchoolById(schoolId);
        ArrayList<Reason> reasons = managerService.listAllReasons(Reason.SEND_ERR);
        ArrayList<ExpressOrder> orders = managerService.listExpressOrderByConfig(schoolId, managerId, config);
        if (orders == null) return permissionDeny(map);
        map.put("reasons", reasons);
        map.put("orders", orders);
        map.put("school", school);
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        map.put("parts", userService.listAllParts(schoolId));
        map.put("stimes", userService.getAllSendTimes(schoolId, false));
        map.put("managerId", managerId);
        map.put("cur_config", config.equals("none") ? "everyone all -1 -1" : config);
        return "manager/send_express_order";
    }

    /**
     * 管理员根据扫码结果查看订单详情
     * orderId此处为快递单号
     */
    @RequestMapping("watch_order")
    public String watch_order(
            @RequestParam int managerId,
            @RequestParam String orderId,
            ModelMap map
    ) {

        ArrayList<ExpressOrder> orders = managerService.getOrderByCN(managerId, orderId);
        map.put("managerId", managerId);
        map.put("orderId", orderId);
        map.put("orders", orders);
        return "manager/watch_order";
    }

    /**
     * 楼长交接选择
     */
    @RequestMapping("lzjj_switch")
    public String lzjj_switch(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            ModelMap map
    ) {
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        return "manager/lzjj_switch";
    }

    /**
     * 楼长交接-管理员转交订单
     */
    @RequestMapping("lzjj_send")
    public String lzjj_send(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            ModelMap map
    ) {
        int year = TimeFormat.getThisYear(null);
        int month = TimeFormat.getThisMonth(null);
        int day = TimeFormat.getThisDay(null);
        int orderState = 2; // -1 as default
        int sendTime = -1;// -1 as default
        School school = managerService.getSchoolById(schoolId);
        ArrayList<ExpressOrder> orders = managerService.managerAccess2School(managerId, schoolId) ?
                managerService.commonOrderGet(-1, schoolId, sendTime, year, month, day, orderState) : new ArrayList<ExpressOrder>();
        ArrayList<ExpressOrder> rs = filterOrder(orders);
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        map.put("schoolName", school.getSchoolName());
        map.put("orders", rs);
        return "manager/lzjj_send";
    }

    @RequestMapping("lzjjQRmake")
    public String lzjjQRmake(@RequestParam int managerId,
                             @RequestParam int schoolId,
                             @RequestParam String[] checked_orders,
                             ModelMap map) {
        String data = transfer2Json(checked_orders);
        managerService.log(managerId, 6, managerId + "楼长送出订单" + data);
        map.put("result", true);
        map.put("is_url", true);
        map.put("url", "javascript:icesea.makeQRcode('bone_client_web://lzjj_receive.do?managerId=MANAGERID&token=TOKEN&data=" + data + "&omid=" + managerId + "&schoolId=" + schoolId + "');");
        map.put("notice", "数据准备完毕，戳我生成二维码");
        return "manager/common_result";
    }

    /**
     * 过滤没有被设置为楼长交接单的订单
     */
    private ArrayList<ExpressOrder> filterOrder(ArrayList<ExpressOrder> orders) {
        ArrayList<ExpressOrder> tmp = new ArrayList<ExpressOrder>();
        for (int i = 0; i < orders.size(); i++) {
            if (!orders.get(i).isLLJJ())
                tmp.add(orders.get(i));
        }
        return tmp;
    }

    /**
     * 楼长交接-楼长接收订单
     */

    @RequestMapping("lzjj_receive")
    public String lzjj_receive(@RequestParam int managerId,
                               @RequestParam int schoolId,
                               @RequestParam int omid, // 旧的管理员id
                               @RequestParam String data,
                               ModelMap map) {
        ArrayList<ExpressOrder> orders = new ArrayList<ExpressOrder>();
        if (managerService.managerAccess2School(managerId, schoolId)) {
            JSONArray arr = JSONArray.parseArray(data);
            for (int i = 0; i < arr.size(); i++) {
                ExpressOrder it = managerService.getExpressOrderById(arr.getInteger(i));
                if (it != null && !it.isLLJJ()) {
                    Manager m = managerService.getManagerById(managerId);
                    it.setRider_id(m.getId());
                    it.setRider_name(m.getName());
                    it.setLLJJ(true); //标记为楼长交接件
                    orders.add(it);
                    managerService.log(m.getId(), 7, m.getId() + "楼长接收订单" + it.getId());
                    /** 赏工资*/
                    managerService.rewardT(omid, schoolId, it.getId());
                    managerService.rewardR(managerId, schoolId, it.getId());

                    JSONObject jdata = new JSONObject();
                    jdata.put("where", m.getAddress());
                    jdata.put("name", m.getName());
                    it.setLastSms(System.currentTimeMillis());
                    managerService.update(it);
                    noticeService.CommonSMSSend("SMS_46225057", it.getReceive_phone(), jdata);
                }
            }
        }
        School school = managerService.getSchoolById(schoolId);
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        map.put("schoolName", school.getSchoolName());
        map.put("orders", orders);
        return "manager/lzjj_result";
    }

    /**
     * 增加学校
     */

    @RequestMapping("zjxx")
    public String zjxx(@RequestParam int managerId, ModelMap map) {
        if (!managerService.managerAccess2Privilege(managerId, "xxgl"))
            return permissionDeny(map);

        map.put("managerId", managerId);
        return "manager/zjxx";
    }

    /**
     * 增加学校结果
     */

    @RequestMapping("zjxx_result")
    public String zjxx_result(@RequestParam int managerId,
                              @RequestParam String name_ch,
                              @RequestParam String name_en,
                              @RequestParam Long servicePhone,
                              ModelMap map) {
        name_en = name_en.toLowerCase();
        if (!managerService.managerAccess2Privilege(managerId, "xxgl"))
            return permissionDeny(map);
        if (managerService.schoolExist(name_ch, name_en)) {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "校名/校名缩写 已经存在，不能重复！");
            return "manager/common_result";
        } else {
            managerService.createSchool(name_ch, name_en, servicePhone);
            map.put("result", true);
            map.put("is_url", true);
            map.put("url", "http://xiaogutou.qdxiaogutou.com/app/xtsz.do?managerId=MANAGERID&schoolId=SCHOOLID&token=TOKEN");
            map.put("notice", name_ch + " 创建成功");
            return "manager/common_result";
        }
    }

    /**
     * 管理员权限管理
     */
    @RequestMapping("privilege_manage_select")
    public String privilege_manage_select(@RequestParam int managerId,
                                          @RequestParam int schoolId,
                                          ModelMap map) {
        if (!managerService.managerAccess2Privilege(managerId, "qxgl"))
            return permissionDeny(map);
        List<Manager> ms = managerService.listSchoolManagers(schoolId);
        map.put("managers", ms);
        map.put("managerId", managerId);
        map.put("schoolId", schoolId);
        return "manager/privilege_manage_select";
    }

    /**
     * 管理员权限管理
     *
     * @mid 管理员id
     */
    @RequestMapping("privilege_manage")
    public String privilege_manage(@RequestParam int managerId,
                                   @RequestParam int mid,
                                   ModelMap map) {
        if (!managerService.managerAccess2Privilege(managerId, "qxgl"))
            return permissionDeny(map);
        map.put("managerId", managerId);
        map.put("mid", mid);
        map.put("privileges", managerService.editPrivilege(mid));
        return "manager/privilege_manage";
    }

    /**
     * 学校管理功能选择页
     */
    @RequestMapping("school_manage_select")
    public String school_manage_select(@RequestParam int managerId,
                                       ModelMap map) {
        map.put("managerId", managerId);
        return "manager/school_manage_select";
    }



    /**
     * 管理学校管理员--选择学校
     */
    @RequestMapping("glxxgly_select")
    public String glxxgly_select(@RequestParam int managerId,
                                 ModelMap map) {
        if (!managerService.managerAccess2Privilege(managerId, "xxgl")) {
            return permissionDeny(map);
        }

        map.put("schools", managerService.lisAllSchool());
        map.put("managerId", managerId);
        return "manager/glxxgly_select";
    }

    /**
     * 管理学校管理员权限
     */
    @RequestMapping("glxxgly")
    public String glxxgly(@RequestParam int managerId,
                          @RequestParam int sid,
                          ModelMap map) {
        if (!managerService.managerAccess2Privilege(managerId, "xxgl")) {
            return permissionDeny(map);
        }
        map.put("sid", sid);
        map.put("managers", managerService.listAllManagersAndMarkThisSchool(sid));
        map.put("managerId", managerId);
        map.put("schoolName", managerService.getSchoolById(sid).getSchoolName());
        return "manager/glxxgly";
    }

    /**
     * 通用 权限拒绝判定
     */
    public String permissionDeny(ModelMap map) {
        map.put("result", false);
        map.put("is_url", false);
        map.put("notice", "无权操作");
        return "manager/common_result";
    }

    /**
     * 原因管理
     */
    @RequestMapping("reason_manage")
    public String reason_manage(@RequestParam int managerId,
                                ModelMap map) {
        if (!managerService.managerAccess2Privilege(managerId, "reason_manage")) {
            return permissionDeny(map);
        }

        map.put("reasons_s", managerService.listAllReasons(Reason.SEND_ERR));
        map.put("reasons_f", managerService.listAllReasons(Reason.FETCH_ERR));
        map.put("managerId", managerId);
        return "manager/reason_manage";
    }

    /**
     * 原因管理 -- 增加原因
     */
    @RequestMapping("reason_manage_add")
    public String reason_manage_add(@RequestParam int managerId,
                                    @RequestParam String type,
                                    @RequestParam String reason,
                                    ModelMap map) {
        if (!managerService.managerAccess2Privilege(managerId, "reason_manage")) {
            return permissionDeny(map);
        }
        managerService.save(new Reason(type.equals("send") ? Reason.SEND_ERR : Reason.FETCH_ERR, reason));
        managerService.sp_listAllReason();
        map.put("result", true);
        map.put("notice", "添加成功");
        map.put("is_url", false);
        return "manager/common_result";
    }

    /**
     * 管理队伍
     */
    @RequestMapping("work_group")
    public String work_group(
            @RequestParam int managerId,
            @RequestParam int schoolId,
                             ModelMap map) {
        if (!managerService.managerAccess2Privilege(managerId, "work_group")) {
            return permissionDeny(map);
        }
        map.put("managers", managerService.listSchoolManagers(schoolId));
        map.put("managerId", managerId);
        map.put("schoolId", schoolId);
        return "manager/work_group";
    }

    @RequestMapping("manager_add")
    public String manager_add(@RequestParam int managerId, @RequestParam int schoolId,
                              @RequestParam String name,
                              @RequestParam String phone,
                              @RequestParam String wxpay,
                              @RequestParam String alipay,
                              @RequestParam String openid,
                              @RequestParam String pdesc, //描述
                              @RequestParam String addr, //详细地址
                              ModelMap map
    ) {
        if (!managerService.managerAccess2Privilege(managerId, "work_group")) {
            return permissionDeny(map);
        }
        if (managerService.managerPhoneExist(phone.trim())) {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "手机号已存在，创建失败！");
            return "manager/common_result";
        }

        if (addr.length() < 1) {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "必须输入详细的地址");
            return "manager/common_result";
        }
        Manager m = (Manager) managerService.merge(
                new Manager(name, phone, MD5.encryption("123456"), alipay, wxpay, openid, pdesc, addr));
        managerService.save(new PrivilegeDist(m.getId(), 18));
        managerService.save(new SchoolDist(schoolId,m.getId()));
        map.put("result", true);
        map.put("notice", "添加成功,初始密码123456，请注意分配权限，如需分红，请注意分配");
        map.put("is_url", true);
        map.put("url", "privilege_manage_select.do?managerId=MANAGERID&token=TOKEN&schoolId=SCHOOLID");
        return "manager/common_result";
    }

    /**
     * 客服
     */
    @RequestMapping("cservice")
    public String cservice(@RequestParam int managerId,
                           @RequestParam int schoolId,
                           ModelMap map) {
        if (managerService.managerAccess2School(managerId, schoolId)) {
            map.put("waitcservice", managerService.listWaitCService(schoolId));
            map.put("incservice", managerService.listInCService(managerId));
            map.put("completecservice", managerService.listCompleteCService(managerId));
        } else {
            return permissionDeny(map);
        }
        map.put("managerId", managerId);
        map.put("schoolId", schoolId);
        return "manager/cservice";
    }


    /**
     * 客服接入
     * csid是int类型的本地会话序号，非Long型cid
     */
    @RequestMapping("connect_service")
    public String connect_service(@RequestParam int managerId,
                                  @RequestParam int schoolId,
                                  @RequestParam int csid,
                                  HttpSession session,
                                  ModelMap map) {
        if (managerService.managerAccess2Privilege(managerId, "kfgd")) {
            Conversation c = noticeService.getConversationIfWait(managerId, csid, schoolId);
            if (c == null) {
                map.put("result", false);
                map.put("is_url", false);
                map.put("notice", "来晚一步");
                return "manager/common_result";
            } else {
                String appKey = session.getServletContext().getInitParameter("cservice_appkey");
                String secret = session.getServletContext().getInitParameter("cservice_secret");

                JSONObject data = HttpUtils.sendJSONGet("http://cservice.nanayun.cn/manage/setcs.do",
                        "appKey=" + appKey + "&secret=" + secret + "&cid=" + c.getCid() + "&rid=" + managerId);

                managerService.save(new Log(10, "cid=" + c.getCid() + "设置rid=" + managerId + ":" + data.getBoolean("result"), -1));
                map.put("result", true);
                map.put("is_url", true);
                map.put("url", c.getServerEnter());
                map.put("notice", "正在连接，请稍后...");
                return "manager/common_result";
            }
        } else {
            return permissionDeny(map);
        }
    }

    /**
     * 充值订单列表
     */
    @RequestMapping("charge_list")
    public String charge_list(@RequestParam int managerId,
                              @RequestParam int schoolId,
                              ModelMap map) {
        ArrayList<ChargeVipOrder> list = managerService.listChargeList(schoolId);
        map.put("list", list);
        map.put("managerId", managerId);
        map.put("schoolId", schoolId);
        return "manager/charge_list";
    }

    /**
     * vip列表
     */
    @RequestMapping("vip_list")
    public String vip_list(@RequestParam int managerId,
                           @RequestParam int schoolId,
                           ModelMap map) {
        ArrayList<User> users = managerService.listVIP(schoolId);
        map.put("list", users);
        map.put("managerId", managerId);
        map.put("schoolId", schoolId);
        return "manager/vip_list";
    }

    /**
     * 我的收入
     */
    @RequestMapping("my_incomes")
    public String my_incomes(@RequestParam int managerId,
                             ModelMap map) {
        List<ChargingSystem> ins = managerService.listMyIncomes(managerId);
        map.put("ins", ins);
        map.put("ins_sum", managerService.getMyIncomeSum(managerId));
        map.put("ins_wait",managerService.getWaitIncomeSum(managerId));
        return "manager/my_incomes";
    }


    /**
     * 工资清算
     */
    @RequestMapping("gzqs")
    public String gzqs(@RequestParam int managerId,
                       ModelMap map) {
        if (managerService.managerAccess2Privilege(managerId, "gzqs")) {
            ArrayList<PayLog> payLogs = managerService.makePayLogs();
            map.put("logs", payLogs);
        } else {
            return permissionDeny(map);
        }
        return "manager/gzqs";
    }

    /**
     * 系统设置
     */
    @RequestMapping("xtsz")
    public String xtsz(@RequestParam int managerId, @RequestParam int schoolId,
                       ModelMap map) {
        if (managerService.managerAccess2Privilege(managerId, "xtsz") && managerService.managerAccess2School(managerId, schoolId)) {
            map.put("managerId", managerId);
            map.put("schoolId", schoolId);
            map.put("schoolName", managerService.getSchoolById(schoolId).getSchoolName());
            map.put("ms", managerService.listSchoolManagers(schoolId));
            map.put("config", userService.getSchoolConfBySchoolId(schoolId));
            map.put("vips", userService.getAllVipMeals(schoolId));
            map.put("parts", userService.listAllParts(schoolId));
            map.put("exps", userService.listAllExpresses(schoolId, false));
            map.put("stimes", userService.getAllSendTimes(schoolId, false));
            return "manager/sys_setting";
        } else return permissionDeny(map);
    }

    /**
     * 本校其他设置
     */
    @RequestMapping("update_sys_setting")
    public String update_sys_setting(@RequestParam int managerId,
                                     @RequestParam int schoolId,
                                     @RequestParam int fristCost,
                                     @RequestParam int qs, //取件费
                                     @RequestParam int ss, //送件费
                                     @RequestParam int zjs,//转交楼长
                                     @RequestParam int jss,//楼长接收
                                     @RequestParam int aus,//自动开始
                                     @RequestParam int ausd,//自动结束
                                     @RequestParam String phone,//自动结束
                                     @RequestParam String ausn,//自动停止说明
                                     @RequestParam String ausdn,//手动停止说明
                                     @RequestParam String shopUrl,//校园微店
                                     ModelMap map
    ) {
        if (!managerService.managerAccess2Privilege(managerId, "xtsz") || !managerService.managerAccess2School(managerId, schoolId)) {
            return permissionDeny(map);
        }
        if (phone.length() != 11) {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "手机号错误:" + phone);
            return "manager/common_result";
        }
        Long sphone = null;
        try {
            sphone = Long.parseLong(phone);
        } catch (Exception e) {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "手机号错误:" + phone);
            return "manager/common_result";
        }
        if (biggerThan0(qs, ss, zjs, jss)) {
            SchoolConfigs sc = userService.getSchoolConfBySchoolId(schoolId);
            sc.setFirst_cost(fristCost);
            sc.setEach_fetch(qs);
            sc.setEach_send(ss);
            sc.setEach_give(zjs);
            sc.setServicePhone(sphone);
            sc.setEach_receive(jss);
            sc.setAuto_start(aus);
            sc.setAuto_close(ausd);
            sc.setAuto_close_info(ausn);
            sc.setHand_close_info(ausdn);
            sc.setShop_url(shopUrl);
            managerService.update(sc);
            map.put("result", true);
            map.put("is_url", false);
            map.put("notice", "更新完成");
            return "manager/common_result";
        } else {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "参数格式不合法：必须是大于0的正整数");
            return "manager/common_result";
        }
    }

    /**
     * 大于0判断
     */
    private boolean biggerThan0(int... s) {
        for (int i = 0; i < s.length; i++) {
            if (s[i] < 1) return false;
        }
        return true;
    }

    /**
     * 校园消息
     */
    @RequestMapping("xyxx")
    public String xyxx(@RequestParam int managerId, @RequestParam int schoolId,
                       ModelMap map) {
        if (managerService.managerAccess2Privilege(managerId, "xyxx") && managerService.managerAccess2School(managerId, schoolId)) {
            ArrayList<SysMsg> msgs = userService.getAllSystemMsg(schoolId);
            map.put("msgs", msgs);
            map.put("schoolId", schoolId);
            map.put("managerId", managerId);
            return "manager/schoolNoticeList";
        } else {
            return permissionDeny(map);
        }
    }

    @RequestMapping("publish_notice")
    public String publish_notice(@RequestParam int managerId,
                                 @RequestParam int schoolId,
                                 @RequestParam String title,
                                 @RequestParam String content,
                                 ModelMap map) {
        if (managerService.managerAccess2Privilege(managerId, "xyxx") && managerService.managerAccess2School(managerId, schoolId)) {
            SysMsg msg = managerService.publis_notice(schoolId, managerId, title, content);
            noticeService.publishNotice(msg);
            map.put("result", true);
            map.put("is_url", false);
            map.put("notice", "发布任务已经在执行了，发送给本校今天下单的用户");
            return "manager/common_result";
        } else {
            return permissionDeny(map);
        }
    }

    @RequestMapping("addVipMeal")
    public String addVipMeal(@RequestParam int managerId,
                             @RequestParam int schoolId, @RequestParam int pay, @RequestParam int gift, ModelMap map) {
        if (managerService.managerAccess2Privilege(managerId, "xyxx") && managerService.managerAccess2School(managerId, schoolId)) {
            if (gift / pay > 0.1) {
                map.put("result", false);
                map.put("is_url", false);
                map.put("notice", "赠送比例不得大于10%,整数");
            } else {
                map.put("result", true);
                map.put("is_url", false);
                map.put("notice", "添加成功");
                managerService.save(new ChargeVip(pay, gift, schoolId));
            }
            return "manager/common_result";
        } else {
            return permissionDeny(map);
        }
    }

    @RequestMapping("addSchoolPart")
    public String addSchoolPart(@RequestParam int managerId,
                                @RequestParam int schoolId, @RequestParam String pname, @RequestParam int ppay, ModelMap map) {
        if (managerService.managerAccess2Privilege(managerId, "xyxx") && managerService.managerAccess2School(managerId, schoolId)) {
            managerService.save(new SendPart(pname, ppay, schoolId));
            map.put("result", true);
            map.put("is_url", false);
            map.put("notice", "添加成功");
            return "manager/common_result";
        } else {
            return permissionDeny(map);
        }
    }

    @RequestMapping("adBusiness")
    public String adBusiness() {
        return "manager/adBusiness";
    }

    @RequestMapping("helpSendSet")
    public String helpSendSet(@RequestParam int managerId, @RequestParam int schoolId, ModelMap map) {
        map.put("orders", managerService.listHelpSendOrders(schoolId));
        map.put("ess", userService.getAllSendExpresses(schoolId));
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        return "manager/helpsend_set";
    }

    @RequestMapping("addSendExpress")
    public String addSendExpress(@RequestParam int managerId,
                                 @RequestParam int schoolId,
                                 @RequestParam String ename,
                                 @RequestParam String ephone,
                                 @RequestParam int eprice, ModelMap map) {
        if (!managerService.managerAccess2Privilege(managerId, "help_send")) {
            return permissionDeny(map);
        } else {
            if (ephone.length() != 11) {
                map.put("result", false);
                map.put("is_url", true);
                map.put("url", "http://xiaogutou.qdxiaogutou.com/app/helpSendSet.do?managerId=MANAGERID&token=TOKEN&schoolId=SCHOOLID#settings");
                map.put("notice", "手机号" + ephone + "格式不正确");
                return "manager/common_result";
            }
            if (eprice < 1) {
                map.put("result", false);
                map.put("is_url", true);
                map.put("url", "http://xiaogutou.qdxiaogutou.com/app/helpSendSet.do?managerId=MANAGERID&token=TOKEN&schoolId=SCHOOLID#settings");
                map.put("notice", "费用需为正整数");
                return "manager/common_result";
            }
            managerService.save(new SendExpress(ename, eprice, ephone, schoolId));
            map.put("result", false);
            map.put("is_url", true);
            map.put("url", "http://xiaogutou.qdxiaogutou.com/app/helpSendSet.do?managerId=MANAGERID&token=TOKEN&schoolId=SCHOOLID#settings");
            map.put("notice", "添加成功");
            return "manager/common_result";
        }
    }


    @RequestMapping("addExpress")
    public String addExpress(@RequestParam int managerId,
                             @RequestParam String exp_name,
                             @RequestParam int schoolId,
                             @RequestParam int price,
                             @RequestParam int exp_endH,
                             @RequestParam int exp_endM,
                             ModelMap map) {
        System.out.println("mid=" + managerId);
        if (managerService.managerAccess2Privilege(managerId, "xtsz")) {
            Express exp = new Express(exp_name, price, schoolId, exp_endH,exp_endM);
            managerService.save(exp);
            map.put("result", true);
            map.put("is_url", true);
            map.put("url", "http://xiaogutou.qdxiaogutou.com/app/xtsz.do?managerId=MANAGERID&schoolId=SCHOOLID&token=TOKEN#express_config");
            map.put("notice", "添加成功");
            return "manager/common_result";
        } else return permissionDeny(map);
    }

    @RequestMapping("feedback")
    public String feedback(@RequestParam int managerId, ModelMap map) {
        map.put("feedbacks", managerService.listAllFeedBacks());
        map.put("my_fbs", managerService.listMyFeedBacks(managerId));
        map.put("managerId", managerId);
        return "manager/feedback";
    }

    @RequestMapping("resp_feedback")
    public String resp_feedback(@RequestParam int managerId, @RequestParam int fid, ModelMap map) {
        FeedBack fb = managerService.getFeedBackById(fid);
        if (fb == null) return permissionDeny(map);
        if (fb.isResponsed()) {
            map.put("result", false);
            map.put("is_url", true);
            map.put("url", "http://xiaogutou.qdxiaogutou.com/app/feedback.do?managerId=MANAGERID&token=TOKEN");
            map.put("notice", "已经有管理员回复了");
            return "manager/common_result";
        }
        map.put("fb", fb);
        map.put("managerId", managerId);
        return "manager/resp_feedback";
    }

    @RequestMapping("response_feedback")
    public String response_feedback(@RequestParam int managerId,
                                    @RequestParam String resp,
                                    @RequestParam int fid, ModelMap map) {
        FeedBack fb = managerService.getFeedBackById(fid);
        if (fb == null) return permissionDeny(map);
        if (resp.length() < 1) {
            map.put("result", false);
            map.put("is_url", true);
            map.put("url", "http://xiaogutou.qdxiaogutou.com/app/feedback.do?managerId=MANAGERID&token=TOKEN");
            map.put("notice", "内容无效");
            return "manager/common_result";
        }
        if (fb.isResponsed()) {
            map.put("result", false);
            map.put("is_url", true);
            map.put("url", "http://xiaogutou.qdxiaogutou.com/app/feedback.do?managerId=MANAGERID&token=TOKEN");
            map.put("notice", "已经有管理员回复了!");
            return "manager/common_result";
        }

        fb.setResp(resp);
        fb.setRespMid(managerId);
        managerService.update(fb);
        map.put("result", false);
        map.put("is_url", true);
        map.put("url", "http://xiaogutou.qdxiaogutou.com/app/feedback.do?managerId=MANAGERID&token=TOKEN");
        map.put("notice", "回复成功");

        noticeService.respFeedBack(fb.getId(), fb.getOpenid());
        return "manager/common_result";
    }

    @RequestMapping("addSendTime")
    public String addSendTime(@RequestParam int managerId, @RequestParam int schoolId,
                              @RequestParam String notice,
                              @RequestParam Long limit, ModelMap map) {
        if (managerService.managerAccess2Privilege(managerId, "xtsz")) {
            SendTime st = new SendTime(notice, limit, schoolId);
            managerService.save(st);
            map.put("result", true);
            map.put("is_url", true);
            map.put("url", "http://xiaogutou.qdxiaogutou.com/app/xtsz.do?managerId=MANAGERID&schoolId=SCHOOLID&token=TOKEN#sendtime_config");
            map.put("notice", "添加成功");
            managerService.listAllSendTime();
            return "manager/common_result";
        } else return permissionDeny(map);
    }

    @RequestMapping("out_put_order")
    public String out_put_order(@RequestParam int managerId,
                                @RequestParam int schoolId,
                                String only,
                                ModelMap map) {
        if (managerService.managerAccess2Privilege(managerId, "out_put_order")
                && managerService.managerAccess2School(managerId, schoolId)) {
            ArrayList<ExpressOrder> orders =
                    managerService.commonOrderGet(-1, schoolId,
                            -1,
                            TimeFormat.getThisYear(null),
                            TimeFormat.getThisMonth(null),
                            TimeFormat.getThisDay(null), 1);


            ArrayList<Express> expresses = userService.listAllExpresses(schoolId, true);
            map.put("expresses", expresses);
            // 筛选生效
            map.put("orders", selectExrpess(orders,only));
            map.put("schoolId", schoolId);
            map.put("managerId", managerId);
        } else return permissionDeny(map);
        return "manager/out_put_order";
    }

    @RequestMapping("makeOutPutOrders")
    public String makeOutPutOrders(@RequestParam int managerId,
                                   @RequestParam int schoolId,
                                   @RequestParam String[] checked_orders,
                                   ModelMap map) {
        if (checked_orders.length < 1) {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "没有选择任何订单");
            return "manager/common_result";
        }
        String json = transfer2Json(checked_orders);
        OutPutOrders out = new OutPutOrders(json, managerId, schoolId);
        managerService.save(out);
        String url = "http://xiaogutou.qdxiaogutou.com/api/output.do?k=" + out.getSkey();
        map.put("result", true);
        map.put("is_url", false);
        map.put("url", url);
        map.put("notice", "已生成链接,请复制粘贴到需要的地方");
        return "manager/common_result";
    }

    private String transfer2Json(String[] checked_orders) {
        JSONArray arr = new JSONArray();
        for (int i = 0; i < checked_orders.length; i++) {
            arr.add(Integer.parseInt(checked_orders[i]));
        }
        return arr.toJSONString();
    }

    @RequestMapping("school_status")
    public String school_status(@RequestParam int managerId,
                                @RequestParam int schoolId, ModelMap map) {
        School school = managerService.getSchoolById(schoolId);
        SchoolConfigs schoolConfigs = userService.getSchoolConfBySchoolId(schoolId);
        Long moveSum = managerService.getSchoolMoveOrderSum(schoolId);
        Long djSum = managerService.getHelpSendOrderSum(schoolId);
        Long dqSum = managerService.getExpressOrderSum(schoolId);
        ArrayList<ExpressOrder> tdorders = managerService.getTodayExpressOrderSum(schoolId);
        Long todayIncome = managerService.getTodayExpressTodayIncome(schoolId);


        map.put("moveSum", moveSum);
        map.put("djSum", djSum);
        map.put("dqSum", dqSum);
        map.put("todaySum", tdorders.size());//全部
        map.put("todaySum_v", tdorders.size() - getOrderSizeByStatus(tdorders, -1));//有效
        map.put("todaySum_t", getOrderSizeByStatus(tdorders, 0));//待接
        map.put("todaySum_f", getOrderSizeByStatus(tdorders, 1));//待取
        map.put("todaySum_f_f", getOrderSizeByStatus(tdorders, -2));//取件失败的待取
        map.put("todaySum_s", getOrderSizeByStatus(tdorders, 2));//待送
        map.put("todaySum_s_f", getOrderSizeByStatus(tdorders, -3));//送件失败的待送
        map.put("todaySum_c", getOrderSizeByStatus(tdorders, 3));//完成
        map.put("todayIncome", todayIncome);
        map.put("school", school);
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        map.put("config", schoolConfigs);

        return "manager/school_status";
    }

    /**
     * 统计订单中st状态的数量
     */
    private int getOrderSizeByStatus(ArrayList<ExpressOrder> tdorders, int st) {
        int sum = 0;
        for (int i = 0; i < tdorders.size(); i++) {
            if (tdorders.get(i).getOrder_state() == st)
                sum++;
        }
        return sum;
    }


    @RequestMapping("modifypass")
    public String modifypass(@RequestParam int managerId, ModelMap map) {
        map.put("managerId", managerId);
        map.put("manager", managerService.getManagerById(managerId));
        return "manager/modifypass";
    }

    @RequestMapping("modify_pass")
    public String modify_pass(@RequestParam int managerId,
                              @RequestParam String oldpass,
                              @RequestParam String newpass,
                              ModelMap map) {
        Manager manager = managerService.getManagerById(managerId);
        if (manager.getPass().equals(MD5.encryption(oldpass))) {
            manager.setPass(MD5.encryption(newpass));
            managerService.update(manager);
            AppCgi.clearToken(managerId);
            map.put("result", true);
            map.put("is_url", false);
            map.put("notice", "更新完成，请退出此账号重新登录");
            return "manager/common_result";
        } else {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "旧密码不正确");
            return "manager/common_result";
        }
    }


    /**
     * 电子对账
     */
    @RequestMapping("Reconciliation")
    public String Reconciliation(@RequestParam int managerId,
                                 @RequestParam Long date, ModelMap map) {
        date = (date.equals(-1L) ? TimeFormat.getTimesmorning() : date);
        Long perDate = date - 86400000;
        Long outSum = managerService.getOutSum(0L);
        Long todayOutSum = managerService.getOutSum(date);
        Long todayIncome = managerService.getTodayIncome(date);
        List<PayLog> payLogs = managerService.getReconciliationList(date);

        map.put("managerId", managerId);
        map.put("perDate", perDate);
        map.put("date", TimeFormat.format2yyyy_MM_dd(date));
        map.put("outSum", outSum);
        map.put("todayOutSum", todayOutSum);
        map.put("todayIncome", todayIncome);
        map.put("payLogs", payLogs);
        return "manager/reconciliation";
    }

    /**
     * 订单转移
     * */
    @RequestMapping("transfer_order")
    public String transfer_order(@RequestParam int managerId,@RequestParam int schoolId,
                                 String only,
                                 ModelMap map){

            ArrayList<ExpressOrder> orders =
                    managerService.commonOrderGet(managerId, schoolId,
                            -1,
                            TimeFormat.getThisYear(null),
                            TimeFormat.getThisMonth(null),
                            TimeFormat.getThisDay(null), 1);

            ArrayList<Express> expresses = userService.listAllExpresses(schoolId, true);
            map.put("expresses", expresses);
            // 筛选生效
            map.put("orders", selectExrpess(orders,only));
            map.put("friends",managerService.listSchoolManagers(schoolId));
            map.put("schoolId", schoolId);
            map.put("managerId", managerId);

        return "manager/transfer_order";
    }

    /**
     * 处理订单转移
     * */
    @RequestMapping("dealTransferOrder")
    public String dealTransferOrder(@RequestParam int managerId,
                                    @RequestParam int schoolId,
                                    @RequestParam int towho_select,
                                    @RequestParam Integer[] checked_orders,
                                    ModelMap map){
        if (checked_orders.length < 1) {
            map.put("is_url", false);
            map.put("result", false);
            map.put("notice", "没有选择任何订单");
            return "manager/common_result";
        }
        int eff = managerService.dealTransferOrder(managerId,schoolId,towho_select,checked_orders);
        map.put("result", true);
        map.put("is_url", false);
        map.put("notice", "操作已完成,共"+eff+"单");
        return "manager/common_result";

    }


    @RequestMapping("querySchoolMoveOrders")
    public String querySchoolMoveOrders(@RequestParam int schoolId,ModelMap map){
        List<SchoolMoveOrder> orders = managerService.getSchoolMoveOrderBySchoolId(schoolId);
        map.put("orders",orders);
        return "manager/schoolMoveOrders";
    }

}
