package Dao;


import Entity.Kuaidi;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
@Repository
public class UKuaidiDao {

    @Resource
    SessionFactory sessionFactory;

    public List<Kuaidi> query (int kid)
    {
        System.out.println(sessionFactory);
        Session session = sessionFactory.openSession();
        List<Kuaidi> list = new ArrayList<Kuaidi>();
        Query query =  session.createQuery("from  Kuaidi where kid =: Oid")
               .setParameter("Oid",kid);
        list =query.list();
        session.getTransaction().commit();
        session.close();
        System.out.println("ok");
        return list;
    }

}
