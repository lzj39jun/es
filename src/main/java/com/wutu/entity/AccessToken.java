package com.wutu.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class AccessToken extends BaseEntity {

    @ManyToOne
    public User user;
    @Column(columnDefinition = STRING + "'token'")
    public String accesstoken;
    @Column(columnDefinition = STRING + "'APP版本'")
    public String appVersion;
    @Column(columnDefinition = STRING + "'APP类型'")
    public String appType;
    @Column(columnDefinition = STRING + "'osVersion'")
    public String osVersion;
    @Column(columnDefinition = STRING + "'客户端类型'")
    public String clientType;

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
