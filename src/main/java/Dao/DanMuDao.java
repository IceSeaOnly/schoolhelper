package Dao;

import Entity.DanMu;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/26.
 */
@Repository
public class DanMuDao {
    @Resource
    SessionFactory sessionFactory;

    /**
     * 最新n条已审核的用户消息
     * */
    public ArrayList<DanMu> lastestUserDanmu(int n) {
        Session session = sessionFactory.openSession();
        ArrayList<DanMu> rs = (ArrayList<DanMu>)
                session.createQuery("from DanMu where priority = false and pass = true order by createTime desc")
                .setMaxResults(n)
                .list();
        session.close();
        return rs;
    }

    public ArrayList<DanMu> lastestManagerDanmu(int n) {
        Session session = sessionFactory.openSession();
        ArrayList<DanMu> rs = (ArrayList<DanMu>)
                session.createQuery("from DanMu where priority = true order by createTime desc")
                .setMaxResults(n)
                .list();
        session.close();
        return rs;
    }

    public void delete(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from DanMu where id = :I")
                .setParameter("I",id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
