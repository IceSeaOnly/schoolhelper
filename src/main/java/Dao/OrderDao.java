package Dao;

import Entity.ExpressOrder;
import Entity.SysMsg;
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

    public ArrayList<ExpressOrder> getRefereeOrderBySchool(int status,int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<ExpressOrder> res = (ArrayList<ExpressOrder>) session.createQuery("from ExpressOrder where schoolId = :S and cloudCoinProcess = :CCP and order_state = 3")
                .setParameter("S",schoolId)
                .setParameter("CCP",status)
                .list();
        session.close();
        return res;
    }

    public void updateOrderOutOfDate(int userId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update ExpressOrder set order_state = -1 where user_id = :U and has_pay = false and orderTimeStamp + 1800000 < :T")
                .setParameter("U",userId)
                .setParameter("T", System.currentTimeMillis())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
