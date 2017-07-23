package Service;

import Dao.UKuaidiDao;
import Entity.Kuaidi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
@Service
public class KuaiDiService {
    @Autowired
    UKuaidiDao uKuaidiDaodao;

    public List<Kuaidi>  ChaKuaiDi (int id)
    {
        uKuaidiDaodao = new UKuaidiDao();
        List<Kuaidi> list =  new ArrayList<Kuaidi>();
        list=uKuaidiDaodao.query(id);
        return list ;
    }

}
