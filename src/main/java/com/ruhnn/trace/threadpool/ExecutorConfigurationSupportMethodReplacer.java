package com.ruhnn.trace.threadpool;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.*;

/**
 * obj have method of submit
 * replace submit to the other
 * @author damon
 */
class ExecutorConfigurationSupportMethodReplacer implements MethodReplacer {
    private final String executeMethodName = "execute";
    private final String submitMethodName = "submit";

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
        if (!(obj instanceof ThreadPoolTaskExecutor)){
            return null;
        }
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) obj;
        ThreadPoolExecutor threadPoolExecutor = taskExecutor.getThreadPoolExecutor();
        //execute
        Object o = args[0];
        Future<?> future = null;
        if (executeMethodName.equals(method.getName())
                && o instanceof Runnable){
            if (args.length == 2 ){
                execute((Runnable)o,(long)args[1],threadPoolExecutor);
            }else{
                execute((Runnable)o,threadPoolExecutor);
            }
        }else if (submitMethodName.equals(method.getName())){
            if (o instanceof Runnable){
                future = submit((Runnable)o,threadPoolExecutor);
            }else if (o instanceof Callable){
                future = submit((Callable)o,threadPoolExecutor);
            }
        }
        return future;
    }

    public void execute(Runnable task, long l,ThreadPoolExecutor executor) {
        this.execute(task,executor);
    }

    public Future<?> submit(Runnable task,ThreadPoolExecutor executor) {
        try {
            task = TtlRunnable.get(task,false,true);
            return executor.submit(task);
        } catch (RejectedExecutionException var4) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
        }
    }

    public <T> Future<T> submit(Callable<T> task,ThreadPoolExecutor executor) {
        try {
            task = TtlCallable.get(task,false,true);
            return executor.submit(task);
        } catch (RejectedExecutionException var4) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
        }
    }

    public void execute(Runnable task,ThreadPoolExecutor executor) {
        try {
            task = TtlRunnable.get(task,false,true);
            executor.execute(task);
        } catch (RejectedExecutionException var4) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, var4);
        }
    }
}
