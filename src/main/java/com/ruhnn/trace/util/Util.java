package com.ruhnn.trace.util;

import java.util.UUID;

/**
 * @author damon
 */
public class Util {

    /**
     * 返回8位随机字符串
     * @return 返回8位随机字符串
     */
    public static String getDefaultRandomStr(){
        return getRandomStr(0,8);
    }

    public static String getRandomStr(int start,int end){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(start,end);
    }
}
