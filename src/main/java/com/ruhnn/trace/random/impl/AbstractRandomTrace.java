package com.ruhnn.trace.random.impl;

import com.ruhnn.trace.random.IDefaultRandomTrace;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author damon
 */
public abstract class AbstractRandomTrace<T> implements IDefaultRandomTrace<T> {
    @Override
    public String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "127.0.0.1";
        }
    }

    @Override
    public Long getCurrentTime() {
        return System.currentTimeMillis();
    }
}
