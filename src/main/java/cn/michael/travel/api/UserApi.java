package cn.michael.travel.api;

import cn.michael.travel.pojo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Api(value = "旅游网管理接口")
public interface UserApi {
    @ApiOperation("生成验证码")
    void checkCode(HttpSession session) throws Exception;
    @ApiOperation("login")
    @ApiImplicitParam(name = "user", value = "user", dataType = "User")
    void login(HttpSession session, String check, User user) throws IOException;
    @ApiOperation("active")
    @ApiImplicitParam(name = "code", value = "code", dataType = "String")
    void active(String code) throws IOException;
    @ApiOperation("regist")
    @ApiImplicitParam(name = "user", value = "user", dataType = "User")
    void regist(User user) throws IOException;

    @ApiOperation("isEmailActive")
    @ApiImplicitParam(name = "email", value = "email", dataType = "String")
    void isEmailActive(String email) throws Exception;
    @ApiOperation("校验email格式")
    @ApiImplicitParam(name = "email", value = "email", dataType = "String")
    void checkEmail(String email) throws IOException;
    @ApiOperation("loginOut")
    void loginOut() throws Exception;
    @ApiOperation("getLoginUserData")
    void getLoginUserData() throws Exception;
}
