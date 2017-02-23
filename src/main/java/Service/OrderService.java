package Service;

import Dao.OrderDao;
import Entity.ExpressOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/30.
 */
@Service
public class OrderService {
    @Resource
    OrderDao orderDao;

    public ArrayList<ExpressOrder> getOrdersByUserId(int id,int schoolId) {
        ArrayList<ExpressOrder> res = orderDao.getOrdersByUserId(id,schoolId);
        return (res == null?new ArrayList<ExpressOrder>():res);
    }

    /**
     * 超时订单设置为无效
     * */
    public void updateOrderOutOfDate(int userId) {
        orderDao.updateOrderOutOfDate(userId);
    }
}
