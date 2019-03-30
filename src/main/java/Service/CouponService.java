package Service;

import Dao.CouponDao;
import Entity.Gift;
import Entity.GiftRecord;
import Entity.User.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/4/11.
 */
@Service
public class CouponService {
    @Resource
    CouponDao couponDao;

    // 查询我有多少可用的免单券
    public int howManyFreeIHave(User user) {
        ArrayList<GiftRecord> rs = couponDao.howManyFreeIHave(user.getId(),user.getSchoolId());
        if(rs == null) return 0;
        int sum = rs.size();
        ArrayList<Integer> outOfdate = new ArrayList<Integer>();
        for (int i = 0; i < rs.size(); i++) {
            if(rs.get(i).getOutOfDate() < System.currentTimeMillis()){
                sum--;
                outOfdate.add(rs.get(i).getId());
            }
        }
        if(outOfdate.size() > 0)
            couponDao.setGiftRecordOutOfDate(outOfdate);
        return sum;
    }

    public void setCouponOutOfDate(User user){
        ArrayList<GiftRecord> all = getMyCoupons(user.getId());
        if(all == null || all.size() == 0) return;
        ArrayList<Integer> outOfdate = new ArrayList<Integer>();
        for (int i = 0; i < all.size(); i++) {
            if(all.get(i).getOutOfDate() < System.currentTimeMillis()){
                outOfdate.add(all.get(i).getId());
            }
        }
        if(outOfdate.size() > 0)
            couponDao.setGiftRecordOutOfDate(outOfdate);
    }

    // 使用一张免单券
    public void consumeOneFreeGift(User user) {
        ArrayList<GiftRecord> rs = couponDao.howManyFreeIHave(user.getId(),user.getSchoolId());
        if(rs.size() > 0){
            ArrayList<Integer> outOfdate = new ArrayList<Integer>();
            outOfdate.add(rs.get(0).getId());
            couponDao.consumeFreeGift(outOfdate);
        }
    }

    public ArrayList<GiftRecord> getMyCoupons(int id) {
        ArrayList<GiftRecord> rs = couponDao.getMyCoupons(id);
        return rs == null ? new ArrayList<GiftRecord>():rs;
    }

    // 返回g活动最新至多100条记录
    public ArrayList<GiftRecord> listLasted100Record(int g) {
        ArrayList<GiftRecord> rs = couponDao.listLasted100Record(g);
        return rs == null?new ArrayList<GiftRecord>():rs;
    }

    public Long sumAllRecord(int g) {
        Long sum = couponDao.sumAllRecord(g);
        return sum == null ?0:sum;
    }

    public GiftRecord consumeMaxCoupon(User user) {
        GiftRecord g = couponDao.getMaxLiJianCoupon(user.getId(),user.getSchoolId());
        if(g != null){
            couponDao.consumeFreeGift(Arrays.asList(g.getId()));
        }
        return g;
    }

    public int howManyCouponIHave(int userId) {
        return couponDao.howManyCouponIHave(userId);
    }

    public GiftRecord findById(int userid, int couponId) {
        ArrayList<GiftRecord> ls = getMyCoupons(userid);
        for (GiftRecord l : ls) {
            if(l.getId() == couponId){
                return l;
            }
        }
        return null;
    }

    public void consumeOne(int couponId) {
        couponDao.consumeFreeGift(Arrays.asList(couponId));
    }

    public GiftRecord consumeOneByCtype(User user, int ctype) {
        howManyFreeIHave(user);
        ArrayList<GiftRecord> ls = getMyCoupons(user.getId());
        for (GiftRecord l : ls) {
            if(l.isValid() && !l.isUsed() && l.getCtype() == ctype){
                return l;
            }
        }
        return null;
    }
}
