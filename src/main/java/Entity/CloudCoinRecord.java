package Entity;

import Utils.TimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by IceSea on 2018/4/18.
 * GitHub: https://github.com/IceSeaOnly
 */
@Entity
@Table
public class CloudCoinRecord {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer userId;
    private Integer many;
    private String remark;
    private Long ts;
    private String tsTime;

    public CloudCoinRecord() {
    }

    public CloudCoinRecord(Integer userId, Integer many, String remark) {
        this.userId = userId;
        this.many = many;
        this.remark = remark;
        setTs(System.currentTimeMillis());
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
        this.tsTime = TimeFormat.format(ts);
    }

    public String getTsTime() {
        return tsTime;
    }

    public void setTsTime(String tsTime) {
        this.tsTime = tsTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMany() {
        return many;
    }

    public void setMany(Integer many) {
        this.many = many;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
