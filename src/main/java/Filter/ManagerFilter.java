package Filter;

import Controllers.Manager.AppCgi;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/1/8.
 */
public class ManagerFilter implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object o) throws Exception {

        if(req.getParameter("managerId") == null || req.getParameter("token") == null)
            return false;
        if(!AppCgi.validateToken(Integer.parseInt(req.getParameter("managerId")),req.getParameter("token"))){
            return false;
        }
        req.getSession().setAttribute("Stoken",AppCgi.newToken(Integer.parseInt(req.getParameter("managerId"))));
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}