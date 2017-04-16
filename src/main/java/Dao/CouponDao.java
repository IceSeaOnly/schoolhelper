package Dao;

import Entity.GiftRecord;
import Utils.TimeFormat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Time;
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

    public void consumeFreeGift(ArrayList<Integer> outOfdate) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update GiftRecord set valid = false,used = true,useTime = :T,usedTimeStr = :TS where id in :S")
                .setParameterList("S",outOfdate)
                .setParameter("T",System.currentTimeMillis())
                .setParameter("TS", TimeFormat.format(System.currentTimeMillis()))
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

    public ArrayList<GiftRecord> listLasted100Record(int g) {
        Session session = sessionFactory.openSession();
        ArrayList<GiftRecord>rs = (ArrayList<GiftRecord>) session.createQuery("from GiftRecord where gid = :G order by getTime desc")
                .setParameter("G",g)
                .setMaxResults(100)
                .list();
        session.close();
        return rs;
    }

    public Long sumAllRecord(int g) {
        Session session = sessionFactory.openSession();
        Long sum = (Long) session.createQuery("select coalesce(count (*),0) from GiftRecord where gid = :G")
                .setParameter("G",g)
                .uniqueResult();
        session.close();
        return sum;
    }
}