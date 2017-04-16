package Service;

import Dao.CouponDao;
import Entity.Gift;
import Entity.GiftRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/11.
 */
@Service
public class CouponService {
    @Resource
    CouponDao couponDao;

    // 查询我有多少可用的免单券
    public int howManyFreeIHave(int uid) {
        ArrayList<GiftRecord> rs = couponDao.howManyFreeIHave(uid);
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

    // 使用一张免单券
    public void consumeOneFreeGift(int uid) {
        ArrayList<GiftRecord> rs = couponDao.howManyFreeIHave(uid);
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
}
