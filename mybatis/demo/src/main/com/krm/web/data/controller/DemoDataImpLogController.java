package com.krm.web.data.controller;

import java.text.DecimalFormat;
import java.util.Date;
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
import com.krm.common.constant.Constant;
import com.krm.common.utils.DateUtils;
import com.krm.common.utils.StringConvert;
import com.krm.common.utils.StringUtil;
import com.krm.common.utils.excel.ExportExcel;
import com.krm.common.utils.excel.ImportExcel;
import com.krm.web.data.model.DemoDataImpLog;
import com.krm.web.data.service.DemoDataImpLogService;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author Parker
 * 数据导入记录控制层
 * 2018-08-22
 */
@Controller
@RequestMapping("data/demoDataImpLog")
public class DemoDataImpLogController extends BaseController {
	
	public static final String BASE_URL = "data/demoDataImpLog";
	private static final String BASE_PATH = "data/demoDataImpLog/";
	
	@Resource
	private DemoDataImpLogService demoDataImpLogService;
	
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
		return "data:demoDataImpLog";
	}
	
	/**
	 * 跳转到模块页面
	 * @param model
	 * @return 模块html
	 */
	@RequestMapping
	public String toDemoDataImpLog(Model model){
		logger.info("跳转到数据导入记录页面(" + getBasePath() + "demoDataImpLog-list)");
		checkPermission("query");
		return getBasePath() + "demoDataImpLog-list";
	}
	
	/**
	 * 分页显示
	 * @param params
	 * @return
	 */
	@RequestMapping(value="list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params, Model model){
		logger.info("分页显示数据导入记录，参数：" + params.toString());
        checkPermission("query");
        //权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("", "u", getBaseUrl(), "id"));
		if (params.containsKey("sortC")){
			//如果传过来的参数是驼峰式，这里需要将驼峰转成下划线式
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<CommonEntity> page = demoDataImpLogService.queryPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute DemoDataImpLog entry, MultipartHttpServletRequest request){
		logger.info("开始保存数据导入记录");
		int count = 0;
		if(StringUtil.isEmpty(entry.getPkid())){
			checkPermission("add");
       		entry.setPkid(demoDataImpLogService.generateId());
       		count = demoDataImpLogService.save(entry);
		}else{
			checkPermission("update");
       		count = demoDataImpLogService.update(entry);
		}
		if(count > 0){
			logger.info("保存数据导入记录成功！");
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	@RequestMapping(value="delete", method = RequestMethod.POST)
	@ResponseBody
	public Result del(String id, @RequestParam Map<String, Object> params){
		logger.info("开始删除数据导入记录，参数：" + id);
		checkPermission("delete");
		int count = demoDataImpLogService.deleteDemoDataImpLog(id);
		if(count > 0){
			logger.info("删除数据导入记录成功！");
			return Result.successResult();
		}
		logger.info("删除数据导入记录失败！");
		return Result.errorResult();
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes", method = RequestMethod.POST)
	@ResponseBody
	public Result dels(@RequestParam(value = "ids[]") String[] ids, @RequestParam Map<String, Object> params){
		logger.info("开始批量删除数据导入记录，参数：" + ids);
		checkPermission("delete");
		int count = demoDataImpLogService.deleteDemoDataImpLog(ids);
		if(count > 0){
			logger.info("删除数据导入记录成功！");
			return Result.successResult();
		}
		logger.info("删除数据导入记录失败！");
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer")
	public String layer(String id, @RequestParam Map<String, Object> params, @PathVariable String mode, Model model){
		DemoDataImpLog entry = null;
		if(StringUtils.equals("add", mode)){
			logger.info("弹窗显示【数据导入记录】添加页面(" + getBasePath() + "demoDataImpLog-add)");
			checkPermission("add");
			return getBasePath() + "demoDataImpLog-add";
		}else if(StringUtils.equals("edit", mode)){
			logger.info("弹窗显示【数据导入记录】编辑页面(" + getBasePath() + "demoDataImpLog-update)");
			checkPermission("update");
			params.put("id", id);
			entry = demoDataImpLogService.queryOne(params);
			model.addAttribute("entry", entry);
			return getBasePath() + "demoDataImpLog-update";
		}else if(StringUtils.equals("detail", mode)){
			logger.info("弹窗显示【数据导入记录】详情页面(" + getBasePath() + "demoDataImpLog-detail)");
			checkPermission("query");
			params.put("id", id);
			CommonEntity entity = demoDataImpLogService.queryOneCommon(params);
			model.addAttribute("entry", entity);
		}else if(StringUtils.equals("import", mode)){
			logger.info("弹窗显示【数据导入记录】Excel导入页面(" + getBasePath() + "demoDataImpLog-import)");
			checkPermission("import");
			return getBasePath() + "demoDataImpLog-import";
		}
		return getBasePath() + "demoDataImpLog-detail";
	}
	
	/**
     * 数据导入记录Excel导入模板
     * @param response
     * @param redirectAttributes
     * @return
     * @throws Exception 
     */
    @RequestMapping("import/template/download")
    public void importDemoDataImpLogTemplate(HttpServletResponse response) throws Exception {
    	logger.info("开始下载数据导入记录Excel导入模板");
    	checkPermission("import");
		String fileName = "数据导入记录Excel导入模板.xlsx";
		List<DemoDataImpLog> list = Lists.newArrayList();
		list.add(new DemoDataImpLog());
		new ExportExcel("数据导入记录", DemoDataImpLog.class, 2).setDataList(list).write(response, fileName).dispose();
    }
    

	/**
     * 数据导入记录跳转
     * @param response
     * @param redirectAttributes
     * @return
     * @throws Exception 
     */
    @RequestMapping("uploadfile")
    public String  importDemoDataImpLog(HttpServletResponse response) throws Exception {
    	return getBasePath() + "demoDataImpLog-uploadfile";
    }
    /**
     * 数据导入记录数据导入
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    @ResponseBody
    public Result importFile(@RequestParam("file") MultipartFile fileList[], HttpServletResponse response) throws Exception {
    	logger.info("开始导入数据导入记录数据");
    	checkPermission("import");
    	Long start = System.currentTimeMillis();
    	int successNum = 0;
    	int failureNum = 0;
    	for (MultipartFile file : fileList) {
    		ImportExcel ei;
    		StringBuilder failureMsg = new StringBuilder();
   			ei = new ImportExcel(file, 1, 0);
   			List<DemoDataImpLog> list = ei.getDataList(DemoDataImpLog.class);
   			for (DemoDataImpLog entry : list) {
   				entry.setPkid(demoDataImpLogService.generateId());
   				entry.setCreateBy(SysUserUtils.getCacheLoginUser().getId());
   				entry.setCreateDate(new Date());
   				//entry.setDelFlag(Constant.DEL_FLAG_NORMAL);
			}
   			successNum = demoDataImpLogService.insertBatch(list);
    		if (failureNum > 0){
    			failureMsg.insert(0, "，失败导入 " + failureNum + " 条数据导入记录数据，导入信息如下：");
    		}
    		Long end = System.currentTimeMillis();
    		DecimalFormat df = new DecimalFormat("######0.00");
    		logger.info("导入用时"+df.format((double)(end-start)/(double)1000)+"秒");
		}
    	return new Result(1, "操作成功！，成功导入"+successNum+"条，失败导入"+failureNum+"条");
    }
    
    /**
	 * 数据导入记录导出excel
	 */
	@RequestMapping(value="export", method = RequestMethod.POST)
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception{
		logger.info("开始导出数据导入记录数据");
		checkPermission("export");
		String fileName = "数据导入记录" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		//权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("", "u", getBaseUrl(), "id"));
		try {
			for (String key : params.keySet()){ // 处理中文乱码
				String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"), "UTF-8");
				paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
				params.put(key, paramsTrans.trim());
			}
		} catch (Exception e) {
		}
		List<DemoDataImpLog> list = demoDataImpLogService.entityList(params);
		new ExportExcel("数据导入记录", DemoDataImpLog.class).setDataList(list)
			.write(response, fileName).dispose();
	}
}