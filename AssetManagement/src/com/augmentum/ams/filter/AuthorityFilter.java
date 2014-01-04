/**
 * 
 */
package com.augmentum.ams.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.jasig.cas.client.validation.Assertion;

/**
 * @author Grylls.Xu
 * @time Oct 14, 2013 9:38:33 AM
 */
public class AuthorityFilter extends AuthorizationFilter {

    private static Logger logger = Logger.getLogger(AuthorityFilter.class);
    private static final String CONST_CAS_ASSERTION = "_const_cas_assertion_";

    /* (non-Javadoc)
     * @see org.apache.shiro.web.filter.AccessControlFilter#isAccessAllowed(javax.servlet.ServletRequest, javax.servlet.ServletResponse, java.lang.Object)
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object arg2) throws Exception {
        System.out.println("AuthorityFilter isAccessAllowed method");

        Subject subject = super.getSubject(servletRequest, servletResponse);
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println(request.getRequestURI());
        Object loginUser = subject.getSession().getAttribute("userVo");
        if (loginUser == null) {
            Assertion assertion = (Assertion) request.getSession().getAttribute(CONST_CAS_ASSERTION);
            if (assertion == null) {
                logger.info("Assertion is null,");
                return true;
            }
            String userName = assertion.getPrincipal().getName();
            UsernamePasswordToken token = new UsernamePasswordToken(userName, userName);
            try{
                subject.login(token);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
