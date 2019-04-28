package com.top.model;

import lombok.Data;

/**用例基础数据
 * 登录场景
 */

@Data
public class LoginCase {
    private String rawId;
    private String email;
    private String expected;
}
