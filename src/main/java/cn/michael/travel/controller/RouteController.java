package cn.michael.travel.controller;

import cn.michael.travel.api.RouteApi;
import cn.michael.travel.pojo.Route;
import cn.michael.travel.pojo.User;
import cn.michael.travel.service.RouteService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RouteController implements RouteApi {
    @Autowired
    RouteService routeService;
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpServletRequest request;

    @GetMapping("/routeCareChoose")
    public void routeCareChoose() throws Exception {
        String jsonRouteData = routeService.getCareChoose();
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(jsonRouteData);
    }

    @GetMapping("/getFavoriteRangePageData")
    public void getFavoriteRangePageData(@RequestParam("pageNum") String strPageNum,
                                         @RequestParam("pageSize") String strPageSize,
                                         @RequestParam("rname") String rname,
                                         @RequestParam("startPrice") String startPrice,
                                         @RequestParam("endPrice") String endPrice) throws Exception {
        String jsonString = routeService.pageQueryOrderByCountDesc(strPageNum, strPageSize,rname,startPrice,endPrice);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(jsonString);
    }

    @GetMapping("/pageQueryMyFavorite")
    public void pageQueryMyFavorite(@RequestParam("pageNum") String strPageNum,
                                    @RequestParam("pageSize") String strPageSize) throws IOException {
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        String favoriteJson = routeService.pageQueryMyFavorite(loginUser, strPageNum, strPageSize);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(favoriteJson);

    }

    @GetMapping("/addFavorite")
    public void addFavorite(@RequestParam("rid") String rid) throws Exception {
        Map<String, Object> result = new HashMap<>();
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser) {
            result.put("addFavoriteFlag", false);
        } else {
            int uid = loginUser.getUid();
            routeService.addFavorite(uid, rid);
            Route route = routeService.queryRouteByRid(rid);
            int count = route.getCount();
            result.put("addFavoriteFlag", true);
            result.put("count", count);
        }
        String addFavoriteData = JSON.toJSONString(result);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(addFavoriteData);
    }

    @GetMapping("/queryRouteDetail")
    public void queryRouteDetail(@RequestParam("rid") String rid) throws IOException {
        String detailJson = routeService.queryRouteDetailByRid(rid);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(detailJson);
    }

    @GetMapping("/routePageQuery")
    public void routePageQuery(String strPageSize,
                               String strPageNum,
                               String cid,
                               String rname) throws Exception {
        String jsonPageData = routeService.pageQuery(strPageNum, strPageSize, cid, rname);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(jsonPageData);
    }
}