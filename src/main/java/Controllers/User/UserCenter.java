package Controllers.User;

import Entity.*;
import Entity.User.User;
import Service.AvatarService;
import Service.CouponService;
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
    AvatarService avatarService;
    @Resource
    UserService userService;
    @Resource
    CouponService couponService;

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
        map.put("sconfig",userService.getSchoolConfBySchoolId(user.getSchoolId()));
        if(user.getUsername().equals("") && user.getPhone().equals("")){
            map.put("is_url",true);
            map.put("notice","请先完善信息~");
            map.put("url","/user/change_my_school.do");
            return "user/common_result";
        }else map.put("should_complete_user_info",false);

        map.put("freeSum",couponService.howManyCouponIHave(user.getId()));
        //return "user/user_center"; 启用新版首页
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

    public ModelMap emptyInsertError(ModelMap map){
        map.put("title","未输入内容!");
        map.put("msg","未输入内容");
        map.put("btnmsg","返回我的");
        map.put("url","/user/user_center.do");
        return map;
    }
    @RequestMapping("post_danmu")
    public String toPostDanmu(){
        return "user/post_danmu";
    }
    @RequestMapping("post_danmu_forresult")
    public String postDanmu(ModelMap map,@RequestParam String content,HttpSession session){
        if(content.length() == 0){
            emptyInsertError(map);
            return "errors/msg";
        }
        User user = (User) session.getAttribute("user");
        userService.sava(
                new DanMu(false,
                        user.getId(),
                        content,
                        avatarService.getRandomAvatar(user.getId()%2).getImg(),
                        true));
        map.put("title","发射成功");
        map.put("msg","您的弹幕将显示在首页中哦");
        map.put("btnmsg","走你");
        map.put("url","/user/index.do");
        return "errors/msg";
    }
    @RequestMapping("to_feedback")
    public String to_feedback(){
        return "user/feedback";
    }

    @RequestMapping("feedback")
    public String feedback(ModelMap map,HttpSession session,@RequestParam String content){
        if(content.length() == 0){
            emptyInsertError(map);
            return "errors/msg";
        }
        User user = (User) session.getAttribute("user");
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
                              @RequestParam String dormitory,
                              HttpSession session){
        User user = (User) session.getAttribute("user");
        user.setUsername(name);
        user.setPhone(phone);
        user.setPart(part);
        user.setBuilding(building);
        user.setDormitory(dormitory);
        userService.update(user);
        return "redirect:/user/index.do";
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
    public String charge_vip(HttpSession session,ModelMap map){
        User user = (User) session.getAttribute("user");
        ArrayList<ChargeVip> meals = userService.getAllVipMeals(user.getSchoolId());
        map.put("meals",meals);
        System.out.println("charge vip meals = "+meals.size());
        return "user/charge_vip";
    }

    @RequestMapping("pay_charge_vip")
    public String pay_charge_vip(@RequestParam int setmeal,HttpSession session,ModelMap map){
        User user = (User) session.getAttribute("user");
        ArrayList<ChargeVip> meals = userService.getAllVipMeals(user.getSchoolId());
        ChargeVip cv = getMeal(meals,setmeal);
        if(cv == null) return "errors/illegal";
        ChargeVipOrder cvo = new ChargeVipOrder();
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

    @RequestMapping("my_coupons")
    public String my_coupons(HttpSession session,ModelMap map){
        User user = (User) session.getAttribute("user");
        SchoolConfigs sc = userService.getSchoolConfBySchoolId(user.getSchoolId());
        ArrayList<GiftRecord> rs = couponService.getMyCoupons(user.getId());
        ArrayList<GiftRecord> valid = getCoupons(rs,true);
        ArrayList<GiftRecord> invalid = getCoupons(rs,false);
        map.put("valid",valid);
        map.put("invalid",invalid);
        map.put("schoolConfig",sc);
        return "user/my_coupons";
    }

    private ArrayList<GiftRecord> getCoupons(ArrayList<GiftRecord> rs, boolean b) {
        ArrayList<GiftRecord> rss = new ArrayList<GiftRecord>();
        for (int i = 0; i < rs.size(); i++) {
            if(rs.get(i).isValid() == b)
                rss.add(rs.get(i));
        }
        return rss;
    }
}
