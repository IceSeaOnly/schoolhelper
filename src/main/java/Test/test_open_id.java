package Test;

import Controllers.SysConfig;
import Service.NoticeService;
import Utils.MD5;
import Utils.TimeFormat;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Scanner;

/**
 * Created by Administrator on 2016/9/30.
 */
public class test_open_id {

    @Test
    public void testReplace(){
        String url = "helloWORLDhehehe12345";
        url = url.replace("WORLD","world");
        url = url.replace("123456","789");
        System.out.println(url);
    }
    @Test
    public void test(){
        //c03bc06262d96428dd66c23841c50ea3
        System.out.println(MD5.encryption("123456"));
    }

    public static void main(String[] args) {
        String openid = "oz1S1v_7W7O1t-KxfdFK5Sk6eJVs";
        new NoticeService()
                .chargeSuccess("小骨头赠款到账",
                        "6604",
                        "50.00元",
                        "0",
                        "系统赠款",
                        "亲爱的用户您好，50.00元赠款已经送到您的账户，再一次为我们的工作失误向您道歉，希望您能继续支持小骨头。",
                        openid,"");
    }
    @Test
    public void md5(){
        String wd = "bone_client_web://123456";
        System.out.println(MD5.encryption(String.valueOf(11486303952995L)+100+"5wNA7Hxzz3hJJjhaqGJ0"));
    }
}
