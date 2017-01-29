package Controllers.User;

import Controllers.SysConfig;
import Service.UserService;
import Utils.UserValidate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

/**
 * Created by Administrator on 2016/8/30.
 */
@Controller
@RequestMapping("/")
public class weixin_login {

    @Resource
    UserService userService;

    @RequestMapping("userlogin")
    public String goto_login(ModelMap map){
        map.put("url",SysConfig.ThisServer+"/deal_userlogin.do");
        map.put("server", SysConfig.LoginAddress);
        return "user/weixin_login";
    }

    @RequestMapping("deal_userlogin")
    public String dealUserLogin(@RequestParam String openid,@RequestParam String validate, HttpSession session){
        String backUrl = (String) session.getAttribute("backUrl");
        if(backUrl == null)
            backUrl = "/user/user_center.do";
        if(UserValidate.validate_user(openid,validate)){
            session.setAttribute("user",userService.getUserByOpenId(openid));
            return "redirect:"+backUrl;
        }
        else
            return "errors/illigal";
    }
}
