package cn.michael.travel.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "旅游网管理接口")
public interface CategoryApi {
    @ApiOperation("查询所有类别")
    void queryAllCategory() throws Exception;
}
