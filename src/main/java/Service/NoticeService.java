package Service;

import WxApi.SendTemplateMsg;
import com.alibaba.fastjson.JSONObject;


/**
 * Created by Administrator on 2016/11/24.
 */
public class NoticeService{
    public void paySuccess(String detailcontent, String moneysum,
                           String remark, String productName, String openid,String url){
        JSONObject data = new JSONObject();
        JSONObject First = new JSONObject();
        First.put("value", detailcontent);

        JSONObject OrderMoneySum = new JSONObject();
        OrderMoneySum.put("value", moneysum);

        JSONObject OrderProductName = new JSONObject();
        OrderProductName.put("value",productName);

        JSONObject Remark = new JSONObject();
        Remark.put("value", remark);

        data.put("first", First);
        data.put("orderMoneySum", OrderMoneySum);
        data.put("orderProductName", OrderProductName);
        data.put("Remark", Remark);

        JSONObject json = new JSONObject();
        json.put("touser", openid);//OPENID
        json.put("template_id","QL9zFOOV9JpJQwP2uFgXLlleebgM34ViORxyXFCxSOA");
        json.put("url", url);
        json.put("topcolor", "#FF0000");
        json.put("data", data);
        SendTemplateMsg stm = new SendTemplateMsg(json);
        new Thread(stm).start();
    }
}
