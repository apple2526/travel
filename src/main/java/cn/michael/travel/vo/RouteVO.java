package cn.michael.travel.vo;

import cn.michael.travel.pojo.Category;
import cn.michael.travel.pojo.RouteImg;
import cn.michael.travel.pojo.Seller;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RouteVO implements Serializable {
    private static final long serialVersionUID = -8355941386729053435L;
    private int rid;
    private String rname;
    private double price;
    private String introduce;
    private String rflag;
    private String rdate;
    private String themeTour;
    private int count;
    private int cid;
    private String rimage;
    private int sid;
    private String sourceId;
    private Seller seller;
    private Category category;
    private List<RouteImg> routeImgList;
}
