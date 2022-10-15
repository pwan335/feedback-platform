package com.elec5619.config;

import com.elec5619.utils.TokenUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    //Controller逻辑执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

//        //跨域请求会首先发一个option请求，直接返回正常状态并通过拦截器
//        if(request.getMethod().equals("OPTIONS")){
//            response.setStatus(HttpServletResponse.SC_OK);
//            return true;
//        }
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 取得token
        String tokenHeader = request.getHeader(TokenUtils.TOKEN_HEADER);
        if (tokenHeader == null || !tokenHeader.startsWith(TokenUtils.TOKEN_PREFIX)) {
            JSONObject json=new JSONObject();
            json.put("code","500");
            json.put("data", "");
            json.put("msg","You need to login first!");
            response.getWriter().append(json.toString());
            System.out.println("Token is not exist");
        }
        tokenHeader = tokenHeader.replace(TokenUtils.TOKEN_PREFIX, "");
        // 验证token是否有效--无效已做异常抛出，由全局异常处理后返回对应信息
        // TokenUtils.getTokenBody(tokenHeader);

        String token = request.getHeader("token");
        if (tokenHeader != null){
            boolean result= TokenUtils.verify(tokenHeader);
            if (result){
                System.out.println("通过拦截器");
                return true;
            }
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            JSONObject json=new JSONObject();
            json.put("code","500");
            json.put("data", "");
            json.put("msg","token verify fail");
            response.getWriter().append(json.toString());
            System.out.println("认证失败，未通过拦截器");
        } catch (Exception e) {
            return false;
        }
        /**
         * 还可以在此处检验用户存不存在等操作
         */
        return false;
    }

    //Controller逻辑执行完毕但是视图解析器还未进行解析之前
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle....");
    }

    //Controller逻辑和视图解析器执行完毕
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion....");
    }
}
