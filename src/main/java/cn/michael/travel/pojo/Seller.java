package cn.michael.travel.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Seller implements Serializable {
    private static final long serialVersionUID = 7499114076774892578L;
    @ApiModelProperty("商家id")
    @Id
    private int sid;
    @ApiModelProperty("商家名称")
    private String sname;
    @ApiModelProperty("商家电话")
    private String consphone;
    @ApiModelProperty("商家地址")
    private String address;
}
