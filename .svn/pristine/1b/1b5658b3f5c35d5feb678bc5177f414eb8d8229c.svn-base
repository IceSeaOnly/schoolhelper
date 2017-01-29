package WxApi;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class GetRequestBase {
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
			HttpGet httpget =  new HttpGet(getRequestUrl());				
			// ???get????.    
			CloseableHttpResponse response = httpclient.execute(httpget);  
			try {  
				// ?????????    
				HttpEntity entity = response.getEntity();  
				  
				// ????????    
				//System.out.println(response.getStatusLine());  
				if (entity != null) {  
					// ?????????????    
					//System.out.println("Response content length: " + entity.getContentLength());  
					// ??????????    
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
			// ???????,??????    
			try {  
				httpclient.close();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}
		return msg;
	}
	
	
}
