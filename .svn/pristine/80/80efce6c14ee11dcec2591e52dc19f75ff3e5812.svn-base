package Controllers.Manager;

import Entity.ExpressOrder;
import Entity.Manager.IndexItemEntity;
import Entity.Manager.Manager;
import Entity.Manager.PrivilegeDist;
import Entity.Manager.Reason;
import Entity.School;
import Service.ManagerService;
import Utils.MD5;
import Utils.SuccessAnswer;
import Utils.TimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/8.
 */
@Controller
@RequestMapping("app")
public class Business {
    @Resource
    ManagerService managerService;

    /**
     * 管理员请求权限列表
     */
    @ResponseBody
    @RequestMapping("list_function")
    public String list_function(@RequestParam int managerId, HttpSession session) {
        managerService.listAllReasons(0);//初始化原因列表，防止nullpoint错误
        ArrayList<IndexItemEntity> fs = managerService.listMyFunctions(managerId);
        String nk = (String) session.getAttribute("Stoken");
        return SuccessAnswer.successWithObject(nk, fs);
    }

    /**
     * 管理员请求所管辖的学校列表
     */
    @ResponseBody
    @RequestMapping("list_school")
    public String list_school(@RequestParam int managerId, HttpSession session) {
        ArrayList<School> fs = managerService.listMySchool(managerId);
        String nk = (String) session.getAttribute("Stoken");
        return SuccessAnswer.successWithObject(nk, fs);
    }


    /**
     * 查看历史订单，包括今天
     */
    @RequestMapping("history_orders")
    public String histroy_orders(@RequestParam int managerId,
                                 @RequestParam int schoolId,
                                 @RequestParam String yyyy_MM_dd,
                                 ModelMap map) {
        yyyy_MM_dd = yyyy_MM_dd.equals("/") ? TimeFormat.format2yyyy_MM_dd(System.currentTimeMillis()) : yyyy_MM_dd;
        Long TS = TimeFormat.data2Timestamp(yyyy_MM_dd);
        int year = TimeFormat.getThisYear(TS);
        int month = TimeFormat.getThisMonth(TS);
        int day = TimeFormat.getThisDay(TS);
        School school = managerService.getSchoolById(schoolId);
        ArrayList<ExpressOrder> orders =
                managerService.commonOrderGet(schoolId, -1, year, month, day, -1);
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
                managerService.commonOrderGet(schoolId, sendTime, year, month, day, orderState);
        map.put("orders", orders);
        map.put("school", school);
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        return "manager/waitting2take_orders";
    }


    /**
     * 管理员查看待取订单
     * waitting_to_fetch_orders.do?managerId=MANAGERID&token=TOKEN&schoolId=SCHOOLID
     */
    @RequestMapping("waitting_to_fetch_orders")
    public String waitting_to_fetch_orders(@RequestParam int managerId,
                                           @RequestParam int schoolId,
                                           ModelMap map) {
        int year = TimeFormat.getThisYear(null);
        int month = TimeFormat.getThisMonth(null);
        int day = TimeFormat.getThisDay(null);
        int orderState = 1; // -1 as default
        int sendTime = -1;// -1 as default
        School school = managerService.getSchoolById(schoolId);
        ArrayList<ExpressOrder> orders = managerService.managerAccess2School(managerId, schoolId) ?
                managerService.commonOrderGet(schoolId, sendTime, year, month, day, orderState) : new ArrayList<ExpressOrder>();

        ArrayList<Reason> reasons = managerService.listAllReasons(Reason.FETCH_ERR);
        map.put("orders", orders);
        map.put("school", school);
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        map.put("reasons", reasons);
        return "manager/waitting_to_fetch_orders";
    }

