package Controllers.Manager;

import Entity.Manager.Log;
import Service.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/1/30.
 */
@Controller
@RequestMapping("api")
public class Api {

    @Resource
    ManagerService managerService;

    @RequestMapping("cscb")
    @ResponseBody
    public String CustomServiceCallBack(@RequestParam String notice,@RequestParam Long cid,String type){
        if(notice.equals("newMsg")){
            managerService.save(new Log(8,"会话新消息，cid="+cid+",type = "+type,-1));
        }else if(notice.equals("serverEnd")){
            managerService.save(new Log(8,"客服结束会话，cid="+cid,-1));
            managerService.ConversationEnd(cid,1);
        }else if(notice.equals("userEnd")){
            managerService.save(new Log(8,"用户结束会话，cid="+cid,-1));
            managerService.ConversationEnd(cid,0);
        }else
            managerService.save(new Log(8,notice+"，cid="+cid,-1));
        return "success";
    }
}
