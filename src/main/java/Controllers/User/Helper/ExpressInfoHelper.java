package Controllers.User.Helper;

import Dao.UKuaidiDao;
import Entity.Kuaidi;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.sql.ordering.antlr.SortSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.parsing.SourceExtractor;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;


import javax.annotation.Resource;
import javax.persistence.metamodel.SetAttribute;
import javax.servlet.http.HttpServletRequest;
import java.awt.event.KeyAdapter;
import java.net.SocketPermission;
import java.util.ArrayList;
import java.util.List;

import Service.KuaiDiService;

/**
 * Created by Administrator on 2017/6/26.
 * 主要逻辑写在这里，可以在这个目录下新建其他文件
 * 在其他包内新建时主要建在对应的包内
 */
@Controller
@RequestMapping("help")
public class ExpressInfoHelper {
    @Autowired
    KuaiDiService kuaiDiService;

    @RequestMapping("test")
    public String test(HttpServletRequest request , Model model
      ,@RequestParam(value = "search",required = false) Integer id){//你这里明明有id参数，先也用到了啊不是从网页上传吗我的小demo
//        List<Kuaidi> list = new ArrayList<Kuaidi>();
//        System.out.println("niece");
//        UKuaidiDao kao = new UKuaidiDao();
//        System.out.println("sss");
//       list= kao.query(id);
//        System.out.println(list.toString());
        List<Kuaidi> list = new ArrayList<Kuaidi>();
         list = kuaiDiService.ChaKuaiDi(id);
        model.addAttribute("kuaidilist",list);

        System.out.println( id);
        return "help/delivery";
    }


}
