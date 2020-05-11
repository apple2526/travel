package cn.michael.travel.dao;

import cn.michael.travel.pojo.RouteImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RouteImgRepository extends JpaRepository<RouteImg, Integer>, JpaSpecificationExecutor<RouteImg> {
    List<RouteImg> findByRid(int rid);
}
