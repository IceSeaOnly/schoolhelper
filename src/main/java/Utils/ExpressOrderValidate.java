package Utils;

import Entity.Express;
import Entity.SendPart;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/31.
 * 校验订单输入数据是否正常
 */
public class ExpressOrderValidate {


    public static boolean validate(int express, int part, ArrayList<Express> expresses, ArrayList<SendPart> parts){

        if(expresses == null || parts == null){
            return false;
        }

        boolean v1 = false;
        boolean v2 = false;

        for(int i = 0;i < expresses.size();i++){
            if(expresses.get(i).getId() == express){
                v1 = true;
                break;
            }
        }

        for(int i = 0;i < parts.size();i++){
            if(parts.get(i).getId() == part){
                v2 = true;
                break;
            }
        }

        if(v1 && v2) return true;
        return false;
    }
}
