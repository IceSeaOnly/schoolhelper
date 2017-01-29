package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/5.
 */
@Table
@Entity
public class SchoolMoveOrder {
    @Id
    @GeneratedValue
    private int id;
    private String open_id;
    private String name;
    private String phone;
    private String moveTime;
    private Date orderTime;
    private boolean haspay;
    private boolean deal;
    private String orderKey;
    private int schoolId;

    public SchoolMoveOrder(String name, String phone, String moveTime,String open_id,String orderKey,int scid) {
        this.open_id = open_id;
        this.name = name;
        this.phone = phone;
        this.moveTime = moveTime;
        this.orderTime = new Date(System.currentTimeMillis());
        this.haspay = false;
        this.deal = false;
        this.orderKey = orderKey;
        this.schoolId = scid;
    }

    public SchoolMoveOrder() {
        this.orderTime = new Date(System.currentTimeMillis());
        this.haspay = false;
        this.deal = false;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMoveTime() {
        return moveTime;
    }

    public void setMoveTime(String moveTime) {
        this.moveTime = moveTime;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public boolean isHaspay() {
        return haspay;
    }

    public void setHaspay(boolean haspay) {
        this.haspay = haspay;
    }

    public boolean isDeal() {
        return deal;
    }

    public void setDeal(boolean deal) {
        this.deal = deal;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
}
