package com.top.model;

import lombok.Data;

/**用例基础数据
 *
 */

@Data
public class BettingLotteryCase {
    private String lotteryAccount;
    private String lotteryToken;
    private String expected;
}
