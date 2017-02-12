package Service;

import Dao.ManagerDao;
import Dao.UserDao;
import Entity.*;
import Entity.Manager.*;
import Entity.User.User;
import Utils.TimeFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/7.
 */
@Service
public class ManagerService {
    @Resource
    ManagerDao managerDao;
    private static ArrayList<Reason> reasons;
    private static Long reason_update_time;


    public Manager ManagerLogin(String phone, String pass) {
        return managerDao.managerLogin(phone,pass);
    }

    public void updateLastLogin(String phone) {
        managerDao.updateLostLogin(phone);
    }

    public ArrayList<ExpressOrder> commonOrderGet(int schoolId, int sendTime, int year, int month, int day, int orderState) {
        Long from = TimeFormat.data2Timestamp(year,month,day);
        Long end = from+86400000L;
        ArrayList<ExpressOrder> res = managerDao.commonOrderGet(schoolId,from,end,sendTime,orderState);
        return res == null?new ArrayList<ExpressOrder>():res;
    }

    public School getSchoolById(int schoolId) {
        return managerDao.getSchoolById(schoolId);
    }

    public ArrayList<School> listMySchool(int id) {
        return managerDao.listMySchool(id);
    }

    public ArrayList<IndexItemEntity> listMyFunctions(int managerId) {
        ArrayList<IndexItemEntity>res = managerDao.listMyFunctions(managerId);
        return res == null?new ArrayList<IndexItemEntity>():res;
    }


    public Manager getManagerById(int managerId){
        return managerDao.getManagerById(managerId);
    }
    /**
     * 判断manger是否可以管理school
     * */
    public boolean managerAccess2School(int managerId, int schoolId) {
        return managerDao.managerAccess2School(managerId,schoolId);
    }

    /**
     * 管理员接单，接单成功返回true，否则false
     * */
    public boolean managerTakeOrder(int managerId, int schoolId, int orderId) {
        String managerName = getManagerById(managerId).getName();
        return managerDao.managerTakeOrder(managerId,managerName,schoolId,orderId);
    }

    public boolean managerFetchOrder(int managerId, int schoolId, int orderId) {
        return managerDao.managerFetchOrder(managerId,schoolId,orderId);
    }

    /**
     * 补充订单的快递单号
     * */
    public boolean update_courier_number(int managerId, int schoolId, int orderId, String data) {
        return managerDao.update_courier_number(managerId,schoolId,orderId,data);
    }

    /**
     * 管理员根据订单号查看详单
     * 管理员需可以管理该校
     * */
    public ArrayList<ExpressOrder> getOrderByCN(int managerId, String CN) {
        ArrayList<ExpressOrder> res = managerDao.getOrderByCN(managerId,CN);
        return res == null?new ArrayList<ExpressOrder>():res;
    }


    public void sp_listAllReason(){
        reasons = managerDao.listAllReasons();
    }
    public ArrayList<Reason> listAllReasons(int type) {
        if(reason_update_time == null || System.currentTimeMillis() - reason_update_time > 90000){
            sp_listAllReason();
            reason_update_time = System.currentTimeMillis();
        }
        ArrayList<Reason>t = new ArrayList<Reason>();
        for (int i = 0; i < reasons.size(); i++) {
            if(reasons.get(i).getType() == type)
                t.add(reasons.get(i));
        }
        return t;
    }

    public boolean updateExpressOrderResultReason(int managerId, int schoolId, int orderId, int status, int reasonId) {
        return managerDao.updateExpressOrderResultReason(managerId,schoolId,orderId,status,reasonId);
    }

    public static String reason2String(int reason) {
        for (int i = 0; i < reasons.size(); i++) {
            if(reasons.get(i).getId() == reason)
                return reasons.get(i).getWhy();
        }
        return "【特征无记录】";
    }

    public ExpressOrder getExpressOrderById(int orderId) {
        return managerDao.getExpressOrderById(orderId);
    }

    public void update(Object it) {
        managerDao.update(it);
    }

    public void log(int managerId,int log_tid,String status){
        managerDao.save(new Log(log_tid, LogTag.getTagWithStatus(log_tid).getTagDesc()+" - "+status,managerId));
    }

    /**
     * 判断管理员是否有名为
     * @function_name （权限名）
     * 的权限
     * */
    public boolean managerAccess2Privilege(int managerId, String function_name) {
        return managerDao.managerAccess2Privilege(managerId,function_name);
    }

    /**
     * 检查该学校是否已经存在
     * */
    public boolean schoolExist(String name_ch, String name_en) {
        return managerDao.schoolExist(name_ch,name_en);
    }

    public void createSchool(String name_ch, String name_en, Long servicePhone) {
        School sc = (School) managerDao.merge(new School(name_ch,name_en));
        SchoolConfigs conf = new SchoolConfigs(sc.getId());
        conf.setServicePhone(servicePhone);
        managerDao.save(conf);
    }

