package cn.michael.travel.service;

import cn.michael.travel.dao.*;
import cn.michael.travel.pojo.*;
import cn.michael.travel.util.DateUtil;
import cn.michael.travel.vo.FavoriteVO;
import cn.michael.travel.vo.RouteVO;
import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.*;

@Service
public class RouteService {
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    RouteImgRepository routeImgRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    DateUtil dateUtil;

    public String getCareChoose() {
        List<Route> populateRouteList = routeRepository.findPopulateRoutes();
        List<Route> newestRouteList = routeRepository.findNewestRoutes();
        List<Route> themeRouteList = routeRepository.findThemeRoutes();
        Map<String, List<Route>> result = new HashMap<>();
        result.put("populate", populateRouteList);
        result.put("newest", newestRouteList);
        result.put("theme", themeRouteList);
        return JSON.toJSONString(result);
    }

    public String pageQuery(String strPageNum, String strPageSize, String cid, String rname) {
        int pageNum = 1;
        int pageSize = 10;
        if (StringUtils.isNotBlank(strPageNum)) {
            pageNum = Integer.parseInt(strPageNum);
        }
        if (StringUtils.isNotBlank(strPageSize)) {
            pageSize = Integer.parseInt(strPageSize);
        }
        int startCount = (pageNum - 1) * pageSize;
        List<Route> routeList = findRoutePage(startCount, pageSize, cid, rname);
        int totalCount;
        try {
            totalCount = findTotalCount(cid, rname);
        } catch (DataAccessException e) {
            e.printStackTrace();
            totalCount = 0;
        }
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        int prePage = pageNum == 1 ? pageNum : pageNum - 1;
        int nextPage = pageNum == totalPage ? totalPage : totalPage - 1;
        Map<String, Object> pageResult = new HashMap<>();
        pageResult.put("data", routeList);
        pageResult.put("totalCount", totalCount);
        pageResult.put("totalPage", totalPage);
        pageResult.put("prePage", prePage);
        pageResult.put("nextPage", nextPage);
        return JSON.toJSONString(pageResult);
    }

