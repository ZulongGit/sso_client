package com.krm.web.sys.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.utils.DateUtils;
import com.krm.common.utils.StringConvert;
import com.krm.common.utils.StringUtil;
import com.krm.common.utils.excel.ExportExcel;
import com.krm.common.utils.excel.ImportExcel;
import com.krm.web.sys.model.SysUpdateLog;
import com.krm.web.sys.service.SysUpdateLogService;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author Parker
 * 系统升级日志控制层
 * 2017-12-27
 */
@Controller
@RequestMapping("sys/sysUpdateLog")
public class SysUpdateLogController extends BaseController {
	
	public static final String BASE_URL = "sys/sysUpdateLog";
	private static final String BASE_PATH = "sys/sysUpdateLog/";
	
	@Resource
	private SysUpdateLogService sysUpdateLogService;
	
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
		return "sys:sysUpdateLog";
	}
	
	/**
	 * 跳转到模块页面
	 * @param model
	 * @return 模块html
	 */
	@RequestMapping
	public String tosysUpdateLog(Model model){
		logger.info("跳转到系统升级日志页面(" + BASE_PATH + "sysUpdateLog-list)");
		checkPermission("query");
		return  BASE_PATH + "sysUpdateLog-list";
	}
	
	/**
	 * 分页显示
	 * @param params
	 * @return
	 */
	@RequestMapping(value="list", method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params,Model model){
		logger.info("分页显示系统升级日志，参数：" + params.toString());
		checkPermission("query");
		//权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("", "u",BASE_URL, "id"));
		if (params.containsKey("sortC")){
			//如果传过来的参数是驼峰式，这里需要将驼峰转成下划线式
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<CommonEntity> page = sysUpdateLogService.queryPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute SysUpdateLog sysUpdateLog, MultipartHttpServletRequest request){
		logger.info("开始保存系统升级日志，参数：" + sysUpdateLog.toString());
		int count = 0;
		if(StringUtil.isEmpty(sysUpdateLog.getId())){
			checkPermission("add");
       		sysUpdateLog.setId(sysUpdateLogService.generateId());
       		count = sysUpdateLogService.save(sysUpdateLog);
		}else{
			checkPermission("update");
       		count = sysUpdateLogService.update(sysUpdateLog);
		}
		if(count > 0){
			logger.info("保存系统升级日志成功！");
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.POST)
	@ResponseBody
	public Result del(String id, @RequestParam Map<String, Object> params){
		logger.info("开始删除系统升级日志，参数：" + id);
		checkPermission("delete");
		int count = sysUpdateLogService.deleteSysUpdateLog(id);
		if(count > 0){
			logger.info("删除系统升级日志成功！");
			return Result.successResult();
		}
		logger.info("删除系统升级日志失败！");
		return Result.errorResult();
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes", method=RequestMethod.POST)
	@ResponseBody
	public Result dels(@RequestParam(value = "ids[]") String[] ids, @RequestParam Map<String, Object> params){
		logger.info("开始批量删除系统升级日志，参数：" + ids);
		checkPermission("delete");
		int count = sysUpdateLogService.deleteSysUpdateLog(ids);
		if(count > 0){
			logger.info("删除系统升级日志成功！");
			return Result.successResult();
		}
		logger.info("删除系统升级日志失败！");
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer", method=RequestMethod.POST)
	public String layer(String id, @RequestParam Map<String, Object> params, @PathVariable String mode, Model model){
		SysUpdateLog sysUpdateLog = null;
		if(StringUtils.equals("add", mode)){
			logger.info("弹窗显示【系统升级日志】添加页面(" + BASE_PATH + "sysUpdateLog-add)");
			checkPermission("add");
			return  BASE_PATH + "sysUpdateLog-add";
		}else if(StringUtils.equals("edit", mode)){
			logger.info("弹窗显示【系统升级日志】编辑页面(" + BASE_PATH + "sysUpdateLog-edit)");
			checkPermission("update");
			params.put("id", id);
			sysUpdateLog = sysUpdateLogService.queryOne(params);
			model.addAttribute("sysUpdateLog", sysUpdateLog);
			return  BASE_PATH + "sysUpdateLog-update";
		}else if(StringUtils.equals("detail", mode)){
			logger.info("弹窗显示【系统升级日志】详情页面(" + BASE_PATH + "sysUpdateLog-detail)");
			checkPermission("query");
			params.put("id", id);
			sysUpdateLog = sysUpdateLogService.queryOne(params);
			model.addAttribute("sysUpdateLog", sysUpdateLog);
		}else if(StringUtils.equals("import", mode)){
			logger.info("弹窗显示【系统升级日志】Excel导入页面(" + BASE_PATH + "sysUpdateLog-import)");
			checkPermission("import");
			return  BASE_PATH + "sysUpdateLog-import";
		}
		return  BASE_PATH + "sysUpdateLog-detail";
	}
	
	/**
     * 系统升级日志Excel导入模板
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("import/template/download")
    public void importSysUpdateLogTemplate(HttpServletResponse response) {
    	logger.info("开始下载系统升级日志Excel导入模板");
    	checkPermission("import");
		try {
			String fileName = "系统升级日志Excel导入模板.xlsx";
			List<SysUpdateLog> list = Lists.newArrayList();
			list.add(new SysUpdateLog());
			new ExportExcel("系统升级日志", SysUpdateLog.class, 2).setDataList(list).write(response, fileName).dispose();
		} catch (Exception e) {
			logger.info("导入模板下载失败！失败信息：" + e.getMessage());
		}
    }
    
    /**
     * 系统升级日志数据导入
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    @ResponseBody
    public Result importFile(MultipartHttpServletRequest request, HttpServletResponse response) {
    	logger.info("开始导入系统升级日志数据");
    	checkPermission("import");
    	Long start = System.currentTimeMillis();
    	List<MultipartFile> fileList = request.getFiles("file");
    	if(fileList == null || fileList.size() == 0){
    		fileList.clear();
    		fileList = new ArrayList<MultipartFile>();
    		Iterator<String> files = request.getFileNames();
    		while (files.hasNext()) {
    			MultipartFile file = request.getFile(files.next());
    			if (file != null) {
    				fileList.add(file);
    			}
    		}
    	}
    	int successNum = 0;
    	int failureNum = 0;
    	for (MultipartFile file : fileList) {
    		ImportExcel ei;
    		StringBuilder failureMsg = new StringBuilder();
    		try {
    			ei = new ImportExcel(file, 1, 0);
    			List<SysUpdateLog> list = ei.getDataList(SysUpdateLog.class);
    			for (SysUpdateLog sysUpdateLog : list) {
    				if(sysUpdateLogService.saveOrUpdate(sysUpdateLog) > 0){
    					successNum++;
    				}else{
    					failureNum++;
    				}
				}
    		} catch (Exception e) {
    			logger.info(e.toString());
    		}
    		if (failureNum > 0){
    			failureMsg.insert(0, "，失败导入 " + failureNum + " 条系统升级日志数据，导入信息如下：");
    		}
    		Long end = System.currentTimeMillis();
    		DecimalFormat df = new DecimalFormat("######0.00");
    		logger.info("导入用时"+df.format((double)(end-start)/(double)1000)+"秒");
		}
    	return new Result(1, "操作成功！，成功导入"+successNum+"条，失败导入"+failureNum+"条");
    }
    
    /**
	 * 系统升级日志导出excel
	 */
	@RequestMapping(value="export", method = RequestMethod.POST)
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response){
		logger.info("开始导出系统升级日志数据");
		checkPermission("export");
		String fileName = "系统升级日志" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		//权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("", "u",BASE_URL, "id"));
		try {
			for (String key : params.keySet()){ // 处理中文乱码
				String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"), "UTF-8");
				paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
				params.put(key, paramsTrans.trim());
			}
		} catch (Exception e) {
		}
		List<SysUpdateLog> list = sysUpdateLogService.entityList(params);
		try {
			new ExportExcel("系统升级日志", SysUpdateLog.class).setDataList(list)
				.write(response, fileName).dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
