package models.auth;

import models.base.IdObject;

/**
 * Created by felipeplazas on 4/7/17.
 */
public class SessionToken extends IdObject {

    private String token;

    private long expireTimeStamp;

    private String userGroup;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireTimeStamp() {
        return expireTimeStamp;
    }

    public void setExpireTimeStamp(long expireTimeStamp) {
        this.expireTimeStamp = expireTimeStamp;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
