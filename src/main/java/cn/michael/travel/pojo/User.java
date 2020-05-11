package cn.michael.travel.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 4942643018519427507L;
    @ApiModelProperty("用户id")
    @Id
    private int uid;
    @ApiModelProperty("用户名，账号")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("真实姓名")
    private String name;
    @ApiModelProperty("出生日期")
    private String birthday;
    @ApiModelProperty("男或女")
    private String sex;
    @ApiModelProperty("手机号")
    private String telephone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("激活码")
    private String code;
    @ApiModelProperty("激活状态")
    private int status;
}