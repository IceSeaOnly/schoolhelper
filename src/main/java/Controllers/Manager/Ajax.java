package Controllers.Manager;

import Entity.ExpressOrder;
import Service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/1/9.
 */
@Controller
@RequestMapping("ajax")
public class Ajax {

    @Resource
    ManagerService managerService;

    @RequestMapping("take_order")
    @ResponseBody
    public String ajax_take_order(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            @RequestParam int orderId){
        return String.valueOf(managerService.managerTakeOrder(managerId,schoolId,orderId));
    }

    @RequestMapping("fetch_order")
    @ResponseBody
    public String fetch_order(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            @RequestParam int orderId){
        return String.valueOf(managerService.managerFetchOrder(managerId,schoolId,orderId));
    }

    @RequestMapping("update_courier_number")
    @ResponseBody
    public String update_courier_number(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            @RequestParam int orderId,
            @RequestParam String data){

        return String.valueOf(managerService.update_courier_number(managerId,schoolId,orderId,data));
    }

    /**
     * 更新配送结果
     * */
    @RequestMapping("express_order_send_result")
    @ResponseBody
    public String express_order_send_result(
            @RequestParam int managerId,
            @RequestParam int schoolId,
            @RequestParam int orderId,
            @RequestParam boolean result,
            @RequestParam int reasonId){

        return String.valueOf(
                managerService.updateExpressOrderResultReason(managerId,schoolId,orderId,result? ExpressOrder.SEND_SUCCESS:ExpressOrder.ORDER_SEND_FAILED,reasonId)
        );
    }

    @RequestMapping("privilege_changed")
    @ResponseBody
    public String privilege_changed(@RequestParam int managerId,
                                    @RequestParam int mid,
                                    @RequestParam int privilegeId){
        if(!managerService.managerAccess2Privilege(managerId,"qxgl"))
            return "false";
        if (managerService.managerAccess2PrivilegeId(mid,privilegeId))
            managerService.revokePrivilege(mid,privilegeId);
        else
            managerService.grantPrivilege(mid,privilegeId);
        return "true";
    }
    @RequestMapping("school_manager_changed")
    @ResponseBody
    public String school_manager_changed(@RequestParam int managerId,
                                    @RequestParam int mid,
                                    @RequestParam int sid){
        if (managerService.managerAccess2Privilege(managerId,"xxgl")){
            if(managerService.managerAccess2School(mid,sid))
                managerService.deleteSchoolManager(sid,mid);
            else
                managerService.addSchoolManager(sid,mid);
            return "true";
        }
        return "false";
    }
    @RequestMapping("delete_reason")
    @ResponseBody
    public String delete_reason(@RequestParam int managerId,
                                    @RequestParam int rid){
        if (managerService.managerAccess2Privilege(managerId,"reason_manage")){
            managerService.deleteReason(rid);
            return "true";
        }
        return "false";
    }
        @RequestMapping("delete_manager")
    @ResponseBody
    public String delete_manager(@RequestParam int managerId,
                                    @RequestParam int mid){
        if (managerService.managerAccess2Privilege(managerId,"work_group")){
            managerService.deleteManager(mid);
            return "true";
        }
        return "false";
    }


}
