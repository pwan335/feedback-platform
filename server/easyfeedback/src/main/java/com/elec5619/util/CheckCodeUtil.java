package com.elec5619.util;

import cn.hutool.core.util.RandomUtil;

public class CheckCodeUtil {

    public static  String getCode(){
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<6;i++){
            builder.append(RandomUtil.randomInt(0,10));
        }
        return builder.toString();
    }
}
