package cn.michael.travel.controller;

import cn.michael.travel.api.FavoriteApi;
import cn.michael.travel.pojo.User;
import cn.michael.travel.service.FavoriteService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FavoriteController implements FavoriteApi {
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpServletRequest request;
    @GetMapping("/isFavorite")
    public void isFavorite(@RequestParam("rid") String rid) throws Exception {
        Map<String, Object> result = new HashMap<>();
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser) {
            result.put("isFavorite", false);
        } else {
            int uid = loginUser.getUid();
            boolean isFavorite = favoriteService.isFavoriteByRidAndUid(uid, rid);
            result.put("isFavorite", isFavorite);
        }
        String isFavorite = JSON.toJSONString(result);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(isFavorite);
    }
}
