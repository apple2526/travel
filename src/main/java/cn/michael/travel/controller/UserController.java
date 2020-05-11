package cn.michael.travel.controller;

import cn.michael.travel.api.UserApi;
import cn.michael.travel.pojo.User;
import cn.michael.travel.service.UserService;
import cn.michael.travel.util.MailUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController implements UserApi {
    @Autowired
    UserService userService;
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpServletRequest request;
    @Autowired
    MailUtil mailUtil;

    @GetMapping("/checkCode")
    public void checkCode(HttpSession session) throws Exception {
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        String checkCode = userService.getCheckCode();
        session.setAttribute("code", checkCode);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("黑体", Font.BOLD, 24));
        g.drawString(checkCode, 15, 25);
        ImageIO.write(image, "PNG", response.getOutputStream());
    }

    @PostMapping("/login")
    public void login(HttpSession session, String check, User user) throws IOException {
        Map<String, Object> result = new HashMap<>();
        String serverCode = (String) session.getAttribute("code");
        response.setContentType("text/html; charset=utf-8");
        if (!serverCode.equalsIgnoreCase(check)) {
            result.put("loginFlag", false);
            result.put("errorMsg", "验证码错误！");
            response.getWriter().println(JSON.toJSONString(result));
            return;
        }
        User loginUser = userService.login(user);
        if (null == loginUser) {
            result.put("loginFlag", false);
            result.put("errorMsg", "用户名或密码错误！");
        } else {
            request.getSession().setAttribute("loginUser", loginUser);
            result.put("loginFlag", true);
        }
        response.getWriter().println(JSON.toJSONString(result));
    }

    @GetMapping("/active")
    public void active(@RequestParam("code") String code) throws IOException {
        User user = userService.active(code);
        response.setContentType("text/html; charset=utf-8");
        if(user!=null&&user.getStatus()==0){
            userService.setUserStatus(user);
            response.getWriter().println("激活成功，3秒钟之后跳转到登录页面！");
            response.setHeader("refresh", "3;/login.html");

        }else if(user!=null&&user.getStatus()==1){
            response.getWriter().println("此账号已激活，3秒钟之后跳转到登录页面！");
            response.setHeader("refresh", "3;/index.html");
        }else{
            response.getWriter().println("激活码错误，请检查激活码后重试，3秒钟之后跳转到主页！");
            response.setHeader("refresh", "3;/index.html");
        }
    }

    @PostMapping("/regist")
    public void regist(User user) throws IOException {
        userService.register(user);
        Map<String, Object> result = new HashMap<>();
        String code = user.getCode();
        String email = user.getEmail();
        try {
            mailUtil.sendEmail(email,
                    "<h1>恭喜您，注册成功！</h1><a href='http://localhost:8080/active?code=" + code + "'>请点击此链接，进行激活</a>");
            result.put("checkFlag", true);
        } catch (EmailException emailException) {
            emailException.printStackTrace();
            result.put("checkFlag", false);
            result.put("msg", "注册出现异常，请联系管理员！");
        }
        String s = JSON.toJSONString(result);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(s);
    }

    @GetMapping("/isEmailActive")
    public void isEmailActive(@RequestParam("email") String email) throws Exception {
        boolean checkFlag = userService.findUserByEmail(email);
        Map<String, Object> result = new HashMap<>();
        if (checkFlag) {
            result.put("checkFlag", true);
        } else {
            result.put("checkFlag", false);
            result.put("resultInfo", "账号未激活，请先激活");
        }
        String jsonRes = JSON.toJSONString(result);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(jsonRes);
    }

    @GetMapping("/checkEmail")
    public void checkEmail(@RequestParam("email") String email) throws IOException {
        boolean checkFlag = userService.findUserByEmail(email);
        Map<String, Object> result = new HashMap<>();
        result.put("checkEmailFlag", !checkFlag);
        String jsonString = JSON.toJSONString(result);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(jsonString);
    }

    @GetMapping("/loginOut")
    public void loginOut() throws Exception {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null != loginUser) {
            request.getSession().invalidate();
        }
        response.setContentType("text/html; charset=utf-8");
        response.sendRedirect("/login.html");
    }

    @GetMapping("/getLoginUserData")
    public void getLoginUserData() throws Exception {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        Map<String, Object> result = new HashMap<>();
        if (null != loginUser) {
            result.put("loginFlag", true);
            result.put("loginUser", loginUser);
        } else {
            result.put("loginFlag", false);
        }
        String loginData = JSON.toJSONString(result);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(loginData);
    }
}