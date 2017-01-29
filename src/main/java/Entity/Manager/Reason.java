package Entity.Manager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/1/11.
 * 配送失败的原因
 */
@Entity
@Table
public class Reason {
    public static int FETCH_ERR = 0;
    public static int SEND_ERR = 1;
    @Id
    @GeneratedValue
    private int id;
    private int type; // 原因类型：取件异常、配送异常
    private String why;

    public Reason(int type, String why) {
        this.type = type;
        this.why = why;
    }

    public Reason() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
