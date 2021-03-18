package com.damon.web.filter;

import com.damon.constant.trace.TraceConstant;
import com.damon.random.IDefaultRandomTrace;
import com.damon.random.impl.DefaultTraceSupplier;
import com.damon.ttl.mdc.TtlMdcUtil;
import com.damon.util.Util;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author damon
 */
public class WebRequestTraceFilter implements Filter{
    @Value("${response-head}")
    private String                       responseHeadKey = "R-X-Ruhnn-TraceId";

    private final IDefaultRandomTrace<String>  iDefaultRandomTrace;

    {
        iDefaultRandomTrace = new DefaultTraceSupplier();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        TtlMdcUtil.MdcTtlRemoveContainer mdcTtlRemoveContainer = doTrace();
        ((HttpServletResponse) servletResponse).setHeader(responseHeadKey, MDC.get(TraceConstant.TRACE_KEY));
        filterChain.doFilter(servletRequest,servletResponse);
        mdcTtlRemoveContainer.remove();
    }

    @Override
    public void destroy() {

    }

    private TtlMdcUtil.MdcTtlRemoveContainer doTrace(){
         return TtlMdcUtil.construct(iDefaultRandomTrace.get(), TraceConstant.TRACE_KEY);
    }
}
