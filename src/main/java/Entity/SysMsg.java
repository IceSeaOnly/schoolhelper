package Entity;

import Utils.TimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/1.
 * 面向用户的系统消息
 */
@Entity
@Table
public class SysMsg {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String content;
    private Long addTime;
    private int schoolId;
    private int managerId;
    private String managerName;
    private String timeStr;

    public SysMsg(String title,String content,int scid,int mid,String mname) {
        this.title = title;
        this.content = content;
        this.addTime = System.currentTimeMillis();
        this.timeStr = TimeFormat.format(this.addTime);
        this.schoolId = scid;
        this.managerId = mid;
        this.managerName = mname;
    }

    public SysMsg(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}
