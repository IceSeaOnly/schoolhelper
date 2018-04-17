package Controllers.User;

import Entity.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


/**
 * Created by IceSea on 2018/4/15.
 * GitHub: https://github.com/IceSeaOnly
 */
@RequestMapping("user")
@Controller
public class RefereeController {

    @RequestMapping("makeMyRefereeQRcode")
    public String makeMyRefereeQRcode(HttpSession session, ModelMap map) {
        User u = (User) session.getAttribute("user");
        session.setAttribute("tag",u.getId());
        return "redirect:/api/makeMyRefereeQRcode.do?tag=1&refId=" + u.getId();
    }
}
