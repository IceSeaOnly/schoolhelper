package Controllers.Manager;

import Entity.ExpressOrder;
import Entity.SchoolConfigs;
import Entity.User.User;
import Service.NoticeService;
import Service.OrderService;
import Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by IceSea on 2018/4/15.
 * GitHub: https://github.com/IceSeaOnly
 */
@Controller
@RequestMapping("app")
public class ReferreManager {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;

    /**
     * 新用户下单给推荐人返水
     */
    @RequestMapping("payReferreGift")
    public String payReferreGift(@RequestParam int managerId,
                                 @RequestParam Integer schoolId,
                                 ModelMap map) {

        int count = 0;
        int sum = 0;
        List<ExpressOrder> ls = orderService.getRefereeOrderBySchool(schoolId, 0);
        if (ls != null && ls.size() > 0) {
            SchoolConfigs sc = userService.getSchoolConfBySchoolId(schoolId);
            for (ExpressOrder order : ls) {
                User user = userService.getUserById(order.getUser_id());
                if (user.getReferee() > 0) {
                    count++;
                    sum += sc.getRefereeGift();
                    userService.userAddCloudCoin(user.getReferee(), sc.getRefereeGift());
                    System.out.println(String.format("manger %d pay cloud coin %d to user %d as referee gift of order %d",
                            managerId, sc.getRefereeGift(), user.getReferee(), order.getId()));
                }
            }
        }

        map.put("url",String.format("共结算 %d 个订单，支付了 %d 个云币。",count,sum));
        map.put("is_url",false);
        map.put("result",true);
        map.put("notice",String.format("共结算 %d 个订单，支付了 %d 个云币。",count,sum));
        System.out.println(String.format("共结算 %d 个订单，支付了 %d 个云币。",count,sum));
        return "manager/common_result";
    }
}
