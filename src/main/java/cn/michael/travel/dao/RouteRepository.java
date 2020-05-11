package cn.michael.travel.dao;

import cn.michael.travel.pojo.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface RouteRepository extends JpaRepository<Route, Integer>, JpaSpecificationExecutor<Route> {
    @Query(value = "SELECT  rid,rname,price,introduce,rflag,rdate,theme_tour,count,cid,rimage,sid,source_id FROM route WHERE rflag='1' ORDER BY count DESC LIMIT 0,4", nativeQuery = true)
    List<Route> findPopulateRoutes();

    @Query(value = "SELECT rid,rname,price,introduce,rflag,rdate,theme_tour,count,cid,rimage,sid,source_id FROM route WHERE rflag='1' ORDER BY rdate DESC LIMIT 0,4", nativeQuery = true)
    List<Route> findNewestRoutes();

    @Query(value = " SELECT rid,rname,price,introduce,rflag,rdate,theme_tour,count,cid,rimage,sid,source_id FROM route WHERE rflag='1' AND theme_tour='1' ORDER BY rdate DESC LIMIT 0,4", nativeQuery = true)
    List<Route> findThemeRoutes();

    @Query(value = "SELECT tbr.rid,tbr.rname,tbr.price,tbr.introduce,tbr.rflag,tbr.rdate,tbr.theme_tour,tbr.count,tbr.cid,tbr.rimage,tbr.sid,tbr.source_id,tbc.cid,tbc.cname,tbs.sid,tbs.sname,tbs.consphone,tbs.address FROM route tbr INNER JOIN category tbc ON tbr.cid=tbc.cid INNER JOIN seller tbs ON tbr.sid=tbs.sid WHERE tbr.rid=?rid", nativeQuery = true)
    Map<String, Object> findRouteAndDetailByRid(int rid);

    Route findByRid(int rid);

    @Query(value = " SELECT  tbf.rid,tbf.date,tbf.uid,tbr.rid,tbr.rname,tbr.price,tbr.introduce,tbr.rflag,tbr.rdate,tbr.theme_tour,tbr.count,tbr.cid,tbr.rimage,tbr.sid,tbr.source_id FROM favorite tbf INNER JOIN route tbr ON tbf.rid=tbr.rid WHERE tbf.uid = ?uid LIMIT ?startCount,?pageSize", nativeQuery = true)
    List<Map<String, Object>> findMyFavorite(int uid, int startCount, int pageSize);

    @Query(value = "SELECT COUNT(rid) FROM route WHERE rflag=1 And rname like ?rname And price>=?startPrice And  ?endPrice>=price", nativeQuery = true)
    int findTotalCountFromRoute(Map<String, String> conditionMap);

    @Query(value = "SELECT rid,rname,price,introduce,rflag,rdate,theme_tour,count,cid,rimage,sid,source_id FROM route where rname like ?rname And price>=?startPrice And ?endPrice>=price ORDER BY count DESC LIMIT ?startCount, ?pageSize", nativeQuery = true)
    List<Route> findRoutePageOrderByCountDesc(int startCount, int pageSize, Map<String, String> conditionMap);
}
