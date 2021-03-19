package com.ruhnn.trace.dubbo.filter.provider;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import com.ruhnn.trace.constant.trace.TraceConstant;
import com.ruhnn.trace.ttl.mdc.TtlMdcUtil;

/**
 * @author damon
 */
@Activate(group = Constants.PROVIDER)
public class TtlMdcDubboProviderFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        TtlMdcUtil.MdcTtlRemoveContainer traceIdTtl = TtlMdcUtil.construct(getTraceOfRpcContext(TraceConstant.TRACE_KEY),TraceConstant.TRACE_KEY);
//        MdcTtlRemoveContainer spanIdTtl = construct(TraceConstant.SPAN_KEY);
//        MdcTtlRemoveContainer pSpanIdTtl = construct(TraceConstant.PARENT_SPAN_KEY);
        Result invoke;
        try {
             invoke = invoker.invoke(invocation);
        } finally {
            traceIdTtl.remove();
//            spanIdTtl.remove();
//            pSpanIdTtl.remove();
        }
        return invoke;
    }

    private String getTraceOfRpcContext(String key){
        return RpcContext.getContext().getAttachment(key);
    }
}