    public Object merge(Object obj){
        return managerDao.merge(obj);
    }

    public boolean managerAccess2PrivilegeId(int managerId, int privilegeId) {
        return managerDao.managerAccess2PrivilegeId(managerId,privilegeId);
    }

    public void revokePrivilege(int managerId, int privilegeId) {
        managerDao.privilegeOpt(managerId,privilegeId,false);
    }

    public void grantPrivilege(int managerId, int privilegeId) {
        managerDao.privilegeOpt(managerId,privilegeId,true);
    }

    public List<Manager> listAllManagers() {
        List<Manager>ms = managerDao.listAllManagers();
        return ms == null?new ArrayList<Manager>():ms;
    }

    public ArrayList<IndexItemEntity> editPrivilege(int mid) {
        ArrayList<IndexItemEntity> all = listAllIndexEntity();
        ArrayList<PrivilegeDist> mine = listMyPrivilegeDist(mid);
        ArrayList<Integer>indexs = new ArrayList<Integer>();
        for (int i = 0; i < mine.size(); i++) {
            indexs.add(mine.get(i).getPrivilegeId());
        }
        for (int i = 0; i < all.size(); i++) {
            if(indexs.contains(all.get(i).getId()))
                all.get(i).setTmp_status(true);
            else
                all.get(i).setTmp_status(false);
        }
        return all;
    }

    private ArrayList<PrivilegeDist> listMyPrivilegeDist(int mid) {
        ArrayList<PrivilegeDist>  rs = managerDao.listMyPrivilegeDist(mid);
        return rs == null?new ArrayList<PrivilegeDist>():rs;
    }

    private ArrayList<IndexItemEntity> listAllIndexEntity() {
        ArrayList<IndexItemEntity>its = managerDao.listAllIndexEntity();
        return its == null?new ArrayList<IndexItemEntity>():its;
    }

    public ArrayList<School> lisAllSchool() {
        return managerDao.listAllSchool();
    }

    public ArrayList<Manager> lisSchoolManagers(int sid) {
        ArrayList<Manager> all = (ArrayList<Manager>) listAllManagers();
        ArrayList<SchoolDist> sl = listSchoolDist(sid);

        ArrayList<Integer>indexs = new ArrayList<Integer>();
        for (int i = 0; i < sl.size(); i++) {
            indexs.add(sl.get(i).getManagerId());
        }
        for (int i = 0; i < all.size(); i++) {
            if(indexs.contains(all.get(i).getId()))
                all.get(i).setTmp_tag(true);
            else
                all.get(i).setTmp_tag(false);
        }
        return all;
    }

    private ArrayList<SchoolDist> listSchoolDist(int sid) {
        ArrayList<SchoolDist>rs = managerDao.listSchoolDist(sid);
        return rs == null?new ArrayList<SchoolDist>():rs;
    }

    public void deleteSchoolManager(int sid, int mid) {
        managerDao.schoolManagerOpt(sid,mid,false);
    }

    public void addSchoolManager(int sid, int mid) {
        managerDao.schoolManagerOpt(sid,mid,true);
    }

    public void deleteReason(int rid) {
        managerDao.deleteReason(rid);
        sp_listAllReason();
    }

    public void save(Object obj) {
        managerDao.save(obj);
    }

    public void deleteManager(int mid) {
        managerDao.deleteManager(mid);
    }

    public boolean managerPhoneExist(String phone) {
        return managerDao.managerPhoneExist(phone);
    }

    public void ConversationEnd(Long cid, int type) {
        managerDao.ConversationEnd(cid,type);
    }

    public ArrayList<Conversation> listWaitCService(int schoolId) {
        ArrayList<Conversation> res = managerDao.listWaitCService(schoolId);
        return res == null?new ArrayList<Conversation>():res;
    }

    public ArrayList<Conversation> listInCService(int managerId) {
        ArrayList<Conversation> res = managerDao.listMyCService(managerId,false);
        return res == null?new ArrayList<Conversation>():res;
    }

    public ArrayList<Conversation> listCompleteCService(int managerId) {
        ArrayList<Conversation> res = managerDao.listMyCService(managerId,true);
        return res == null?new ArrayList<Conversation>():res;
    }

    public ArrayList<ChargeVipOrder> listChargeList(int schoolId) {
        ArrayList<ChargeVipOrder>ls = managerDao.listChargeList(schoolId);
        return ls == null ?new ArrayList<ChargeVipOrder>():ls;
    }

    public ArrayList<User> listVIP(int schoolId) {
        ArrayList<User> rs = managerDao.listVIP(schoolId);
        return rs == null?new ArrayList<User>():rs;
    }

