package Entity.Manager;

import Utils.TimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/1/30.
 * 客服工单登记表
 */
@Table
@Entity
public class Conversation {
    @Id
    @GeneratedValue
    private int id;
    private int userid;
    private int serverid;
    private int schoolId;
    private String userToken;
    private String serverToken;
    private String callBcakToken;
    private int orderId;
    private Long cid;
    private String ckey;
    private boolean isServerEnd;
    private boolean isUserEnd;
    private boolean waitingService; //标记是否有新消息
    private int score;
    private String endText;
    private Long createTime;

    public String getUserEnter(){
        return "http://cservice.nanayun.cn/service/enterChat.do?ckey="+ckey+"&uid="+userid+"&cid="+cid+"&token="+userToken;
    }
    public String getServerEnter(){
        return "/api/CustomerServiceEnterChat.do?cid="+cid;
    }
    public String getRealServiceUrl(){
        return "http://cservice.nanayun.cn/service/enterChat.do?ckey="+ckey+"&uid="+serverid+"&cid="+cid+"&token="+serverToken;
    }
    public String getStatus(){
        if(isServerEnd && isUserEnd)
            return "已结束";
        if(isUserEnd)
            return "你已结单";
        if(isServerEnd)
            return "客服已结单";
        return "进行中";
    }
    public Conversation(int userid,int schoolId, Long cid, String ckey,int oid,String callBackToken,String utoken,String stoken) {
        this.orderId = oid;
        this.schoolId = schoolId;
        this.userid = userid;
        this.serverid = -1;
        this.cid = cid;
        this.ckey = ckey;
        this.isServerEnd = false;
        this.isUserEnd = false;
        this.score = -1;
        this.endText = null;
        this.callBcakToken = callBackToken;
        this.userToken = utoken;
        this.serverToken = stoken;
        this.createTime = System.currentTimeMillis();
    }


    public Conversation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getServerid() {
        return serverid;
    }

    public void setServerid(int serverid) {
        this.serverid = serverid;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getCkey() {
        return ckey;
    }

    public void setCkey(String ckey) {
        this.ckey = ckey;
    }

    public boolean isServerEnd() {
        return isServerEnd;
    }

    public void setServerEnd(boolean serverEnd) {
        isServerEnd = serverEnd;
    }

    public boolean isUserEnd() {
        return isUserEnd;
    }

    public void setUserEnd(boolean userEnd) {
        isUserEnd = userEnd;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getEndText() {
        return endText;
    }

    public void setEndText(String endText) {
        this.endText = endText;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getServerToken() {
        return serverToken;
    }

    public void setServerToken(String serverToken) {
        this.serverToken = serverToken;
    }

    public String getCallBcakToken() {
        return callBcakToken;
    }

    public void setCallBcakToken(String callBcakToken) {
        this.callBcakToken = callBcakToken;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public String getCreateTime2String() {
        return TimeFormat.format(createTime);
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public boolean isWaitingService() {
        return waitingService;
    }

    public void setWaitingService(boolean waitingService) {
        this.waitingService = waitingService;
    }
}
