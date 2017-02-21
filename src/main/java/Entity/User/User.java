package Entity.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/8/30.
 */
@Table
@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String open_id;
    private String username;
    private String phone;
    private int part; // 宿舍区id
    private String building; //楼号
    private String dormitory;//宿舍号
    private int order_sum;
    private int memery_points;//会员积分
    private boolean vip; // 成为会员
    private int my_money; // 会员卡余额,单位分
    private int schoolId;

    public User() {
    }

    public User(String open_id, String username, String phone, int part, String building,String dormitory, int order_sum, int memery_points,int scid) {
        this.open_id = open_id;
        this.dormitory = dormitory;
        this.username = username;
        this.phone = phone;
        this.part = part;
        this.building = building;
        this.order_sum = order_sum;
        this.memery_points = memery_points;
        this.vip = false;
        this.my_money = 0;
        this.schoolId = scid;
    }

    public User(String openid) {
        this.open_id = openid;
        this.username = "";
        this.phone = "";
        this.part = 0;
        this.building = "";
        this.order_sum = 0;
        this.memery_points = 0;
        this.vip = false;
        this.my_money = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPart() {
        return part;
    }

    public void setPart(int part) {
        this.part = part;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getOrder_sum() {
        return order_sum;
    }

    public void setOrder_sum(int order_sum) {
        this.order_sum = order_sum;
    }

    public int getMemery_points() {
        return memery_points;
    }

    public void setMemery_points(int memery_points) {
        this.memery_points = memery_points;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public int getMy_money() {
        return my_money;
    }

    public void setMy_money(int my_money) {
        this.my_money = my_money;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }
}
