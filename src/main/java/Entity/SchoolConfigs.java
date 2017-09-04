package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/1/16.
 */
@Table
@Entity
public class SchoolConfigs {
    @Id
    @GeneratedValue
    private int id;
    private int schoolId;
    private int first_cost;
    private String shop_url;
    private int auto_start;
    private int auto_close;
    private boolean hand_close;
    private boolean firstDiscount;//首单优惠
    private boolean ifTenThenFree;//满十减一
    private boolean schoolMove;//校园搬运
    private boolean helpSend;//代寄
    private boolean enableCoupon;//优惠券开关
    private String auto_close_info;
    private String hand_close_info;
    private String refund_url;
    private int sumIncome; // 总收入
    private Long servicePhone; //客服电话

    private int each_fetch; //每次取件金额
    private int each_send; // 每次送达金额
    private int each_give; // 每次转交楼长
    private int each_receive; // 每次楼长接收


    public SchoolConfigs(int schoolId) {
        this.ifTenThenFree = false;
        this.firstDiscount = false;
        this.sumIncome = 0;
        this.schoolId = schoolId;
        this.first_cost = 99;
        this.shop_url = "";
        this.auto_start = 7;
        this.auto_close = 15;
        this.helpSend = false;
        this.schoolMove =false;
        this.hand_close = false;
        this.auto_close_info = "自动关闭提示未设置";
        this.hand_close_info = "手动关闭提示未设置";
        this.refund_url = "http://weixin.qdxiaogutou.com/refund.php";
        this.each_fetch = this.each_send = this.each_fetch = this.each_receive = 0;
        this.servicePhone = 0L;
        this.enableCoupon = true;
    }

    public SchoolConfigs() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getFirst_cost() {
        return first_cost;
    }

    public void setFirst_cost(int first_cost) {
        this.first_cost = first_cost;
    }

    public String getShop_url() {
        return shop_url;
    }

    public void setShop_url(String shop_url) {
        this.shop_url = shop_url;
    }

    public int getAuto_start() {
        return auto_start;
    }

    public void setAuto_start(int auto_start) {
        this.auto_start = auto_start;
    }

    public int getAuto_close() {
        return auto_close;
    }

    public void setAuto_close(int auto_close) {
        this.auto_close = auto_close;
    }

    public boolean isHand_close() {
        return hand_close;
    }

    public void setHand_close(boolean hand_close) {
        this.hand_close = hand_close;
    }

    public String getAuto_close_info() {
        return auto_close_info;
    }

    public void setAuto_close_info(String auto_close_info) {
        this.auto_close_info = auto_close_info;
    }

    public String getHand_close_info() {
        return hand_close_info;
    }

    public void setHand_close_info(String hand_close_info) {
        this.hand_close_info = hand_close_info;
    }

    public String getRefund_url() {
        return refund_url;
    }

    public void setRefund_url(String refund_url) {
        this.refund_url = refund_url;
    }

    public int getSumIncome() {
        return sumIncome;
    }

    public void setSumIncome(int sumIncome) {
        this.sumIncome = sumIncome;
    }


    public int getEach_fetch() {
        return each_fetch;
    }

    public void setEach_fetch(int each_fetch) {
        this.each_fetch = each_fetch;
    }

    public int getEach_send() {
        return each_send;
    }

    public void setEach_send(int each_send) {
        this.each_send = each_send;
    }

    public int getEach_give() {
        return each_give;
    }

    public void setEach_give(int each_give) {
        this.each_give = each_give;
    }

    public int getEach_receive() {
        return each_receive;
    }

    public void setEach_receive(int each_receive) {
        this.each_receive = each_receive;
    }

    public boolean isFirstDiscount() {
        return firstDiscount;
    }

    public void setFirstDiscount(boolean firstDiscount) {
        this.firstDiscount = firstDiscount;
    }

    public boolean isIfTenThenFree() {
        return ifTenThenFree;
    }

    public void setIfTenThenFree(boolean ifTenThenFree) {
        this.ifTenThenFree = ifTenThenFree;
    }

    public Long getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(Long servicePhone) {
        this.servicePhone = servicePhone;
    }

    public boolean isSchoolMove() {
        return schoolMove;
    }

    public void setSchoolMove(boolean schoolMove) {
        this.schoolMove = schoolMove;
    }

    public boolean isHelpSend() {
        return helpSend;
    }

    public void setHelpSend(boolean helpSend) {
        this.helpSend = helpSend;
    }

    public boolean isEnableCoupon() {
        return enableCoupon;
    }

    public void setEnableCoupon(boolean enableCoupon) {
        this.enableCoupon = enableCoupon;
    }
}
