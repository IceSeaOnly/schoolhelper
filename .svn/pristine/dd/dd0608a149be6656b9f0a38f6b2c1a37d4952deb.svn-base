package Entity;

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
    private Date addTime;
    private int schoolId;

    public SysMsg(String title,String content,int scid) {
        this.title = title;
        this.content = content;
        this.addTime = new Date(System.currentTimeMillis());
        this.schoolId = scid;
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

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
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
}
