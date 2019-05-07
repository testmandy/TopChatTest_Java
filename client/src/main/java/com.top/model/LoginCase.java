package com.top.model;

import lombok.Data;

/**用例基础数据
 * 登录场景
 * @Author mandy
 * @Create 2019/5/7 14:10
 */

@Data
public class LoginCase {
    private String rawId;
    private String displayName;
    private String photoURL;
    private String email;
    private String providerId;
    private String accessToken;
    private String federatedId;
    private String code;
    private String expected;
}
