package Entity;

/**
 * Created by Administrator on 2016/9/13.
 * 用户首页的广告组，每组三个，不足三个的以“虚位以待”填充
 */
public class AdGroup {
    private UserIndexAd ad1;
    private UserIndexAd ad2;
    private UserIndexAd ad3;

    public AdGroup(UserIndexAd ad1, UserIndexAd ad2, UserIndexAd ad3) {
        this.ad1 = ad1;
        this.ad2 = ad2;
        this.ad3 = ad3;
    }

    public UserIndexAd getAd1() {
        return ad1;
    }

    public void setAd1(UserIndexAd ad1) {
        this.ad1 = ad1;
    }

    public UserIndexAd getAd2() {
        return ad2;
    }

    public void setAd2(UserIndexAd ad2) {
        this.ad2 = ad2;
    }

    public UserIndexAd getAd3() {
        return ad3;
    }

    public void setAd3(UserIndexAd ad3) {
        this.ad3 = ad3;
    }
}
