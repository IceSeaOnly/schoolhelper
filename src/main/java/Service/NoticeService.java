package Service;

import Dao.NoticeDao;
import Entity.ExpressOrder;
import Entity.Manager.Conversation;
import Entity.Manager.Log;
import Entity.User.User;
import Utils.HttpUtils;
import Utils.MD5;
import Utils.TimeFormat;
import WxApi.SendTemplateMsg;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by Administrator on 2016/11/24.
 */
@Service
public class NoticeService{

    @Resource
    NoticeDao noticeDao;
    @Resource
    UserService userService;
    /**
     * 支付成功通知
     */
    public void paySuccess(String detailcontent, String moneysum,
                           String remark, String productName, String openid,String url){
        JSONObject data = new JSONObject();
        data.put("first", newItem(detailcontent));
        data.put("orderMoneySum", newItem(moneysum));
        data.put("orderProductName", newItem(productName));
        data.put("Remark", newItem(remark));
        commonTPLSend("QL9zFOOV9JpJQwP2uFgXLlleebgM34ViORxyXFCxSOA",openid,url,data);
    }

    /**
     * 客服消息类通知
     * */
    public void ComstomServiceMessage(String serviceInfo,String serviceType,String serviceStatus,
                                      Long time,String remark,String url,String openid){
        JSONObject data = new JSONObject();
        data.put("serviceInfo",newItem(serviceInfo,"ff0000"));
        data.put("serviceType",newItem(serviceType));
        data.put("serviceStatus",newItem(serviceStatus));
        data.put("serviceStatus",newItem(serviceStatus));
        data.put("remark",newItem(remark));
        data.put("time",newItem(TimeFormat.format(time)));
        commonTPLSend("uwkkxs620Qo5TRyoPDDmEMkUCCvT64pgZGLMwN0icTA",openid,url,data);
    }
    /**
     * 预付服务派单通知
     */
    public void ReservationService(String first,
                                   String content,
                                   String good,
                                   String serviceDate,
                                   String name,
                                   String money,
                                   String remark,
                                   String openid,String url){
        JSONObject data = new JSONObject();
        data.put("first", newItem(first));
        data.put("Content1", newItem(content));
        data.put("expDate", newItem(serviceDate));
        data.put("Good", newItem(good));
        data.put("name", newItem(name));
        data.put("menu", newItem(money));
        data.put("Remark", newItem(remark));
        commonTPLSend("TvCDzMyZauj2ROxzLvXox3DicuRKTjIS3kqodbEnakw",openid,url,data);
    }




    private JSONObject newItem(Object val){
        JSONObject it = new JSONObject();
        it.put("value",val);
        return it;
    }
    private JSONObject newItem(Object val,String color){
        JSONObject it = new JSONObject();
        it.put("value",val);
        it.put("color",color);
        return it;
    }
    private void commonTPLSend(String tpl,String openid,String url,JSONObject data){
        JSONObject json = new JSONObject();
        json.put("touser", openid);//OPENID
        json.put("template_id",tpl);
        json.put("url", url);
        json.put("topcolor", "#FF0000");
        json.put("data", data);
        SendTemplateMsg stm = new SendTemplateMsg(json);
        new Thread(stm).start();
    }

    /**
     * 创建一个专为该订单服务的客服工单
     * 首先检查该用户未完成工单是否超过5个，超过则需先完成未结单的客服工单
     * */
    public Conversation newOrderConversation(User user, ExpressOrder order, ServletContext servletContext) throws UnsupportedEncodingException {
        Long size = noticeDao.sizeMyUnCompleteConversation(user.getId());
        if(size > 5)
            return null;

        Conversation c = null;
        c = noticeDao.getExistConversation(order.getId());
        if(c != null)
            return c;

        String appKey = servletContext.getInitParameter("cservice_appkey");
        String secret = servletContext.getInitParameter("cservice_secret");
        String ori_url = "http://xiaogutou.qdxiaogutou.com/api/cscb.do";
        JSONObject data = HttpUtils.sendJSONGet(
                "http://cservice.nanayun.cn/manage/newChat.do",
                "appKey="+appKey
                        +"&secret="+secret
                        +"&encodeUrl="+ URLEncoder.encode(ori_url,"UTF-8")
                        +"&lid="+user.getId()
                        +"&cbt="+ MD5.encryption(String.valueOf(System.currentTimeMillis()+"ffff")));

        if(data.getBoolean("result")){
            c = new Conversation(user.getId(),
                    data.getLong("cid"),
                    data.getString("ckey"),
                    order.getId(),
                    data.getString("cbt"),
                    data.getString("utoken"),
                    data.getString("stoken"));
            userService.sava(c);
            JSONObject ans = HttpUtils.sendJSONPost("http://cservice.nanayun.cn/manage/add_msg.do",
                    "appKey="+appKey
                            +"&secret="+secret
                            +"&cid="+data.getLong("cid")
                            +"&word="+URLEncoder.encode(order.toString(),"UTF-8")
                            +"&type=0");
            userService.sava(new Log(9,"插入消息:"+ans.getBoolean("result"),-1));
            return c;
        }else
            return null;
    }
}
