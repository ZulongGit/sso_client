package com.krm.web.sys.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 表单验证（包含验证码）过滤类
 * @author ThinkGem
 * @version 2013-5-19
 */
@Service("formAuthenticationFilter")
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}


	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String p = getPassword(request);
		if ( p==null){
			 p = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		String captcha = getCaptcha(request);
		return new UsernamePasswordToken(username,  p.toCharArray(), rememberMe, host, captcha);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (!this.isLoginRequest(request, response)) {
			this.saveRequestAndRedirectToLogin(request, response);
			return false;
		}
		if (this.isLoginSubmission(request, response)) {
			return this.executeLogin(request, response);
		}
		return true;
	}
	
	@Override
	protected boolean executeLogin(final ServletRequest request, final ServletResponse response) throws Exception {
		final AuthenticationToken token = this.createToken(request, response);
		if (token == null) {
			final String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
			throw new IllegalStateException(msg);
		}
		try {
			final Subject subject = this.getSubject(request, response);
			subject.login(token);
			return this.onLoginSuccess(token, subject, request, response);
		} catch (AuthenticationException e) {
			return this.onLoginFailure(token, e, request, response);
		}
	}
	
	@Override
	protected boolean onLoginFailure(final AuthenticationToken token, final AuthenticationException e,
			final ServletRequest request, final ServletResponse response) {
		this.setFailureAttribute(request, e);
		return true;
	}
	
	@Override
	protected void issueSuccessRedirect(final ServletRequest request, final ServletResponse response) throws Exception {
		WebUtils.redirectToSavedRequest(request, response, this.getSuccessUrl());
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
			Object mappedValue) {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		String requestUri =  httpRequest.getRequestURI();
		Subject subject = this.getSubject(request, response);
		return subject.isAuthenticated();
	}
	
	@Override
	protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
		return request instanceof HttpServletRequest && WebUtils.toHttp(request).getMethod().equalsIgnoreCase("POST");
	}
}