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
    private Long outOfDate;//失效时间
    private String strTime;
    private String outOfDateStr;
    private boolean valid; //有效
    private String usedTimeStr;
    private Long useTime;
    private boolean used;
    private int ctype = 0;
    private int clijian = 0;

    public String getOutOfDateStr() {
        return outOfDateStr;
    }

    public void setOutOfDateStr(String outOfDateStr) {
        this.outOfDateStr = outOfDateStr;
    }

    public GiftRecord(int uid, int gid, String uname, String openid, Long exp,int ctype,int clijian) {
        this.clijian = clijian;
        this.ctype = ctype;
        this.uid = uid;
        this.gid = gid;
        this.uname = uname;
        this.openid = openid;
        this.getTime = System.currentTimeMillis();
        this.strTime = TimeFormat.format(this.getGetTime());
        this.valid = true;
        this.outOfDate = getGetTime()+exp;
        this.outOfDateStr = TimeFormat.format(this.outOfDate);
        this.used = false;
    }

    public GiftRecord() {
    }

    public Long getOutOfDate() {
        return outOfDate;
    }

    public void setOutOfDate(Long outOfDate) {
        this.outOfDate = outOfDate;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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

    public String getUsedTimeStr() {
        return usedTimeStr;
    }

    public void setUsedTimeStr(String usedTimeStr) {
        this.usedTimeStr = usedTimeStr;
    }

    public Long getUseTime() {
        return useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int ctype) {
        this.ctype = ctype;
    }

    public int getClijian() {
        return clijian;
    }

    public void setClijian(int clijian) {
        this.clijian = clijian;
    }
}
