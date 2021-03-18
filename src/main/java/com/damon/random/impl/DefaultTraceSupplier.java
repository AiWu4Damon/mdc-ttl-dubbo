package com.damon.random.impl;

import com.damon.util.Util;

/**
 * @author damon
 */
public class DefaultTraceSupplier extends AbstractRandomTrace<String>{
    @Override
    public int getIncrement() {
        return 0;
    }

    @Override
    public String random() {
        return Util.getDefaultRandomStr();
    }

    @Override
    public String get() {
        return random();
    }
}
