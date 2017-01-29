package Filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2016/8/30.
 */
public class UserFilter implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object o) throws Exception {
        HttpSession session = req.getSession();
        if(session.getAttribute("user") == null){
            session.setAttribute("backUrl",req.getRequestURL().toString());
            resp.sendRedirect("/userlogin.do");
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
