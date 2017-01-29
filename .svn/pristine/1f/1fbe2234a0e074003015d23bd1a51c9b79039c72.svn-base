package Entity.Manager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/1/7.
 */
@Table
@Entity
public class PrivilegeDist {
    @Id
    @GeneratedValue
    private int id;
    private int managerId;
    private int privilegeId;


    public PrivilegeDist(int managerId, int privilegeId) {
        this.managerId = managerId;
        this.privilegeId = privilegeId;
    }

    public PrivilegeDist() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(int privilegeId) {
        this.privilegeId = privilegeId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

}
