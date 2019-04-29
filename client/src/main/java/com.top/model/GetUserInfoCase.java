package com.top.model;

import lombok.Data;

/**用例基础数据
 *
 */

@Data
public class GetUserInfoCase {
    private String userId;
    private String address;
    private String displayName;
    private double balance;
    private String publicKey;
    private String expected;
}


