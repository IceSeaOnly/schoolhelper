package Dao;

import Entity.ChargeVipOrder;
import Entity.ExpressOrder;
import Entity.Manager.*;
import Entity.School;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/7.
 */
@Repository
public class ManagerDao{
    @Resource
    SessionFactory sessionFactory;

    public Manager managerLogin(String phone, String pass) {
        Session session = sessionFactory.openSession();
        Manager res = (Manager) session.createQuery("from Manager where phone = :PH and pass = :PA and forbiden = false ")
                .setParameter("PH",phone)
                .setParameter("PA",pass)
                .uniqueResult();
        session.close();
        return res;
    }

    public void updateLostLogin(String phone) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update Manager set lastLogin = :L where phone = :P")
                .setParameter("L",System.currentTimeMillis())
                .setParameter("P",phone)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public ArrayList<ExpressOrder> commonOrderGet(int schoolId, Long from, Long end, int sendTime, int orderState) {
        Session session = sessionFactory.openSession();
        Query q = session.createQuery("from ExpressOrder where schoolId = :SID and orderTimeStamp between :F and :E"
                +(sendTime == -1?"":" and sendtime_id = :S")
                +(orderState == -1?"":" and order_state = :STA"))
                .setParameter("SID",schoolId)
                .setParameter("F",from)
                .setParameter("E",end);
        if(sendTime>-1){
            q.setParameter("S",sendTime);
        }

        if(orderState > -1){
            q.setParameter("STA",orderState);
        }
        ArrayList<ExpressOrder>orders = (ArrayList<ExpressOrder>)q.list();
        session.close();
        return orders;
    }

    public School getSchoolById(int schoolId) {
        Session session = sessionFactory.openSession();
        School entity = (School) session.createQuery("from School where id = :I")
                .setParameter("I",schoolId)
                .uniqueResult();
        session.close();
        return entity;
    }

    public ArrayList<IndexItemEntity> listMyFunctions(int managerId) {
        Session session = sessionFactory.openSession();
        ArrayList<IndexItemEntity>res = (ArrayList<IndexItemEntity>)
                session.createQuery("from IndexItemEntity where id in (select privilegeId from PrivilegeDist where managerId = :M) order by id")
                .setParameter("M",managerId)
                .list();
        session.close();
        return res;
    }

    public ArrayList<School> listMySchool(int id) {
        Session session = sessionFactory.openSession();
        ArrayList<School>res = (ArrayList<School>)
                session.createQuery("from School where id in (select schoolId from SchoolDist where managerId = :M) order by id")
                        .setParameter("M",id)
                        .list();
        session.close();
        return res;
    }

    public boolean managerAccess2School(int managerId, int schoolId) {
        Session session = sessionFactory.openSession();
        SchoolDist sd = (SchoolDist) session.createQuery("from SchoolDist where managerId = :M and schoolId = :S")
                .setParameter("M",managerId)
                .setParameter("S",schoolId)
                .uniqueResult();
        session.close();
        return sd != null;
    }

    public boolean managerTakeOrder(int managerId, String managerName, int schoolId, int orderId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int e = session.createQuery("update ExpressOrder set order_state = :ST,rider_id = :RID,rider_name=:RN where schoolId = :SID and id = :OID and order_state = :OST")
                .setParameter("ST",ExpressOrder.ACCEPT_ORDER)
                .setParameter("RID",managerId)
                .setParameter("RN",managerName)
                .setParameter("SID",schoolId)
                .setParameter("OID",orderId)
                .setParameter("OST",ExpressOrder.NORMAL_STATE)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return e>0;
    }

    public Manager getManagerById(int managerId) {
        Session session = sessionFactory.openSession();
        Manager m = (Manager) session.get(Manager.class,managerId);
        session.close();
        return m;
    }

