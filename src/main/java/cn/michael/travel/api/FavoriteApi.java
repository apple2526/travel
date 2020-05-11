package cn.michael.travel.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "旅游网管理接口")
public interface FavoriteApi {
    @ApiOperation("isFavorite")
    @ApiImplicitParam(name = "rid", value = "rid", dataType = "String")
    void isFavorite(String rid) throws Exception;
}
