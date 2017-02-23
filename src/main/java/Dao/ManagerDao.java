package Dao;

import Entity.*;
import Entity.Manager.*;
import Entity.User.User;
import Utils.TimeFormat;
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

    public ArrayList<ExpressOrder> commonOrderGet(int mid,int schoolId, Long from, Long end, int sendTime, int orderState) {
        Session session = sessionFactory.openSession();
        Query q = session.createQuery("from ExpressOrder where has_pay = true and schoolId = :SID and orderTimeStamp between :F and :E"
                +(sendTime == -1?"":" and sendtime_id = :S")
                +(orderState == -1?"":" and order_state = :STA")
                +(mid == -1?" order by id desc":" and rider_id = :MID  order by id desc"))
                .setParameter("SID",schoolId)
                .setParameter("F",from)
                .setParameter("E",end);
        if(sendTime>-1){
            q.setParameter("S",sendTime);
        }

        if(orderState != -1){
            q.setParameter("STA",orderState);
        }

        if(mid != -1){
            q.setParameter("MID",mid);
        }
        ArrayList<ExpressOrder>orders = (ArrayList<ExpressOrder>)q.list();
        session.close();
        for (int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getSchoolId() != schoolId)
                orders.remove(i);
        }
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

    public boolean managerFetchOrder(int managerId, int schoolId, int orderId, int reasonId, boolean res) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int e = session.createQuery("update ExpressOrder set order_state = :ST,reason = :REAID where rider_id = :RID and schoolId = :SID and id = :OID")
                .setParameter("ST",res?ExpressOrder.GOT_EXPRESS_SENDING:ExpressOrder.ORDER_NOT_EXIST)
                .setParameter("RID",managerId)
                .setParameter("REAID",reasonId)
                .setParameter("SID",schoolId)
                .setParameter("OID",orderId)
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
        int e = session.createQuery("update ExpressOrder set rider_id = :R, order_state = :ST,reason = :REA where id = :OID and schoolId = :SID")
                .setParameter("R",managerId)
                .setParameter("ST",status)
                .setParameter("REA",reasonId)
                .setParameter("OID",orderId)
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

    public List<Manager> listAllManagers(int scid) {
        Session session = sessionFactory.openSession();
        List<Manager> rs = session.createQuery("from Manager where id in (select managerId from SchoolDist where schoolId = :S)")
                .setParameter("S",scid)
                .list();
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
        ArrayList<Conversation>cs = (ArrayList<Conversation>) session.createQuery("from Conversation where serverid = -1 and schoolId = :S  order by id desc")
                .setParameter("S",schoolId)
                .list();
        session.close();
        return cs;
    }

    public ArrayList<Conversation> listMyCService(int managerId,boolean status) {
        Session session = sessionFactory.openSession();
        ArrayList<Conversation>cs = (ArrayList<Conversation>) session.createQuery("from Conversation where serverid = :S and isServerEnd = :ST  order by id desc")
                .setParameter("ST",status)
                .setParameter("S",managerId)
                .list();
        session.close();
        return cs;
    }

    public ArrayList<ChargeVipOrder> listChargeList(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<ChargeVipOrder> ls = (ArrayList<ChargeVipOrder>) session.createQuery("from ChargeVipOrder where schoolId = :S and has_pay = true order by id desc")
                .setParameter("S",schoolId)
                .list();
        session.close();
        return ls;
    }

    public ArrayList<User> listVIP(int schoolId) {
        Session session = sessionFactory.openSession();
        ArrayList<User> rs = (ArrayList<User>) session.createQuery("from User where my_money > 0 and schoolId = :S")
                .setParameter("S",schoolId)
                .list();
        session.close();
        return rs;
    }

    public void clearReward(int omid, int orderId, int ttype) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("update ChargingSystem set valid = false where mid = :M and mtype  = :T and oid = :O")
                .setParameter("M",omid)
                .setParameter("T",ttype)
                .setParameter("O",orderId)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public List<ChargingSystem> listMyIncomes(int managerId) {
        Session session = sessionFactory.openSession();
        List<ChargingSystem>rs = session.createQuery("from ChargingSystem where mid = :M order by id desc")
                .setParameter("M",managerId)
                .setMaxResults(100)
                .list();
        session.close();
        return rs;
    }

    /**
     * 生成管理员id为mid的工资单
     * */
    public PayLog payLog(int mid,String openId,String name) {
        PayLog p = null;
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // 做标记
        int eff = session.createQuery("update ChargingSystem set checked = true where mid = :M and valid = true and settled = false ")
                .setParameter("M",mid).executeUpdate();
        if (eff != 0){
            // 统计
            Long sum = (Long) session.createQuery("select sum(money) from ChargingSystem where mid = :M and valid = true and checked = true and settled = false ")
                    .setParameter("M",mid).uniqueResult();

            if(sum < 100){ //最小转账金额1元
                session.getTransaction().rollback();
                session.close();
                return null;
            }
            // 标记已结算
            session.createQuery("update ChargingSystem set settled = true  where mid = :M and valid = true and checked = true and settled = false ")
                    .setParameter("M",mid).executeUpdate();
            p = (PayLog) merge(
                    new PayLog(name,openId,
                            Long.parseLong(mid+""+System.currentTimeMillis()),
                            sum.intValue(),
                            name+",id:"+mid+","+ TimeFormat.format(System.currentTimeMillis()),
                            "pass_not_visiable",mid));
        }
        session.getTransaction().commit();
        session.close();
        return p;
    }

    public PayLog getPayLogById(int orderId) {
        Session session = sessionFactory.openSession();
        PayLog p = (PayLog) session.createQuery("from PayLog where id = :O")
                .setParameter("O",orderId)
                .uniqueResult();
        session.close();
        return p;
    }

    public ArrayList<PayLog> listPayLogs() {
        Session session = sessionFactory.openSession();
        ArrayList<PayLog>ps = (ArrayList<PayLog>) session.createQuery("from PayLog where hasPay = false ")
                .list();
        session.close();
        return ps;
    }

    public SysMsg getShoolNoticeById(int nid) {
        Session session = sessionFactory.openSession();
        SysMsg m = (SysMsg) session.get(SysMsg.class,nid);
        session.close();
        return m;
    }

    public void deleteVipMeal(int id) {
        Session session =sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from ChargeVip where id = :I")
                .setParameter("I",id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public ArrayList<SendExpressOrder> listHelpSendOrders(int sid) {
        Session session = sessionFactory.openSession();
        ArrayList<SendExpressOrder>rs = (ArrayList<SendExpressOrder>) session.createQuery("from SendExpressOrder where haspay = true and schoolId = :S order by id desc")
                .setMaxResults(50)
                .setParameter("S",sid)
                .list();
        session.close();
        return rs;
    }

    public void deleteSendExpress(int id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from SendExpress where id = :D")
                .setParameter("D",id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public SendExpress getSendExpressById(int id) {
        Session session = sessionFactory.openSession();
        SendExpress se = (SendExpress) session.createQuery("from SendExpress where id = :I")
                .setParameter("I",id)
                .uniqueResult();
        session.close();
        return se;
    }

    public Express geteExpressById(int id) {
        Session session = sessionFactory.openSession();
        Express se = (Express) session.createQuery("from Express where id = :I")
                .setParameter("I",id)
                .uniqueResult();
        session.close();
        return se;
    }

    public ArrayList<FeedBack> listAllFeedBacks() {
        Session session = sessionFactory.openSession();
        ArrayList<FeedBack> rs = (ArrayList<FeedBack>) session.createQuery("from FeedBack where responsed = false").list();
        session.close();
        return rs;
    }

    public ArrayList<FeedBack> listMyFeedBacks(int managerId) {
        Session session = sessionFactory.openSession();
        ArrayList<FeedBack> rs =
                (ArrayList<FeedBack>) session.createQuery("from FeedBack where respMid = :M")
                        .setParameter("M",managerId)
                        .list();
        session.close();
        return rs;
    }

    public FeedBack getFeedBackById(int fid) {
        Session session = sessionFactory.openSession();
        FeedBack rs= (FeedBack) session.createQuery("from FeedBack where id = :M")
                        .setParameter("M",fid)
                        .uniqueResult();
        session.close();
        return rs;
    }

    public SendTime getSendTimeById(int id) {
        Session session = sessionFactory.openSession();
        SendTime st = (SendTime) session.createQuery("from SendTime where id = :I")
                .setParameter("I",id)
                .uniqueResult();
        session.close();
        return st;
    }

    public boolean refundVipPay(int user_id, int shouldPay) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int eff = session.createQuery("update User set my_money = my_money + :M where id = :I")
                .setParameter("M",shouldPay)
                .setParameter("I",user_id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return eff>0;
    }

    public OutPutOrders getOutPutOrderByKey(String k) {
        Session session = sessionFactory.openSession();
        OutPutOrders out = (OutPutOrders) session.createQuery("from OutPutOrders where skey = :K")
                .setParameter("K",k)
                .uniqueResult();
        session.close();
        return out;
    }

    public ArrayList<ExpressOrder> getExpressOrderByIds(ArrayList<Integer> ids) {
        Session session = sessionFactory.openSession();
        ArrayList<ExpressOrder> rs =
                (ArrayList<ExpressOrder>) session.createQuery("from ExpressOrder where id in :S")
                .setParameterList("S",ids)
                .list();
        session.close();
        return rs;
    }

    /**
     * 复杂参数的订单获取器
     * */
    public ArrayList<ExpressOrder> listExpressOrderByConfig(int sid,int mid, int pep, int otype, int part, int stime) {
        Session session = sessionFactory.openSession();
        String sql = "from ExpressOrder where schoolId = :SID "
                +(pep==0?"":"and rider_id = :R ")
                +(otype==0?"and (order_state = 2 or order_state = -3) ":"and order_state = 2 ")
                +(part<0?"":"and part = :P ")
                +(stime<0?"":"and sendtime_id = :St");
        Query query = session.createQuery(sql);
        query.setParameter("SID",sid);
        if(pep == 1)
            query.setParameter("R",mid);
        if(part>-1)
            query.setParameter("P",part);
        if(stime>-1)
            query.setParameter("St",stime);
        ArrayList<ExpressOrder>rs = (ArrayList<ExpressOrder>) query.list();
        session.close();
        return rs;
    }

    public SchoolMoveOrder getSchoolMoveOrderByKey(String orderKey) {
        Session session = sessionFactory.openSession();
        SchoolMoveOrder so = (SchoolMoveOrder) session.createQuery("from SchoolMoveOrder where orderKey = :K")
                .setParameter("K",orderKey)
                .uniqueResult();
        session.close();
        return so;
    }

    public SendExpressOrder getSendExpressOrderByKey(String orderKey) {
        Session session = sessionFactory.openSession();
        SendExpressOrder so = (SendExpressOrder) session.createQuery("from SendExpressOrder where orderKey = :K")
                .setParameter("K",orderKey)
                .uniqueResult();
        session.close();
        return so;
    }

    public Long getSchoolMoveOrderSum(int schoolId) {
        Session session  = sessionFactory.openSession();
        Long sum = (Long) session.createQuery("select coalesce(count(*),0) from SchoolMoveOrder where schoolId = :S and haspay = true ")
                .setParameter("S",schoolId)
                .uniqueResult();
        session.close();
        return sum;
    }

    public Long getHelpSendOrderSum(int schoolId) {
        Session session  = sessionFactory.openSession();
        Long sum = (Long) session.createQuery("select coalesce(count(*),0) from SendExpressOrder where schoolId = :S and haspay = true ")
                .setParameter("S",schoolId)
                .uniqueResult();
        session.close();
        return sum;
    }

    public Long getExpressOrderSum(int schoolId) {
        Session session  = sessionFactory.openSession();
        Long sum = (Long) session.createQuery("select coalesce(count(*),0) from ExpressOrder where schoolId = :S and order_state != -1 and has_pay = true ")
                .setParameter("S",schoolId)
                .uniqueResult();
        session.close();
        return sum;
    }

    public ArrayList<ExpressOrder> getTodayExpressOrderSum(int schoolId) {
        Session session  = sessionFactory.openSession();
        ArrayList<ExpressOrder> sum = (ArrayList<ExpressOrder>) session.createQuery("from ExpressOrder where schoolId = :S and has_pay = true and orderTimeStamp > :T")
                .setParameter("T",TimeFormat.getTimesmorning())
                .setParameter("S",schoolId)
                .list();
        session.close();
        return sum;
    }

    public Long getTodayExpressTodayIncome(int schoolId) {
        Session session = sessionFactory.openSession();
        Long sum = (Long) session.createQuery("select coalesce(sum(shouldPay),0) from ExpressOrder where schoolId = :S and has_pay = true and orderTimeStamp > :T")
                .setParameter("T",TimeFormat.getTimesmorning())
                .setParameter("S",schoolId)
                .uniqueResult();
        session.close();
        return sum;
    }


    public Long getOutSum(long date) {
        Session session = sessionFactory.openSession();
        Long sum = (Long) session.createQuery("select coalesce(sum(money),0) from ChargingSystem where valid = true and time between :S and :E")
                .setParameter("S",date)
                .setParameter("E",date==0?9487663392000L:date+86400000)
                .uniqueResult();
        session.close();
        return sum;
    }


    public List<PayLog> getReconciliationList(Long date) {
        ArrayList<PayLog>logs = new ArrayList<PayLog>();
        Session session = sessionFactory.openSession();
        List rs = session.createQuery("select mid,sum(money) from ChargingSystem where valid = true and time between :S and :E group by mid")
                .setParameter("S",date)
                .setParameter("E",date+86400000)
                .list();
        session.close();
        if(rs != null){
            for (Object data :
                    rs) {
                Object[] rss = (Object[]) data;
                logs.add(new PayLog((Integer) rss[0],(Long) rss[1]));
            }
        }
        return logs;
    }

    public ArrayList<ExpressOrder> searchOrderByPhone(String search, int sid) {
        ArrayList<ExpressOrder> rs = new ArrayList<ExpressOrder>();
        Session session = sessionFactory.openSession();
        rs.addAll((ArrayList<ExpressOrder>) session.createQuery("from ExpressOrder where has_pay = true and schoolId = :S and (express_phone = :P or receive_phone = :P) order by id desc")
                .setParameter("S",sid)
                .setParameter("P",search)
                .list());
        session.close();
        return rs;
    }

    public ArrayList<ExpressOrder> searchOrderByName(String search, int sid) {
        ArrayList<ExpressOrder> rs = new ArrayList<ExpressOrder>();
        Session session = sessionFactory.openSession();
        rs.addAll((ArrayList<ExpressOrder>) session.createQuery("from ExpressOrder where has_pay = true and schoolId = :S and (express_name = :P or receive_name = :P) order by id desc")
                .setParameter("S",sid)
                .setParameter("P",search)
                .list());
        session.close();
        return rs;
    }

    public ArrayList<ExpressOrder> getOrdersByStatus(int managerId, Integer[] status) {
        ArrayList<ExpressOrder> rs = null;
        Session session = sessionFactory.openSession();
        rs = (ArrayList<ExpressOrder>) session.createQuery("from ExpressOrder where has_pay = true and rider_id = :R and order_state in :ST order by id desc")
                .setParameterList("ST",status)
                .setParameter("R",managerId)
                .list();
        session.close();
        return rs;
    }
}
