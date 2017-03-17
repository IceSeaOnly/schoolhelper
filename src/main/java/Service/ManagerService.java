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
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/1/7.
 */
@Service
public class ManagerService {
    @Resource
    ManagerDao managerDao;
    @Resource
    NoticeService noticeService;
    private static ArrayList<Reason> reasons;
    public static ArrayList<School> schools;
    private static Long reason_update_time;



    public Manager ManagerLogin(String phone, String pass) {
        return managerDao.managerLogin(phone,pass);
    }

    public void updateLastLogin(String phone) {
        managerDao.updateLostLogin(phone);
    }

    public ArrayList<ExpressOrder> commonOrderGet(int mid,int schoolId, int sendTime, int year, int month, int day, int orderState) {
        Long from = TimeFormat.data2Timestamp(year,month,day);
        Long end = from+86400000L;
        ArrayList<ExpressOrder> res = managerDao.commonOrderGet(mid,schoolId,from,end,sendTime,orderState);
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

    public boolean managerFetchOrder(int managerId, int schoolId, int orderId, int reasonId, boolean res) {
        return managerDao.managerFetchOrder(managerId,schoolId,orderId,reasonId,res);
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
        noticeService.DistributedLog(new Log(log_tid, LogTag.getTagWithStatus(log_tid).getTagDesc()+" - "+status,managerId).toString());
        //managerDao.save(new Log(log_tid, LogTag.getTagWithStatus(log_tid).getTagDesc()+" - "+status,managerId));
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

    public ArrayList<Manager> listSchoolManagers(int sid) {
        ArrayList<Manager> all = managerDao.listSchoolManagers(sid);
        return all == null?new ArrayList<Manager>():all;
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
    public void managerDividend(int schoolId,int sum,int orderId,String info) {
        // 列出该校所有管理
        ArrayList<Manager>ms = (ArrayList<Manager>) managerDao.listAllManagers(schoolId);
        // 为每个管理分红
        for (int i = 0; i < ms.size(); i++) {
            if(ms.get(i).getSalaryConfig(schoolId) != null)
                managerDao.save(new ChargingSystem(
                        ms.get(i).getId(),
                        orderId,
                        (int)(sum*ms.get(i).getSalaryConfig(schoolId).getRate()),
                        ChargingSystem.Ftype,
                        info));
        }
    }
    /**
     * 赏取件费
     * 返回值为true的时候，表示此单可以分红
     * 返回值为false的时候，表示此单已经分红过，有旧的取件费作废
     * */
    @Resource
    UserDao userDao;
    public boolean rewardFetchOrder(int schoolId, int managerId,int orderId) {
        SchoolConfigs conf = userDao.getSchoolConfBySchoolId(schoolId);
        int eff = managerDao.clearReward(managerId,orderId,ChargingSystem.Qtype);
        log(managerId,11,orderId+"订单重复的取件费已经删除，共"+eff+"条");
        if(conf != null){
            userDao.save(new ChargingSystem(
                    managerId,
                    orderId,
                    conf.getEach_fetch(),
                    ChargingSystem.Qtype,
                    "取件工资"));
        }
        return eff == 0;
    }
    /**
     * 赏配送费
     * */
    public void rewardSendOrder(int managerId, int schoolId, int orderId) {
        SchoolConfigs conf = userDao.getSchoolConfBySchoolId(schoolId);
        ExpressOrder e = userDao.getExpressOrderById(orderId);
        int eff = managerDao.clearReward(managerId,orderId,ChargingSystem.Stype);
        log(managerId,11,orderId+"订单重复的配送费已经删除，共"+eff+"条");
        if(e != null && !e.isLLJJ() && conf != null){
            userDao.save(new ChargingSystem(
                    managerId,
                    orderId,
                    conf.getEach_send(),
                    ChargingSystem.Stype,
                    "配送工资"));
            log(managerId,11,orderId+"订单已赏配送费"+conf.getEach_send());
        }else{
            log(managerId,11,orderId+"订单赏配送费失败，e="+(e==null?"null,conf=":"not null,conf=")+"conf="+(conf==null?"null":"not null")+(e.isLLJJ()?"是楼长交接件":"不是楼长交接件"));
        }
    }

    /**
     * 赏 转交费
     * */
    public void rewardT(int omid, int schoolId, int orderId) {
        log(omid,11,"赏转交费时，"+omid+"对订单"+orderId+"的楼长收件、取件费失效");
        managerDao.clearReward(omid,orderId,ChargingSystem.Rtype);
        managerDao.clearReward(omid,orderId,ChargingSystem.Ttype);
        SchoolConfigs conf = userDao.getSchoolConfBySchoolId(schoolId);
        managerDao.save(new ChargingSystem(omid,orderId,conf.getEach_give(),ChargingSystem.Ttype,"转交楼长所得"));
    }

    /**
     * 赏 楼长接收费用
     * */
    public void rewardR(int managerId, int schoolId, int orderId) {
        log(managerId,11,"赏接收费时，"+managerId+"对订单"+orderId+"的楼长收件、取件费失效");
        managerDao.clearReward(managerId,orderId,ChargingSystem.Rtype);
        managerDao.clearReward(managerId,orderId,ChargingSystem.Ttype);
        SchoolConfigs conf = userDao.getSchoolConfBySchoolId(schoolId);
        managerDao.save(new ChargingSystem(managerId,orderId,conf.getEach_receive(),ChargingSystem.Ttype,"楼长收件所得"));
    }

    public List<ChargingSystem> listMyIncomes(int managerId) {
        List<ChargingSystem>res = managerDao.listMyIncomes(managerId);
        return res == null?new ArrayList<ChargingSystem>():res;
    }


    public ArrayList<PayLog> makePayLogs() {
        Calendar cal = Calendar.getInstance();
        if(cal.get(Calendar.DAY_OF_WEEK) == 1){
            ArrayList<Manager>ms = (ArrayList<Manager>) managerDao.listAllManagers();
            for (int i = 0; i < ms.size(); i++) { //对所有管理员逐一统计
                managerDao.payLog(ms.get(i).getId(),ms.get(i).getOpenId(),ms.get(i).getName());
            }
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

    public ArrayList<FeedBack> listAllFeedBacks() {
        ArrayList<FeedBack>fbs = managerDao.listAllFeedBacks();
        return fbs == null?new ArrayList<FeedBack>():fbs;
    }

    /**
     * 获取我处理的用户反馈
     * */
    public ArrayList<FeedBack> listMyFeedBacks(int managerId) {
        ArrayList<FeedBack> rs = managerDao.listMyFeedBacks(managerId);
        return rs == null?new ArrayList<FeedBack>():rs;
    }

    public FeedBack getFeedBackById(int fid) {
        return managerDao.getFeedBackById(fid);
    }

    public SendTime getSendTimeById(int id) {
        return managerDao.getSendTimeById(id);
    }

    /**
     * 余额支付退款
     * */
    public boolean refundVipPay(int user_id, int shouldPay) {
        return managerDao.refundVipPay(user_id,shouldPay);
    }

    public OutPutOrders getOutPutOrderByKey(String k) {
        return managerDao.getOutPutOrderByKey(k);
    }

    public ArrayList<ExpressOrder> getExpressOrderByIds(ArrayList<Integer> ids) {
        ArrayList<ExpressOrder> rs = managerDao.getExpressOrderByIds(ids);
        return rs == null?new ArrayList<ExpressOrder>():rs;
    }

    /**
     * config:配置信息
     * 根据配置信息提取订单
     * config为一个字符串，含有5个部分【取件人】【新旧件】【区域】【配送时间】
     * 5个部分的默认配置参数为everyone all -1 -1，含义均为不限
     * 两两以空格隔开
     * 若config == ‘none’，则为默认配置
     * */
    public ArrayList<ExpressOrder> listExpressOrderByConfig(int sid, int managerId, String config) {
        if(config.equals("none")){
            config = "everyone all -1 -1";
        }

        String[] cfgs = config.split(" ");
        if(cfgs.length != 4) return null;
        try{
            int pep = (cfgs[0].equals("everyone")?0:1);
            int otype = (cfgs[1].equals("all")?0:1);
            int part = Integer.parseInt(cfgs[2]);
            int stime = Integer.parseInt(cfgs[3]);
            ArrayList<ExpressOrder> res = managerDao.listExpressOrderByConfig(sid,managerId,pep,otype,part,stime);
            return res == null?new ArrayList<ExpressOrder>():res;
        }catch (Exception e){
            log(managerId,11,"管理员"+managerId+"触发了一个异常:"+e.getMessage());
            return null;
        }
    }

    public SchoolMoveOrder getSchoolMoveOrderByKey(String orderKey) {
        return managerDao.getSchoolMoveOrderByKey(orderKey);
    }

    public SendExpressOrder getSendExpressOrderByKey(String orderKey) {
        return managerDao.getSendExpressOrderByKey(orderKey);
    }

    public Long getSchoolMoveOrderSum(int schoolId) {
        return managerDao.getSchoolMoveOrderSum(schoolId);
    }

    public Long getHelpSendOrderSum(int schoolId) {
        return managerDao.getHelpSendOrderSum(schoolId);
    }

    public Long getExpressOrderSum(int schoolId) {
        return managerDao.getExpressOrderSum(schoolId);
    }

    public ArrayList<ExpressOrder> getTodayExpressOrderSum(int schoolId) {
        return managerDao.getTodayExpressOrderSum(schoolId);
    }

    public Long getTodayExpressTodayIncome(int schoolId) {
        return managerDao.getTodayExpressTodayIncome(schoolId);
    }


    /**
     * 支出统计
     * @Param date 开始时间
     * */
    public Long getOutSum(long date) {
        return managerDao.getOutSum(date);
    }

    /**
     * 下载这一天的对账单
     * */
    public List<PayLog> getReconciliationList(Long date) {
        List<Manager>ms = listAllManagers();
        List<PayLog>logs = managerDao.getReconciliationList(date);
        for (int i = 0; i < logs.size(); i++) {
            logs.get(i).setmName(getNameFromList(ms,logs.get(i).getMid()));
        }
        // 借用 pdesc,passwd设置取件数、配送数
        for (int i = 0; i < logs.size(); i++) {
            logs.get(i).setPdesc(getTodayWordIndex(logs.get(i).getMid(),ChargingSystem.Qtype,date));
            logs.get(i).setPasswd(getTodayWordIndex(logs.get(i).getMid(),ChargingSystem.Stype,date));
        }
        return logs;
    }

    /**
     * 获取某个工作人员今天的工作指标，stype为ChargingSystem中的5个指标之一
     * */
    private String getTodayWordIndex(int mid, int stype, Long date) {
        Long sum = managerDao.getTodayWordIndex(mid,stype,date);
        return sum == null?"0":String.valueOf(sum);
    }

    /**
     * 从管理员名单中找出id为mid的名字
     * */
    private String getNameFromList(List<Manager> ms, int mid) {
        for (int i = 0; i < ms.size(); i++) {
            if(ms.get(i).getId() == mid)
                return ms.get(i).getName();
        }
        return "无名氏";
    }

    /**
     * 搜索订单
     * @Param search 姓名/手机号/订单号
     * */
    public ArrayList<ExpressOrder> searchOrders(String search,int sid) {
        if(search.length() == 11 && onlyDigit(search)){ //11位且仅有数字，判断为手机号
            return managerDao.searchOrderByPhone(search,sid);
        }else if(onlyDigit(search)){ //仅有数字，判断为订单号
            ArrayList<ExpressOrder>rs = new ArrayList<ExpressOrder>();
            rs.add(managerDao.getExpressOrderById(Integer.parseInt(search)));
            return rs;
        }else{ //认为是姓名
            return managerDao.searchOrderByName(search,sid);
        }
    }

    private boolean onlyDigit(String search) {
        try{
            Long.parseLong(search);
            return true;
        }catch (Exception e){}
        return false;
    }

    public static String getSchoolName(int schoolId) {
        if(schools != null)
        for (int i = 0; i < schools.size(); i++) {
            if(schools.get(i).getId() == schoolId)
                return schools.get(i).getSchoolName();
        }
        return "校名未知";
    }

    public ArrayList<ExpressOrder> getOrdersByStatus(int managerId, Integer[] status) {
        ArrayList<ExpressOrder> rs = managerDao.getOrdersByStatus(managerId,status);
        return rs == null?new ArrayList<ExpressOrder>():rs;
    }

    public Long getMyIncomeSum(int managerId) {
        return managerDao.getMyIncomeSum(managerId);
    }

    /**
     * 拉取所有用户，如果是sid学校的管理员，则做标记
     * */
    public ArrayList<Manager> listAllManagersAndMarkThisSchool(int sid) {
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

    public Long getWaitIncomeSum(int managerId) {
        return managerDao.getWaitIncomeSum(managerId);
    }

    /**
     * 订单转移
     * */
    public int dealTransferOrder(int managerId, int schoolId, int towho_select, Integer[] checked_orders) {
        return managerDao.dealTransferOrder(managerId,schoolId,towho_select,checked_orders);
    }
}
