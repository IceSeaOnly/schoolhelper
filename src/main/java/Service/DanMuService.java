package Service;

import Dao.DanMuDao;
import Entity.DanMu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;


/**
 * Created by Administrator on 2017/3/26.
 */
@Service
public class DanMuService {
    @Resource
    DanMuDao danMuDao;

    /**
     * @Param mode 查询模式
     * mode = 0 表示用户查询模式，返回2条管理员信息和8条最新用户消息
     * mode = 1 表示管理员查询模式，仅查询未审核的
     * */
    public ArrayList<DanMu> query(int mode){
        ArrayList<DanMu> rs = new ArrayList<DanMu>();
        if(mode == 0){
            rs.addAll(lastestManagerDanmu(2));
            rs.addAll(lastestUserDanmu(10));
        }else{
            rs.addAll(lastestUserDanmu(10));
        }
        System.out.println("dm="+rs.size());
        return rs;
    }

    private ArrayList<DanMu> lastestUserDanmu(int i) {
        return danMuDao.lastestUserDanmu(i);
    }

    private ArrayList<DanMu> lastestManagerDanmu(int i) {
        return danMuDao.lastestManagerDanmu(i);
    }

    public void delete(int id){
        danMuDao.delete(id);
    }
}
