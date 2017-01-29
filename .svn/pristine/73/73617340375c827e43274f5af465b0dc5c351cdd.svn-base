package Entity;

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

    public FeedBack(String openid, String content, Long posttime) {
        this.openid = openid;
        this.content = content;
        this.posttime = posttime;
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
}
