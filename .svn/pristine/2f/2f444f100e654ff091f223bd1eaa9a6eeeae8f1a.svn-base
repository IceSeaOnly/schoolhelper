package Entity;

import Utils.TimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2016/9/30.
 * 会员充值订单
 */
@Entity
@Table
public class ChargeVipOrder {
    @Id
    @GeneratedValue
    private int id;
    private int shouldpay;
    private int shouldgive;
    private String orderKey;
    private String openid;
    private String username;
    private boolean has_pay;
    private Date orderTime;

    private int schoolId;

    public ChargeVipOrder() {
        this.has_pay = false;
        this.username = "你的账户";
        this.orderTime = new Date(System.currentTimeMillis());
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public ChargeVipOrder(int shouldpay, int shouldgive, String orderKey, String openid, String username, boolean has_pay, Date orderTime, int schoolId) {
        this.shouldpay = shouldpay;
        this.shouldgive = shouldgive;
        this.orderKey = orderKey;
        this.openid = openid;
        this.username = username;
        this.has_pay = has_pay;
        this.orderTime = orderTime;
        this.schoolId = schoolId;
    }

    public String getTimeString(){
        return TimeFormat.format(orderTime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShouldpay() {
        return shouldpay;
    }

    public void setShouldpay(int shouldpay) {
        this.shouldpay = shouldpay;
    }

    public int getShouldgive() {
        return shouldgive;
    }

    public void setShouldgive(int shouldgive) {
        this.shouldgive = shouldgive;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHas_pay() {
        return has_pay;
    }

    public void setHas_pay(boolean has_pay) {
        this.has_pay = has_pay;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
