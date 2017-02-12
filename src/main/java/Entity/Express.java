package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/31.
 * 快递名字
 */
@Entity
@Table
public class Express{
    @Id
    @GeneratedValue
    private int id;
    private String expressName;
    private int sendPrice;
    private int hour_out_of_service;
    private int minute_out_of_service;
    private boolean available;
    /** 该快递目前已经下的订单*/
    private Long curSum;
    /** 快递属于哪个学校*/
    private int schoolId;

    public Express(String expressName, int sendPrice,int scid) {
        this.expressName = expressName;
        this.sendPrice = sendPrice;
        this.hour_out_of_service = 99;
        this.minute_out_of_service = 99;
        this.available = true;
        this.schoolId = scid;
    }

    public Express() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public int getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(int sendPrice) {
        this.sendPrice = sendPrice;
    }

    public int getHour_out_of_service() {
        return hour_out_of_service;
    }

    public void setHour_out_of_service(int hour_out_of_service) {
        this.hour_out_of_service = hour_out_of_service;
    }

    public int getMinute_out_of_service() {
        return minute_out_of_service;
    }

    public void setMinute_out_of_service(int minute_out_of_service) {
        this.minute_out_of_service = minute_out_of_service;
    }

    public Long getCurSum() {
        return curSum;
    }

    public void setCurSum(Long curSum) {
        this.curSum = curSum;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
}
