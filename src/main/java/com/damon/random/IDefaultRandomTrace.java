package com.damon.random;

import java.util.function.Supplier;

/**
 * @author damon
 */
public interface IDefaultRandomTrace<T> extends IBaseRandom, Supplier<T> {

    /**
     * @return 获取本机ip
     */
    String getIp();

    /**
     * @return 获取当前时间
     */
    Long getCurrentTime();

    /**
     * @return 获取种子自增量
     */
    int getIncrement();
}
