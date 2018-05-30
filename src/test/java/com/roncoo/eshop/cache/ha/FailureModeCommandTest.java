package com.roncoo.eshop.cache.ha;

import com.roncoo.eshop.cache.ha.hystrix.command.FailureModeCommand;

/**
 * @Author: cks
 * @Date: Created by 17:17 2018/5/29
 * @Package: com.roncoo.eshop.cache.ha
 * @Description:
 */
public class FailureModeCommandTest {
    public static void main(String[] args) {
        try {
            FailureModeCommand failureModeCommand = new FailureModeCommand(true);
            System.out.println(failureModeCommand.execute());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
