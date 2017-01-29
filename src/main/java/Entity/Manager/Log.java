package Entity.Manager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/1/13.
 */
@Entity
@Table
public class Log {

    @Id
    @GeneratedValue
    private int id;
    private int logId; // 类型码
    private String status; // 附加信息
    private int managerId;
    private Long time;

    public Log(int logId, String status, int managerId) {
        this.logId = logId;
        this.status = status;
        this.managerId = managerId;
        this.time = System.currentTimeMillis();
    }

    public Log() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
