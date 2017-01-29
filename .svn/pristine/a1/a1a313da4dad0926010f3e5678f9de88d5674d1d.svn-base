package Entity.Manager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/1/7.
 */
@Entity
@Table
public class SchoolDist {
    @Id
    @GeneratedValue
    private int id;
    private int schoolId;
    private int managerId;

    public SchoolDist(int schoolId, int managerId) {
        this.schoolId = schoolId;
        this.managerId = managerId;
    }

    public SchoolDist() {
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

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
