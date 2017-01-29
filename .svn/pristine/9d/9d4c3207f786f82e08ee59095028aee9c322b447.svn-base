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
    private String auto_close_info;
    private String hand_close_info;
    private String refund_url;


    public SchoolConfigs(int schoolId) {
        this.schoolId = schoolId;
        this.first_cost = 99;
        this.shop_url = "";
        this.auto_start = 7;
        this.auto_close = 15;
        this.hand_close = false;
        this.auto_close_info = "自动关闭提示未设置";
        this.hand_close_info = "手动关闭提示未设置";
        this.refund_url = "http://weixin.qdxiaogutou.com/refund.php";
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
}
