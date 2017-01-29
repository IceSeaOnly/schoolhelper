package WxApi;


import com.alibaba.fastjson.JSONObject;

public class GetAccessToken extends GetRequestBase{
	private String appid = "wxc834b95e39745f5f";
	private String secret = "917c72e1021d13412605b9ea3f142943";
	
	public String doGetAccessToken(){
		String msg = null;
		this.setRequestUrl("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
		msg = this.doRequest();
		if(msg != null){
			JSONObject json = JSONObject.parseObject(msg);
			return json.getString("access_token");
		}
		return msg;
	}
}
