package com.top.model;

import lombok.Data;

import java.sql.Timestamp;

//用户模块

@Data
public class User {
    private int userId;
    private String providerId;
    private String rawId;
    private String invitationCode;
    private String photoUrl;
    private String email;
    private String federatedId;
    private String displayName;
    private int expiresIn;
    private int inviterId;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Override
    public String toString(){
        return ("{id:" + userId + "," +
                "rawId:" + rawId + "," +
                "email:" + email + "," +
                "displayName:" + displayName + "}");

    }

}
