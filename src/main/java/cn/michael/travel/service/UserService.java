package cn.michael.travel.service;

import cn.michael.travel.dao.UserRepository;
import cn.michael.travel.pojo.User;
import cn.michael.travel.util.Md5Util;
import cn.michael.travel.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return null != user;
    }

    public void register(User user) {
        user.setStatus(0);
        user.setCode(UuidUtil.getUuid());
        String password = user.getPassword();
        String md5Pwd = null;
        try {
            md5Pwd = Md5Util.encodeByMd5(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setPassword(md5Pwd);
        userRepository.save(user);
    }

    public User active(String code) {
        return userRepository.findByCode(code);
    }
    public void setUserStatus(User user) {
        user.setStatus(1);
        userRepository.save(user);
    }
    public User login(User user) {
        String email = user.getEmail();
        String password = null;
        try {
            password = Md5Util.encodeByMd5(user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userRepository.findByEmailAndPassword(email, password);
    }

    public String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            int index = r.nextInt(size);
            char c = base.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }
}
