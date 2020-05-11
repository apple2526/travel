package cn.michael.travel.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResultInfo implements Serializable {
    private static final long serialVersionUID = -4557421906404379615L;
    @ApiModelProperty("后端返回结果正常为true，发生异常返回false")
    private boolean flag;
    @ApiModelProperty("后端返回结果数据对象")
    private Object data;
    @ApiModelProperty("发生异常的错误消息")
    private String errorMsg;
}
