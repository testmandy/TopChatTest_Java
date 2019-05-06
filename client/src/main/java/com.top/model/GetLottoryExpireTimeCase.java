package com.top.model;

import lombok.Data;

/**用例基础数据
 *
 */

@Data
public class GetLottoryExpireTimeCase {
    private String lotteryAccount;
    private String expected;
}
