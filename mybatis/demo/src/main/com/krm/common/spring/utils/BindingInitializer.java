package com.krm.common.spring.utils;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

/**
 * 数据绑定初始化类
 */
public class BindingInitializer implements WebBindingInitializer {
	/**
	 * 初始化数据绑定
	 */
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new DateTypeEditor());
		binder.registerCustomEditor(MultipartFile.class, new DateTypeEditor());
	}
}
