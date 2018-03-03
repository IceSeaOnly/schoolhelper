package Utils;

import org.junit.Test;

/**
 * Created by Administrator on 2016/8/4.
 */
public class UserValidate {
    public static boolean validate_user(String openid, String validate) {
        if (validate.equals(MD5.encryption(openid+"基础密码"))) return true;
        else return false;
    }
}
