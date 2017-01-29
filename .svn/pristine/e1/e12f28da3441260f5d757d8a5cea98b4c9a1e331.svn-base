package Utils;



import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * Created by Administrator on 2016/9/1.
 */
public class MailService {
    public static boolean sendMail(String who, String title, String content){
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIakKZGL7LjvgU", "ewVnzY4Jj3Y3xwBUl53WZyuDOcZhNI");
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName("robot@notice.haidaoo.com");
            request.setFromAlias("Time管理团队");
            request.setAddressType(1);
            request.setTagName("notifymail");
            request.setReplyToAddress(true);
            request.setToAddress(who);
            request.setSubject(title);
            request.setHtmlBody(content);
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
            return false;
        }
        catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
