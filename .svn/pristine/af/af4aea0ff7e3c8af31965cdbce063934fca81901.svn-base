package Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/13.
 * 用户首页的广告
 */
@Entity
@Table
public class UserIndexAd {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String image_url;
    private String url;
    private Date addTime;
    private int schoolId;

    public UserIndexAd(String title, String image_url, String url, Date addTime,int scid) {
        this.title = title;
        this.image_url = image_url;
        this.url = url;
        this.addTime = addTime;
        this.schoolId = scid;
    }

    public UserIndexAd() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
}
