package Controllers.User;

import Entity.AdGroup;
import Entity.Express;
import Entity.SendPart;
import Entity.User.User;
import Service.UserService;
import Utils.MailSendThread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import static Utils.ExpressOrderHelper.getSendPart;
/**
 * Created by Administrator on 2016/8/30.
 */
@Controller
@RequestMapping("user")
public class Index {

    @Resource
    UserService userService;

    @RequestMapping("index")
    public String index(ModelMap map,HttpSession session){
        User u = (User) session.getAttribute("user");
        String schoolshop = userService.getSchoolShopUrl(u.getSchoolId());
        String news = userService.getLastestNews(u.getSchoolId());
        ArrayList<AdGroup> adGroups = userService.getAdGroups(u);
        map.put("schoolshop",schoolshop);
        map.put("news",news);
        map.put("adGroups",adGroups);
        return "user/index";
    }

    @RequestMapping("computer_help")
    public String computer_help(){
        return "user/computer_help";
    }

    @RequestMapping("take_computer_help_order")
    public String take_computer_help_order(
            @RequestParam String name,
            @RequestParam String qq,
            @RequestParam String helpwhat,
            @RequestParam String time,
            HttpSession session
    ){
        User user = (User) session.getAttribute("user");
        ArrayList<SendPart> parts = userService.listAllParts(user.getSchoolId());

        String title = "电脑服务订单";
        String content = "提交时间:"+new Date(System.currentTimeMillis())+"<br>姓名:"+name+"<br>QQ:"+qq
                +"<br>问题描述："+helpwhat+"<br>期望被联系时间："+time
                +"<br>-------------------------<br>"+"下单微信信息：<br>OpenId:"+user.getOpen_id()+"<br>姓名："+user.getUsername()
                +"<br>手机号："+user.getPhone()+"<br>"
                +getSendPart(user.getPart(),parts,user.getSchoolId()).getPartName()+" "+user.getBuilding()+"<br>";
        String sendTo = "17854258196@139.com";
        new MailSendThread(sendTo,title,content).start();
        return "/user/computer_help_ordered";
    }
}
