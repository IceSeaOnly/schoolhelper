package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/2/24.
 * 免单活动
 */
@Table
@Entity
public class Gift {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int sum;
    private boolean onlyNewCustomer;//仅限新用户

    public Gift() {
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

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public boolean isOnlyNewCustomer() {
        return onlyNewCustomer;
    }

    public void setOnlyNewCustomer(boolean onlyNewCustomer) {
        this.onlyNewCustomer = onlyNewCustomer;
    }
}
