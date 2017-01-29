package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/11/22.
 */
@Table
@Entity
public class School {
    @Id
    @GeneratedValue
    private int id;
    private String schoolName;
    private String tag;/**SDUST*/

    public School(String schoolName, String tag) {
        this.schoolName = schoolName;
        this.tag = tag;
    }

    public School() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
