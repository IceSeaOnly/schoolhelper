package Dao;

import Entity.Avatars;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/26.
 */
@Repository
public class AvatarDao {
    @Resource
    SessionFactory sessionFactory;

    public Avatars getRandomAvatar(int sex) {
        Session session = sessionFactory.openSession();
        Avatars a = (Avatars) session.createQuery("from Avatars where sex = :S and id > ((select max(id) from Avatars)-(select min(id) from Avatars)) * RAND() + (select min(id) from Avatars) limit 1")
                .setParameter("S",sex)
                .uniqueResult();
        session.close();
        return a;
    }
}
