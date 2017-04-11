package Dao;

import Entity.GiftRecord;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/11.
 */
@Repository
public class CouponDao {
    @Resource
    SessionFactory sessionFactory;

    public ArrayList<GiftRecord> howManyFreeIHave(int uid) {
        Session session = sessionFactory.openSession();
        ArrayList<GiftRecord>rs = (ArrayList<GiftRecord>) session.createQuery("from GiftRecord where uid = :U and valid = true ")
                .setParameter("U",uid)
                .list();
        session.close();
        return rs;
    }


    public void setGiftRecordOutOfDate(ArrayList<Integer> outOfdate) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update GiftRecord set valid = false where id in :S")
                .setParameterList("S",outOfdate)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public ArrayList<GiftRecord> getMyCoupons(int id) {
        Session session = sessionFactory.openSession();
        ArrayList<GiftRecord>rs = (ArrayList<GiftRecord>) session.createQuery("from GiftRecord where uid = :U")
                .setParameter("U",id)
                .list();
        session.close();
        return rs;
    }
}
