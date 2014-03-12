/**
 * 
 */
package com.augmentum.ams.shiro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.user.Role;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.CustomerAssetService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.user.AuthorityService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.user.UserVo;

/**
 * @author Grylls.Xu
 * @time Oct 14, 2013 9:39:05 AM
 */
public class AmsRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private RemoteEmployeeService remoteEmployeeService;

    @Autowired
    private RemoteCustomerService remoteCustomerService;

    @Autowired
    private CustomerAssetService customerAssetService;

    private Logger logger = Logger.getLogger(AmsRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        logger.info("AmsRealm  doGetAuthorizationInfo start!");

        User user;
        try {
            user = (User) principals.fromRealm(getName()).iterator().next();
        } catch (UnauthorizedException e) {
            String message = "Employee does not have privilege";
            logger.error(message, e);
            throw new UnauthorizedException(e);
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // Get role permission, then save in shrio
        List<String> authorityNames = new ArrayList<String>();
        List<Role> employeeRoles = user.getRoles();

        for (Role employeeRole : employeeRoles) {
            info.addRole(employeeRole.getRoleName().toString());
            // List<Authority> authorities = employeeRole.getAuthorities();
            List<String> authorities = authorityService.getAuthorityByRole(employeeRole);
            try {
                for (String authority : authorities) {
                    authorityNames.add(authority);
                }
            } catch (IllegalArgumentException e) {
                String message = user.getUserName() + " does not have privilege";
                logger.error(message, e);
                throw new UnauthorizedException(e);
            }
            logger.info(user.getUserName() + " authorization success");

            info.addStringPermissions(authorityNames);
        }

        logger.info("AmsRealm  doGetAuthorizationInfo end!");
        return info;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org
     * .apache.shiro.authc.AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {

        logger.info("AmsRealm ------   doGetAuthenticationInfo");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        Subject subject = SecurityUtils.getSubject();
        HttpServletRequest request = WebUtils.getHttpRequest(subject);
        String userAccountName = String.valueOf(usernamePasswordToken.getUsername());
        UserVo userVo = null;
        try {
            userVo = remoteEmployeeService.getLoginUser(request);
        } catch (BusinessException e1) {
            e1.printStackTrace();
        }

        if (null != userVo) {
            User user = userService.validateEmployee(userVo);
            SecurityUtils.getSubject().getSession().setTimeout(600000000);
            SecurityUtils.getSubject().getSession().setAttribute("userVo", userVo);
            SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);

            List<String> roleList = new ArrayList<String>();
            for (Role role : user.getRoles()) {
                roleList.add(role.getRoleName().toString());
            }
            SecurityUtils.getSubject().getSession().setAttribute("userRoleList", roleList);

            initEmployeeCustomerAndRole(request);
            initLanguage(request);

            try {
                return new SimpleAuthenticationInfo(user, userAccountName, getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void initLanguage(HttpServletRequest request) {

        // initiate the locale language according to the browser language
        String localeLanguage = request.getLocale().getLanguage();
        Locale locale = null;
        // Check the locale
        if ("en".equals(localeLanguage)) {
            locale = new Locale("en", "US");
            request.getSession().setAttribute("localeLanguage", locale);
        } else {
            locale = new Locale("zh", "CN");
            request.getSession().setAttribute("localeLanguage", locale);
        }
    }

    private void initEmployeeCustomerAndRole(HttpServletRequest request) {

        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserVo userVo = (UserVo) session.getAttribute("userVo");

        logger.info("userVo in index() of homeController" + userVo.toString());

        // Avoid repeat get data from IAP each time when refresh the page.
        if (null == session.getAttribute("customerList")) {
            logger.info(userVo.getEmployeeId() + " has already sign in");
            List<CustomerVo> list = null;

            try {
                // TODO: check the empty set and add log
                list = remoteCustomerService.getCustomerByEmployeeId(userVo.getEmployeeId(),
                        request);
            } catch (BusinessException e) {
                logger.error("Get customer by employeeId[ " + userVo.getEmployeeId()
                        + " ] from IAP error", e);

                // TODO no need to catch exception and throw exception
            }
            // Get the list of customer shown in subMenu
            List<Customer> customerVisibleList = customerAssetService.findVisibleCustomerList(
                    userVo, list);

            Set<Customer> set = new HashSet<Customer>(customerVisibleList);
            customerVisibleList.clear();
            customerVisibleList.addAll(set);

            String userRole = userService.getUserRole(userVo);
            session.setAttribute("userRole", userRole);
            session.setAttribute("customerList", customerVisibleList);
        }
    }
}
