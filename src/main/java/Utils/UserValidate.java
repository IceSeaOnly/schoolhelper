package Utils;

import org.junit.Test;

/**
 * Created by Administrator on 2016/8/4.
 */
public class UserValidate {
    public static boolean validate_user(String openid, String validate) {
        if (validate.equals(MD5.encryption(openid+"binghai"))) return true;
        else return false;
    }

    @Test
    public void test(){
        System.out.println(MD5.encryption("oz1S1vzOeqH4L_53d-1x_RORWkCc"+"binghai"));
    }
}
