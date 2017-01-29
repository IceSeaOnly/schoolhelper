package Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/8/31.
 */
@Entity
@Table
public class SysConfigs {
    @Id
    @GeneratedValue
    private int id;
    private String _key;
    private String _value;

    public SysConfigs() {
    }

    public SysConfigs(String _key, String _value) {
        this._key = _key;
        this._value = _value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get_key() {
        return _key;
    }

    public void set_key(String _key) {
        this._key = _key;
    }

    public String get_value() {
        return _value;
    }

    public void set_value(String _value) {
        this._value = _value;
    }
}
