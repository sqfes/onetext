package com.example.demo.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.exception.ServiceException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

//    @Resource
//    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");
        }
        // 放开登陆的权限，在接口上加上@AuthAccess就不需要登陆就可以访问接口
        if (handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }
//        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException("401", "请登录");
        }
//        // 获取 token 中的 user id
//        String userId;
//        try {
//            userId = JWT.decode(token).getAudience().get(0);
//        } catch (JWTDecodeException j) {
//            throw new ServiceException("401", "请登录");
//        }
//        // 根据token中的userid查询数据库
//        User user = userMapper.selectById(Integer.valueOf(userId));
//        if (user == null) {
//            throw new ServiceException("401", "请登录");
//        }
//        // 用户密码加签验证 token
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
//        System.out.println("jwtVerifier"+jwtVerifier);
//        try {
//            jwtVerifier.verify(token); // 验证token
//        } catch (JWTVerificationException e) {
//            throw new ServiceException("401", "请登录");
//        }
        return true;
    }
}
