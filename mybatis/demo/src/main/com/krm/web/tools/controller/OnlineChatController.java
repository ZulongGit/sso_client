package com.krm.web.tools.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.krm.common.base.BaseController;

@Controller
@RequestMapping("tools/chat")
public class OnlineChatController extends BaseController{

	public static final String BASE_URL = "tools/chat";
	private static final String BASE_PATH = "tools/chat/";
	
	@Override
	protected String getBaseUrl() {
		return BASE_URL;
	}

	@Override
	protected String getBasePath() {
		return BASE_PATH;
	}

	@Override
	protected String getBasePermission() {
		return "tools:chat";
	}
	
	@RequestMapping
	public String toChat(){
		return BASE_PATH + "index";
	}

}
