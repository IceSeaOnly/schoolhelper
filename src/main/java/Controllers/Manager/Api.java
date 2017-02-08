package Controllers.Manager;

import Entity.Manager.Conversation;
import Entity.Manager.Log;
import Entity.SysMsg;
import Service.ManagerService;
import Service.NoticeService;
import Utils.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/1/30.
 */
@Controller
@RequestMapping("api")
public class Api {

    @Resource
    ManagerService managerService;
    @Resource
    NoticeService noticeService;

    @RequestMapping("cscb")
    @ResponseBody
    public String CustomServiceCallBack(@RequestParam String notice,
                                        @RequestParam Long cid,
                                        String type,
                                        HttpSession session){
        if(notice.equals("newMsg")){
            managerService.save(new Log(8,"会话新消息，cid="+cid+",type = "+type,-1));
        }else if(notice.equals("serverEnd")){
            managerService.save(new Log(8,"客服结束会话，cid="+cid,-1));
            managerService.ConversationEnd(cid,1);
            String appKey = session.getServletContext().getInitParameter("cservice_appkey");
            String secret = session.getServletContext().getInitParameter("cservice_secret");
            JSONObject data = HttpUtils.sendJSONGet("http://cservice.nanayun.cn/manage/qConversation.do","appKey="+appKey+"&secret="+secret+"&cid="+cid);
            Conversation c = noticeService.getConversationByCid(cid);
            if(c != null){
                c.setEndText(data.getJSONObject("conversation").getString("endText"));
                managerService.update(c);
            }else
                System.out.println("c is null");

        }else if(notice.equals("userEnd")){
            managerService.save(new Log(8,"用户结束会话，cid="+cid,-1));
            managerService.ConversationEnd(cid,0);
            String appKey = session.getServletContext().getInitParameter("cservice_appkey");
            String secret = session.getServletContext().getInitParameter("cservice_secret");
            JSONObject data = HttpUtils.sendJSONGet("http://cservice.nanayun.cn/manage/qConversation.do","appKey="+appKey+"&secret="+secret+"&cid="+cid);
            Conversation c = noticeService.getConversationByCid(cid);
            if(c != null){
                c.setScore(data.getJSONObject("conversation").getInteger("score"));
                managerService.update(c);
            }else
                System.out.println("c is null");

        }else
            managerService.save(new Log(8,notice+"，cid="+cid,-1));
        return "success";
    }


    /**
     * 校园消息展示接口
     * 无需身份认证
     * */
    @RequestMapping("xyxx")
    public String xyxx(@RequestParam int nid, ModelMap map){
        SysMsg notice = managerService.getShoolNoticeById(nid);
        if(notice == null){
            map.put("result",false);
            map.put("is_url",false);
            map.put("notice","该消息已删除");
            return "manager/common_result";
        }
        map.put("notice",notice);
        return "manager/schoolNotice";
    }
}
