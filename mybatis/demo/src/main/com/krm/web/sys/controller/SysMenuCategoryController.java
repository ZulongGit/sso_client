package com.krm.web.sys.controller;

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
import com.krm.web.sys.model.SysMenuCategory;
import com.krm.web.sys.service.SysMenuCategoryService;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author Parker
 * 菜单分类控制层
 * 2018-08-02
 */
@Controller
@RequestMapping("sys/sysMenuCategory")
public class SysMenuCategoryController extends BaseController {
	
	public static final String BASE_URL = "sys/sysMenuCategory";
	private static final String BASE_PATH = "sys/sysMenuCategory/";
	
	@Resource
	private SysMenuCategoryService sysMenuCategoryService;
	
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
		return "sys:sysMenuCategory";
	}
	
	/**
	 * 跳转到模块页面
	 * @param model
	 * @return 模块html
	 */
	@RequestMapping
	public String toSysMenuCategory(Model model){
		logger.info("跳转到菜单分类页面(" + getBasePath() + "sysMenuCategory-list)");
		checkPermission("query");
		return getBasePath() + "sysMenuCategory-list";
	}
	
	/**
	 * 分页显示
	 * @param params
	 * @return
	 */
	@RequestMapping(value="list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params, Model model){
		logger.info("分页显示菜单分类，参数：" + params.toString());
        checkPermission("query");
        //权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("", "", getBaseUrl(), "id"));
		if (params.containsKey("sortC")){
			//如果传过来的参数是驼峰式，这里需要将驼峰转成下划线式
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<CommonEntity> page = sysMenuCategoryService.queryPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute SysMenuCategory entry, MultipartHttpServletRequest request){
		logger.info("开始保存菜单分类");
		int count = 0;
		if(StringUtil.isEmpty(entry.getId())){
			checkPermission("add");
       		entry.setId(sysMenuCategoryService.generateId());
       		count = sysMenuCategoryService.save(entry);
		}else{
			checkPermission("update");
       		count = sysMenuCategoryService.update(entry);
		}
		if(count > 0){
			logger.info("保存菜单分类成功！");
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
		logger.info("开始删除菜单分类，参数：" + id);
		checkPermission("delete");
		int count = sysMenuCategoryService.deleteSysMenuCategory(id);
		if(count > 0){
			logger.info("删除菜单分类成功！");
			return Result.successResult();
		}
		logger.info("删除菜单分类失败！");
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
		logger.info("开始批量删除菜单分类，参数：" + ids);
		checkPermission("delete");
		int count = sysMenuCategoryService.deleteSysMenuCategory(ids);
		if(count > 0){
			logger.info("删除菜单分类成功！");
			return Result.successResult();
		}
		logger.info("删除菜单分类失败！");
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer", method=RequestMethod.POST)
	public String layer(String id, @RequestParam Map<String, Object> params, @PathVariable String mode, Model model){
		SysMenuCategory entry = null;
		if(StringUtils.equals("add", mode)){
			logger.info("弹窗显示【菜单分类】添加页面(" + getBasePath() + "sysMenuCategory-add)");
			checkPermission("add");
			return getBasePath() + "sysMenuCategory-add";
		}else if(StringUtils.equals("edit", mode)){
			logger.info("弹窗显示【菜单分类】编辑页面(" + getBasePath() + "sysMenuCategory-update)");
			checkPermission("update");
			params.put("id", id);
			entry = sysMenuCategoryService.queryOne(params);
			model.addAttribute("entry", entry);
			return getBasePath() + "sysMenuCategory-update";
		}else if(StringUtils.equals("detail", mode)){
			logger.info("弹窗显示【菜单分类】详情页面(" + getBasePath() + "sysMenuCategory-detail)");
			checkPermission("query");
			params.put("id", id);
			CommonEntity entity = sysMenuCategoryService.queryOneCommon(params);
			model.addAttribute("entry", entity);
		}else if(StringUtils.equals("import", mode)){
			logger.info("弹窗显示【菜单分类】Excel导入页面(" + getBasePath() + "sysMenuCategory-import)");
			checkPermission("import");
			return getBasePath() + "sysMenuCategory-import";
		}
		return getBasePath() + "sysMenuCategory-detail";
	}
	
	/**
     * 菜单分类Excel导入模板
     * @param response
     * @param redirectAttributes
     * @return
     * @throws Exception 
     */
    @RequestMapping("import/template/download")
    public void importSysMenuCategoryTemplate(HttpServletResponse response) throws Exception {
    	logger.info("开始下载菜单分类Excel导入模板");
    	checkPermission("import");
		String fileName = "菜单分类Excel导入模板.xlsx";
		List<SysMenuCategory> list = Lists.newArrayList();
		list.add(new SysMenuCategory());
		new ExportExcel("菜单分类", SysMenuCategory.class, 2).setDataList(list).write(response, fileName).dispose();
    }
    
    /**
     * 菜单分类数据导入
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    @ResponseBody
    public Result importFile(@RequestParam("file") MultipartFile fileList[], HttpServletResponse response) throws Exception {
    	logger.info("开始导入菜单分类数据");
    	checkPermission("import");
    	Long start = System.currentTimeMillis();
    	int successNum = 0;
    	int failureNum = 0;
    	for (MultipartFile file : fileList) {
    		ImportExcel ei;
    		StringBuilder failureMsg = new StringBuilder();
   			ei = new ImportExcel(file, 1, 0);
   			List<SysMenuCategory> list = ei.getDataList(SysMenuCategory.class);
   			for (SysMenuCategory entry : list) {
   				entry.setId(sysMenuCategoryService.generateId());
   				entry.setCreateBy(SysUserUtils.getCacheLoginUser().getId());
   				entry.setCreateDate(new Date());
   				entry.setDelFlag(Constant.DEL_FLAG_NORMAL);
			}
   			successNum = sysMenuCategoryService.insertBatch(list);
    		if (failureNum > 0){
    			failureMsg.insert(0, "，失败导入 " + failureNum + " 条菜单分类数据，导入信息如下：");
    		}
    		Long end = System.currentTimeMillis();
    		DecimalFormat df = new DecimalFormat("######0.00");
    		logger.info("导入用时"+df.format((double)(end-start)/(double)1000)+"秒");
		}
    	return new Result(1, "操作成功！，成功导入"+successNum+"条，失败导入"+failureNum+"条");
    }
    
    /**
	 * 菜单分类导出excel
	 */
	@RequestMapping(value="export", method = RequestMethod.POST)
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception{
		logger.info("开始导出菜单分类数据");
		checkPermission("export");
		String fileName = "菜单分类" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		//权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("", "", getBaseUrl(), "id"));
		try {
			for (String key : params.keySet()){ // 处理中文乱码
				String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"), "UTF-8");
				paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
				params.put(key, paramsTrans.trim());
			}
		} catch (Exception e) {
		}
		List<SysMenuCategory> list = sysMenuCategoryService.entityList(params);
		new ExportExcel("菜单分类", SysMenuCategory.class).setDataList(list)
			.write(response, fileName).dispose();
	}
}