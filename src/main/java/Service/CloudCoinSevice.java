package Service;

import Dao.CloudCoinDao;
import Entity.CloudCoinRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IceSea on 2018/4/18.
 * GitHub: https://github.com/IceSeaOnly
 */
@Service
public class CloudCoinSevice {
    @Resource
    private CloudCoinDao cloudCoinDao;

    public List<CloudCoinRecord> getRecordByUserId(int userId) {
        List<CloudCoinRecord> ls = cloudCoinDao.getRecordByUserId(userId);
        return ls == null ? new ArrayList<CloudCoinRecord>() : ls;
    }
}
