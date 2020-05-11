package cn.michael.travel.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "route")
public class Route implements Serializable {
    private static final long serialVersionUID = -8355941386729053435L;
    @ApiModelProperty("线路id")
    @Id
    @Column(name = "rid")
    private int rid;
    @ApiModelProperty("线路名称")
    @Column(name = "rname")
    private String rname;
    @ApiModelProperty("价格")
    @Column(name = "price")
    private double price;
    @ApiModelProperty("线路介绍")
    @Column(name = "introduce")
    private String introduce;
    @ApiModelProperty("是否上架，0代表没有上架，1代表是上架")
    @Column(name = "rflag")
    private String rflag;
    @ApiModelProperty("上架时间")
    @Column(name = "rdate")
    private String rdate;
    @ApiModelProperty("是否主题旅游，0代表不是，1代表是是否主题旅游")
    @Column(name = "theme_tour")
    private String themeTour;
    @ApiModelProperty("收藏数量")
    @Column(name = "count")
    private int count;
    @ApiModelProperty("所属分类")
    @Column(name = "cid")
    private int cid;
    @ApiModelProperty("缩略图")
    @Column(name = "rimage")
    private String rimage;
    @ApiModelProperty("所属商家")
    @Column(name = "sid")
    private int sid;
    @ApiModelProperty("抓取数据的来源id")
    @Column(name = "source_id")
    private String sourceId;
}
