package Entity.Manager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/2/1.
 * 支付筹码
 */
@Table
@Entity
public class ChargingSystem {
    public static int Qtype = 0; //取件所得
    public static int Stype = 1; //送件所得
    public static int Ttype = 2; //楼长交接-送
    public static int Rtype = 3; //楼长交接-收
    public static int Ftype = 4; //分红所得

    @Id
    @GeneratedValue
    private int id;
    private int oid; //订单编号
    private int mid;
    private int money;
    private int mtype; //类型
    private Long time; // 添加时间
    private boolean isValid; // 有效
    private boolean isSettled; //结清
    private String info;

    public ChargingSystem(int mid,int oid, int money, int mtype,String info) {
        this.info = info;
        this.oid = oid;
        this.mid = mid;
        this.money = money;
        this.mtype = mtype;
        this.time = System.currentTimeMillis();
        this.isValid = true;
        this.isSettled = false;
    }

    public ChargingSystem() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMtype() {
        return mtype;
    }

    public void setMtype(int mtype) {
        this.mtype = mtype;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public boolean isSettled() {
        return isSettled;
    }

    public void setSettled(boolean settled) {
        isSettled = settled;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
