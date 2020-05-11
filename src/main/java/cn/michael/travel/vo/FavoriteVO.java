package cn.michael.travel.vo;

import cn.michael.travel.pojo.Route;
import cn.michael.travel.pojo.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class FavoriteVO implements Serializable {
    private static final long serialVersionUID = -2712872057795887124L;
    @Id
    private Integer rid;
    private Integer uid;
    private String date;
    private Route route;
    private User user;
}
