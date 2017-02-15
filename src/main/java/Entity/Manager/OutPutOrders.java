package Entity.Manager;

import Utils.MD5;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/2/15.
 * 导出订单信息所用
 */
@Table
@Entity
public class OutPutOrders {
    @Id
    @GeneratedValue
    private int id;
    private String skey;
    private String jsonData;
    private int managerId;
    private Long makeTime; // 产生时间
    private Long lastRead; // 最近一次阅读时间
    private int readTimes; // 阅读次数
    private int schoolId;


    public OutPutOrders(String jsonData, int managerId,int schoolId) {
        this.schoolId = schoolId;
        this.makeTime = System.currentTimeMillis();
        this.jsonData = jsonData;
        this.skey = MD5.encryption(jsonData+getMakeTime());
        this.managerId = managerId;
        this.lastRead = getMakeTime();
        this.readTimes = 0;
    }

    public OutPutOrders() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public Long getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Long makeTime) {
        this.makeTime = makeTime;
    }

    public Long getLastRead() {
        return lastRead;
    }

    public void setLastRead(Long lastRead) {
        this.lastRead = lastRead;
        setReadTimes(getReadTimes()+1);
    }

    public int getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(int readTimes) {
        this.readTimes = readTimes;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
}
