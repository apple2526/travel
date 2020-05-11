package cn.michael.travel.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;

@Api(value = "旅游网管理接口")
public interface RouteApi {
    @ApiOperation("addFavorite")
    @ApiImplicitParam(name = "rid", value = "rid", dataType = "String")
    void addFavorite(String rid) throws Exception;

    @ApiOperation("getFavoriteRangePageData")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "pageNum", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", dataType = "String"),
            @ApiImplicitParam(name = "rname", value = "rname", dataType = "String"),
            @ApiImplicitParam(name = "startPrice", value = "startPrice", dataType = "String"),
            @ApiImplicitParam(name = "endPrice", value = "endPrice", dataType = "String")})
    void getFavoriteRangePageData(String strPageNum, String strPageSize,
                                  String rname, String startPrice, String endPrice) throws Exception;

    @ApiOperation("pageQueryMyFavorite")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum", value = "pageNum", dataType = "String"),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", dataType = "String")})
    void pageQueryMyFavorite(String strPageNum, String strPageSize) throws IOException;

    @ApiOperation("queryRouteDetail")
    @ApiImplicitParam(name = "rid", value = "rid", dataType = "String")
    void queryRouteDetail(String rid) throws IOException;

    @ApiOperation("routeCareChoose")
    void routeCareChoose() throws Exception;

    @ApiOperation("routePageQuery")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageSize", value = "pageSize", dataType = "String"),
            @ApiImplicitParam(name = "pageNum", value = "pageNum", dataType = "String"),
            @ApiImplicitParam(name = "cid", value = "cid", dataType = "String"),
            @ApiImplicitParam(name = "rname", value = "rname", dataType = "String")})
    void routePageQuery(String strPageSize, String strPageNum, String cid, String rname) throws Exception;
}
