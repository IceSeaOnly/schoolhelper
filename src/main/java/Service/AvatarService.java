package Service;

import Dao.AvatarDao;
import Entity.Avatars;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/3/26.
 */
@Service
public class AvatarService {
    @Resource
    AvatarDao avatarDao;

    /**
     * 任意获取一个符合性别的头像
     * */
    public Avatars getRandomAvatar(int sex){
        Avatars avatars = avatarDao.getRandomAvatar(sex);
        return avatars == null?new Avatars(1,"http://image.binghai.site/data/f_99555199.png"):avatars;
    }
}
