package Entity;

import Utils.TimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/12/24.
 */
@Table
@Entity
public class FeedBack {
    @Id
    @GeneratedValue
    private int id;
    private String openid;
    private String content;
    private Long posttime;
    private String strTime;
    private boolean responsed;
    private String resp; //回复消息
    private Long respTime; // 回复时间
    private String strRespTime;
    private int respMid;//处理此单的管理员id

    public FeedBack(String openid, String content, Long posttime) {
        this.openid = openid;
        this.content = content;
        this.posttime = posttime;
        this.strTime = TimeFormat.format(posttime);
        this.resp = "";
        this.responsed = false;
        this.respTime = null;
        this.strRespTime = "";
    }

    public FeedBack() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPosttime() {
        return posttime;
    }

    public void setPosttime(Long posttime) {
        this.posttime = posttime;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }

    public String getResp() {
        return resp;
    }

    public void setResp(String resp) {
        this.resp = resp;
        setRespTime(System.currentTimeMillis());
        setStrRespTime(TimeFormat.format(this.respTime));
        setResponsed(true);
    }

    public Long getRespTime() {
        return respTime;
    }

    public void setRespTime(Long respTime) {
        this.respTime = respTime;
    }

    public String getStrRespTime() {
        return strRespTime;
    }

    public void setStrRespTime(String strRespTime) {
        this.strRespTime = strRespTime;
    }

    public boolean isResponsed() {
        return responsed;
    }

    public void setResponsed(boolean responsed) {
        this.responsed = responsed;
    }

    public int getRespMid() {
        return respMid;
    }

    public void setRespMid(int respMid) {
        this.respMid = respMid;
    }
}
