package Controllers.User;

import Entity.*;
import Entity.User.User;
import Service.UserService;
import Utils.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/30.
 * 用户中心
 */
@Controller
@RequestMapping("user")
public class UserCenter {

    @Resource
    UserService userService;


    @RequestMapping("check_send_orders")
    public String check_send_orders(ModelMap map,HttpSession session){
        User user = (User) session.getAttribute("user");
        ArrayList<SendExpress> expresses = userService.getAllSendExpresses(user.getSchoolId());
        map.put("expresses",expresses);
        return "user/check_send_orders";
    }

    public String getSchoolName(int sid){
        ArrayList<School>schools = userService.listAllSchool();
        for (int i = 0; i < schools.size(); i++) {
            if(schools.get(i).getId() == sid)
                return schools.get(i).getSchoolName();
        }
        return "未设置学校";
    }

    @RequestMapping("user_center")
    public String user_center(HttpSession session, ModelMap map){
        User user = (User) session.getAttribute("user");
        user = userService.getUserByOpenId(user.getOpen_id());
        session.setAttribute("user",user);
        map.put("schoolname",getSchoolName(user.getSchoolId()));
        if(user.getUsername().equals("") && user.getPhone().equals("")){
            map.put("should_complete_user_info",true);
        }else map.put("should_complete_user_info",false);

        //return "user/user_center";
        return "user/member_center";
    }

    @RequestMapping("change_my_info")
    public String change_my_info(ModelMap map,HttpSession session){
        User user = (User) session.getAttribute("user");
        ArrayList<SendPart> parts = userService.listAllParts(user.getSchoolId());
        ArrayList<School>schools = userService.listAllSchool();
        map.put("schools",schools);
        map.put("parts",parts);
        return "user/change_my_info";
    }

    @RequestMapping("change_my_school")
    public String change_my_school(ModelMap map,HttpSession session){
        User user = (User) session.getAttribute("user");
        ArrayList<School>schools = userService.listAllSchool();
        map.put("schools",schools);
        return "user/change_my_school";
    }

    @RequestMapping("to_feedback")
    public String to_feedback(){
        return "user/feedback";
    }

    @RequestMapping("feedback")
    public String feedback(ModelMap map,HttpSession session,@RequestParam String content){
        User user = (User) session.getAttribute("user");
        if(content.length() == 0){
            map.put("title","未输入内容!");
            map.put("msg","未输入内容");
            map.put("btnmsg","返回我的");
            map.put("url","/user/user_center.do");
            return "errors/msg";
        }
        userService.sava(new FeedBack(user.getOpen_id(),content,System.currentTimeMillis()));
        map.put("title","反馈成功");
        map.put("msg","感谢您反馈");
        map.put("btnmsg","返回我的");
        map.put("url","/user/user_center.do");
        return "errors/msg";
    }

    @RequestMapping("update_info")
    public String update_info(@RequestParam String name,
                              @RequestParam String phone,
                              @RequestParam int part,
                              @RequestParam String building,
                              HttpSession session){
        User user = (User) session.getAttribute("user");
        user.setUsername(name);
        user.setPhone(phone);
        user.setPart(part);
        user.setBuilding(building);
        userService.update(user);
        return "redirect:/user/user_center.do";
    }
    @RequestMapping("update_school")
    public String update_school(@RequestParam int school,
                              HttpSession session){
        User user = (User) session.getAttribute("user");
        user.setSchoolId(school);
        userService.update(user);
        return "redirect:/user/change_my_info.do";
    }

    @RequestMapping("system_msg")
    public String system_msg(ModelMap map,HttpSession session){
        User user = (User) session.getAttribute("user");
        ArrayList<SysMsg>msgs = userService.getAllSystemMsg(user.getSchoolId());
        map.put("msgs",msgs);
        return "user/system_msg";
    }

    @RequestMapping("charge_vip")
    public String charge_vip(ModelMap map){
        ArrayList<ChargeVip> meals = userService.getAllVipMeals();
        map.put("meals",meals);
        System.out.println("charge vip meals = "+meals.size());
        return "user/charge_vip";
    }

    @RequestMapping("pay_charge_vip")
    public String pay_charge_vip(@RequestParam int setmeal,HttpSession session,ModelMap map){
        ArrayList<ChargeVip> meals = userService.getAllVipMeals();
        ChargeVip cv = getMeal(meals,setmeal);
        if(cv == null) return "errors/illegal";
        ChargeVipOrder cvo = new ChargeVipOrder();
        User user = (User) session.getAttribute("user");
        cvo.setOpenid(user.getOpen_id());
        cvo.setShouldpay(cv.getPay());
        cvo.setShouldgive(cv.getPay()+cv.getGift());
        cvo.setOrderKey(MD5.encryption(user.getOpen_id()+System.currentTimeMillis()));
        cvo.setUsername(user.getUsername());
        cvo.setSchoolId(user.getSchoolId());
        userService.sava(cvo);
        map.put("key",cvo.getOrderKey());
        return "user/pay_charge_vip";
    }

    private ChargeVip getMeal(ArrayList<ChargeVip> meals, int setmeal) {
        for(int i = 0;i < meals.size();i++){
            if(meals.get(i).getId() == setmeal)
                return meals.get(i);
        }
        return null;
    }
}
