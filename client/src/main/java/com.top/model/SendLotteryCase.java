package com.top.model;

import lombok.Data;

/**用例基础数据
 *
 */

@Data
public class SendLotteryCase {
    private String address;
    private String userId;
    private String amount;
    private String expected;
}

