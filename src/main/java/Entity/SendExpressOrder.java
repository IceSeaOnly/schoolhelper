package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/15.
 * 代寄快递订单
 */
@Entity
@Table
public class SendExpressOrder {
    @Id
    @GeneratedValue
    private int id;
    private String express;
    private int shouldPay;
    private boolean haspay;
    private Date orderTime;
    private String orderKey;
    private String name;
    private String phone;
    private String expPhone;//快递点的电话
    private String address;
    private String open_id;
    private int schoolId;

    public SendExpressOrder(
            String express, String ephone,int shouldPay, boolean haspay, Date orderTime, String orderKey,
            String name, String phone, String address,String openid,int scid) {
        this.expPhone = ephone;
        this.express = express;
        this.shouldPay = shouldPay;
        this.haspay = haspay;
        this.orderTime = orderTime;
        this.orderKey = orderKey;
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.open_id = openid;
        this.schoolId = scid;
    }

    public SendExpressOrder() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public int getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(int shouldPay) {
        this.shouldPay = shouldPay;
    }

    public boolean isHaspay() {
        return haspay;
    }

    public void setHaspay(boolean haspay) {
        this.haspay = haspay;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getExpPhone() {
        return expPhone;
    }

    public void setExpPhone(String expPhone) {
        this.expPhone = expPhone;
    }
}
