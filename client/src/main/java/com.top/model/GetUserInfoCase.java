package com.top.model;

import lombok.Data;

/**用例基础数据
 *
 */

@Data
public class GetUserInfoCase {
    private String userId;
    private String rawId;
    private String displayName;
    private String photoURL;
    private String email;
    private String providerId;
    private String accessToken;
    private String federatedId;
    private String code;
    private String expected;
//    private String userId;
//    private String address;
//    private String displayName;
//    private double balance;
//    private String publicKey;
//    private String expected;
}


