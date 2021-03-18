package com.damon.ttl.mdc;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.damon.util.Util;
import org.slf4j.MDC;

/**
 * @author damon
 */
public class TtlMdcUtil {

    public static MdcTtlRemoveContainer construct(String traceId,final String key){
        if (traceId == null){
            traceId = Util.getDefaultRandomStr();
        }
        TransmittableThreadLocal<String> traceIdTtl = new TransmittableThreadLocal<String>(){
            @Override
            protected void beforeExecute() {
                MDC.put(key,this.get());
            }

            @Override
            protected void afterExecute() {
                //线程执行完后清除
                MDC.remove(key);
            }
        };
        traceIdTtl.set(traceId);
        MDC.put(key,traceId);
        return new MdcTtlRemoveContainer(traceIdTtl,key);
    }

    public static class MdcTtlRemoveContainer{
        private final ThreadLocal<String> threadLocal;

        private final String              mdcKey;

        public MdcTtlRemoveContainer(ThreadLocal<String> threadLocal, String mdcKey) {
            this.threadLocal = threadLocal;
            this.mdcKey = mdcKey;
        }

        public void remove(){
            if (null != threadLocal){
                threadLocal.remove();
            }
            if (null != mdcKey){
                MDC.remove(mdcKey);
            }
        }
    }
}
