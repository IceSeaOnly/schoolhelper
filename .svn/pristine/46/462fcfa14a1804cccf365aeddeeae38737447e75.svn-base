package WxApi;


import com.alibaba.fastjson.JSONObject;

public class SendTemplateMsg extends PostSendBase implements Runnable{
	private String data;
	private String token;
	
	public SendTemplateMsg(JSONObject content) {
		super();
		this.data = content.toString();
	}
	
	public String send(){
		GetAccessToken gat = new GetAccessToken();
		token = gat.doGetAccessToken();
		if(token == null)
			return null;
		String msg = null;
		this.setPostUrl("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token);
		this.setContent(data);
		return this.postSend();
	}

	public void run() {
		send();
	}
}