    /**
     * 管理员配送订单
     * send_express_order.do?managerId=MANAGERID&token=TOKEN&schoolId=SCHOOLID
     */
    @RequestMapping("send_express_order")
    public String send_express_order(@RequestParam int managerId,
                                     @RequestParam int schoolId,
                                     ModelMap map) {
        int year = TimeFormat.getThisYear(null);
        int month = TimeFormat.getThisMonth(null);
        int day = TimeFormat.getThisDay(null);
        int orderState = 2; // -1 as default
        int sendTime = -1;// -1 as default
        School school = managerService.getSchoolById(schoolId);
        ArrayList<ExpressOrder> orders = managerService.managerAccess2School(managerId, schoolId) ?
                managerService.commonOrderGet(schoolId, sendTime, year, month, day, orderState) : new ArrayList<ExpressOrder>();
        ArrayList<Reason> reasons = managerService.listAllReasons(Reason.SEND_ERR);
        map.put("reasons", reasons);
        map.put("orders", orders);
        map.put("school", school);
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
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
                managerService.commonOrderGet(schoolId, sendTime, year, month, day, orderState) : new ArrayList<ExpressOrder>();
        map.put("schoolId", schoolId);
        map.put("managerId", managerId);
        map.put("schoolName", school.getSchoolName());
        map.put("orders", orders);
        return "manager/lzjj_send";
    }

    /**
     * 楼长交接-楼长接收订单
     */

