package com.sandbox.aide;

import org.junit.Test;

public class StringTest {

    @Test
    public void testTirm(){
        String str = "\t sandbox-info        \tACTIVE  \tLOADED  \t0    \t0    \t0.0.4          \tluanjia@taobao.com\n" +
                "sandbox-module-mgr  \tACTIVE  \tLOADED  \t0    \t0    \t0.0.2          \tluanjia@taobao.com\n" +
                "sandbox-control     \tACTIVE  \tLOADED  \t0    \t0    \t0.0.3          \tluanjia@taobao.com\n" +
                "repeater            \tACTIVE  \tLOADED  \t0    \t0    \t1.0.0          \tzhaoyb1990\n";
        System.out.println(str.trim());
        System.out.println(str);
    }
}
