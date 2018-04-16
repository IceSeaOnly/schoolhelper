package Controllers.User;

import Controllers.SysConfig;
import Entity.User.User;
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
    private static final String Referee = "Referee";

    @Resource
    UserService userService;

    /**
     * refereeId 为推荐人Id，用于被推荐人注册时使用
     */
    @RequestMapping("userlogin")
    public String goto_login(Integer refereeId, HttpSession session, ModelMap map) {
        if (refereeId != null) {
            session.setAttribute(Referee, refereeId);
        }
        map.put("url", SysConfig.ThisServer + "/deal_userlogin.do");
        map.put("server", SysConfig.LoginAddress);
        return "user/weixin_login";
    }

    @RequestMapping("deal_userlogin")
    public String dealUserLogin(@RequestParam String openid, @RequestParam String validate, HttpSession session) {
        String backUrl = (String) session.getAttribute("backUrl");
        if (backUrl == null)
            backUrl = "/user/user_center.do";
        if (UserValidate.validate_user(openid, validate)) {
            Object ref = session.getAttribute(Referee);
            if(ref != null && ref instanceof Integer){
                Integer refId = (Integer) ref;
                session.removeAttribute(Referee);
                User u = userService.getUserByOpenId(openid, refId);
                if(u.getReferee() != refId){ // 老用户打开了推荐人链接
                    return "errors/onlyNewReg4Referee";
                }else{
                    return "redirect:https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MzI4NTY3MjM4Mg==&scene=124#wechat_redirect";
                }
//                session.setAttribute("user", u);
            }else{
                session.setAttribute("user", userService.getUserByOpenId(openid));
            }
            return "redirect:" + backUrl;
        } else
            return "errors/illigal";
    }
}
