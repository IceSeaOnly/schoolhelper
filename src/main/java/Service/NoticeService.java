package Service;

import Dao.NoticeDao;
import Entity.ExpressOrder;
import Entity.Manager.Conversation;
import Entity.Manager.Log;
import Entity.SchoolConfigs;
import Entity.SysMsg;
import Entity.User.User;
import Utils.HttpUtils;
import Utils.MD5;
import Utils.TimeFormat;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


/**
 * Created by Administrator on 2016/11/24.
 */
@Service
public class NoticeService {

    @Resource
    NoticeDao noticeDao;
    @Resource
    UserService userService;


    /**
     * 支付成功通知
     */
    public void paySuccess(String detailcontent, String moneysum,
                           String remark, String productName, String openid, String url) {
        JSONObject data = new JSONObject();
        data.put("first", newItem(detailcontent));
        data.put("orderMoneySum", newItem(moneysum));
        data.put("orderProductName", newItem(productName));
        data.put("Remark", newItem(remark));
        JSONArray arr = new JSONArray();
        arr.add(commonTPLMaker("QL9zFOOV9JpJQwP2uFgXLlleebgM34ViORxyXFCxSOA", openid, url, data));
        DistributedTPLSend(arr);
    }

    /**
     * 客服消息类通知
     */
    public void ComstomServiceMessage(String serviceInfo, String serviceType, String serviceStatus,
                                      Long time, String remark, String url, String openid) {
        JSONObject data = new JSONObject();
        data.put("serviceInfo", newItem(serviceInfo, "ff0000"));
        data.put("serviceType", newItem(serviceType));
        data.put("serviceStatus", newItem(serviceStatus));
        data.put("serviceStatus", newItem(serviceStatus));
        data.put("remark", newItem(remark));
        data.put("time", newItem(TimeFormat.format(time)));
        JSONArray arr = new JSONArray();
        arr.add(commonTPLMaker("uwkkxs620Qo5TRyoPDDmEMkUCCvT64pgZGLMwN0icTA", openid, url, data));
        DistributedTPLSend(arr);
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
                                   String openid, String url) {
        JSONObject data = new JSONObject();
        data.put("first", newItem(first));
        data.put("Content1", newItem(content));
        data.put("expDate", newItem(serviceDate));
        data.put("Good", newItem(good));
        data.put("name", newItem(name));
        data.put("menu", newItem(money));
        data.put("Remark", newItem(remark));
        JSONArray arr = new JSONArray();
        arr.add(commonTPLMaker("TvCDzMyZauj2ROxzLvXox3DicuRKTjIS3kqodbEnakw", openid, url, data));
        DistributedTPLSend(arr);
    }


    private JSONObject newItem(Object val) {
        JSONObject it = new JSONObject();
        it.put("value", val);
        return it;
    }

    private JSONObject newItem(Object val, String color) {
        JSONObject it = new JSONObject();
        it.put("value", val);
        it.put("color", color);
        return it;
    }

    private JSONObject commonTPLMaker(String tpl, String openid, String url, JSONObject data) {
        JSONObject json = new JSONObject();
        json.put("touser", openid);//OPENID
        json.put("template_id", tpl);
        json.put("url", url);
        json.put("data", data);
        return json;
    }

    private static CloudAccount account = null;
    private static MNSClient client = null;
    private static void DistributedTPLSend(JSONArray arr){
        JSONObject ds = new JSONObject();
        ds.put("type","wxnotice");
        ds.put("datas",arr);
        if(account == null){
            account = new CloudAccount(PassConfig.accessKey, PassConfig.secret, PassConfig.MNSurl);
            client = account.getMNSClient();
        }
        Message message = new Message();
        message.setMessageBody(ds.toJSONString());
        CloudQueue queue = client.getQueueRef("bone");
        queue.putMessage(message);
    }
    /**
     * 日志服务转向分布式
     * */
    public static void DistributedLog(String log){
        if(account == null){
            account = new CloudAccount(PassConfig.accessKey, PassConfig.secret, PassConfig.MNSurl);
            client = account.getMNSClient();
        }
        Message message = new Message();
        message.setMessageBody(log);
        CloudQueue queue = client.getQueueRef("boneLog");
        queue.putMessage(message);
    }
    /**
     * 创建一个专为该订单服务的客服工单
     * 首先检查该用户未完成工单是否超过5个，超过则需先完成未结单的客服工单
     */
    public Conversation newOrderConversation(User user, ExpressOrder order, ServletContext servletContext) throws UnsupportedEncodingException {
        Long size = noticeDao.sizeMyUnCompleteConversation(user.getId());
        if (size > 5)
            return null;

        Conversation c = null;
        c = noticeDao.getExistConversation(order.getId());
        if (c != null)
            return c;

        String appKey = servletContext.getInitParameter("cservice_appkey");
        String secret = servletContext.getInitParameter("cservice_secret");
        String ori_url = "http://xiaogutou.qdxiaogutou.com/api/cscb.do";
        JSONObject data = HttpUtils.sendJSONGet(
                "http://cservice.nanayun.cn/manage/newChat.do",
                "appKey=" + appKey
                        + "&secret=" + secret
                        + "&encodeUrl=" + URLEncoder.encode(ori_url, "UTF-8")
                        + "&lid=" + user.getId()
                        + "&cbt=" + MD5.encryption(String.valueOf(System.currentTimeMillis() + "ffff")));

        if (data.getBoolean("result")) {
            c = new Conversation(user.getId(),
                    user.getSchoolId(),
                    data.getLong("cid"),
                    data.getString("ckey"),
                    order.getId(),
                    data.getString("cbt"),
                    data.getString("utoken"),
                    data.getString("stoken"));
            userService.sava(c);
            JSONObject ans = HttpUtils.sendJSONPost("http://cservice.nanayun.cn/manage/add_msg.do",
                    "appKey=" + appKey
                            + "&secret=" + secret
                            + "&cid=" + data.getLong("cid")
                            + "&word=" + URLEncoder.encode(order.toString(), "UTF-8")
                            + "&type=0");
            userService.sava(new Log(9, "插入消息:" + ans.getBoolean("result"), -1));
            return c;
        } else
            return null;
    }

