package com.krm.web.sys.security;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import com.krm.common.utils.CookieUtils;

/**
 * 集成websession管理器，重写两个方法实现自己的需求
 * @author Parker
 *
 */
public class WebSessionManager extends DefaultWebSessionManager {
	// 自定义缓存，存储 客户端-sessionid
	public static final Map<String, Serializable> MAP = new HashMap<String, Serializable>();

	private static Log log = LogFactory.getLog(WebSessionManager.class);
	private static Integer sessionCookieTime = 30 * 60;// sessionId的cookie存活时间。单位为S
	private static Integer sessionTime_bs = 30 * 60 * 1000;// session的过期时间B/S客户端。单位为MS
	private static Integer sessionTime_cs = 30 * 24 * 60 * 60 * 1000;// session的过期时间C/S客户端。单位为MS

	/**
	 * 根据客户端的sessionIdKey获取真正的sessionId
	 */
	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String sessionIdKey = getSeesionIdKey(req);
		Serializable sessionId = MAP.get(sessionIdKey);
		if (null != sessionId) {
			// 延长cookie过期时间
			setSessionIdKeyCookie(res, sessionIdKey, sessionCookieTime);
		}
		return sessionId;
	}

	/**
	 * 创建一个session
	 */
	@Override
	protected void onStart(Session session, SessionContext context) {
		// 判断是否是http请求
		if (!WebUtils.isHttp(context)) {
			log.debug("HTTP请求才能创建session");
			return;
		}
		HttpServletRequest request = WebUtils.getHttpRequest(context);
		HttpServletResponse response = WebUtils.getHttpResponse(context);
		request.removeAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE);
		request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_IS_NEW, Boolean.TRUE);
		String sessionId = UUID.randomUUID().toString().trim().toUpperCase();
		Serializable id = session.getId();
		setSessionIdKeyCookie(response, sessionId, sessionCookieTime);// 设置cookie过期时间
		String token = request.getParameter("access_token");
		if (token != null) {
			session.setTimeout(sessionTime_cs);// 设置C/S的session过期时间
		} else {
			session.setTimeout(sessionTime_bs);// 设置B/S的session过期时间
		}
		MAP.put(sessionId, id);// 存储sessionIdKey和真正的sessionId
	}

	/**
	 * 获取客户端存储的sessionIdKey
	 * 
	 * @param request
	 * @return
	 */
	private String getSeesionIdKey(HttpServletRequest request) {
		String sessionIdKey = null;
		try {
			sessionIdKey = CookieUtils.getCookie(request, "JSESSIONID");
			if (null == sessionIdKey || sessionIdKey.isEmpty()) {
				sessionIdKey = request.getParameter("access_token");
			}
		} catch (Exception e) {
			log.debug("获取sessionIdKey失败");
		}
		return sessionIdKey;
	}

	/**
	 * 设置sessionIdKey的cookie
	 * 
	 * @param sessionId
	 *            sessionId
	 * @param age
	 *            age
	 */
	private void setSessionIdKeyCookie(HttpServletResponse response, String sessionId, Integer age) {
		Cookie cookie = new Cookie("JSESSIONID", sessionId.toUpperCase());
		cookie.setHttpOnly(Boolean.TRUE);
		cookie.setPath("/");
		cookie.setMaxAge(sessionCookieTime);
		response.addCookie(cookie);
	}

}
