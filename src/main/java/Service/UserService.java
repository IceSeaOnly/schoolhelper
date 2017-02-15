package Service;

import Dao.UserDao;
import Entity.*;
import Entity.User.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016/8/30.
 */
@Service
public class UserService {

    @Resource
    UserDao userDao;

    public User getUserByOpenId(String openid) {
        // 查找用户
        User user = userDao.getUserByOpenid(openid);
        // 判断用户是否存在
        // 不存在则自动创建
        if (user == null) {
            user = userDao.createEmpeyNewUser(openid);
        }
        return user;
    }

    public void update(Object obj) {
        userDao.update(obj);
    }

    public ArrayList<SendPart> listAllParts(int schoolId) {
        ArrayList<SendPart> parts = userDao.listAllparts(schoolId);
        return parts == null ? new ArrayList<SendPart>() : parts;
    }

    /**
     * @param only_available  此选项为真时，只返回当前为available为true的item
     * */
    public ArrayList<Express> listAllExpresses(int schoolId,boolean only_available) {
        ArrayList<Express> res = userDao.listAllExpress(schoolId,only_available);
        return res == null ? new ArrayList<Express>() : res;
    }

    // 得到首单优惠的价格
    public int getFirstOrderCost(int cost,int sid) {
        SchoolConfigs sc = getSchoolConfBySchoolId(sid);
        int res = sc.getFirst_cost();
        return res <cost ? cost : res;
    }

    // 查询该phone使用过，如果是则返回true
    public boolean phoneExsit(String express_phone, String sendto_phone) {

        boolean res = userDao.phoneExsitInExpressPhone(express_phone);

        if (res) return true;

        res = userDao.phoneExisttInSendPhone(express_phone);

        if (res) return true;

        res = userDao.phoneExsitInExpressPhone(sendto_phone);

        if (res) return true;

        res = userDao.phoneExisttInSendPhone(sendto_phone);

        return res;
    }

    public void sava(Object order) {
        userDao.save(order);
    }

    public ExpressOrder getExpressOrderById(User user, int id) {
        return userDao.getExpressOrderById(user.getId(), id);
    }


    public String getSchoolShopUrl(int sid) {
        SchoolConfigs sc = getSchoolConfBySchoolId(sid);
        if (sc.getShop_url() == null) return "javascript:alert('正在接入中，敬请期待。');";
        if (sc.getShop_url().length() == 0) return "javascript:alert('正在接入中，敬请期待。');";
        return "document.location='" + sc.getShop_url()+ "';";
    }

    public SchoolConfigs getSchoolConfBySchoolId(int sid) {
        return userDao.getSchoolConfBySchoolId(sid);
    }

    public int ExpressServiceRunning(int sid) {
        SchoolConfigs sc = getSchoolConfBySchoolId(sid);
        int nh = new Date(System.currentTimeMillis()).getHours();
        int nm = new Date(System.currentTimeMillis()).getMinutes();

        if (sc.isHand_close()) { // 手动关闭
            return 1;
        }
        if (nh >= sc.getAuto_close() || nh < sc.getAuto_start()) { // 自动关闭
            System.out.println("now hour:"+nh+" ,start="+sc.getAuto_start()+" ,stop="+sc.getAuto_close());
            return 2;
        }
        return 0;
    }

    public String getServiceStopReason(int state,int sid) {
        SchoolConfigs sc = getSchoolConfBySchoolId(sid);
        if (state == 1)
            return sc.getHand_close_info();
        return sc.getAuto_close_info();
    }

    public ArrayList<SysMsg> getAllSystemMsg(int schoolId) {
        ArrayList<SysMsg> res = userDao.getAllSysMsg(schoolId);
        return res == null ? new ArrayList<SysMsg>() : res;
    }


    public String getLastestNews(int schoolId) {
        String news = userDao.getLastestNews(schoolId);
        return news == null ? "暂无公告" : news;
    }

    public ArrayList<AdGroup> getAdGroups(User u) {
        ArrayList<AdGroup> adgroups = new ArrayList<AdGroup>();
        ArrayList<UserIndexAd> ads = userDao.getAds(u.getSchoolId());
        SchoolConfigs sc = userDao.getSchoolConfBySchoolId(u.getSchoolId());
        if(sc.isHelpSend()){
            ads.add(0,new UserIndexAd("代寄快递","http://image.binghai.site/data/f_42889900.jpg","/user/help_send_express.do",null,u.getSchoolId()));
        }
        if(sc.isSchoolMove()){
            ads.add(0,new UserIndexAd("校园搬运","http://image.binghai.site/data/f_91899796.jpg","/user/schoolmove.do",null,u.getSchoolId()));
        }
        UserIndexAd no_body = new UserIndexAd("虚位以待", "../images/waiting_for_service.png", "#", new Date(),u.getSchoolId());
        AdGroup group = null;
        for (int i = 0; i < ads.size(); i++) {
            if (i % 3 == 0) {
                group = new AdGroup(no_body, no_body, no_body);
                group.setAd1(ads.get(i));
            }
            if (i % 3 == 1) {
                group.setAd2(ads.get(i));
            }
            if (i % 3 == 2) {
                group.setAd3(ads.get(i));
                adgroups.add(group);
            }
        }
        if (ads.size() % 3 != 0) {
            adgroups.add(group);
        }
        return adgroups;
    }

    /**
     * 查询所有vip套餐
     */
    public ArrayList<ChargeVip> getAllVipMeals(int sid) {
        ArrayList<ChargeVip> meals = userDao.getAllVipMeals(sid);
        return meals == null ? new ArrayList<ChargeVip>() : meals;
    }

    public ArrayList<SendExpress> getAllSendExpresses(int schoolId) {
        ArrayList<SendExpress> res;
        res = userDao.getAllSendExpresses(schoolId);
        return res == null ? new ArrayList<SendExpress>() : res;
    }

    public ArrayList<SendExpressOrder> getExpressOrder(String expressname,int schoolId) {
        ArrayList<SendExpressOrder> res = userDao.getExpressOrder(expressname,schoolId);
        return res==null?new ArrayList<SendExpressOrder>():res;
    }

    public ExpressOrder getExpressOrderByOrderKey(String orderKey) {
        return userDao.getExpressOrderByOrderKey(orderKey);
    }

    /** 获取该时段的订单总数*/
    public Long getSendTimeCurrentSum(int sendtime_id,int schoolId) {
        return userDao.getSendTimeCurrentSum(sendtime_id,schoolId);
    }

    /**
     * @param only_available 该参数为真时，只返回avaiable=true的item
     * */
    public ArrayList<SendTime> getAllSendTimes(int schoolId,boolean only_available) {
        return userDao.getAllSendTimes(schoolId,only_available);
    }

    public String getSchoolTag(int schoolId) {
        return userDao.getSchoolTag(schoolId);
    }

    public ArrayList<School> listAllSchool() {
        ArrayList<School> res = userDao.listAllSchool();
        return res == null?new ArrayList<School>():res;
    }

    public User getUserById(int user_id) {
        return userDao.getUserById(user_id);
    }
}
