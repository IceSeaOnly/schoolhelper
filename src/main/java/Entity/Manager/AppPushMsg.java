package Entity.Manager;

import Utils.MD5;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/3/18.
 * 推送到app的消息
 */
@Entity
@Table
public class AppPushMsg {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String content;
    private String toWho;
    private String pass;
    private Long time;

    public AppPushMsg() {
    }

    public AppPushMsg(String title, String content, String toWho) {
        this.title = title;
        this.content = content;
        this.toWho = toWho;
        this.pass = MD5.sortPass();
        this.time = System.currentTimeMillis();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToWho() {
        return toWho;
    }

    public void setToWho(String toWho) {
        this.toWho = toWho;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
