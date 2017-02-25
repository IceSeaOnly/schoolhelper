package Entity;

import Utils.TimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/2/24.
 * 活动领取记录
 */
@Entity
@Table
public class GiftRecord {
    @Id
    @GeneratedValue
    private int id;
    private int uid;
    private int gid;
    private String uname;
    private String openid;
    private Long getTime;
    private String strTime;

    public GiftRecord(int uid, int gid, String uname, String openid) {
        this.uid = uid;
        this.gid = gid;
        this.uname = uname;
        this.openid = openid;
        this.getTime = System.currentTimeMillis();
        this.strTime = TimeFormat.format(this.getGetTime());
    }

    public GiftRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Long getGetTime() {
        return getTime;
    }

    public void setGetTime(Long getTime) {
        this.getTime = getTime;
    }

    public String getStrTime() {
        return strTime;
    }

    public void setStrTime(String strTime) {
        this.strTime = strTime;
    }
}
