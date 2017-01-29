package Utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/1/7.
 */
public class FailedAnswer {
    public static String commomAnswer(){
        JSONObject ans = new JSONObject();
        ans.put("result",false);
        return ans.toJSONString();
    }
}
