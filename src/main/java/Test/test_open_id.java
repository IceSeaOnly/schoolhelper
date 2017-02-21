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
        System.out.println(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = null;
        while ((line=sc.nextLine())!= null){
            StringBuilder sb = new StringBuilder(line);
            sb.reverse();
            System.out.print(sb.toString());
        }
        String openid = "oz1S1v_7W7O1t-KxfdFK5Sk6eJVs";
//        new NoticeService()
//                .ReservationService("第一句话","这里是内容","女人","晚上3点半","潘金莲","你看着给","祝玩得愉快",openid,"http://www.binghai.site");
    }
    @Test
    public void md5(){
        String wd = "bone_client_web://123456";
        System.out.println(MD5.encryption(String.valueOf(11486303952995L)+100+"5wNA7Hxzz3hJJjhaqGJ0"));
    }
}
