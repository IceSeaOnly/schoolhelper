package Controllers.User;

import Entity.User.User;
import Service.CloudCoinSevice;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CloudCoinSevice cloudCoinSevice;

    @RequestMapping("makeMyRefereeQRcode")
    public String makeMyRefereeQRcode(HttpSession session, ModelMap map) {
        User u = (User) session.getAttribute("user");
        session.setAttribute("tag", u.getId());
        return "redirect:/api/makeMyRefereeQRcode.do?tag=1&refId=" + u.getId();
    }

    @RequestMapping("myCoinPage")
    public String myCoinPage(HttpSession session, ModelMap map) {
        User u = (User) session.getAttribute("user");
        map.put("records", cloudCoinSevice.getRecordByUserId(u.getId()));
        map.put("user",u);
        return "user/myCoinPage";
    }
}
