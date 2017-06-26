package Service;

import Dao.NoticeDao;
import Entity.ExpressOrder;
import Entity.Manager.AppPushMsg;
import Entity.Manager.Conversation;
import Entity.Manager.Log;
import Entity.Manager.Manager;
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
    ManagerService managerService;
    @Resource
    UserService userService;


    /**
     * 支付成功通知
     */
    public void paySuccess(String detailcontent, String moneysum,
                           String remark, String productName, String openid, String url,ExpressOrder obj) {
        JSONObject data = new JSONObject();
        data.put("first", newItem(detailcontent));
        data.put("orderMoneySum", newItem(moneysum));
        data.put("orderProductName", newItem(productName));
        data.put("Remark", newItem(remark,"#FF0000"));
        JSONArray arr = new JSONArray();
        arr.add(commonTPLMaker("QL9zFOOV9JpJQwP2uFgXLlleebgM34ViORxyXFCxSOA", openid, url, data));
        DistributedTPLSend(arr);
        if(obj != null){
            notifyNewOrder(ManagerService.sendTime2String(obj.getSendtime_id()),obj.getSchoolId());
        }
    }

    /**
     * 推送消息到该校管理员
     * */
    public void notifyNewOrder(String time, int schoolId) {
        ArrayList<Manager>ms = managerService.listSchoolManagers(schoolId);
        for (int i = 0; i < ms.size(); i++) {
            if(ms.get(i).isNewOrderNotice()){
                AppPushMsg msg = (AppPushMsg) managerService.merge(new AppPushMsg(
                        "要求"+time+"配送",
                        "新订单到达",ms.get(i).getPhone()));
                pushToAppClient(msg);
            }
        }
    }

    /**
     * 充值提醒
     * */
    public void chargeSuccess(String detailcontent,String userNo, String moneysum,String jiFen,
                           String chargeType, String remark, String openid, String url) {
        JSONObject data = new JSONObject();
        data.put("first", newItem(detailcontent));
        data.put("keyword1", newItem(userNo));
        data.put("keyword2", newItem(moneysum));
        data.put("keyword3", newItem(jiFen));
        data.put("keyword4", newItem(chargeType));
        data.put("keyword5", newItem(TimeFormat.format(System.currentTimeMillis())));
        data.put("remark", newItem(remark,"#FF0000"));
        JSONArray arr = new JSONArray();
        arr.add(commonTPLMaker("uCSlXj7JbUHbOhjhqvlHCAEYzCFig-rn0O1qYQkQVRE", openid, url, data));
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
     *
     * */
    public void NoticeManagers(int sid,String title,String content,String good,double money){
        ArrayList<Manager>ms = managerService.listSchoolManagers(sid);
        for (int i = 0; i < ms.size(); i++) {
            ReservationService(title,
                    content,
                    good,
                    "刚刚",
                    "等待处理",
                    money+"元",
                    "remark",
                    ms.get(i).getOpenId(),"");
            pushToAppClient(new AppPushMsg(title,content,ms.get(i).getPhone()));
        }
    }
    /**
     * 预付服务派单通知
     */
    public void ReservationService(String first,String content, String good, String serviceDate, String name, String money, String remark, String openid, String url) {
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
        sendToMessageQueue("bone",ds.toJSONString());
    }
    /**
     * 提醒取件失败
     * */
    public void NoticeFetchFailed(String reason,int id,String openid){
        JSONObject data = new JSONObject();
        data.put("first", newItem("您的快递取件失败"));
        data.put("keyword1", newItem("刚刚"));
        data.put("keyword2", newItem(reason));
        data.put("keyword3", newItem(reason));
        data.put("remark", newItem("请点击查看并申请客服服务，以处理此问题"));
        JSONArray arr = new JSONArray();
        String url = "http://xiaogutou.qdxiaogutou.com/user/see_order_detail.do?id="+id;
        arr.add(commonTPLMaker("W-4A3QQP-WgFS4-Ve5V1eG272O1N1Kvzck3aHmQDbEg", openid, url, data));
        DistributedTPLSend(arr);
    }
    /**
     * 日志服务转向分布式
     * */
    public static void DistributedLog(String log){
        sendToMessageQueue("boneLog",log);
    }
    /**
     * 发布到消息队列
     * */
    public static void sendToMessageQueue(String queueName,String msgBody){
        if(account == null){
            account = new CloudAccount("","","");
            client = account.getMNSClient();
        }
        Message message = new Message();
        message.setMessageBody(msgBody);
        CloudQueue queue = client.getQueueRef(queueName);
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
        ArrayList<String> openids = noticeDao.todayOrderedUsers(msg.getSchoolId());
        System.out.println("all openid is "+openids.size());
        threadPublis(openids, url, data);
    }
    /**
     * 发布全校通知
     */
    public void publishNoticeByHand(String title,String businessType,String content,String managerName,String time,
            String key4,ArrayList<String> openids,String url) {
        JSONObject data = new JSONObject();
        data.put("first", newItem(title,"#FF0000"));
        data.put("keyword1", newItem(businessType));
        data.put("keyword2", newItem(managerName));
        data.put("keyword3", newItem(time));
        data.put("keyword4", newItem(key4,"#FF0000"));
        data.put("remark", newItem(content,"#458B00"));
        threadPublis(openids, url, data);
    }

    /**
     * 批量发布
     */
    public void threadPublis(final ArrayList<String> openids, final String url, final JSONObject data) {
        JSONArray arr = new JSONArray();
        for (int i = 0; i < openids.size(); i++) {
            //预警：arr.add(commonTPLMaker("84wlEJrZ9Ak6ikc19gJa8G2FM0j34tf6M4e2NuKoBj0", openids.get(i), url, data));
            // 业务变更通知
            arr.add(commonTPLMaker("_WTYCY90t9g6prdhEaGCx6ugWIueEe0a7OCQjJ7dcMQ", openids.get(i), url, data));
            if(arr.size() > 20){
                DistributedTPLSend(arr);
                arr = new JSONArray();
            }
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
        sendToMessageQueue("bone",data.toJSONString());
    }

    /**
     * 推送到手机端的通知
     * */
    public void pushToAppClient(AppPushMsg msg) {
        JSONObject data = new JSONObject();
        data.put("type","alipush");
        data.put("msg",msg);
        sendToMessageQueue("bone",data.toJSONString());
    }
}
