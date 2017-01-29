package WxApi;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class PostRequestBase {
	private String RequestUrl;

	public String getRequestUrl() {
		return RequestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		RequestUrl = requestUrl;
	}
	
	
	public String doRequest(){
		String msg = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();  
		try {  
			HttpPost httppost =  new HttpPost(getRequestUrl());	
			// ִ��get����.    
			CloseableHttpResponse response = httpclient.execute(httppost);  
			try {  
				// ��ȡ��Ӧʵ��    
				HttpEntity entity = response.getEntity();  
				  
				// ��ӡ��Ӧ״̬    
				//System.out.println(response.getStatusLine());  
				if (entity != null) {  
					// ��ӡ��Ӧ���ݳ���    
					//System.out.println("Response content length: " + entity.getContentLength());  
					// ��ӡ��Ӧ����    
					msg = EntityUtils.toString(entity);  
				}  
				  
			} finally {  
				response.close();  
			}  
		} catch (ClientProtocolException e) {  
			e.printStackTrace();  
		} catch (ParseException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		} finally {  
			// �ر�����,�ͷ���Դ    
			try {  
				httpclient.close();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}
		return msg;
	}
}
