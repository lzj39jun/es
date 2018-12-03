package com.wutu.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户对象",description = "用户对象user")
public class UserVO extends BaseVO{

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "手机号1")
    private String phone;

    private AccessTokenVO accessToken;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
