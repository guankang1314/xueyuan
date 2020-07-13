package com.guli.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


@Component
public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * 过滤器执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    //判断

    @Override
    public boolean shouldFilter() {

        //得到当前访问上下文的对象
        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request = requestContext.getRequest();

        String requestURI = request.getRequestURI();

        System.err.println(requestURI);

        String str = "/vod/getPlayAuthFront/";

        //判断
        if (!StringUtils.isEmpty(requestURI) && requestURI.contains(str)) {

            return true;
        }


        return false;
    }

    @Override
    public Object run() throws ZuulException {

        System.err.println("run方法执行了.....");

        RequestContext currentContext = RequestContext.getCurrentContext();

        System.err.println(currentContext);

        HttpServletRequest request = currentContext.getRequest();

        System.err.println(request);

        String token = request.getParameter("token");

        //登录校验
        if (StringUtils.isEmpty(token)) {

            //不会向下游服务器执行
            currentContext.setSendZuulResponse(false);

            //设置返回的状态码
            currentContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());

        }

        return null;
    }
}
