package Controllers.User;

import Entity.ExpressOrder;
import Entity.Manager.AppPushMsg;
import Entity.Manager.Conversation;
import Entity.Manager.Manager;
import Entity.User.User;
import Service.ManagerService;
import Service.NoticeService;
import Service.OrderService;
import Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/30.
 */
@Controller
@RequestMapping("user")
public class Order {

    @Resource
    ManagerService managerService;
    @Resource
    OrderService orderService;
    @Resource
    UserService userService;
    @Resource
    NoticeService noticeService;

    @RequestMapping("my_orders")
    public String my_orders(HttpSession session, ModelMap map){
        User user = (User) session.getAttribute("user");
        orderService.updateOrderOutOfDate(user.getId());
        ArrayList<ExpressOrder> orders = orderService.getOrdersByUserId(user.getId(),user.getSchoolId());
        map.put("orders",orders);
        return "user/my_orders";
    }

    @RequestMapping("order_need_help")
    public String order_need_help(@RequestParam int oid,HttpSession session,ModelMap map){
        User user = (User) session.getAttribute("user");
        ExpressOrder order = userService.getExpressOrderById(user,oid);
        if(order == null){
            return "errors/illigal";
        }

        try {
            Conversation conversation = noticeService.newOrderConversation(user,order,session.getServletContext());
            ArrayList<Manager>ms = managerService.listSchoolManagers(user.getSchoolId());
            for (int i = 0; i < ms.size(); i++) {
                if(ms.get(i).isCs_notice()){
                    AppPushMsg msg = (AppPushMsg) managerService.merge(new AppPushMsg("新客服工单","新的客服工单",ms.get(i).getPhone()));
                    noticeService.pushToAppClient(msg);
                    noticeService.ComstomServiceMessage(managerService.getSchoolName(order.getSchoolId())+"有新的客服工单，请注意处理","用户申请客服","刚刚到达",System.currentTimeMillis(),"请打开app处理客服工单","",ms.get(i).getOpenId());
                }
            }
            noticeService.ComstomServiceMessage("客服会话已生成","订单咨询","正在服务",System.currentTimeMillis(),"订单"+oid+"的客服会话已生成，点击进入",conversation.getUserEnter(),user.getOpen_id());
            return "redirect:"+conversation.getUserEnter();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("msg","系统发生故障");
        return "errors/error";
    }
}
