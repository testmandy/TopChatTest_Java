package com.top.model;

import lombok.Data;

/**用例基础数据
 *
 */

@Data
public class GetUserInfoCase {
    private int uid;
    private String expected;
}
