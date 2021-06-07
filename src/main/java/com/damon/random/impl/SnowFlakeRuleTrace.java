package com.damon.random.impl;

/**
 * support snowFlakeRule to replace UUID/GUID
 * snowFlake has 64bit space
 * 0-00000..00000000-0000000000-000000000000 the total is 64bit
 *   {   41bit     }
 * @author damon
 */
public class SnowFlakeRuleTrace extends AbstractRandomTrace<String>{
    @Override
    public int getIncrement() {
        return 0;
    }

    @Override
    public String random() {
        return null;
    }

    @Override
    public String get() {
        return null;
    }
}
