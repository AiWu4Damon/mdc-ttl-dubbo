package com.ruhnn.trace.ttl.mdc;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * @author damon
 */
public class TtlMdcUtil {
    private static final Logger logger = LoggerFactory.getLogger(TtlMdcUtil.class);

    public static MdcTtlRemoveContainer construct(String traceId,final String key){
        String oldTraceId = MDC.get(key);
        if (oldTraceId != null){
            logger.warn("warn@traceId case memory leak,oldTraceId:{},newTraceId:{}",oldTraceId,traceId);
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