    public Conversation getConversationIfWait(int managerId, int csid, int schoolId) {
        int eff = noticeDao.getConversationIfWait(managerId, csid, schoolId);
        System.out.println("eff=" + eff);
        if (eff > 0) {
            return getConversationById(csid);
        }
        return null;
    }

    public Conversation getConversationById(int csid) {
        return noticeDao.getConversationById(csid);
    }

    public Conversation getConversationByCid(Long cid) {
        return noticeDao.getConversationByCid(cid);
    }

    /**
     * 发布全校通知
     */
    public void publishNotice(SysMsg msg) {
        String url = "http://xiaogutou.qdxiaogutou.com/api/xyxx.do?nid=" + msg.getId();
        JSONObject data = new JSONObject();
        data.put("keyword1", newItem(msg.getTitle()));
        data.put("keyword2", newItem(msg.getManagerName()));
        data.put("keyword3", newItem(msg.getTimeStr()));
        data.put("keyword4", newItem("点击查看详细内容"));
        data.put("remark", newItem("点击查看详细内容"));
        ArrayList<String> openids = noticeDao.listSchoolAllOpenIds(msg.getSchoolId());
        System.out.println("all openid is "+openids.size());
        threadPublis(openids, url, data);
    }

    /**
     * 批量发布
     */
    public void threadPublis(final ArrayList<String> openids, final String url, final JSONObject data) {
        JSONArray arr = new JSONArray();
        for (int i = 0; i < openids.size(); i++) {
            arr.add(commonTPLMaker("84wlEJrZ9Ak6ikc19gJa8G2FM0j34tf6M4e2NuKoBj0", openids.get(i), url, data));
        }
        DistributedTPLSend(arr);
    }


    public void respFeedBack(int id, String openid) {
        JSONObject data = new JSONObject();
        data.put("first", newItem("客服回复了您的反馈"));
        data.put("keyword1", newItem("客服反馈"));
        data.put("keyword2", newItem(TimeFormat.format(System.currentTimeMillis())));
        data.put("remark", newItem("点击查看反馈"));
        JSONArray arr = new JSONArray();
        String url = "http://xiaogutou.qdxiaogutou.com/api/feedback.do?id="+id;
        arr.add(commonTPLMaker("UJJCnJjz7oqaiewVmLwJMiagrK8o5Zf4xsvxq9FGdmk", openid, url, data));
        DistributedTPLSend(arr);
    }


    public void refund(int id, int user_id, int shouldPay, String s) {
        User user = userService.getUserById(user_id);
        if(user == null) return;
        JSONObject data = new JSONObject();
        data.put("first", newItem("您的"+id+"号订单已经完成退款"));
        data.put("reason", newItem("管理员执行退款操作"));
        data.put("refund", newItem((double)shouldPay/100+"元"));
        data.put("remark", newItem(s));
        JSONArray arr = new JSONArray();
        String url = "http://xiaogutou.qdxiaogutou.com/user/user_center.do";
        arr.add(commonTPLMaker("aysTZUEIPhvcGsaIViS75qMY0u9OfCQ0EfjHpRSAA10", user.getOpen_id(), url, data));
        DistributedTPLSend(arr);
    }

    public void CommonSMSSend(String tpl,String phone,JSONObject para){
        JSONObject data = new JSONObject();
        data.put("type","sms");
        data.put("phone",phone);
        data.put("param",para);
        data.put("tpl",tpl);
        if(account == null){
            account = new CloudAccount(PassConfig.accessKey, PassConfig.secret, PassConfig.MNSurl);
            client = account.getMNSClient();
        }
        Message message = new Message();
        message.setMessageBody(data.toJSONString());
        CloudQueue queue = client.getQueueRef("bone");
        queue.putMessage(message);
    }
}
