package Controllers.User.Helper;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/6/26.
 * 主要逻辑写在这里，可以在这个目录下新建其他文件
 * 在其他包内新建时主要建在对应的包内
 */
@Controller
@RequestMapping("help")
public class ExpressInfoHelper {
    @RequestMapping("test")
    @ResponseBody
    public String test(){
        return "hello";
    }
}
