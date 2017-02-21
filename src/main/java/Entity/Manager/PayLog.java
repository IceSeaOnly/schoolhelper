package Entity.Manager;

import Utils.MD5;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/2/1.
 * 结算日志
 */
@Table
@Entity
public class PayLog {
    @Id
    @GeneratedValue
    private int id;

    private String openId;
    private Long tradeNo;
    private int amount;
    private String pdesc;
    private String passwd;
    private String mName;

    // 以下是系统参考参数
    private Long createTime;
    private Long completeTime;
    private boolean hasPay;
    private int mid; // 管理员id
    private Long payTime;

    public PayLog(String name,String openId, Long tradeNo, int amount, String pdesc, String passwd,int mid) {
        this.mName = name;
        this.openId = openId;
        this.tradeNo = tradeNo;
        this.amount = amount;
        this.pdesc = pdesc;
        this.passwd = passwd;
        this.createTime = System.currentTimeMillis();
        this.completeTime = null;
        this.hasPay = false;
        this.mid = mid;
    }

    public PayLog(int mid, int amount) {
        this.mid = mid;
        this.amount = amount;
    }

    public PayLog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Long getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(Long tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Long completeTime) {
        this.completeTime = completeTime;
    }

    public boolean isHasPay() {
        return hasPay;
    }

    public void setHasPay(boolean hasPay) {
        this.hasPay = hasPay;
        if(hasPay) payTime = System.currentTimeMillis();
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    /**
     * 生成秘钥的方法
     *
     * @param pass*/
    public void makePass(String pass) {
        setPasswd(MD5.encryption(String.valueOf(getTradeNo())+getAmount()+pass));
    }

    /**
     * 重新生成订单号
     * */
    public void reMakeTradeNo() {
        setTradeNo(getTradeNo()-1);
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
