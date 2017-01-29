package Dao;

import Entity.ExpressOrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/30.
 */
@Repository
public class OrderDao {

    @Resource
    SessionFactory sessionFactory;

    public ArrayList<ExpressOrder> getOrdersByUserId(int id,int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<ExpressOrder> res = (ArrayList<ExpressOrder>) session.createQuery("from ExpressOrder where user_id = :ID and schoolId = :S order by orderTime desc ")
                .setParameter("S",schoolId)
                .setParameter("ID",id)
                .list();
        session.close();
        return res;
    }
}
