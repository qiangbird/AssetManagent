/**
 * 
 */
package com.augmentum.ams.shiro;

import java.util.ArrayList;
import java.util.List;

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
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.user.Authority;
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

	private Logger logger = Logger.getLogger(AmsRealm.class);

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

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
			List<Authority> authorities = employeeRole.getAuthorities();
			try {
				for (Authority authority : authorities) {
					authorityNames.add(authority.getName());
				}
			} catch (IllegalArgumentException e) {
				String message = user.getUserName()
						+ " does not have privilege";
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
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		System.out.println("AmsRealm ------   doGetAuthenticationInfo");
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		Subject subject = SecurityUtils.getSubject();
		HttpServletRequest request = WebUtils.getHttpRequest(subject);
		String userAccountName = String.valueOf(usernamePasswordToken
				.getUsername());
		UserVo userVo = null;
		try {
			userVo = remoteEmployeeService.getLoginUser(request);
		} catch (DataException e1) {
			e1.printStackTrace();
		}

		if (null != userVo) {
			User user = userService.validateEmployee(userVo);
			SecurityUtils.getSubject().getSession().setTimeout(600000000);
			SecurityUtils.getSubject().getSession()
					.setAttribute("userVo", userVo);
			SecurityUtils.getSubject().getSession()
					.setAttribute("currentUser", user);
			try {
				return new SimpleAuthenticationInfo(user, userAccountName,
						getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
