package com.krm.web.codegen.controller;

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
import com.krm.web.codegen.model.GenDict;
import com.krm.web.codegen.service.GenDictService;
import com.krm.web.codegen.service.ProjectsService;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author Parker
 * 枚举类数据字典控制层
 * 2018-05-28
 */
@Controller
@RequestMapping("codegen/genDict")
public class GenDictController extends BaseController {
	
	public static final String BASE_URL = "codegen/genDict";
	private static final String BASE_PATH = "codegen/genDict/";
	
	@Resource
	private GenDictService genDictService;
	@Resource
	private ProjectsService projectsService;
	
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
		return "codegen:genDict";
	}
	
	/**
	 * 跳转到模块页面
	 * @param model
	 * @return 模块html
	 */
	@RequestMapping
	public String toGenDict(Model model){
		logger.info("跳转到枚举类数据字典页面(" + getBasePath() + "genDict-list)");
		checkPermission("query");
		return getBasePath() + "genDict-list";
	}
	
	/**
	 * 分页显示
	 * @param params
	 * @return
	 */
	@RequestMapping(value="list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params, Model model){
		logger.info("分页显示枚举类数据字典，参数：" + params.toString());
        checkPermission("query");
        params.put("userId", SysUserUtils.getCacheLoginUser().getId());
        String[] proIds = projectsService.queryMyProject(params);
        params.put("proIds", proIds);
        //权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("o", "u", getBaseUrl(), "id"));
		if (params.containsKey("sortC")){
			//如果传过来的参数是驼峰式，这里需要将驼峰转成下划线式
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		params.put("delFlag", Constant.DEL_FLAG_NORMAL);
		PageInfo<CommonEntity> page = genDictService.queryPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute GenDict entry, MultipartHttpServletRequest request){
		logger.info("开始保存枚举类数据字典");
		int count = 0;
		if(StringUtil.isEmpty(entry.getId())){
			checkPermission("add");
       		entry.setId(genDictService.generateId());
       		count = genDictService.save(entry);
		}else{
			checkPermission("update");
       		count = genDictService.update(entry);
		}
		if(count > 0){
			logger.info("保存枚举类数据字典成功！");
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
		logger.info("开始删除枚举类数据字典，参数：" + id);
		checkPermission("delete");
		int count = genDictService.deleteGenDict(id);
		if(count > 0){
			logger.info("删除枚举类数据字典成功！");
			return Result.successResult();
		}
		logger.info("删除枚举类数据字典失败！");
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
		logger.info("开始批量删除枚举类数据字典，参数：" + ids);
		checkPermission("delete");
		int count = genDictService.deleteGenDict(ids);
		if(count > 0){
			logger.info("删除枚举类数据字典成功！");
			return Result.successResult();
		}
		logger.info("删除枚举类数据字典失败！");
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer", method=RequestMethod.POST)
	public String layer(String id, @RequestParam Map<String, Object> params, @PathVariable String mode, Model model){
		GenDict entry = null;
		if(StringUtils.equals("add", mode)){
			logger.info("弹窗显示【枚举类数据字典】添加页面(" + getBasePath() + "genDict-add)");
			checkPermission("add");
			entry = new GenDict();
			entry.setSort(10);
			model.addAttribute("entry", entry);
			return getBasePath() + "genDict-add";
		}else if (StringUtils.equalsIgnoreCase(mode, "formAdd")){
			logger.info("弹窗显示添加字典页面（带参数）");
			checkPermission("add");
			Integer sort = 0;
			List<GenDict> list = genDictService.entityList(params);
			for (GenDict temp : list) {
				if(temp.getSort().intValue() >= sort.intValue()){
					sort = temp.getSort() + 10;
					entry = temp;
				}
			}
			entry.setSort(sort);
			entry.setLabel(null);
			entry.setValue(null);
			entry.setId(null);
			model.addAttribute("entry", entry);
			return getBasePath() + "genDict-add";
		}else if(StringUtils.equals("edit", mode)){
			logger.info("弹窗显示【枚举类数据字典】编辑页面(" + getBasePath() + "genDict-update)");
			checkPermission("update");
			params.put("id", id);
			entry = genDictService.queryOne(params);
			model.addAttribute("entry", entry);
			return getBasePath() + "genDict-update";
		}else if(StringUtils.equals("detail", mode)){
			logger.info("弹窗显示【枚举类数据字典】详情页面(" + getBasePath() + "genDict-detail)");
			checkPermission("query");
			params.put("id", id);
			CommonEntity entity = genDictService.queryOneCommon(params);
			model.addAttribute("entry", entity);
		}else if(StringUtils.equals("import", mode)){
			logger.info("弹窗显示【枚举类数据字典】Excel导入页面(" + getBasePath() + "genDict-import)");
			checkPermission("import");
			return getBasePath() + "genDict-import";
		}
		return getBasePath() + "genDict-detail";
	}
	
	/**
     * 枚举类数据字典Excel导入模板
     * @param response
     * @param redirectAttributes
     * @return
     * @throws Exception 
     */
    @RequestMapping("import/template/download")
    public void importGenDictTemplate(HttpServletResponse response) throws Exception {
    	logger.info("开始下载枚举类数据字典Excel导入模板");
    	checkPermission("import");
		String fileName = "枚举类数据字典Excel导入模板.xlsx";
		List<GenDict> list = Lists.newArrayList();
		list.add(new GenDict());
		new ExportExcel("枚举类数据字典", GenDict.class, 2).setDataList(list).write(response, fileName).dispose();
    }
    
    /**
     * 枚举类数据字典数据导入
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    @ResponseBody
    public Result importFile(@RequestParam("file") MultipartFile fileList[], HttpServletResponse response) throws Exception {
    	logger.info("开始导入枚举类数据字典数据");
    	checkPermission("import");
    	Long start = System.currentTimeMillis();
    	int successNum = 0;
    	int failureNum = 0;
    	for (MultipartFile file : fileList) {
    		ImportExcel ei;
    		StringBuilder failureMsg = new StringBuilder();
   			ei = new ImportExcel(file, 1, 0);
   			List<GenDict> list = ei.getDataList(GenDict.class);
   			for (GenDict entry : list) {
   				entry.setId(genDictService.generateId());
   				entry.setCreateBy(SysUserUtils.getCacheLoginUser().getId());
   				entry.setCreateDate(new Date());
   				entry.setDelFlag(Constant.DEL_FLAG_NORMAL);
			}
   			successNum = genDictService.insertBatch(list);
    		if (failureNum > 0){
    			failureMsg.insert(0, "，失败导入 " + failureNum + " 条枚举类数据字典数据，导入信息如下：");
    		}
    		Long end = System.currentTimeMillis();
    		DecimalFormat df = new DecimalFormat("######0.00");
    		logger.info("导入用时"+df.format((double)(end-start)/(double)1000)+"秒");
		}
    	return new Result(1, "操作成功！，成功导入"+successNum+"条，失败导入"+failureNum+"条");
    }
    
    /**
	 * 枚举类数据字典导出excel
	 */
	@RequestMapping(value="export", method = RequestMethod.POST)
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception{
		logger.info("开始导出枚举类数据字典数据");
		checkPermission("export");
		String fileName = "枚举类数据字典" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
		//权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("o", "u", getBaseUrl(), "id"));
		try {
			for (String key : params.keySet()){ // 处理中文乱码
				String paramsTrans = new String(((String) params.get(key)).getBytes("ISO-8859-1"), "UTF-8");
				paramsTrans = java.net.URLDecoder.decode(paramsTrans, "UTF-8");
				params.put(key, paramsTrans.trim());
			}
		} catch (Exception e) {
		}
		List<GenDict> list = genDictService.entityList(params);
		new ExportExcel("枚举类数据字典", GenDict.class).setDataList(list)
			.write(response, fileName).dispose();
	}
	
	/**
	 * 生成SQL
	 * @param
	 * @return
	 */
	@RequestMapping(value="generateSql", method = RequestMethod.POST)
	@ResponseBody
	public Result generateSql(@RequestParam(value = "ids[]") String[] ids, @RequestParam Map<String, Object> params){
		logger.info("开始生成数据字典sql，参数：" + ids);
		StringBuilder sb = new StringBuilder();
		sb.append("<style>.contents {font-family: \"Times New Roman\",Georgia,Serif;}.keyWords {color: blue;font-weight: bold;}"
				+ ".text {color: #FF0080;} .type {color: #ff4006;} .comments {color: #777;font-weight: bold;font-style: italic;}</style>");
		sb.append("<pre class='contents'>");
		for (String id : ids) {
			GenDict dict = genDictService.queryById(id);
			sb.append("<span class='keyWords'>INSERT INTO</span> sys_dict(ID, LABEL, VALUE, TYPE, DESCRIPTION, SORT) <span class='keyWords'> VALUES <br></span>"
					+ "		('<span class='text'>"+dict.getId()+"</span>', '<span class='text'>"+dict.getLabel()+"</span>', '<span class='text'>"+dict.getValue()+"</span>', '<span class='text'>"+dict.getType()+"</span>', '<span class='text'>"+dict.getDescription()+"</span>', "+dict.getSort()+");<br>");
		}
		if(sb.length() > 0){
			return Result.successResult(sb.toString());
		}
		return Result.errorResult();
	}
}