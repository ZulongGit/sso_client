

package com.krm.web.sys.controller;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.krm.common.base.BaseController;
import com.krm.common.base.Result;
import com.krm.web.sys.model.SysLog;
import com.krm.web.sys.service.SysLogService;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("syslog")
public class SysLogController extends BaseController {
	
	public static final String BASE_URL = "syslog";
	private static final String BASE_PATH = "sys/log/";

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
		return "syslog";
	}
	
	@Resource
	private SysLogService sysLogService;
	
	/**
	 * 跳转到模块页面
	 */
	@RequestMapping
	public String toSysLog(Model model){
		checkPermission("query");
		return BASE_PATH + "log";
	}
	
	/**
	 * 分页显示
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model){
		checkPermission("query");
		PageInfo<SysLog> page = sysLogService.findPageInfo(params);
		model.addAttribute("page", page);
		return BASE_PATH + "log-list";
	}
	
	/**
	 * 添加或更新
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysLog sysLog){
		return sysLogService.saveSysLog(sysLog);
	}
	
	
	/**
	 * 删除
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Result del(String id){
		checkPermission("delete");
		int count = sysLogService.deleteByPrimaryKey(id);
		if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(String id,Model model){
		checkPermission("query");
		SysLog sysLog = sysLogService.getById(id);
		model.addAttribute("sysLog", sysLog);
		return BASE_PATH + "log-detail";
	}
	

}
