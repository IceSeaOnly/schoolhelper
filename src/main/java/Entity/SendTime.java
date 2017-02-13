package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/11/14.
 * 配送时间类
 */
@Table
@Entity
public class SendTime {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private Long s_limit;
    private Long curSum;
    private int schoolId;
    private boolean available;//删除后变为不可用

    public SendTime(String name, Long s_limit,int schoolId) {
        this.name = name;
        this.s_limit = s_limit;
        this.schoolId = schoolId;
        this.available = true;
    }

    public SendTime() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getS_limit() {
        return s_limit;
    }

    public void setS_limit(Long s_limit) {
        this.s_limit = s_limit;
    }

    public Long getCurSum() {
        return curSum;
    }

    public void setCurSum(Long curSum) {
        this.curSum = curSum;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
