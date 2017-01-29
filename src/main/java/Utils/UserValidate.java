package Utils;

/**
 * Created by Administrator on 2016/8/4.
 */
public class UserValidate {
    public static boolean validate_user(String openid, String validate) {
        if (validate.equals(MD5.encryption(openid+"binghai"))) return true;
        else return false;
    }
}
