package Utils;

import javax.mail.MessagingException;

/**
 * Created by Administrator on 2016/9/1.
 */
public class MailSendThread extends Thread {
    private String content;
    private String sendTo;
    private String title;
    public MailSendThread(String sendTo,String title,String content) {
        super();
        this.content = content;
        this.title = title;
        this.sendTo = sendTo;
    }

    @Override
    public void run() {
            MailService.sendMail(sendTo,title, content);
    }
}
