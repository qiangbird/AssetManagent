/**
 * 
 */
package com.augmentum.ams.shiro;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.user.Role;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.user.RoleService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.web.vo.user.UserVo;

/**
 * @author Grylls.Xu
 * @time Oct 14, 2013 9:39:05 AM
 */
public class AmsRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RemoteEmployeeService remoteEmployeeService;

    /* (non-Javadoc)
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //User user = (User) principles.fromRealm(getName()).iterator().next();
        System.out.println("AmsRealm +++++   doGetAuthorizationInfo");
        User user = (User) principals.fromRealm(getName()).iterator().next();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //        List<String> roles = userService.getUserRoleByUserId(user.getUserId());
        //
        //        for (String roleName : roles) {
        //            List<String> authorityNames = null;
        //            authorityNames = roleService.getAuthoritiesByRoleName(RoleEnum.getRoleEnum(roleName));
        //
        //            info.addStringPermissions(authorityNames);
        //        }

        List<Role> roles = user.getRoles();
        for (Role role : roles) {
            List<String> authorityNames = null;
            authorityNames = roleService.getAuthoritiesByRoleName(role.getRoleName());

            info.addStringPermissions(authorityNames);
        }

        return info;
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        System.out.println("AmsRealm ------   doGetAuthenticationInfo");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        Subject subject = SecurityUtils.getSubject();
        HttpServletRequest request = WebUtils.getHttpRequest(subject);
        String userAccountName = String.valueOf(usernamePasswordToken.getUsername());
        UserVo userVo = null;
        try {
            userVo = remoteEmployeeService.getLoginUser(request);
        } catch (DataException e1) {
            e1.printStackTrace();
        }

        if (null != userVo) {
            User user = userService.validateEmployee(userVo);
            SecurityUtils.getSubject().getSession().setTimeout(600000000);
            SecurityUtils.getSubject().getSession().setAttribute("userVo", userVo);
            try{
                return new SimpleAuthenticationInfo(user, userAccountName, getName());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }



}
