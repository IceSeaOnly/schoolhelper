package Controllers.Manager;

import Entity.School;
import Utils.FailedAnswer;
import Entity.Manager.Manager;
import Service.ManagerService;
import Utils.MakeToken;
import Utils.SuccessAnswer;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/7.
 */
@Controller
@RequestMapping("app-cgi")
public class AppCgi {
    @Resource
    ManagerService managerService;
    private static Map<Integer,String> id_tokens = new HashMap<Integer, String>();

    public static String newToken(int id){
        String nk = MakeToken.makeToken();
        id_tokens.put(id,nk);
        System.out.println(id+" <--->"+nk);
        return nk;
    }

    public static boolean validateToken(int id,String token){
        if(id_tokens.get(id) == null)
            return false;
        return  id_tokens.get(id).equals(token);
    }

    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@RequestParam String phone,@RequestParam String pass){
        Manager manager = managerService.ManagerLogin(phone,pass);
        if(manager == null)
            return FailedAnswer.commomAnswer();
        // 登录成功，修改最新登录信息
        managerService.updateLastLogin(phone);
        // 预置信息
        ArrayList<School> schools = managerService.listMySchool(manager.getId());
        manager.setTmp_token(newToken(manager.getId()));
        manager.setTmp_schoolId(schools.get(0) == null?-1:schools.get(0).getId());
        manager.setTmp_schoolName(schools.get(0) == null?"未分配学校":schools.get(0).getSchoolName());
        return SuccessAnswer.successWithObject(manager.getTmp_token(),manager);
    }


}