    public boolean managerFetchOrder(int managerId, int schoolId, int orderId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int e = session.createQuery("update ExpressOrder set order_state = :ST where rider_id = :RID and schoolId = :SID and id = :OID and order_state = :OST")
                .setParameter("ST",ExpressOrder.GOT_EXPRESS_SENDING)
                .setParameter("RID",managerId)
                .setParameter("SID",schoolId)
                .setParameter("OID",orderId)
                .setParameter("OST",ExpressOrder.ACCEPT_ORDER)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return e>0;
    }

    public boolean update_courier_number(int managerId, int schoolId, int orderId, String data) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int e = session.createQuery("update ExpressOrder set courierNumber = :CN where rider_id = :RID and schoolId = :SID and id = :OID and order_state = :OST")
                .setParameter("CN",data)
                .setParameter("RID",managerId)
                .setParameter("SID",schoolId)
                .setParameter("OID",orderId)
                .setParameter("OST",ExpressOrder.GOT_EXPRESS_SENDING)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return e>0;
    }

    public ArrayList<ExpressOrder> getOrderByCN(int managerId, String CN) {
        Session session = sessionFactory.openSession();
        ArrayList<ExpressOrder>res = (ArrayList<ExpressOrder>) session.createQuery("from ExpressOrder where courierNumber = :CN and schoolId in (Select schoolId from SchoolDist where managerId = :M)")
                .setParameter("CN",CN)
                .setParameter("M",managerId)
                .list();
        session.close();
        return res;
    }


    public ArrayList<Reason> listAllReasons() {
        Session session = sessionFactory.openSession();
        ArrayList<Reason>rs = (ArrayList<Reason>) session.createQuery("from Reason")
                .list();
        session.close();
        return rs;
    }

    public boolean updateExpressOrderResultReason(int managerId, int schoolId, int orderId, int status, int reasonId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int e = session.createQuery("update ExpressOrder set order_state = :ST,reason = :REA where id = :OID and rider_id = :MID and schoolId = :SID")
                .setParameter("ST",status)
                .setParameter("REA",reasonId)
                .setParameter("OID",orderId)
                .setParameter("MID",managerId)
                .setParameter("SID",schoolId).executeUpdate();
        session.getTransaction().commit();
        session.close();
        return e>0;
    }

    public ExpressOrder getExpressOrderById(int order_id) {
        Session session = sessionFactory.openSession();
        ExpressOrder order = (ExpressOrder) session.get(ExpressOrder.class, order_id);
        session.close();
        return order;
    }

    public void update(Object it) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(it);
        session.getTransaction().commit();
        session.close();
    }

    public void save(Object o) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
    }
    public Object merge(Object o) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Object it = session.merge(o);
        session.getTransaction().commit();
        session.close();
        return it;
    }

    public boolean managerAccess2Privilege(int managerId, String function_name) {
        Session session = sessionFactory.openSession();
        Long num = (Long) session.createQuery("select coalesce(count(*),0) from PrivilegeDist where managerId = :M and privilegeId in (select id from IndexItemEntity where function = :F)")
                .setParameter("M",managerId)
                .setParameter("F",function_name)
                .uniqueResult();
        session.close();
        return num>0;
    }

    public boolean schoolExist(String name_ch, String name_en) {
        Session session = sessionFactory.openSession();
        Long num = (Long) session.createQuery("select coalesce(count(*),0) from School where schoolName = :N or tag = :T")
                .setParameter("N",name_ch)
                .setParameter("T",name_en)
                .uniqueResult();
        session.close();
        return num>0;
    }


    public boolean managerAccess2PrivilegeId(int managerId, int privilegeId) {
        Session session = sessionFactory.openSession();
        Long num = (Long) session.createQuery("select coalesce(count(*),0) from PrivilegeDist where managerId = :M and privilegeId = :P")
                .setParameter("M",managerId)
                .setParameter("P",privilegeId)
                .uniqueResult();
        session.close();
        return num>0;
    }

    public void privilegeOpt(int managerId, int privilegeId,boolean isGrant) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(!isGrant){
            session.createQuery("delete from PrivilegeDist where privilegeId = :P and managerId = :M")
                    .setParameter("P",privilegeId)
                    .setParameter("M",managerId).executeUpdate();
        }else{
            session.save(new PrivilegeDist(managerId,privilegeId));
        }
        session.getTransaction().commit();
        session.close();
    }

    public List<Manager> listAllManagers() {
        Session session = sessionFactory.openSession();
        List<Manager> rs = session.createQuery("from Manager ").list();
        session.close();
        return rs;
    }

    public ArrayList<IndexItemEntity> listAllIndexEntity() {
        Session session = sessionFactory.openSession();
        ArrayList<IndexItemEntity> rs = (ArrayList<IndexItemEntity>) session.createQuery("from IndexItemEntity ").list();
        session.close();
        return rs;
    }


    public ArrayList<PrivilegeDist> listMyPrivilegeDist(int mid) {
        Session session = sessionFactory.openSession();
        ArrayList<PrivilegeDist> rs = (ArrayList<PrivilegeDist>)
                session.createQuery("from PrivilegeDist where managerId = :M")
                .setParameter("M",mid).list();
        session.close();
        return rs;
    }

    public ArrayList<School> listAllSchool() {
        Session session = sessionFactory.openSession();
        ArrayList<School> rs = (ArrayList<School>) session.createQuery("from School ").list();
        session.close();
        return rs;
    }

    public ArrayList<SchoolDist> listSchoolDist(int sid) {
        Session session = sessionFactory.openSession();
        ArrayList<SchoolDist> rs = (ArrayList<SchoolDist>) session.createQuery("from SchoolDist where schoolId = :S")
                .setParameter("S",sid)
                .list();
        session.close();
        return rs;
    }

    public void schoolManagerOpt(int sid, int mid, boolean isAdd) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(!isAdd){
            session.createQuery("delete from SchoolDist where schoolId = :S and managerId = :M")
                    .setParameter("S",sid)
                    .setParameter("M",mid).executeUpdate();
        }else{
            session.save(new SchoolDist(sid,mid));
        }
        session.getTransaction().commit();
        session.close();
    }

    public void deleteReason(int rid) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Reason where id = :I")
                .setParameter("I",rid)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deleteManager(int mid) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from Manager where id = :M")
                .setParameter("M",mid)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public boolean managerPhoneExist(String phone) {
        Session session = sessionFactory.openSession();
        Long size = (Long) session.createQuery("select coalesce(count(*),0) from Manager where phone = :P")
                .setParameter("P",phone)
                .uniqueResult();
        session.close();
        return size>0;
    }

    public void ConversationEnd(Long cid, int type) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        if(type == 0){
            session.createQuery("update Conversation set isUserEnd = true where cid = :C")
                    .setParameter("C",cid)
                    .executeUpdate();
            session.createQuery("update Conversation set isServerEnd = true where cid = :C and serverid = -1")
                    .setParameter("C",cid)
                    .executeUpdate();
        }else{
            session.createQuery("update Conversation set isServerEnd = true where cid = :C")
                    .setParameter("C",cid)
                    .executeUpdate();
        }
        session.getTransaction().commit();
        session.close();
    }

    public ArrayList<Conversation> listWaitCService(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<Conversation>cs = (ArrayList<Conversation>) session.createQuery("from Conversation where serverid = -1 and schoolId = :S")
                .setParameter("S",schoolId)
                .list();
        session.close();
        return cs;
    }

    public ArrayList<Conversation> listMyCService(int managerId,boolean status) {
        Session session = sessionFactory.openSession();
        ArrayList<Conversation>cs = (ArrayList<Conversation>) session.createQuery("from Conversation where serverid = :S and isServerEnd = :ST")
                .setParameter("ST",status)
                .setParameter("S",managerId)
                .list();
        session.close();
        return cs;
    }

    public ArrayList<ChargeVipOrder> listChargeList(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<ChargeVipOrder> ls = (ArrayList<ChargeVipOrder>) session.createQuery("from ChargeVipOrder where schoolId = :S and has_pay = true ")
                .setParameter("S",schoolId)
                .list();
        session.close();
        return ls;
    }
}
