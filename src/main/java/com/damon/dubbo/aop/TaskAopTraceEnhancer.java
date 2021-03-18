package com.damon.dubbo.aop;

import com.damon.constant.trace.TraceConstant;
import com.damon.random.IDefaultRandomTrace;
import com.damon.ttl.mdc.TtlMdcUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;

import javax.annotation.Resource;

/**
 * @author damon
 */
@Aspect
@Order(1)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class TaskAopTraceEnhancer {
    @Resource(name = "defaultTraceSupplier")
    private IDefaultRandomTrace<String> iDefaultRandomTrace;

    @Pointcut("execution(public * com.ruhnn..*.*(..)) && @annotation(com.damon.annotation.Trace)")
    public void tracePointcut(){}

    @Around(value = "tracePointcut()")
    public Object annotationAopAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String traceId = iDefaultRandomTrace.get();
        TtlMdcUtil.MdcTtlRemoveContainer removeContainer = TtlMdcUtil.construct(traceId, TraceConstant.TRACE_KEY);
        Object proceed;
        try {
            //do actual method
            proceed = joinPoint.proceed();
        } finally {
            removeContainer.remove();
        }
        return proceed;
    }
}
