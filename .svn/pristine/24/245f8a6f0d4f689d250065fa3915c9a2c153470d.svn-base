package WxApi;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PostSendBase {
	private String content;
	private String postUrl;
	
	public String postSend(){
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(postUrl);
		httpPost.addHeader("Content-Type", "text/xml");
		StringEntity postEntity = new StringEntity(content, "UTF-8");
		httpPost.setEntity(postEntity);
		CloseableHttpResponse response2 = null;
		try {
			response2 = httpclient.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String msg = null;
		try {
			HttpEntity entity2 = response2.getEntity();
			msg = EntityUtils.toString(entity2,"UTF-8");
//			System.out.println(msg);
			//���ĵ�response
			EntityUtils.consume(entity2);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return msg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public void setPostUrl(String postUrl) {
		this.postUrl = postUrl;
	}
}
