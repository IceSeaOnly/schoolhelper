package Entity.Manager;

import com.alibaba.fastjson.JSONArray;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/1/7.
 */
@Table
@Entity
public class Manager {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String phone;
    private String pass;
    private int salary;
    private Long lastLogin;
    private Long regTime;
    private boolean forbiden;
    private String tmp_token;
    private int tmp_schoolId;
    private String tmp_schoolName;
    private boolean tmp_tag; //通用标记
    private String alipay;
    private String wxpay;
    private String openId;
    private String dividendRatio; // 分红比例20170317升级，double改为String，json格式存储针对某个学校的分红比例
    private String pdesc; //对ta的描述
    private boolean could_delete;//是否可以被删除
    private String address;//详细宿舍地址
    private boolean cs_notice;//客服通知
    private boolean newOrderNotice;//新订单通知

    public Manager(String name, String phone, String pass,String ali,
                   String wx,String openId,String descStr,String add) {
        this.pdesc = descStr;
        JSONArray arr = new JSONArray();
        this.dividendRatio = arr.toJSONString();
        this.openId = openId;
        this.alipay = ali;
        this.wxpay = wx;
        this.name = name;
        this.phone = phone;
        this.pass = pass;
        this.salary = 0;
        this.lastLogin = System.currentTimeMillis();
        this.regTime = System.currentTimeMillis();
        this.forbiden = false;
        this.could_delete = true;
        this.address = add;
        this.cs_notice = true;
        this.newOrderNotice = true;
    }

    public Manager() {
    }

    public String getTmp_schoolName() {
        return tmp_schoolName;
    }

    public void setTmp_schoolName(String tmp_schoolName) {
        this.tmp_schoolName = tmp_schoolName;
    }

    public int getTmp_schoolId() {
        return tmp_schoolId;
    }

    public void setTmp_schoolId(int tmp_schoolId) {
        this.tmp_schoolId = tmp_schoolId;
    }

    public String getTmp_token() {
        return tmp_token;
    }

    public void setTmp_token(String tmp_token) {
        this.tmp_token = tmp_token;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getRegTime() {
        return regTime;
    }

    public void setRegTime(Long regTime) {
        this.regTime = regTime;
    }

    public boolean isForbiden() {
        return forbiden;
    }

    public void setForbiden(boolean forbiden) {
        this.forbiden = forbiden;
    }

    public boolean isTmp_tag() {
        return tmp_tag;
    }

    public void setTmp_tag(boolean tmp_tag) {
        this.tmp_tag = tmp_tag;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getWxpay() {
        return wxpay;
    }

    public void setWxpay(String wxpay) {
        this.wxpay = wxpay;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getDividendRatio() {
        return dividendRatio;
    }

    public void setDividendRatio(String dividendRatio) {
        this.dividendRatio = dividendRatio;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public boolean isCould_delete() {
        return could_delete;
    }

    public void setCould_delete(boolean could_delete) {
        this.could_delete = could_delete;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isCs_notice() {
        return cs_notice;
    }

    public void setCs_notice(boolean cs_notice) {
        this.cs_notice = cs_notice;
    }

    public void setDividendRatio(int schoolId, double val) {
        JSONArray arr = JSONArray.parseArray(getDividendRatio());
        for (int i = 0; i < arr.size(); i++) {
            SalaryConfig config = arr.getObject(i,SalaryConfig.class);
            if(config.getSid() == schoolId){
                config.setRate(val);
                arr.remove(i);
                arr.add(config);
                setDividendRatio(arr.toJSONString());
                return;
            }
        }
        addSalaryConfig(schoolId,val);
    }


    public void addSalaryConfig(int schoolId, double val) {
        JSONArray arr = JSONArray.parseArray(getDividendRatio());
        arr.add(new SalaryConfig(schoolId,val));
        setDividendRatio(arr.toJSONString());
    }

    public SalaryConfig getSalaryConfig(int schoolId) {
        JSONArray arr = JSONArray.parseArray(getDividendRatio());
        for (int i = 0; i < arr.size(); i++) {
            SalaryConfig config = arr.getObject(i,SalaryConfig.class);
            if(config.getSid() == schoolId)
                return config;
        }
        return null;
    }

    public double printSchoolConfig(int sid){
        SalaryConfig config = getSalaryConfig(sid);
        if(config!=null) return config.getRate();
        return 0.0;
    }

    public boolean isNewOrderNotice() {
        return newOrderNotice;
    }

    public void setNewOrderNotice(boolean newOrderNotice) {
        this.newOrderNotice = newOrderNotice;
    }
}
