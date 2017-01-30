package Dao;

import Entity.Manager.Conversation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

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
}
