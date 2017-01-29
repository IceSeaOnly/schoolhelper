package Utils;

import Entity.ExpressOrder;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/11/24.
 */
public class OrderShortByBuild implements Comparator<ExpressOrder> {
    public int compare(ExpressOrder o1, ExpressOrder o2) {
        return o1.getSendTo().compareTo(o2.getSendTo());
    }
}
