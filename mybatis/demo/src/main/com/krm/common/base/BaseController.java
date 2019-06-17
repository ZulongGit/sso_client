package com.krm.common.base;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.krm.common.spring.utils.DateTypeEditor;

@CrossOrigin(origins = "*", maxAge = 3600)
public abstract class BaseController {
	
	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected final String defaultErrorPage = "error/error";
	protected abstract String getBaseUrl();
    protected abstract String getBasePath();
    protected String getBasePermission() {
        return null;
    }
	
    /**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new DateTypeEditor());
		//附件类型转换
		binder.registerCustomEditor(MultipartFile.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text.equals("") ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
	}
	
    /**
     * 检查权限
     * @param subPerm
     */
    protected void checkPermission(String subPerm) {
        if(null == getBasePermission()) {
            return;
        }
        SecurityUtils.getSubject().checkPermission(getBasePermission() + (null != subPerm ? ":" + subPerm : ""));
    }
    
	/**
     * 得到错误页面
     * @return
     */
    protected String getErrorPage() {
        return defaultErrorPage;
    }
    
	
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(Throwable exception, HttpServletRequest request) {
    	logger.error("操作异常", exception);
    	String msg = "操作异常，请联系管理员！";
    	request.setAttribute("msg", msg);
//    	LogUtils.logPageError(request);
    	return getErrorPage();
    }
    
    
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionHandler(RuntimeException exception, HttpServletRequest request) {
		logger.error("『*****系统内部发生错误，抛出异常，以下是异常信息：*****』", exception);
        String msg = exception.getMessage() == null ?  "[500] 抱歉，系统内部发生错误，请联系管理员！" : exception.getMessage();
        request.setAttribute("msg", msg);
//        LogUtils.logPageError(request);
        return getErrorPage();
    }
	
	/**
	 * 没有操作权限异常
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public String exceptionHandler(AuthorizationException exception, HttpServletRequest request) {
		logger.error("『*****该用户没有相关操作权限，抛出异常，以下是异常信息：*****』", exception);
		String msg = "抱歉，您没有相关操作权限，请联系管理员！";
		request.setAttribute("msg", msg);
//		LogUtils.logPageError(request);
		return getErrorPage();
	}
	
	
}
