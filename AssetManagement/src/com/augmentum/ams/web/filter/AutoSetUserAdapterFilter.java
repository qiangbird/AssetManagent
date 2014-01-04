package com.augmentum.ams.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.validation.Assertion;

public class AutoSetUserAdapterFilter implements Filter {

    /** * Default constructor. */
    public AutoSetUserAdapterFilter() {
    }

    /** * @see Filter#destroy() */
    public void destroy() {
    }

    /**
     * Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Object object = httpRequest.getSession().getAttribute("user");
        if (object != null) {
            Assertion assertion = (Assertion) object;
            String loginName = assertion.getPrincipal().getName();
            System.out.println("The current logged in user��" + loginName);
            HttpSession session = httpRequest.getSession();
            session.setAttribute("loginName", loginName);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }
}
