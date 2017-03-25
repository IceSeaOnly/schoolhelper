package Dao;

import Entity.Manager.Conversation;
import Utils.TimeFormat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/1/30.
 */
@Repository
public class NoticeDao {
    @Resource
    SessionFactory sessionFactory;

    /**
     * 计算我的客服工单总数
     * */
    public Long sizeMyConversation(int id) {
        Session session = sessionFactory.openSession();
        Long size = (Long) session.createQuery("select coalesce(count (*),0) from Conversation where userid = :U")
                .setParameter("U",id)
                .uniqueResult();
        session.close();
        return size;
    }

    /**
     * 计算我的未完成的客服工单总数
     * */
    public Long sizeMyUnCompleteConversation(int id) {
        Session session = sessionFactory.openSession();
        Long size = (Long) session.createQuery("select coalesce(count (*),0) from Conversation where isUserEnd = false and userid = :U")
                .setParameter("U",id)
                .uniqueResult();
        session.close();
        return size;
    }

    public Conversation getExistConversation(int id) {
        Session session = sessionFactory.openSession();
        Conversation conversation = (Conversation) session.createQuery("from Conversation where orderId = :I and isUserEnd = false ")
                .setParameter("I",id)
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        return conversation;
    }

    public int getConversationIfWait(int managerId, int csid, int schoolId) {
        Session session =sessionFactory.openSession();
        session.beginTransaction();
        int eff = session.createQuery("update Conversation set serverid = :SID where id = :ID and schoolId = :SC and serverid = -1")
                .setParameter("SID",managerId)
                .setParameter("ID",csid)
                .setParameter("SC",schoolId)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return eff;
    }

    public Conversation getConversationById(int csid) {
        Session session = sessionFactory.openSession();
        Conversation conversation = (Conversation) session.createQuery("from Conversation where id = :I")
                .setParameter("I",csid)
                .uniqueResult();
        session.close();
        return conversation;
    }

    public Conversation getConversationByCid(Long cid) {
        Session session = sessionFactory.openSession();
        Conversation conversation = (Conversation) session.createQuery("from Conversation where cid = :I")
                .setParameter("I",cid)
                .uniqueResult();
        session.close();
        return conversation;
    }

    public ArrayList<String> listSchoolAllOpenIds(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<String>rs = (ArrayList<String>) session.createQuery("select open_id from User where schoolId = :S")
                .setParameter("S",schoolId)
                .list();
        session.close();
        return rs;
    }

    // 返回今日下单的用户openid
    public ArrayList<String> todayOrderedUsers(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<String>rs = (ArrayList<String>) session.createQuery("select open_id from User where schoolId = :S and id in (select user_id from ExpressOrder where orderTimeStamp > :T)")
                .setParameter("S",schoolId)
                .setParameter("T", TimeFormat.getTimesmorning())
                .list();
        session.close();
        return rs;
    }
}
