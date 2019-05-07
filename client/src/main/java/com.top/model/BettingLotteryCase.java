package com.top.model;

import lombok.Data;

/**用例基础数据
 *
 * @Author mandy
 * @Create 2019/5/7 14:10
 */

@Data
public class BettingLotteryCase {
    private String lotteryAccount;
    private String lotteryToken;
    private String expected;
}
