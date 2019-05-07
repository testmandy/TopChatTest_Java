package com.top.model;

/**用户信息基础数据
 *
 * @Author mandy
 * @Create 2019/5/7 14:10
 */

import lombok.Data;

import java.sql.Timestamp;

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