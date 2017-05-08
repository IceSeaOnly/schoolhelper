package Utils;

import Entity.Express;
import Entity.SendPart;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/1.
 */
public class ExpressOrderHelper {

    public static SendPart getSendPart(int part, ArrayList<SendPart> parts,int school) {
        for(int i = 0;i < parts.size();i++){
            if(parts.get(i).getId() == part) return parts.get(i);
        }
        return new SendPart("unknow",0,school);
    }

    public static Express getExpress(int express, ArrayList<Express> expresses,int school) {
        for(int i = 0;i < expresses.size();i++){
            if(expresses.get(i).getId() == express) return expresses.get(i);
        }
        return new Express("unknow",0,school,99,99);
    }
}
