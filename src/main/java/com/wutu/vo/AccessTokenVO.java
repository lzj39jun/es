package com.wutu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "token对象", description = "token对象验证")
public class AccessTokenVO extends BaseVO {

    public UserVO user;
    @ApiModelProperty(value = "'token'")
    public String accesstoken;
    @ApiModelProperty(value = "'APP版本'")
    public String appVersion;
    @ApiModelProperty(value = "'APP类型'")
    public String appType;
    @ApiModelProperty(value = "'osVersion'")
    public String osVersion;
    @ApiModelProperty(value = "'客户端类型'")
    public String clientType;

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public String getAccesstoken() {
        return accesstoken;
    }

    public void setAccesstoken(String accesstoken) {
        this.accesstoken = accesstoken;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
