package cn.michael.travel.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Category implements Serializable {
    private static final long serialVersionUID = -1559679006110036580L;
    @ApiModelProperty("分类id")
    @Id
    private int cid;
    @ApiModelProperty("分类名称")
    private String cname;
}
