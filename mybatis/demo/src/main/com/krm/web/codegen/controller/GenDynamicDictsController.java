package com.krm.web.codegen.controller;

import java.io.BufferedReader;
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

import com.alibaba.druid.proxy.jdbc.ClobProxyImpl;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.constant.Constant;
import com.krm.common.constant.SysConstant;
import com.krm.common.utils.DateUtils;
import com.krm.common.utils.SerializeUtils;
import com.krm.common.utils.StringConvert;
import com.krm.common.utils.StringUtil;
import com.krm.common.utils.excel.ExportExcel;
import com.krm.common.utils.excel.ImportExcel;
import com.krm.web.codegen.model.GenDynamicDicts;
import com.krm.web.codegen.service.GenDynamicDictsService;
import com.krm.web.codegen.service.ProjectsService;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author Parker
 * 项目非固定字典配置控制层
 * 2018-05-30
 */
@Controller
@RequestMapping("codegen/genDynamicDicts")
public class GenDynamicDictsController extends BaseController {
	
	public static final String BASE_URL = "codegen/genDynamicDicts";
	private static final String BASE_PATH = "codegen/genDynamicDicts/";
	
	@Resource
	private GenDynamicDictsService genDynamicDictsService;
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
		return "codegen:genDynamicDicts";
	}
	
	/**
	 * 跳转到模块页面
	 * @param model
	 * @return 模块html
	 */
	@RequestMapping
	public String toGenDynamicDicts(Model model){
		logger.info("跳转到项目非固定字典配置页面(" + getBasePath() + "genDynamicDicts-list)");
		checkPermission("query");
		return getBasePath() + "genDynamicDicts-list";
	}
	
	/**
	 * 分页显示
	 * @param params
	 * @return
	 */
	@RequestMapping(value="list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params, Model model){
		logger.info("分页显示项目非固定字典配置，参数：" + params.toString());
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
		PageInfo<CommonEntity> page = genDynamicDictsService.queryPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save", method = RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute GenDynamicDicts entry, MultipartHttpServletRequest request){
		logger.info("开始保存项目非固定字典配置");
		int count = 0;
		if(StringUtil.isEmpty(entry.getId())){
			checkPermission("add");
		}else{
			checkPermission("update");
		}
		try {
			if(entry.getSqlMode().equals("1")){
				entry.setSqlContent("SELECT t."+entry.getKeyName()+" LABLE, t."+entry.getValueName()+" VALUE "
						+ "FROM "+entry.getTableName() + " t where t.del_flag = 0");
			}else{
				entry.setKeyName(entry.getKeyName1());
				entry.setValueName(entry.getValueName1());
				entry.setUserIdFieldBind(entry.getUserIdFieldBind1());
				entry.setOrganCodeFieldBind(entry.getOrganCodeFieldBind1());
			}
			entry.setSqlContent(SerializeUtils.converObject2String(entry.getSqlContent()).toString());
			if(StringUtil.isEmpty(entry.getId())){
        		entry.setId(genDynamicDictsService.generateId());
        		count = genDynamicDictsService.save(entry);
        	}else{
        		count = genDynamicDictsService.update(entry);
        	}
			if(count > 0){
				logger.info("保存动态数据字典成功！");
				return Result.successResult();
			}
		} catch (Exception e) {
			logger.info("保存动态数据字典失败！");
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
		logger.info("开始删除项目非固定字典配置，参数：" + id);
		checkPermission("delete");
		int count = genDynamicDictsService.deleteGenDynamicDicts(id);
		if(count > 0){
			logger.info("删除项目非固定字典配置成功！");
			return Result.successResult();
		}
		logger.info("删除项目非固定字典配置失败！");
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
		logger.info("开始批量删除项目非固定字典配置，参数：" + ids);
		checkPermission("delete");
		int count = genDynamicDictsService.deleteGenDynamicDicts(ids);
		if(count > 0){
			logger.info("删除项目非固定字典配置成功！");
			return Result.successResult();
		}
		logger.info("删除项目非固定字典配置失败！");
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="{mode}/showlayer", method=RequestMethod.POST)
	public String layer(String id, @RequestParam Map<String, Object> params, @PathVariable String mode, Model model) throws Exception{
		GenDynamicDicts entry = null;
		if(StringUtils.equals("add", mode)){
			logger.info("弹窗显示【项目非固定字典配置】添加页面(" + getBasePath() + "genDynamicDicts-add)");
			checkPermission("add");
			params.put("schema", SysConstant.getValue("currentSchema"));
			List<CommonEntity> list = genDynamicDictsService.allTable(params);
			model.addAttribute("tables", list);
			return getBasePath() + "genDynamicDicts-add";
		}else if(StringUtils.equals("edit", mode)){
			logger.info("弹窗显示【项目非固定字典配置】编辑页面(" + getBasePath() + "genDynamicDicts-update)");
			checkPermission("update");
			params.put("id", id);
			entry = genDynamicDictsService.queryOne(params);
			try {
				entry.setSqlContent(SerializeUtils.converString2Object(entry.getSqlContent()).toString());
			} catch (Exception e) {
			}
			params.put("tableName", "gen_dynamic_dicts");
			params.put("schema", SysConstant.getValue("currentSchema"));
			List<CommonEntity> list = genDynamicDictsService.allTable(params);
			model.addAttribute("tables", list);
			model.addAttribute("entry", entry);
			return getBasePath() + "genDynamicDicts-update";
		}else if(StringUtils.equals("detail", mode)){
			logger.info("弹窗显示【项目非固定字典配置】详情页面(" + getBasePath() + "genDynamicDicts-detail)");
			checkPermission("query");
			params.put("id", id);
			CommonEntity entity = genDynamicDictsService.queryOneCommon(params);
			String sqlContent = "";
			if (entity.get("sqlContent") instanceof ClobProxyImpl) {
				ClobProxyImpl clob = (ClobProxyImpl) entity.get("sqlContent");
				java.io.Reader is = clob.getCharacterStream();// 得到流
				BufferedReader br = new BufferedReader(is);
				String s = br.readLine();
				StringBuffer sb = new StringBuffer();
				while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
					sb.append(s);
					s = br.readLine();
				}
				sqlContent = sb.toString();
			} else {
				sqlContent = entity.getString("sqlContent");
			}
			entity.put("sqlContent", SerializeUtils.converString2Object(sqlContent).toString());
			model.addAttribute("entry", entity);
		}else if(StringUtils.equals("import", mode)){
			logger.info("弹窗显示【项目非固定字典配置】Excel导入页面(" + getBasePath() + "genDynamicDicts-import)");
			checkPermission("import");
			return getBasePath() + "genDynamicDicts-import";
		}
		return getBasePath() + "genDynamicDicts-detail";
	}
	
	/**
     * 项目非固定字典配置Excel导入模板
     * @param response
     * @param redirectAttributes
     * @return
     * @throws Exception 
     */
    @RequestMapping("import/template/download")
    public void importGenDynamicDictsTemplate(HttpServletResponse response) throws Exception {
    	logger.info("开始下载项目非固定字典配置Excel导入模板");
    	checkPermission("import");
		String fileName = "项目非固定字典配置Excel导入模板.xlsx";
		List<GenDynamicDicts> list = Lists.newArrayList();
		list.add(new GenDynamicDicts());
		new ExportExcel("项目非固定字典配置", GenDynamicDicts.class, 2).setDataList(list).write(response, fileName).dispose();
    }
    
    /**
     * 项目非固定字典配置数据导入
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    @ResponseBody
    public Result importFile(@RequestParam("file") MultipartFile fileList[], HttpServletResponse response) throws Exception {
    	logger.info("开始导入项目非固定字典配置数据");
    	checkPermission("import");
    	Long start = System.currentTimeMillis();
    	int successNum = 0;
    	int failureNum = 0;
    	for (MultipartFile file : fileList) {
    		ImportExcel ei;
    		StringBuilder failureMsg = new StringBuilder();
   			ei = new ImportExcel(file, 1, 0);
   			List<GenDynamicDicts> list = ei.getDataList(GenDynamicDicts.class);
   			for (GenDynamicDicts entry : list) {
   				entry.setId(genDynamicDictsService.generateId());
   				entry.setCreateBy(SysUserUtils.getCacheLoginUser().getId());
   				entry.setCreateDate(new Date());
   				entry.setDelFlag(Constant.DEL_FLAG_NORMAL);
			}
   			successNum = genDynamicDictsService.insertBatch(list);
    		if (failureNum > 0){
    			failureMsg.insert(0, "，失败导入 " + failureNum + " 条项目非固定字典配置数据，导入信息如下：");
    		}
    		Long end = System.currentTimeMillis();
    		DecimalFormat df = new DecimalFormat("######0.00");
    		logger.info("导入用时"+df.format((double)(end-start)/(double)1000)+"秒");
		}
    	return new Result(1, "操作成功！，成功导入"+successNum+"条，失败导入"+failureNum+"条");
    }
    
    /**
	 * 项目非固定字典配置导出excel
	 */
	@RequestMapping(value="export", method = RequestMethod.POST)
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response) throws Exception{
		logger.info("开始导出项目非固定字典配置数据");
		checkPermission("export");
		String fileName = "项目非固定字典配置" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
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
		List<GenDynamicDicts> list = genDynamicDictsService.entityList(params);
		new ExportExcel("项目非固定字典配置", GenDynamicDicts.class).setDataList(list)
			.write(response, fileName).dispose();
	}
	
	@RequestMapping(value = "getTableField", method = RequestMethod.POST)
	@ResponseBody
	public List<CommonEntity> getTableField(@RequestParam Map<String, Object> params){
		String tableName = params.get("tableName").toString();
		logger.info("获取表{}字段信息",tableName);
		params.put("schema", SysConstant.getValue("currentSchema"));
		List<CommonEntity> list = genDynamicDictsService.selectFields(params);
		return list;
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
			GenDynamicDicts dict = genDynamicDictsService.queryById(id);
			sb.append("<span class='keyWords'>INSERT INTO</span> sys_dynamic_dict(ID, KEY_NAME, VALUE_NAME, SQL_CONTENT, TYPE, REMARKS, DATA_SCOPE, TABLE_NAME, SQL_MODE, USER_ID_FIELD_BIND, ORGAN_CODE_FIELD_BIND) <span class='keyWords'> VALUES <br></span>"
					+ "		('<span class='text'>"+dict.getId()+"</span>', '<span class='text'>"+dict.getKeyName()+"</span>', '<span class='text'>"+dict.getValueName()+"</span>', '<span class='text'>"+dict.getSqlContent()+"</span>', '<span class='text'>"+dict.getType()+"</span>', '<span class='text'>"+dict.getRemarks()+"</span>', '<span class='text'>"+dict.getDataScope()+"</span>', '<span class='text'>"+dict.getTableName()+"</span>', '<span class='text'>"+dict.getSqlMode()+"</span>', '<span class='text'>"+dict.getUserIdFieldBind()+"</span>', '<span class='text'>"+dict.getOrganCodeFieldBind()+"</span>');<br>");
		}
		if(sb.length() > 0){
			return Result.successResult(sb.toString());
		}
		return Result.errorResult();
	}
}