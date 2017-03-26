package Controllers.User;

import Entity.*;
import Entity.User.User;
import Service.DanMuService;
import Service.NoticeService;
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
    NoticeService noticeService;
    @Resource
    UserService userService;
    @Resource
    DanMuService danMuService;

    @RequestMapping("index")
    public String index(ModelMap map,HttpSession session){
        User u = (User) session.getAttribute("user");
        String schoolshop = userService.getSchoolShopUrl(u.getSchoolId());
        ArrayList<AdGroup> adGroups = userService.getAdGroups(u);
        map.put("schoolshop",schoolshop);
        map.put("adGroups",adGroups);
        map.put("danmus",danMuService.query(0));
        return "user/index";
    }

    @RequestMapping("computer_help")
    public String computer_help(){
        return "user/computer_help";
    }



    /**
     * 免单活动通用处理机制
     * */
    @RequestMapping("free_activity")
    public String free_activity(@RequestParam int gid, ModelMap map,HttpSession session){
        map.put("is_url",false);
        User u = (User) session.getAttribute("user");
        Gift gift = userService.getGiftById(gid);

        if(gift == null){
            map.put("result",false);
            map.put("notice","非法参数");
        }else if(gift.isOnlyNewCustomer() && u.getOrder_sum() != 0){
            map.put("result",true);
            map.put("notice","这个优惠仅限新用户哦~");
        }else if(gift.getSum() > 0){
            boolean exist = userService.giftExist(gid,u.getId());
            if(!exist){
                gift.setSum(gift.getSum()-1);
                userService.update(gift); //名额建减一
                userService.sava(new GiftRecord(u.getId(),gid,u.getUsername(),u.getOpen_id()));//写入领取记录
                u.setFreeSum(u.getFreeSum()+1);//免单加1
                userService.update(u);
                map.put("result",true);
                map.put("notice","领取成功，快去下单吧！");

                noticeService.chargeSuccess("恭喜您在"+gift.getName()+"活动中获得一张免单券",""+u.getId(),"免单券1张","0","免单券","免单券领取成功，快去下单吧！",u.getOpen_id(),"");
            }else{
                map.put("result",true);
                map.put("notice","您已经领取过了，快去下单吧！");
            }
        }else{
            map.put("result",false);
            map.put("notice","来晚一步，今天的活动名额已经没啦！明天再来看看吧！");
        }
        return "user/common_result";
    }
}
