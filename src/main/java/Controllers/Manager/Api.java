package Controllers.Manager;

import Entity.*;
import Entity.Manager.Conversation;
import Entity.Manager.Log;
import Entity.Manager.OutPutOrders;
import Service.CouponService;
import Service.ManagerService;
import Service.NoticeService;
import Utils.HttpUtils;
import Utils.MD5;
import Utils.Safe;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/30.
 */
@Controller
@RequestMapping("api")
public class Api {

    @Resource
    ManagerService managerService;
    @Resource
    CouponService couponService;
    @Resource
    NoticeService noticeService;

    @RequestMapping("cscb")
    @ResponseBody
    public String CustomServiceCallBack(@RequestParam String notice,
                                        @RequestParam Long cid,
                                        String type,
                                        HttpSession session) {
        if (notice.equals("newMsg")) {
            managerService.save(new Log(8, "会话新消息，cid=" + cid + ",type = " + type, -1));
            if (type.equals("user")) {
                Conversation c = noticeService.getConversationByCid(cid);
                c.setWaitingService(true);
                managerService.update(c);
            } else {
                Conversation c = noticeService.getConversationByCid(cid);
                c.setWaitingService(false);
                managerService.update(c);
            }
        } else if (notice.equals("serverEnd")) {
            managerService.save(new Log(8, "客服结束会话，cid=" + cid, -1));
            managerService.ConversationEnd(cid, 1);
            String appKey = session.getServletContext().getInitParameter("cservice_appkey");
            String secret = session.getServletContext().getInitParameter("cservice_secret");
            JSONObject data = HttpUtils.sendJSONGet("http://cservice.nanayun.cn/manage/qConversation.do", "appKey=" + appKey + "&secret=" + secret + "&cid=" + cid);
            Conversation c = noticeService.getConversationByCid(cid);
            if (c != null) {
                c.setEndText(data.getJSONObject("conversation").getString("endText"));
                managerService.update(c);
            } else
                System.out.println("c is null");

        } else if (notice.equals("userEnd")) {
            managerService.save(new Log(8, "用户结束会话，cid=" + cid, -1));
            managerService.ConversationEnd(cid, 0);
            String appKey = session.getServletContext().getInitParameter("cservice_appkey");
            String secret = session.getServletContext().getInitParameter("cservice_secret");
            JSONObject data = HttpUtils.sendJSONGet("http://cservice.nanayun.cn/manage/qConversation.do", "appKey=" + appKey + "&secret=" + secret + "&cid=" + cid);
            Conversation c = noticeService.getConversationByCid(cid);
            if (c != null) {
                c.setScore(data.getJSONObject("conversation").getInteger("score"));
                managerService.update(c);
            } else
                System.out.println("c is null");

        } else
            managerService.save(new Log(8, notice + "，cid=" + cid, -1));
        return "success";
    }


    /**
     * 校园消息展示接口
     * 无需身份认证
     */
    @RequestMapping("xyxx")
    public String xyxx(@RequestParam int nid, ModelMap map) {
        SysMsg notice = managerService.getShoolNoticeById(nid);
        if (notice == null) {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "该消息已删除");
            return "manager/common_result";
        }
        map.put("schoolName", managerService.getSchoolById(notice.getSchoolId()).getSchoolName());
        map.put("notice", notice);
        return "manager/schoolNotice";
    }

    /**
     * 客服回复反馈
     */
    @RequestMapping("feedback")
    public String feedback(@RequestParam int id, ModelMap map) {
        FeedBack fb = managerService.getFeedBackById(id);
        if (fb == null) {
            return illigalVisit(map);
        }
        map.put("fb", fb);
        return "manager/show_feedback_resp";
    }

    public String illigalVisit(ModelMap map) {
        map.put("result", false);
        map.put("is_url", false);
        map.put("notice", "非法访问");
        return "manager/common_result";
    }

    /**
     *
     * */
    @RequestMapping("output")
    public String output(@RequestParam String k, ModelMap map, HttpServletRequest req) {
        OutPutOrders out = managerService.getOutPutOrderByKey(k);
        if (out == null) {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "非法凭据，ip地址已记录");
            try {
                managerService.log(-1, 11, "查看订单导出非法key值，ip:" + Safe.getIpAddr(req));
            } catch (Exception e) {
                managerService.log(-1, 11, "订单导出记录非法ip出现异常:" + e.getMessage());
            }
            return "manager/common_result";
        }
        if (System.currentTimeMillis() > out.getInvalidTime()) {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "凭据超时失效，无法再次查看");
            return "manager/common_result";
        }
        out.setLastRead(System.currentTimeMillis());
        managerService.update(out);

        if (out == null) {
            return illigalVisit(map);
        }
        ArrayList<Integer> ids = new ArrayList<Integer>();
        JSONArray arr = JSONArray.parseArray(out.getJsonData());
        for (int i = 0; i < arr.size(); i++) {
            ids.add(arr.getInteger(i));
        }
        ArrayList<ExpressOrder> orders = managerService.getExpressOrderByIds(ids);
        if (orders == null || orders.size() < 1) {
            return illigalVisit(map);
        }
        HashMap<String, Integer> omap = new HashMap();
        for (int i = 0; i < orders.size(); i++) {
            if (omap.containsKey(orders.get(i).getExpress()))
                omap.put(orders.get(i).getExpress(), omap.get(orders.get(i).getExpress()) + 1);
            else
                omap.put(orders.get(i).getExpress(), 1);
        }
        map.put("omap", omap);
        map.put("orders", orders);
        return "manager/output_res";
    }

    @RequestMapping("couponcheck")
    public String couponcheck(@RequestParam String gid, ModelMap map) {
        int g = getGid(gid);
        if (g == -1) {
            map.put("result", false);
            map.put("is_url", false);
            map.put("notice", "凭据非法或超时失效，无法查看");
            return "manager/common_result";
        }
        Long sum = couponService.sumAllRecord(g);
        ArrayList<GiftRecord> rs = couponService.listLasted100Record(g);
        map.put("rs", rs);
        map.put("sum", sum);
        return "manager/couponcheck";
    }

    private int getGid(String gid) {
        for (int i = 0; i < 999; i++) {
            if (MD5.encryption(i + "").equals(gid))
                return i;
        }
        return -1;
    }

    /**
     * 客服进入会话
     */
    @RequestMapping("CustomerServiceEnterChat")
    public String CustomerServiceEnterChat(@RequestParam Long cid, HttpSession session) {
        Conversation conversation = noticeService.getConversationByCid(cid);
        Object obj = session.getAttribute("managerId");
        if (null == obj) {
            return null;
        }
        Integer mid = (Integer) obj;
        if (conversation.getServerid() != mid) {
            return null;
        }
        conversation.setWaitingService(false);
        managerService.update(conversation);

        return "redirect:" + conversation.getRealServiceUrl();
    }
}
