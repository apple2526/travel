package cn.michael.travel.dao;

import cn.michael.travel.pojo.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer>, JpaSpecificationExecutor<Favorite> {
    List<Favorite> findByUidAndRid(Integer uid, Integer rid);
}
