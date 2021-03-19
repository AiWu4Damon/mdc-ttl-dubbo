package com.ruhnn.trace.dubbo.filter.consumer;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.ruhnn.trace.constant.trace.TraceConstant;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

/**
 * @author damon
 */
@Activate(group = Constants.CONSUMER)
public class TtlMdcDubboConsumerFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Map<String, String> mergeAttachmentMap = new HashMap<>(8);
        mergeAttachmentMap.put(TraceConstant.TRACE_KEY, MDC.get(TraceConstant.TRACE_KEY));
//        mergeAttachmentMap.put(TraceConstant.SPAN_KEY, Util.getDefaultRandomStr());
//        mergeAttachmentMap.put(TraceConstant.PARENT_SPAN_KEY, Util.getDefaultRandomStr());
        RpcContext.getContext().setAttachments(mergeAttachmentMap);
        return invoker.invoke(invocation);
    }
}
