package Entity;

import Utils.TimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/3/26.
 */
@Entity
@Table
public class DanMu {
    @Id
    @GeneratedValue
    private int id;
    private boolean priority; //优先级,true开启表示管理员发送的消息
    private int posterId;
    private String info;
    private String img;
    private Long createTime;
    private String timeStr;
    private boolean pass;// 审核标记

    public DanMu(boolean priority, int posterId, String info, String img,boolean pass) {
        this.priority = priority;
        this.posterId = posterId;
        this.info = info;
        this.img = img;
        this.createTime = System.currentTimeMillis();
        this.timeStr = TimeFormat.format(createTime);
        this.pass = pass;
    }

    public DanMu() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public int getPosterId() {
        return posterId;
    }

    public void setPosterId(int posterId) {
        this.posterId = posterId;
    }
}
