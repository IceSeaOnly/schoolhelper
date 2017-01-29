package Entity;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/8/31.
 * 配送区域
 */
@Entity
@Table
public class SendPart {
    @Id
    @GeneratedValue
    private int id;
    private String partName; // 区域名
    private int sendPrice; // 配送费
    private int schoolId;

    public SendPart(String partName, int sendPrice,int scid) {
        this.partName = partName;
        this.sendPrice = sendPrice;
        this.schoolId = scid;
    }

    public SendPart() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public int getSendPrice() {
        return sendPrice;
    }

    public void setSendPrice(int sendPrice) {
        this.sendPrice = sendPrice;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }
}
