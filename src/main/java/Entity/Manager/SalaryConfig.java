package Entity.Manager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/3/8.
 * 工资配置，用来one2one配置学校和管理员的分红关系
 */
@Entity
@Table
public class SalaryConfig {
    @Id
    @GeneratedValue
    private int id;
    private int mid;
    private int sid;
    private double rate;

    public SalaryConfig() {
    }

    public SalaryConfig(int mid, int sid, double rate) {
        this.mid = mid;
        this.sid = sid;
        this.rate = rate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
