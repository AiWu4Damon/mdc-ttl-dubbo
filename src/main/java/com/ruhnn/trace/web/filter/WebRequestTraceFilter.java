package com.ruhnn.trace.web.filter;

import com.ruhnn.trace.constant.trace.TraceConstant;
import com.ruhnn.trace.random.IDefaultRandomTrace;
import com.ruhnn.trace.random.impl.DefaultTraceSupplier;
import com.ruhnn.trace.ttl.mdc.TtlMdcUtil;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author damon
 */
public class WebRequestTraceFilter implements Filter{
    private final String                       responseHeadKey = "R-X-Ruhnn-TraceId";

    private final IDefaultRandomTrace<String>  defaultRandomTrace = new DefaultTraceSupplier();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        TtlMdcUtil.MdcTtlRemoveContainer mdcTtlRemoveContainer = doTrace();
        ((HttpServletResponse) servletResponse).setHeader(getResponseHeadKey(), MDC.get(TraceConstant.TRACE_KEY));
        filterChain.doFilter(servletRequest,servletResponse);
        mdcTtlRemoveContainer.remove();
    }

    @Override
    public void destroy() {

    }

    private TtlMdcUtil.MdcTtlRemoveContainer doTrace(){
         return TtlMdcUtil.construct(getDefaultRandomTrace().get(), TraceConstant.TRACE_KEY);
    }

    /**
     * @return response header key
     */
    protected String getResponseHeadKey() {
        return responseHeadKey;
    }

    /**
     * @return implementation of traceId
     */
    protected IDefaultRandomTrace<String> getDefaultRandomTrace() {
        return defaultRandomTrace;
    }
}
