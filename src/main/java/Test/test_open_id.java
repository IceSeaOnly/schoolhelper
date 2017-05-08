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
        String openids = "oz1S1v_7W7O1t-KxfdFK5Sk6eJVs";
        String[] ops = openids.split(",");
        NoticeService noticeService = new NoticeService();
        for(String openid:ops){
           noticeService.chargeSuccess("优惠券恢复提醒",
                            "0",
                            "免单券*1",
                            "0",
                            "系统赠送",
                            "亲爱的用户您好，由于系统问题造成部分用户免单券异常失效，给您带来不便敬请谅解。经过系统升级，失效的免单券现已经为您恢复，再一次为我们的工作失误向您道歉，希望您能继续支持小骨头。",
                            openid,"");
            System.out.println(openid);
        }

//        new NoticeService().ReservationService("this is frist",
//                "this is content",
//                "this is good",
//                "today",
//                "name",
//                "10",
//                "remark",
//                openid,"");
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

    @Test
    public void testTime(){
        System.out.println(MD5.encryption("2"));
    }

}
