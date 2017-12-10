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
    private int batchNo; //批次号，表示一个活动
    private int schoolId; // 此优惠的可用学校,-1表示不限学校
    private String schoolName;
    private String name;
    private String notice; //领取后的提示消息
    private Long expiries;//到期时间，微秒
    private int sum;
    private int ctype = 0; // 0 for 免单券，1 for 立减券
    private int lijian = 0; // 立减多少
    private boolean onlyNewCustomer;//仅限新用户

    public Gift() {
    }

    public int getLijian() {
        return lijian;
    }

    public void setLijian(int lijian) {
        this.lijian = lijian;
    }

    public int getCtype() {
        return ctype;
    }

    public void setCtype(int type) {
        this.ctype = type;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Long getExpiries() {
        return expiries;
    }

    public void setExpiries(Long expiries) {
        this.expiries = expiries;
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

    public int getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(int batchNo) {
        this.batchNo = batchNo;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
