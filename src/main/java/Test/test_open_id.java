package Test;

import Controllers.SysConfig;
import Entity.Manager.AppPushMsg;
import Entity.Manager.SalaryConfig;
import Service.NoticeService;
import Utils.MD5;
import Utils.TimeFormat;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.io.File;
import java.util.Calendar;
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
        JSONObject data = new JSONObject();
        data.put("type","alipush");
        data.put("msg",new AppPushMsg("22点配送","新订单通知","17854258196"));
        System.out.println(data.toJSONString());
    }

    public static void main(String[] args) {
        String openid = "oz1S1v_7W7O1t-KxfdFK5Sk6eJVs";

//        new NoticeService()
//                .chargeSuccess("小骨头赠款到账",
//                        "6604",
//                        "50.00元",
//                        "0",
//                        "系统赠款",
//                        "亲爱的用户您好，50.00元赠款已经送到您的账户，再一次为我们的工作失误向您道歉，希望您能继续支持小骨头。",
//                        openid,"");
        new NoticeService().ReservationService("this is frist",
                "this is content",
                "this is good",
                "today",
                "name",
                "10",
                "remark",
                openid,"");
    }
    @Test
    public void md5(){
        File file = new File("F:\\ideaWorkstation\\schoolhelper\\img_github\\JPEG");
        String[] dir = file.list();
        for (int i = 0; i < dir.length; i++) {
            System.out.println("![](https://github.com/IceSeaOnly/schoolhelper/blob/master/img_github/JPEG/"+dir[i]+")");
        }

    }

    @Test
    public void time(){

        System.out.println(TimeFormat.data2Timestamp(2017,2,27));
    }

}
