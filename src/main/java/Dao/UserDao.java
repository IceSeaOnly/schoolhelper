package Dao;

import Entity.*;
import Entity.User.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Administrator on 2016/8/30.
 */
@Repository
public class UserDao {

    @Resource
    SessionFactory sessionFactory;

    public User getUserByOpenid(String openid) {
        Session session = sessionFactory.openSession();
        User user = (User) session.createQuery("from User where open_id = :OID")
                .setParameter("OID", openid)
                .uniqueResult();
        session.close();
        return user;
    }

    public User createEmpeyNewUser(String openid) {
        Session session = sessionFactory.openSession();
        User user = new User(openid);
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public void update(Object obj) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(obj);
        session.getTransaction().commit();
        session.close();
    }

    public ArrayList<SendPart> listAllparts(int schoolId) {
        ArrayList<SendPart> parts = null;
        Session session = sessionFactory.openSession();
        parts = (ArrayList<SendPart>) session.createQuery("from SendPart where schoolId = :I")
                .setParameter("I",schoolId)
                .list();
        session.close();
        return parts;
    }

    public ArrayList<Express> listAllExpress(int schoolId) {
        ArrayList<Express> res = null;
        Session session = sessionFactory.openSession();
        res = (ArrayList<Express>) session.createQuery("from Express where available = true and schoolId = :I")
                .setParameter("I",schoolId)
                .list();
        session.close();
        return res;
    }



    public boolean phoneExsitInExpressPhone(String express_phone) {
        Session session = sessionFactory.openSession();
        Long res = ((Long) session.createQuery("select count(*) from ExpressOrder where express_phone = :P and has_pay = true")
                .setParameter("P", express_phone)
                .list()
                .iterator()
                .next())
                .longValue();

        session.close();
        return res > 0;
    }

    public boolean phoneExisttInSendPhone(String express_phone) {
        Session session = sessionFactory.openSession();
        Long res = ((Long) session.createQuery("select count(*) from ExpressOrder where receive_phone = :P and has_pay = true")
                .setParameter("P", express_phone)
                .list()
                .iterator()
                .next())
                .longValue();

        session.close();
        return res > 0;
    }

    public void save(Object order) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(order);
        session.getTransaction().commit();
        session.close();
    }

    public ExpressOrder getExpressOrderById(int user_id, int order_id) {
        Session session = sessionFactory.openSession();
        ExpressOrder order = (ExpressOrder) session.get(ExpressOrder.class, order_id);
        session.close();
        return order.getUser_id() == user_id ? order : null;
    }


    public ArrayList<SysMsg> getAllSysMsg(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<SysMsg> res = (ArrayList<SysMsg>) session.createQuery("from SysMsg where schoolId = :I order by addTime desc ")
                .setParameter("I",schoolId)
                .list();
        session.close();
        return res;
    }



    public String getLastestNews(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<SysMsg> sms = (ArrayList<SysMsg>) session.createQuery("from SysMsg where schoolId = :I order by addTime desc")
                .setParameter("I",schoolId)
                .list();
        session.close();
        if (sms != null) {
            if (sms.size() > 0)
                return sms.get(0).getContent();
        }
        return null;
    }

    public ArrayList<UserIndexAd> getAds(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<UserIndexAd> ads = (ArrayList<UserIndexAd>) session.createQuery("from UserIndexAd where schoolId = :I")
                .setParameter("I",schoolId)
                .list();
        session.close();
        return ads;
    }

    /**
     * 所有会员套餐
     */
    public ArrayList<ChargeVip> getAllVipMeals(int sid) {
        Session session = sessionFactory.openSession();
        ArrayList<ChargeVip> res = (ArrayList<ChargeVip>) session.createQuery("from ChargeVip where schoolId = :S")
                .setParameter("S",sid)
                .list();
        session.close();
        return res;
    }

    public ArrayList<SendExpress> getAllSendExpresses(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<SendExpress> res = (ArrayList<SendExpress>) session.createQuery("from SendExpress where available = true and schoolId = :I ")
                .setParameter("I",schoolId)
                .list();
        session.close();
        return res;
    }

    public ArrayList<SendExpressOrder> getExpressOrder(String ex,int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<SendExpressOrder> res = (ArrayList<SendExpressOrder>) session.createQuery("from SendExpressOrder where express = :E and haspay = true and schoolId = :I order by orderTime desc")
                .setParameter("I",schoolId)
                .setParameter("E", ex)
                .list();
        session.close();
        return res;
    }

    public ExpressOrder getExpressOrderByOrderKey(String orderKey) {
        Session session = sessionFactory.openSession();
        ExpressOrder order = (ExpressOrder) session.createQuery("from ExpressOrder where orderKey = :K")
                .setParameter("K", orderKey)
                .uniqueResult();
        session.close();
        return order;
    }

    public Long getSendTimeCurrentSum(int sendtime_id,int schoolId) {
        Session session = sessionFactory.openSession();
        Long cursum = (Long) session.
                createQuery("select COALESCE(count(id),0) from ExpressOrder where to_days(orderTime) = to_days(now()) and sendtime_id = :I and has_pay = true and schoolId = :S")
                .setParameter("S",schoolId)
                .setParameter("I", sendtime_id)
                .uniqueResult();
        session.close();
        return cursum;
    }

    public ArrayList<SendTime> getAllSendTimes(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<SendTime> res = (ArrayList<SendTime>) session.createQuery("from SendTime where schoolId = :I order by id desc")
                .setParameter("I",schoolId)
                .list();
        session.close();
        return res;
    }

    public String getSchoolTag(int schoolId) {
        Session session = sessionFactory.openSession();
        School school = (School) session.get(School.class,schoolId);
        session.close();
        return school.getTag();
    }

    public ArrayList<School> listAllSchool() {
        Session session = sessionFactory.openSession();
        ArrayList<School> res = (ArrayList<School>) session.createQuery("from School ").list();
        session.close();
        return res;
    }


    public SchoolConfigs getSchoolConfBySchoolId(int sid) {
        Session session = sessionFactory.openSession();
        SchoolConfigs sc = (SchoolConfigs) session.createQuery("from SchoolConfigs where schoolId = :S")
                .setParameter("S",sid)
                .uniqueResult();
        session.close();
        return sc;
    }

    public void orderSumCutOne(int user_id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update User set order_sum = order_sum-1 where id = :I and order_sum>1")
                .setParameter("I",user_id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
