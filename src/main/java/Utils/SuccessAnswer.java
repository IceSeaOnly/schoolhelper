package Utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/1/7.
 */
public class SuccessAnswer {
    public static String successWithObject(String token,Object entities) {
        JSONObject res = new JSONObject();
        res.put("result",true);
        res.put("token",token);
        res.put("entity",entities);
        return res.toString();
    }

    public static String success(String token){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("token",token);
        jsonObject.put("result",true);
        return jsonObject.toString();
    }
}