    public int findTotalCount(String cidString, String rname) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("rflag").as(Integer.class), 1));
            if (null != cidString && !StringUtils.trim(cidString).equals("")) {
                int cid = Integer.parseInt(cidString);
                list.add(criteriaBuilder.equal(root.get("cid").as(Integer.class), cid));
            }
            if (!"".equals(StringUtils.trim(rname))) {
                list.add(criteriaBuilder.like(root.get("rname").as(String.class), '%' + rname + '%'));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        long count = routeRepository.count(specification);
        return Long.bitCount(count);
    }

    public List<Route> findRoutePage(int startCount, int pageSize, String cidString, String rname) {
        Specification specification = (Specification) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("rflag").as(Integer.class), 1));
            if (null != cidString && !StringUtils.trim(cidString).equals("")) {
                int cid = Integer.parseInt(cidString);
                list.add(criteriaBuilder.equal(root.get("cid").as(Integer.class), cid));

            }
            if (!"".equals(StringUtils.trim(rname))) {
                list.add(criteriaBuilder.like(root.get("rname").as(String.class), '%' + rname + '%'));
            }
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        Pageable pageable = PageRequest.of(startCount, pageSize);
        return routeRepository.findAll(specification, pageable).getContent();
    }

    public Route queryRouteByRid(String rid) {
        return routeRepository.findByRid(Integer.parseInt(rid));
    }

    public String pageQueryOrderByCountDesc(String strPageNum, String strPageSize, String rname, String startPrice, String endPrice) {
        int pageNum = 1;
        int pageSize = 8;
        if (StringUtils.isNotBlank(strPageNum)) {
            pageNum = Integer.parseInt(strPageNum);
        }
        if (StringUtils.isNotBlank(strPageSize)) {
            pageSize = Integer.parseInt(strPageSize);
        }
        int startCount = (pageNum - 1) * pageSize;
        PageRequest pageRequest = PageRequest.of(startCount, pageSize);
        Specification specification = getSpecification(rname, startPrice, endPrice);
        List<Route> routeList = routeRepository.findAll(specification, pageRequest).getContent();
        int totalCount = routeRepository.findAll(specification).size();
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        int prePage = pageNum == 1 ? 1 : pageNum - 1;
        int nextPage = pageNum == totalPage ? totalPage : pageNum + 1;
        Map<String, Object> result = new HashMap<>();
        result.put("data", routeList);
        result.put("totalCount", totalCount);
        result.put("totalPage", totalPage);
        result.put("prePage", prePage);
        result.put("nextPage", nextPage);
        return JSON.toJSONString(result);
    }

    public Specification getSpecification(String rname, String startPrice, String endPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            if (!"".equals(StringUtils.trim(rname))) {
                list.add(criteriaBuilder.like(root.get("rname").as(String.class), '%' + rname + '%'));
            }
            if (!"".equals(StringUtils.trim(startPrice))) {
                list.add(criteriaBuilder.greaterThan(root.get("price").as(Integer.class), Integer.parseInt(startPrice)));
            }
            if (!"".equals(StringUtils.trim(endPrice))) {
                list.add(criteriaBuilder.lessThan(root.get("price").as(Integer.class), Integer.parseInt(endPrice)));
            }
            return criteriaQuery.where(criteriaBuilder.and(list.toArray(new Predicate[list.size()]))).orderBy(criteriaBuilder.desc(root.get("count").as(Integer.class))).getRestriction();
        };
    }

    @Transactional
    public void addFavorite(int uid, String rid) {
        Favorite favorite = new Favorite();
        favorite.setUid(uid);
        favorite.setRid(Integer.parseInt(rid));
        favorite.setDate(dateUtil.parseDate2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        favoriteRepository.save(favorite);
        Route route = routeRepository.findByRid(Integer.parseInt(rid));
        route.setCount(route.getCount() + 1);
        routeRepository.save(route);
    }

    public String pageQueryMyFavorite(User loginUser, String strPageNum, String strPageSize) {
        Map<String, Object> result = new HashMap<>();
        if (null == loginUser) {
            result.put("loginFlag", false);
        } else {
            result.put("loginFlag", true);
            int pageNum = 1;
            int pageSize = 12;
            int startCount = 0;
            if (StringUtils.isNotBlank(strPageNum)) {
                pageNum = Integer.parseInt(strPageNum);
            }
            if (StringUtils.isNotBlank(strPageSize)) {
                pageSize = Integer.parseInt(strPageSize);
            }
            startCount = (pageNum - 1) * pageSize;
            int uid = loginUser.getUid();
            List<FavoriteVO> pageData = new ArrayList<>();
            Page<Favorite> favoritePage = favoriteRepository.findAll(
                    (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("uid").as(Integer.class), uid),
                    PageRequest.of(startCount, pageSize));
            List<Favorite> favoriteList = favoritePage.getContent();
            for (Favorite favorite : favoriteList) {
                FavoriteVO favoriteVO = new FavoriteVO();
                org.springframework.beans.BeanUtils.copyProperties(favorite, favoriteVO);
                Integer rid = favorite.getRid();
                Route route = routeRepository.findById(rid).get();
                favoriteVO.setRoute(route);
                pageData.add(favoriteVO);
            }
            int totalCount = favoriteRepository.findAll(
                    (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("uid").as(Integer.class), uid)).size();
            int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
            int prePage = pageNum == 1 ? 1 : pageNum - 1;
            int nextPage = pageNum == totalPage ? totalPage : pageNum + 1;
            result.put("data", pageData);
            result.put("totalPage", totalPage);
            result.put("prePage", prePage);
            result.put("nextPage", nextPage);
        }
        return JSON.toJSONString(result);
    }

    public String queryRouteDetailByRid(String ridString) {
        int rid = Integer.parseInt(ridString);
        RouteVO routeVO = new RouteVO();
        Route route = routeRepository.findByRid(rid);
        try {
            BeanUtils.copyProperties(routeVO, route);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Category category = categoryRepository.findById(route.getCid()).get();
        routeVO.setCategory(category);
        Seller seller = sellerRepository.findById(route.getSid()).get();
        routeVO.setSeller(seller);
        List<RouteImg> routeImgList = routeImgRepository.findByRid(rid);
        routeVO.setRouteImgList(routeImgList);
        return JSON.toJSONString(routeVO);
    }
}
