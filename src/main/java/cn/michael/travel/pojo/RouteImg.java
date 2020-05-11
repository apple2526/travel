package cn.michael.travel.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "route_img")
public class RouteImg implements Serializable {
    private static final long serialVersionUID = -5257558221268562423L;
    @Id
    private int rgid;
    private int rid;
    private String bigPic;
    private String smallPic;
}