    @RequestMapping("lzjj_receive")
    public String lzjj_receive(@RequestParam int managerId,
                               @RequestParam int schoolId,
                               @RequestParam int orderId,
                               @RequestParam int omid, // 旧的管理员id
                               @RequestParam String key,
                               ModelMap map) {
        ArrayList<ExpressOrder> orders = new ArrayList<ExpressOrder>();
        if (managerService.managerAccess2School(managerId, schoolId)) {
            ExpressOrder it = managerService.getExpressOrderById(orderId);
            if (it != null)
                if (it.getRider_id() == omid && key.equals(it.makeLZJJKey())) {
                    Manager m = managerService.getManagerById(managerId);
                    it.setRider_id(m.getId());
                    it.setRider_name(m.getName());
                    managerService.update(it);
                    orders.add(it);
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
     * */

    @RequestMapping("zjxx")
    public String zjxx(@RequestParam int managerId,ModelMap map){
        if(!managerService.managerAccess2Privilege(managerId,"xxgl"))
            return permissionDeny(map);

        map.put("managerId",managerId);
        return "manager/zjxx";
    }

    /**
     * 增加学校结果
     * */

    @RequestMapping("zjxx_result")
    public String zjxx_result(@RequestParam int managerId,
                              @RequestParam String name_ch,
                              @RequestParam String name_en,
                              ModelMap map){
        name_en = name_en.toLowerCase();
        if(!managerService.managerAccess2Privilege(managerId,"xxgl"))
            return permissionDeny(map);
        if(managerService.schoolExist(name_ch,name_en)){
            map.put("result",false);
            map.put("is_url",false);
            map.put("notice","校名/校名缩写 已经存在，不能重复！");
            return "manager/common_result";
        }else{
            managerService.createSchool(name_ch,name_en);
            map.put("result",true);
            map.put("is_url",false);
            map.put("notice",name_ch+" 创建成功");
            return "manager/common_result";
        }
    }

    /**
     * 管理员权限管理
     * */
    @RequestMapping("privilege_manage_select")
    public String privilege_manage_select(@RequestParam int managerId,
                                          ModelMap map){
        if(!managerService.managerAccess2Privilege(managerId,"qxgl"))
            return permissionDeny(map);
        List<Manager> ms = managerService.listAllManagers();
        map.put("managers",ms);
        map.put("managerId",managerId);
        return "manager/privilege_manage_select";
    }
    /**
     * 管理员权限管理
     * @mid 管理员id
     * */
    @RequestMapping("privilege_manage")
    public String privilege_manage(@RequestParam int managerId,
                                   @RequestParam int mid,
                                          ModelMap map){
        if(!managerService.managerAccess2Privilege(managerId,"qxgl"))
            return permissionDeny(map);
        map.put("managerId",managerId);
        map.put("mid",mid);
        map.put("privileges",managerService.editPrivilege(mid));
        return "manager/privilege_manage";
    }

    /**
     * 学校管理功能选择页
     * */
    @RequestMapping("school_manage_select")
    public String school_manage_select(@RequestParam int managerId,
                                       ModelMap map){
        map.put("managerId",managerId);
        return "manager/school_manage_select";
    }
    /**
     * 管理学校管理员--选择学校
     * */
    @RequestMapping("glxxgly_select")
    public String glxxgly_select(@RequestParam int managerId,
                          ModelMap map){
        if(!managerService.managerAccess2Privilege(managerId,"xxgl")){
            return permissionDeny(map);
        }
        map.put("schools",managerService.lisAllSchool());
        map.put("managerId",managerId);
        return "manager/glxxgly_select";
    }
    /**
     * 管理学校管理员权限
     * */
    @RequestMapping("glxxgly")
    public String glxxgly(@RequestParam int managerId,
                          @RequestParam int sid,
                          ModelMap map){
        if(!managerService.managerAccess2Privilege(managerId,"xxgl")){
            return permissionDeny(map);
        }
        map.put("sid",sid);
        map.put("managers",managerService.lisSchoolManagers(sid));
        map.put("managerId",managerId);
        map.put("schoolName",managerService.getSchoolById(sid).getSchoolName());
        return "manager/glxxgly";
    }

    /**
     * 通用 权限拒绝判定
     * */
    public String permissionDeny(ModelMap map){
        map.put("result",false);
        map.put("is_url",false);
        map.put("notice","无权操作");
        return "manager/common_result";
    }

    /**
     * 原因管理
     * */
    @RequestMapping("reason_manage")
    public String reason_manage(@RequestParam int managerId,
                                ModelMap map){
        if(!managerService.managerAccess2Privilege(managerId,"reason_manage")){
            return permissionDeny(map);
        }

        map.put("reasons_s",managerService.listAllReasons(Reason.SEND_ERR));
        map.put("reasons_f",managerService.listAllReasons(Reason.FETCH_ERR));
        map.put("managerId",managerId);
        return "manager/reason_manage";
    }

    /**
     * 原因管理 -- 增加原因
     * */
    @RequestMapping("reason_manage_add")
    public String reason_manage_add(@RequestParam int managerId,
                                    @RequestParam String type,
                                    @RequestParam String reason,
                                ModelMap map){
        if(!managerService.managerAccess2Privilege(managerId,"reason_manage")){
            return permissionDeny(map);
        }
        managerService.save(new Reason(type.equals("send")?Reason.SEND_ERR:Reason.FETCH_ERR,reason));
        managerService.sp_listAllReason();
        map.put("result",true);
        map.put("notice","添加成功");
        map.put("is_url",false);
        return "manager/common_result";
    }

    /**
     * 管理队伍
     * */
    @RequestMapping("work_group")
    public String work_group(@RequestParam int managerId,
                             ModelMap map){
        if(!managerService.managerAccess2Privilege(managerId,"work_group")){
            return permissionDeny(map);
        }
        map.put("managers",managerService.listAllManagers());
        map.put("managerId",managerId);
        return "manager/work_group";
    }

    @RequestMapping("manager_add")
    public String manager_add(@RequestParam int managerId,
                              @RequestParam String name,
                              @RequestParam String phone,
                              @RequestParam String wxpay,
                              @RequestParam String alipay,
                              ModelMap map
                              ){
        if(!managerService.managerAccess2Privilege(managerId,"work_group")){
            return permissionDeny(map);
        }
        if(managerService.managerPhoneExist(phone.trim())){
            map.put("result",false);
            map.put("is_url",false);
            map.put("notice","手机号已存在，创建失败！");
            return "manager/common_result";
        }
        Manager m = (Manager) managerService.merge(new Manager(name,phone, MD5.encryption("123456"),alipay,wxpay));
        managerService.save(new PrivilegeDist(m.getId(),18));
        map.put("result",true);
        map.put("notice","添加成功,初始密码123456，请注意分配学校和权限");
        map.put("is_url",true);
        map.put("url","school_manage_select.do?managerId=MANAGERID&token=TOKEN");
        return "manager/common_result";
    }
}
