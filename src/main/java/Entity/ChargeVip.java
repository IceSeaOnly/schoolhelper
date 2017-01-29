package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/9/30.
 * 会员充值方案
 * pay:充多少
 * gift：送多少
 */
@Entity
@Table
public class ChargeVip {
    @Id
    @GeneratedValue
    private int id;
    private int pay;
    private int gift;

    public ChargeVip() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    public int getGift() {
        return gift;
    }

    public void setGift(int gift) {
        this.gift = gift;
    }
}
