package Service;

import Dao.OrderDao;
import Entity.Express;
import Entity.ExpressOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30.
 */
@Service
public class OrderService {
    @Resource
    OrderDao orderDao;

    public ArrayList<ExpressOrder> getOrdersByUserId(int id, int schoolId) {
        ArrayList<ExpressOrder> res = orderDao.getOrdersByUserId(id, schoolId);
        return (res == null ? new ArrayList<ExpressOrder>() : res);
    }

    /**
     * status:0/1
     */
    public List<ExpressOrder> getRefereeOrderBySchool(Integer schoolId, Integer status) {
        List ls = orderDao.getRefereeOrderBySchool(schoolId, status);
        return ls == null ? new ArrayList<ExpressOrder>() : ls;
    }

    /**
     * 超时订单设置为无效
     */
    public void updateOrderOutOfDate(int userId) {
        orderDao.updateOrderOutOfDate(userId);
    }
}
