package Entity;

import Service.ManagerService;
import Utils.MD5;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/30.
 */
@Entity
@Table
public class ExpressOrder {
    @Id
    @GeneratedValue
    private int id;
    private int shouldPay; // 应付，单位分
    private boolean has_pay; //是否已经支付
    private String orderKey; // 订单支付号
    private int user_id; //下单人id
    private String express; // 快递名
    private String express_name; // 快递预留姓名
    private String express_phone; // 快递预留手机号
    private String express_number; // 快递编号
    private String sendTo; // 配送地址
    private int part; // 配送区域
    private String receive_phone; //收件人电话
    private String receive_name; //收件人姓名
    private int order_state; // 0已发布，1已接单，2已收件，正在派送，3派送成功，-1订单已取消，-2快递不存在，-3配送失败
    private Date orderTime; // 下单时间
    private String arrive; // 到达时间
    private String otherinfo; // 备注
    private boolean canceled; // 是否已经取消
    private int rider_id;//配送员id
    private String rider_name; //配送员名字
    private boolean has_pay_to_rider;//是否已经跟配送员结算
    private int sendtime_id; // 配送时间属性id
    private int schoolId;
    private Long orderTimeStamp; // 下单时间戳
    private String courierNumber;//快递单号
    private int reason;//流程中出现问题的原因


    public String state_toString(){
        String res = "";
        switch (order_state){
            case -3:
                res = "配送失败";
                break;
            case -2:
                res = "不存在";
                break;
            case -1:
                res = "已取消";
                break;
            case 0:
                res = "已发布 待接单";
                break;
            case 1:
                res = "已接单";
                break;
            case 2:
                res = "已收件";
                break;
            default:
                res = "已完成";
                break;
        }
        return res;
    }
    public String reason2String(){
        return ManagerService.reason2String(reason);
    }

    public String makeLZJJKey(){
        return MD5.encryption(orderKey+rider_id);
    }
    public ExpressOrder() {
    }

    public ExpressOrder(int user_id,
                        int shouldPay,
                        String orderKey,
                        String express,
                        String express_name,
                        String express_phone,
                        String express_number,
                        String sendTo,
                        int part,
                        String receive_phone,
                        String recceive_name,
                        int ordet_state,
                        Date orderTime,
                        Long orderTimeStamp,
                        String arrive,
                        String otherinfo,
                        int sendtime_id,
                        int scid) {
        this.courierNumber = "";
        this.orderTimeStamp = orderTimeStamp;
        this.express = express;
        this.has_pay = false;
        this.orderKey = orderKey;
        this.user_id = user_id;
        this.express_name = express_name;
        this.express_phone = express_phone;
        this.express_number = express_number;
        this.sendTo = sendTo;
        this.receive_phone = receive_phone;
        this.receive_name = recceive_name;
        this.order_state = ordet_state;
        this.orderTime = orderTime;
        this.arrive = arrive;
        this.otherinfo = otherinfo;
        this.shouldPay = shouldPay;
        this.rider_id = -1;
        this.rider_name = "未接单";
        this.has_pay_to_rider = false;
        this.canceled = false;
        this.part = part;
        this.sendtime_id = sendtime_id;
        this.schoolId = scid;
        this.reason = 0;
    }

    public static int NORMAL_STATE = 0;
    public static int ACCEPT_ORDER = 1;
    public static int GOT_EXPRESS_SENDING = 2;
    public static int SEND_SUCCESS = 3;
    public static int ORDER_CENCLE = -1;
    public static int ORDER_NOT_EXIST = -2;
    public static int ORDER_SEND_FAILED = -3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }

    public String getExpress_phone() {
        return express_phone;
    }

    public void setExpress_phone(String express_phone) {
        this.express_phone = express_phone;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getReceive_phone() {
        return receive_phone;
    }

    public void setReceive_phone(String receive_phone) {
        this.receive_phone = receive_phone;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public void setRecceive_name(String recceive_name) {
        this.receive_name = recceive_name;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int ordet_state) {
        this.order_state = ordet_state;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getOtherinfo() {
        return otherinfo;
    }

    public void setOtherinfo(String otherinfo) {
        this.otherinfo = otherinfo;
    }

    public boolean isHas_pay() {
        return has_pay;
    }

    public void setHas_pay(boolean has_pay) {
        this.has_pay = has_pay;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public int getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(int shouldPay) {
        this.shouldPay = shouldPay;
    }

    public boolean isHas_pay_to_rider() {
        return has_pay_to_rider;
    }

    public void setHas_pay_to_rider(boolean has_pay_to_rider) {
        this.has_pay_to_rider = has_pay_to_rider;
    }

    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }

    public int getRider_id() {
        return rider_id;
    }

    public void setRider_id(int rider_id) {
        this.rider_id = rider_id;
    }

    public String getRider_name() {
        return rider_name;
    }

    public void setRider_name(String rider_name) {
        this.rider_name = rider_name;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public int getSendtime_id() {
        return sendtime_id;
    }

    public void setSendtime_id(int sendtime_id) {
        this.sendtime_id = sendtime_id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public Long getOrderTimeStamp() {
        return orderTimeStamp;
    }

    public void setOrderTimeStamp(Long orderTimeStamp) {
        this.orderTimeStamp = orderTimeStamp;
    }

    public String getCourierNumber() {
        return courierNumber;
    }

    public void setCourierNumber(String courierNumber) {
        this.courierNumber = courierNumber;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }
}