    /**
     * 管理分红
     * */
    public void managerDividend(int schoolId,int sum,int orderId) {
        // 列出该校所有管理
        ArrayList<Manager>ms = (ArrayList<Manager>) managerDao.listAllManagers(schoolId);
        // 为每个管理分红
        for (int i = 0; i < ms.size(); i++) {
            if(ms.get(i).getDividendRatio() > 0.00)
                managerDao.save(new ChargingSystem(
                        ms.get(i).getId(),
                        orderId,
                        (int)(sum*ms.get(i).getDividendRatio()),
                        ChargingSystem.Ftype,
                        "取件订单分红"));
        }
    }
    /**
     * 赏取件费
     * */
    @Resource
    UserDao userDao;
    public void rewardFetchOrder(int schoolId, int managerId,int orderId) {
        SchoolConfigs conf = userDao.getSchoolConfBySchoolId(schoolId);
        if(conf != null){
            userDao.save(new ChargingSystem(
                    managerId,
                    orderId,
                    conf.getEach_fetch(),
                    ChargingSystem.Qtype,
                    "取件工资"));
        }
    }
    /**
     * 赏配送费
     * */
    public void rewardSendOrder(int managerId, int schoolId, int orderId) {
        SchoolConfigs conf = userDao.getSchoolConfBySchoolId(schoolId);
        ExpressOrder e = userDao.getExpressOrderById(managerId,orderId);
        if(e != null && !e.isLLJJ() && conf != null){
            userDao.save(new ChargingSystem(
                    managerId,
                    orderId,
                    conf.getEach_send(),
                    ChargingSystem.Stype,
                    "配送工资"));
        }
    }

    /**
     * 赏 转交费
     * */
    public void rewardT(int omid, int schoolId, int orderId) {
        managerDao.clearReward(omid,orderId,ChargingSystem.Rtype);
        SchoolConfigs conf = userDao.getSchoolConfBySchoolId(schoolId);
        managerDao.save(new ChargingSystem(omid,orderId,conf.getEach_give(),ChargingSystem.Ttype,"转交楼长所得"));
    }

    /**
     * 赏 楼长接收费用
     * */
    public void rewardR(int managerId, int schoolId, int orderId) {
        managerDao.clearReward(managerId,orderId,ChargingSystem.Ttype);
        SchoolConfigs conf = userDao.getSchoolConfBySchoolId(schoolId);
        managerDao.save(new ChargingSystem(managerId,orderId,conf.getEach_receive(),ChargingSystem.Ttype,"楼长收件所得"));
    }

    public List<ChargingSystem> listMyIncomes(int managerId) {
        List<ChargingSystem>res = managerDao.listMyIncomes(managerId);
        return res == null?new ArrayList<ChargingSystem>():res;
    }

    private static Long lastLogs = null;
    public ArrayList<PayLog> makePayLogs() {
        if(lastLogs == null)
            lastLogs = System.currentTimeMillis();
        if(System.currentTimeMillis() - lastLogs > 1800000){
            ArrayList<PayLog> rs = new ArrayList<PayLog>();
            lastLogs = System.currentTimeMillis();
            ArrayList<Manager>ms = (ArrayList<Manager>) managerDao.listAllManagers();
            for (int i = 0; i < ms.size(); i++) { //对所有管理员逐一统计
                PayLog t = managerDao.payLog(ms.get(i).getId(),ms.get(i).getOpenId(),ms.get(i).getName());
                if(t != null){
                    rs.add(t);
                }
            }
            return rs;
        }
        ArrayList<PayLog> rs = managerDao.listPayLogs();
        return rs==null?new ArrayList<PayLog>():rs;
    }

    public PayLog getPayLogById(int orderId) {
        return managerDao.getPayLogById(orderId);
    }

    public SysMsg getShoolNoticeById(int nid) {
        return managerDao.getShoolNoticeById(nid);
    }

    /**
     * 全校发布通知
     **/
    public SysMsg publis_notice(int schoolId, int managerId, String title, String content) {
        Manager m = managerDao.getManagerById(managerId);
        return (SysMsg) merge(new SysMsg(title,content,schoolId,managerId,m.getName()));
    }

    public void deleteVipMeal(int id) {
        managerDao.deleteVipMeal(id);
    }

    /**
     * 用户有效订单-1
     * */
    public void orderSumCutOne(int user_id) {
        userDao.orderSumCutOne(user_id);
    }

    public ArrayList<SendExpressOrder> listHelpSendOrders(int sid) {
        ArrayList<SendExpressOrder> rs = managerDao.listHelpSendOrders(sid);
        return rs == null ?new ArrayList<SendExpressOrder>():rs;
    }

    public void deleteSendExpress(int id) {
        managerDao.deleteSendExpress(id);
    }

    public SendExpress getSendExpressById(int id) {
        return managerDao.getSendExpressById(id);
    }

    public Express geteExpressById(int id) {
        return managerDao.geteExpressById(id);
    }
}
