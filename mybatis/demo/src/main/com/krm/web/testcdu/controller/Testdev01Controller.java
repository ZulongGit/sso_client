package com.krm.web.testcdu.controller;

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
import com.krm.web.testcdu.model.Testdev01;
import com.krm.web.testcdu.service.Testdev01Service;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author zhaozulong
 * 报告控制层
 * 2018-12-06
 */
@Controller
@RequestMapping("testcdu/testdev01")
public class Testdev01Controller extends BaseController {
	
	public static final String BASE_URL = "testcdu/testdev01";
	private static final String BASE_PATH = "testcdu/testdev01/";
	
	@Resource
	private Testdev01Service testdev01Service;
	
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
		return "testcdu:testdev01";
	}
	
	/**
	 * 跳转到模块页面
	 * @param model
	 * @return 模块html
	 */
	@RequestMapping
	public String toTestdev01(Model model){
		logger.info("跳转到报告页面(" + getBasePath() + "testdev01-list)");
		checkPermission("query");
		return getBasePath() + "testdev01-list";
	}
	
	/**
	 * 分页显示
	 * @param params
	 * @return
	 */
	@RequestMapping(value="list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params, Model model){
		logger.info("分页显示报告，参数：" + params.toString());
        checkPermission("query");
        //权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("", "u", getBaseUrl(), "id"));
		if (params.containsKey("sortC")){
			//如果传过来的参数是驼峰式，这里需要将驼峰转成下划线式
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<CommonEntity> page = testdev01Service.queryPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute Testdev01 entry, MultipartHttpServletRequest request){
		logger.info("开始保存报告");
		int count = 0;
		if(StringUtil.isEmpty(entry.getId())){
			checkPermission("add");
       		entry.setId(testdev01Service.generateId());
       		count = testdev01Service.save(entry);
		}else{
			checkPermission("update");
       		count = testdev01Service.update(entry);
		}
		if(count > 0){
			logger.info("保存报告成功！");
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
		logger.info("开始删除报告，参数：" + id);
		checkPermission("delete");
		int count = testdev01Service.deleteTestdev01(id);
		if(count > 0){
			logger.info("删除报告成功！");
			return Result.successResult();
		}
		logger.info("删除报告失败！");
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
		logger.info("开始批量删除报告，参数：" + ids);
		checkPermission("delete");
		int count = testdev01Service.deleteTestdev01(ids);
		if(count > 0){
			logger.info("删除报告成功！");
			return Result.successResult();
		}
		logger.info("删除报告失败！");
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer", method=RequestMethod.POST)
	public String layer(String id, @RequestParam Map<String, Object> params, @PathVariable String mode, Model model){
		Testdev01 entry = null;
		if(StringUtils.equals("add", mode)){
			logger.info("弹窗显示【报告】添加页面(" + getBasePath() + "testdev01-add)");
			checkPermission("add");
			return getBasePath() + "testdev01-add";
		}else if(StringUtils.equals("edit", mode)){
			logger.info("弹窗显示【报告】编辑页面(" + getBasePath() + "testdev01-update)");
			checkPermission("update");
			params.put("id", id);
			entry = testdev01Service.queryOne(params);
			model.addAttribute("entry", entry);
			return getBasePath() + "testdev01-update";
		}else if(StringUtils.equals("detail", mode)){
			logger.info("弹窗显示【报告】详情页面(" + getBasePath() + "testdev01-detail)");
			checkPermission("query");
			params.put("id", id);
			CommonEntity entity = testdev01Service.queryOneCommon(params);
			model.addAttribute("entry", entity);
		}else if(StringUtils.equals("import", mode)){
			logger.info("弹窗显示【报告】Excel导入页面(" + getBasePath() + "testdev01-import)");
			checkPermission("import");
			return getBasePath() + "testdev01-import";
		}
		return getBasePath() + "testdev01-detail";
	}
	
	/**
     * 报告Excel导入模板
     * @param response
     * @param redirectAttributes
     * @return
     * @throws Exception 
     */
    @RequestMapping("import/template/download")
    public void importTestdev01Template(HttpServletResponse response) throws Exception {
    	logger.info("开始下载报告Excel导入模板");
    	checkPermission("import");
		String fileName = "报告Excel导入模板.xlsx";
		List<Testdev01> list = Lists.newArrayList();
		list.add(new Testdev01());
		new ExportExcel("报告", Testdev01.class, 2).setDataList(list).write(response, fileName).dispose();
    }
    
    /**
     * 报告数据导入
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    @ResponseBody
    public Result importFile(@RequestParam("file") MultipartFile fileList[], HttpServletResponse response) throws Exception {
    	logger.info("开始导入报告数据");
    	checkPermission("import");
    	Long start = System.currentTimeMillis();
    	int successNum = 0;
    	int failureNum = 0;
    	for (MultipartFile file : fileList) {
    		ImportExcel ei;
    		StringBuilder failureMsg = new StringBuilder();
   			ei = new ImportExcel(file, 1, 0);
   			List<Testdev01> list = ei.getDataList(Testdev01.class);
   			for (Testdev01 entry : list) {
   				entry.setId(testdev01Service.generateId());
   				entry.setCreateBy(SysUserUtils.getCacheLoginUser().getId());
   				entry.setCreateDate(new Date());
   				entry.setDelFlag(Constant.DEL_FLAG_NORMAL);
			}
   			successNum = testdev01Service.insertBatch(list);
    		if (failureNum > 0){
    			failureMsg.insert(0, "，失败导入 " + failureNum + " 条报告数据，导入信息如下：");
    		}
    		Long end = System.currentTimeMillis();
    		DecimalFormat df = new DecimalFormat("######0.00");
    		logger.info("导入用时"+df.format((double)(end-start)/(double)1000)+"秒");
		}
    	return new Result(1, "操作成功！，成功导入"+successNum+"条，失败导入"+failureNum+"条");
    }
    
    /**
	 * 报告导出excel
	 */
	@RequestMapping(value="export", method = RequestMethod.POST)
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception{
		logger.info("开始导出报告数据");
		checkPermission("export");
		String fileName = "报告" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
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
		List<Testdev01> list = testdev01Service.entityList(params);
		new ExportExcel("报告", Testdev01.class).setDataList(list)
			.write(response, fileName).dispose();
	}
}