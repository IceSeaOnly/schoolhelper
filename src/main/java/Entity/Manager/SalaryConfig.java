package Entity.Manager;

/**
 * Created by Administrator on 2017/3/17.
 * 用来在manager的分红中以json的格式表示分红比例
 */
public class SalaryConfig {
    private int sid; //学校
    private Double rate; //分红比例

    public SalaryConfig(int sid, Double rate) {
        this.sid = sid;
        this.rate = rate;
    }

    public SalaryConfig() {
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
