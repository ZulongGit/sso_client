package com.krm.web.sys.security;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import com.krm.common.constant.Constant;
import com.krm.web.sys.model.SysUser;
import com.krm.web.sys.service.SysMenuService;
import com.krm.web.sys.service.SysRoleService;
import com.krm.web.sys.service.SysUserService;
import com.krm.web.util.SysUserUtils;

public class SystemAuthorizingRealm extends AuthorizingRealm {

	@Resource
	private SysMenuService sysMenuService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysRoleService sysRoleService;
	
	/** 
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法 
     */ 
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUser sessionUser = SysUserUtils.getSessionLoginUser();
		if(sessionUser != null){
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			if(sessionUser.isAdmin()) {
				info.addStringPermission("*");
			} else {
				info.addStringPermission("index");
				info.addStringPermissions(sysRoleService.getUserPermissions());
			}
			return info;
		}
		return null;
	}

    /**
     * 认证回调函数，登录信息和用户验证信息验证   
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 判断验证码
		Session session = SecurityUtils.getSubject().getSession();
		String code = (String)session.getAttribute("captcha");
		if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)){
			throw new CaptchaException("验证码错误!");
		}
		SysUser user = null;
		SysUser sysUser = new SysUser();
		sysUser.setUsername(token.getUsername());
		List<SysUser> users = sysUserService.select(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			user = users.get(0);
			if(user.getStatus().equals(Constant.USER_STATUS_NONACTIVATED)){
				throw new AccountException();
			}
			//当前 Realm 的 name
			String realmName = this.getName();
			return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), realmName);
		}
		return null;
	}

	
	/**
	 * 设定密码校验的Hash算法与迭代次数
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		setCredentialsMatcher(new WebCredentialsMatcher());
	}
	
}
