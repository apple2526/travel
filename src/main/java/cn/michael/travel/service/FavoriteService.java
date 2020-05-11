package cn.michael.travel.service;

import cn.michael.travel.dao.FavoriteRepository;
import cn.michael.travel.pojo.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    @Autowired
    FavoriteRepository favoriteRepository;
    public boolean isFavoriteByRidAndUid(int uid, String rid) {
        List<Favorite> favoriteList = favoriteRepository.findByUidAndRid(uid, Integer.parseInt(rid));
        return null != favoriteList && favoriteList.size() > 0;
    }
}
