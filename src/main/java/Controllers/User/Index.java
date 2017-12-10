package Controllers.User;

import Entity.*;
import Entity.User.User;
import Service.NoticeService;
import Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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
//    @Resource
//    DanMuService danMuService;

    @RequestMapping("index")
    public String index(ModelMap map, HttpSession session) {
        User u = (User) session.getAttribute("user");
        String schoolshop = userService.getSchoolShopUrl(u.getSchoolId());
        ArrayList<AdGroup> adGroups = userService.getAdGroups(u);
        map.put("schoolshop", schoolshop);
        map.put("adGroups", adGroups);
        //map.put("danmus",danMuService.query(0));
        return "user/index";
    }

    @RequestMapping("computer_help")
    public String computer_help() {
        return "user/computer_help";
    }


    /**
     * 优惠券领取逻辑
     * gid为批次号
     */
    @RequestMapping("free_activity")
    public String free_activity(@RequestParam int gid, Integer schoolId, ModelMap map, HttpSession session) {
        map.put("is_url", false);
        User u = (User) session.getAttribute("user");
        Gift gift = null;

        ArrayList<Gift> gifts = userService.getGiftById(gid);
        if (gifts.size() > 1 && schoolId == null) {
            // 先选择学校
            map.put("schools", userService.listAllSchool());
            map.put("gid", gid);
            return "user/select_school_4_gift";
        }

        if (gifts == null || gifts.size() == 0) {
            map.put("result", false);
            map.put("notice", "非法参数");
        }

        gift = getGift4School(gifts, schoolId);

        if (gift.isOnlyNewCustomer() && u.getOrder_sum() != 0) {
            map.put("result", true);
            map.put("notice", "这个优惠仅限新用户哦~");
        } else if (gift.getSum() > 0) {
            boolean exist = userService.giftExist(gid, u.getId());
            if (!exist) {
                gift.setSum(gift.getSum() - 1);
                userService.update(gift); //名额减一
                userService.sava(new GiftRecord(u.getId(), gid, gift.getSchoolId(), gift.getSchoolName(), u.getUsername(), u.getOpen_id(), gift.getExpiries(), gift.getCtype(), gift.getLijian()));//写入领取记录
//                u.setFreeSum(u.getFreeSum()+1);//免单加1
//                userService.update(u);
                map.put("result", true);
                map.put("notice", gift.getName());

                noticeService.chargeSuccess("恭喜您在" + gift.getName() + "活动中获得一张" + (gift.getCtype() == 0 ? "免单" : "立减") + "券", "" + u.getId(), (gift.getCtype() == 0 ? "免单" : "立减") + "券1张", "0", "免单券", (gift.getCtype() == 0 ? "免单" : "立减") + "券领取成功，快去下单吧！" + gift.getNotice(), u.getOpen_id(), "");
            } else {
                map.put("result", false);
                map.put("notice", "您已经领取过了，不要太贪心哦");
            }
        } else {
            map.put("result", false);
            map.put("notice", "来晚一步，今天的活动名额已经没啦！明天再来看看吧！");
        }
        map.put("is_url", "/user/user_center.do");
        return "user/gift_result";
    }

    /**
     * 获取对应学校的优惠券
     */
    private Gift getGift4School(ArrayList<Gift> gifts, Integer schoolId) {
        if (schoolId == null || gifts.size() == 1) {
            return gifts.get(0);
        }
        for (Gift g : gifts) {
            if (g.getSchoolId() == schoolId) {
                return g;
            }
        }
        return gifts.get(0);
    }
}
