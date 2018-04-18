package Dao;

import Entity.CloudCoinRecord;
import Entity.ExpressOrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IceSea on 2018/4/18.
 * GitHub: https://github.com/IceSeaOnly
 */
@Repository
public class CloudCoinDao {
    @Resource
    SessionFactory sessionFactory;

    public List<CloudCoinRecord> getRecordByUserId(int userId){
        Session session = sessionFactory.openSession();
        ArrayList<CloudCoinRecord> res = (ArrayList<CloudCoinRecord>) session.createQuery("from CloudCoinRecord where userId = :ID  order by ts desc ")
                .setParameter("ID",userId)
                .list();
        session.close();
        return res;
    }
}
