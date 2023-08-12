package com.shw.crm.settings.web.controller;

import com.shw.crm.commons.Constants;
import com.shw.crm.commons.domain.ReturnObject;
import com.shw.crm.commons.utils.DateUtils;
import com.shw.crm.commons.utils.UUIDUtils;
import com.shw.crm.settings.pojo.User;
import com.shw.crm.settings.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request,
                        HttpServletResponse response, HttpSession session) {
        // 封装前端传来的参数，用于数据库的查询条件
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        // 通过封装的map从数据库中查询对应的User对象
        User user = userService.queryUserByLoginActAndPwd(map);
        System.out.println(3);
        // 返回给前端的 后端响应信息的封装类
        ReturnObject returnObject = new ReturnObject();
        // 由查询结果生成相应信息
        if (user == null) { // 用户信息为空，用户名或者密码错误
            returnObject.setMessage("用户名或者密码错误");
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
        } else { // 用户信息存在，判断当前用户是否符合登录条件
            // 获取当前时间
            String nowTime = DateUtils.formatDateTime(new Date());
            if (nowTime.compareTo(user.getExpireTime()) > 0) {
                // 当前时间大于该用户有效时间，登陆失败，该用户已过期
                returnObject.setMessage("该用户已过期");
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            } else if ("0".equals(user.getLockState())) {
                // 登陆失败，该用户被锁定
                returnObject.setMessage("该用户被锁定");
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
                // 登录失败，该用户ip受限 （request.getLocalAddr()是获取本机ip）
                returnObject.setMessage("该用户ip受限");
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            } else {
                // 登录成功
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                // 将当前的User对象存入session中，用于不同请求路径下的前端页面调用
                session.setAttribute(Constants.SESSION_USER, user); // 比如：在登录后的页面右上角显示登录用户信息或其他页面调用需求
                // 如果需要记住密码，则创建对应的cookie对象（不安全，如何解决？）
                if("true".equals(isRemPwd)){
                    Cookie cookie =new Cookie("loginAct",user.getLoginAct());
                    cookie.setMaxAge(10*24*3600);
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                    Cookie cookie2 =new Cookie("loginPwd",user.getLoginPwd());
                    cookie2.setMaxAge(10*24*3600);
                    cookie2.setPath(request.getContextPath());
                    response.addCookie(cookie2);
                }else{
                    Cookie c1 =new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    c1.setPath(request.getContextPath());
                    response.addCookie(c1);
                    Cookie c2 =new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    c2.setPath(request.getContextPath());
                    response.addCookie(c2);
                }
//                if ("true".equals(isRemPwd)) {
//                    // 检验当前是否已经存在对应的账号和密码cookie，如果存在则不再创建
//                    Cookie[] cookies = request.getCookies();
//                    // 判断是否是一个新用户，isHaveCookie用来标记是否存在当前用户的cookie
//                    boolean isHaveCookie = CookieUtils.findCookieByValue(cookies, user.getId());
//                    // 如果不存在cookie，那么就创建当前新用户的cookie
//                    if (!isHaveCookie) {
//                        CookieUtils.createLoginCookie(user, response);
//                    }
//                } else { // 如果没有勾选记住密码，则将当前Cookie删除（重置同名的cookie存活时间达到删除的效果）
//                    CookieUtils.destroyLoginCookie(response);
//                }
            }
        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //清空cookies
        Cookie c1 =new Cookie("loginAct","1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 =new Cookie("loginPwd","1");
        c2.setMaxAge(0);
        response.addCookie(c2);
        //销毁session
        session.invalidate();
        return "redirect:/";
    }
    /**
     * 跳转到注册界面
     * @return 注册界面静态资源
     */
    @RequestMapping("/settings/qx/user/toRegister.do")
    public String toRegister() {
        return "settings/qx/user/register";
    }

    /**
     * 注册功能
     * @param user 注册用户
     * @param request 请求
     * @return 后端响应给前端的信息
     */
    @RequestMapping("/settings/qx/user/register.do")
    @ResponseBody
    public Object register(User user, HttpServletRequest request) {
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 2); // 在当前日期的基础上增加两年
        // 补充参数
        user.setId(UUIDUtils.getUUID());
        user.setExpireTime(DateUtils.formatDateTime(calendar.getTime())); // 设置过期时间：两年后
        user.setLockState(Constants.ACT_LOCK_STATE_FALSE); // 锁定状态为非锁定
        user.setAllowIps(request.getRemoteAddr()); // 默认当前本机登录ip
        user.setCreatetime(DateUtils.formatDateTime(new Date()));
        ReturnObject returnObject = new ReturnObject();
        // 保存用户
        try {
            int res = userService.saveNewUser(user);
            if (res > 0) { // 保存成功
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else { // 保存失败，服务器端出了问题，为了不影响顾客体验，最好不要直接说出问题
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试...");
            }
        } catch (Exception e) { // 发生了某些异常，捕获后返回信息
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }
        return returnObject;
    }

}
