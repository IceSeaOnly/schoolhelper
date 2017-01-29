package Controllers.User;

import Entity.ExpressOrder;
import Entity.User.User;
import Service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/30.
 */
@Controller
@RequestMapping("user")
public class Order {

    @Resource
    OrderService orderService;

    @RequestMapping("my_orders")
    public String my_orders(HttpSession session, ModelMap map){
        User user = (User) session.getAttribute("user");
        ArrayList<ExpressOrder> orders = orderService.getOrdersByUserId(user.getId(),user.getSchoolId());
        map.put("orders",orders);
        return "user/my_orders";
    }
}
