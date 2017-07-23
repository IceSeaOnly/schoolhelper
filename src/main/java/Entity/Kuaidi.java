package  Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
public class Kuaidi{
    @Id
    @GeneratedValue
    private  int kid ;
    private  String kname ;
    private  String pname ;
    private  String ponenumber;
    private  String gps;

    public Kuaidi() {
    }

    public Kuaidi(String kname, String pname, String ponenumber, String gps) {
        this.kname = kname;
        this.pname = pname;
        this.ponenumber = ponenumber;
        this.gps = gps;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPonenumber() {
        return ponenumber;
    }

    public void setPonenumber(String ponenumber) {
        this.ponenumber = ponenumber;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }
}