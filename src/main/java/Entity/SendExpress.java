package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/10/15.
 * 代寄快递的快递分类
 */
@Entity
@Table
public class SendExpress {
    @Id
    @GeneratedValue
    private int id;
    private String expressname;
    private int orderPrice;
    /** 管理人员的手机号*/
    private String phone;
    private int schoolId;

    public SendExpress(String expressname, int orderPrice,String phone,int scid) {
        this.expressname = expressname;
        this.orderPrice = orderPrice;
        this.schoolId = scid;
        this.phone = phone;
    }

    public SendExpress() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpressname() {
        return expressname;
    }

    public void setExpressname(String expressname) {
        this.expressname = expressname;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
