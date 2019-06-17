package com.krm.web.codegen.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.BadSqlGrammarException;
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
import com.google.common.collect.Maps;
import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.constant.Constant;
import com.krm.common.utils.DateUtils;
import com.krm.common.utils.StringConvert;
import com.krm.common.utils.StringUtil;
import com.krm.common.utils.excel.ExportExcel;
import com.krm.common.utils.excel.ImportExcel;
import com.krm.web.codegen.model.CreateTableHistory;
import com.krm.web.codegen.service.CreateTableHistoryService;
import com.krm.web.codegen.service.ProjectsService;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author Parker
 * 建表记录控制层
 * 2018-01-03
 */
@Controller
@RequestMapping("codegen/createTableHistory")
public class CreateTableHistoryController extends BaseController {
	
	public static final String BASE_URL = "codegen/createTableHistory";
	private static final String BASE_PATH = "codegen/createTableHistory/";
	
	@Resource
	private CreateTableHistoryService createTableHistoryService;
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
		return "codegen:createTableHistory";
	}
	
	/**
	 * 跳转到模块页面
	 * @param model
	 * @return 模块html
	 */
	@RequestMapping
	public String tocreateTableHistory(Model model){
		logger.info("跳转到建表记录页面(" + getBasePath() + "createTableHistory-list)");
		checkPermission("query");
		return  getBasePath() + "createTableHistory-list";
	}
	
	/**
	 * 分页显示
	 * @param params
	 * @return
	 */
	@RequestMapping(value="list", method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params,Model model){
		logger.info("分页显示建表记录，参数：" + params.toString());
		checkPermission("query");
		params.put("userId", SysUserUtils.getCacheLoginUser().getId());
        String[] proIds = projectsService.queryMyProject(params);
        params.put("proIds", proIds);
		//权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("o", "u", getBaseUrl(), "id"));
		if (params.containsKey("sortC")){
			//如果传过来的参数是驼峰式，这里需要将驼峰转成下划线式
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}else{
			params.put("sortC", "UPDATE_DATE");
			params.put("order", "asc");
		}
		params.put("delFlag", Constant.DEL_FLAG_NORMAL);
		PageInfo<CommonEntity> page = createTableHistoryService.queryPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute CreateTableHistory entry, MultipartHttpServletRequest request){
		logger.info("开始保存建表记录，参数：" + entry.toString());
		int count = 0;
		if(StringUtil.isEmpty(entry.getId())){
			checkPermission("add");
			List<CreateTableHistory> list = entry.getList();
			int index = 1;
			for (CreateTableHistory entity : list) {
				entity.setId(createTableHistoryService.generateId());
				entity.setProId(entry.getProId());
				entity.setTableName(entry.getTableName());
				entity.setTableComments(entry.getTableComments());
				entity.setSort(index);
				entity.setCreateBy(SysUserUtils.getSessionLoginUser().getId());
				entity.setCreateDate(new Date());
				entity.setDelFlag("0");
				index++;
			}
       		count = createTableHistoryService.insertBatch(list);
		}else{
			checkPermission("update");
			Map<String, Object> params = Maps.newHashMap();
			params.put("tableName", entry.getTableName());
			createTableHistoryService.deleteByParams(params);
			List<CreateTableHistory> list = entry.getList();
			int index = 1;
			for (CreateTableHistory entity : list) {
				entity.setId(createTableHistoryService.generateId());
				entity.setCreateBy(entry.getCreateBy());
				entity.setProId(entry.getProId());
				entity.setTableName(entry.getTableName());
				entity.setTableComments(entry.getTableComments());
				entity.setSort(index);
				entity.setUpdateBy(SysUserUtils.getSessionLoginUser().getId());
				entity.setUpdateDate(new Date());
				entity.setDelFlag("0");
				index++;
			}
       		count = createTableHistoryService.insertBatch(list);
		}
		if(count > 0){
			logger.info("保存建表记录成功！");
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
		logger.info("开始删除建表记录，参数：" + id);
		checkPermission("delete");
		int count = createTableHistoryService.deleteCreateTableHistory(id);
		if(count > 0){
			logger.info("删除建表记录成功！");
			return Result.successResult();
		}
		logger.info("删除建表记录失败！");
		return Result.errorResult();
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes", method=RequestMethod.POST)
	@ResponseBody
	public Result dels(@RequestParam(value = "tableNames[]") String[] tableNames, @RequestParam Map<String, Object> params){
		logger.info("开始批量删除建表记录，参数：" + tableNames);
		checkPermission("delete");
		int count = createTableHistoryService.deleteCreateTableHistory(tableNames, params);
		if(count > 0){
			logger.info("删除建表记录成功！");
			return Result.successResult();
		}
		logger.info("删除建表记录失败！");
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer", method=RequestMethod.POST)
	public String layer(String id, @RequestParam Map<String, Object> params, @PathVariable String mode, Model model){
		CreateTableHistory entry = null;
		if(StringUtils.equals("add", mode)){
			logger.info("弹窗显示【建表记录】添加页面(" + getBasePath() + "createTableHistory-add)");
			checkPermission("add");
			return getBasePath() + "createTableHistory-add";
		}else if(StringUtils.equals("edit", mode)){
			logger.info("弹窗显示【建表记录】编辑页面(" + getBasePath() + "createTableHistory-edit)");
			checkPermission("update");
			params.put("sortC", "sort");
			params.put("order", "asc");
			List<CreateTableHistory> list = createTableHistoryService.entityList(params);
			if(list != null && list.size() != 0){
				entry = list.get(0);
			}
			model.addAttribute("entry", entry);
			model.addAttribute("list", list);
			return getBasePath() + "createTableHistory-update";
		}
		return getBasePath() + "createTableHistory-detail";
	}
	
	/**
     * 建表记录Excel导入模板
     * @param response
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("import/template/download")
    public void importCreateTableHistoryTemplate(HttpServletResponse response) {
    	logger.info("开始下载建表记录Excel导入模板");
    	checkPermission("import");
		try {
			String fileName = "建表记录Excel导入模板.xlsx";
			List<CreateTableHistory> list = Lists.newArrayList();
			list.add(new CreateTableHistory());
			new ExportExcel("建表记录", CreateTableHistory.class, 2).setDataList(list).write(response, fileName).dispose();
		} catch (Exception e) {
			logger.info("导入模板下载失败！失败信息：" + e.getMessage());
		}
    }
    
    /**
     * 建表记录数据导入
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "import", method=RequestMethod.POST)
    @ResponseBody
    public Result importFile(MultipartHttpServletRequest request, HttpServletResponse response) {
    	logger.info("开始导入建表记录数据");
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
    			List<CreateTableHistory> list = ei.getDataList(CreateTableHistory.class);
    			for (CreateTableHistory entry : list) {
    				if(createTableHistoryService.saveOrUpdate(entry) > 0){
    					successNum++;
    				}else{
    					failureNum++;
    				}
				}
    		} catch (Exception e) {
    			logger.info(e.toString());
    		}
    		if (failureNum > 0){
    			failureMsg.insert(0, "，失败导入 " + failureNum + " 条建表记录数据，导入信息如下：");
    		}
    		Long end = System.currentTimeMillis();
    		DecimalFormat df = new DecimalFormat("######0.00");
    		logger.info("导入用时"+df.format((double)(end-start)/(double)1000)+"秒");
		}
    	return new Result(1, "操作成功！，成功导入"+successNum+"条，失败导入"+failureNum+"条");
    }
    
    /**
	 * 建表记录导出excel
	 */
	@RequestMapping(value="export", method = RequestMethod.POST)
	public void export(@RequestParam Map<String, Object> params, HttpServletResponse response){
		logger.info("开始导出建表记录数据");
		checkPermission("export");
		String fileName = "建表记录" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
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
		List<CreateTableHistory> list = createTableHistoryService.entityList(params);
		try {
			new ExportExcel("建表记录", CreateTableHistory.class).setDataList(list)
				.write(response, fileName).dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="executeCreate", method = RequestMethod.POST)
	@ResponseBody
	public Result executeCreate(@RequestParam Map<String, Object> params, HttpServletResponse response){
		try {
			StringBuilder sb = new StringBuilder();
			StringBuilder comments = new StringBuilder();
			String dbType = params.get("dbType").toString().toUpperCase();
			String tableName = params.get("tableName").toString().toUpperCase();
			String tableComment = params.get("tableComment").toString();
			int isDelete = Integer.parseInt(params.get("isDelete").toString());
			Map<String,Object> defaultValue = Maps.newHashMap();
			defaultValue.put("CREATE_BY", "创建人");
			defaultValue.put("CREATE_DATE", "创建时间");
			defaultValue.put("UPDATE_BY", "最近修改人");
			defaultValue.put("UPDATE_DATE", "最近修改时间");
			defaultValue.put("DEL_FLAG", "逻辑删除标记(0.正常，1.删除)");
			params.put("sortC", "sort");
			params.put("order", "asc");
			List<CreateTableHistory> list = createTableHistoryService.entityList(params);
			if(dbType.toLowerCase().equals("oracle")){
				sb = new StringBuilder();
				if(isDelete == 1){
					sb.append("DROP TABLE "+tableName.toLowerCase()+";");
					params.put("sql", sb.toString());
					createTableHistoryService.executeCreate(params);
				}
				sb.delete(0, sb.length());
				sb.append("CREATE TABLE "+ tableName + "(");
				String primaryKey = "ID";
				for (CreateTableHistory entity : list) {
					//主键字段
					if(StringUtil.isNotEmpty(entity.getIsPrimary()) && entity.getIsPrimary().equals("1")){
						primaryKey = entity.getFieldName().toUpperCase();
					}
					sb.append(entity.getFieldName().toUpperCase());
					//字段类型
					if(entity.getFieldType().equals("1")){
						sb.append(" VARCHAR2(").append(entity.getFieldLength() == null ? 25: entity.getFieldLength()).append(") ");
					}else if(entity.getFieldType().equals("2")){
						sb.append(" NUMBER(").append(entity.getFieldLength() == null ? 15: entity.getFieldLength()).append(") ");
					}else if(entity.getFieldType().equals("3")){
						sb.append(" NUMBER(15,").append(entity.getFieldDecimal() == null ? 5: entity.getFieldDecimal()).append(") ");
					}else if(entity.getFieldType().equals("4")){
						sb.append(" DATE ");
					}else if(entity.getFieldType().equals("5")){
						sb.append(" CLOB ");
					}
					//默认值
					if(StringUtil.isNotEmpty(entity.getFieldDefaultValue())){
						if(entity.getFieldType().equals("1") || entity.getFieldType().equals("5")){
							sb.append(" DEFAULT '"+entity.getFieldDefaultValue()+"' ");
						}else{
							sb.append(" DEFAULT "+entity.getFieldDefaultValue()+" ");
						}
					}
					//是否为空
					if(StringUtil.isNotEmpty(entity.getFieldIsNull()) && entity.getFieldIsNull().equals("1")){
						sb.append(" NULL, ");
					}else{
						sb.append(" NOT NULL, ");
					}
				}
				sb.append(" CREATE_BY VARCHAR2(32) NULL, CREATE_DATE DATE NULL, UPDATE_BY VARCHAR2(32) NULL, UPDATE_DATE DATE NULL, DEL_FLAG CHAR(1) DEFAULT '0' NULL,");
				sb.append(" CONSTRAINT PK_"+tableName+" PRIMARY KEY("+primaryKey+") )");
				params.put("sql", sb.toString());
				createTableHistoryService.executeCreate(params);
				comments.append("<h3>以下语句系统不能执行，请手动执行:</h3><br>");
				//添加注释
				params.put("sql", "COMMENT ON TABLE "+tableName+" IS '"+tableComment+"'");
				params.put("sql", "alter table "+tableName+" comment '广告表';");
				createTableHistoryService.executeCreate(params);
				comments.append("COMMENT ON TABLE "+tableName+" IS '"+tableComment+"';<br>");
				for (CreateTableHistory entity : list) {
					if(StringUtil.isNotEmpty(entity.getFieldAlias())){
						params.put("sql", "COMMENT ON COLUMN "+tableName+"."+entity.getFieldName()+" IS '"+entity.getFieldAlias()+"'");
						comments.append("COMMENT ON COLUMN "+tableName+"."+entity.getFieldName()+" IS '"+entity.getFieldAlias()+"';<br>");
					}
				}
				for (String key : defaultValue.keySet()) {
					params.put("sql", "COMMENT ON COLUMN "+tableName+"."+key+" IS '"+defaultValue.get(key)+"'");
					comments.append("COMMENT ON COLUMN "+tableName+"."+key+" IS '"+defaultValue.get(key)+"';<br>");
				}
			}else if(dbType.toLowerCase().equals("mysql")){
				String primaryKey = "ID";
				sb = new StringBuilder();
				if(isDelete == 1){
					sb.append("DROP TABLE IF EXISTS `"+tableName.toLowerCase()+"`;");
					params.put("sql", sb.toString());
					createTableHistoryService.executeCreate(params);
				}
				sb.delete(0, sb.length());
				sb.append("CREATE TABLE `"+tableName.toLowerCase()+"` (");
				for (CreateTableHistory entity : list) {
					//主键字段
					if(StringUtil.isNotEmpty(entity.getIsPrimary()) && entity.getIsPrimary().equals("1")){
						primaryKey = entity.getFieldName().toUpperCase();
					}
					sb.append("	`"+entity.getFieldName().toUpperCase()+"`");
					//字段类型
					if(entity.getFieldType().equals("1")){
						sb.append(" varchar(").append(entity.getFieldLength() == null ? 25: entity.getFieldLength()).append(")	");
					}else if(entity.getFieldType().equals("2")){
						sb.append(" int(").append(entity.getFieldLength() == null ? 15: entity.getFieldLength()).append(")	");
					}else if(entity.getFieldType().equals("3")){
						sb.append(" double(15,").append(entity.getFieldDecimal() == null ? 5: entity.getFieldDecimal()).append(")	");
					}else if(entity.getFieldType().equals("4")){
						sb.append(" datetime	");
					}else if(entity.getFieldType().equals("5")){
						sb.append(" text	");
					}
					//默认值
					if(StringUtil.isNotEmpty(entity.getFieldDefaultValue())){
						if(entity.getFieldType().equals("1") || entity.getFieldType().equals("5")){
							sb.append(" DEFAULT '"+entity.getFieldDefaultValue()+"' ");
						}else{
							sb.append(" DEFAULT "+entity.getFieldDefaultValue()+" ");
						}
					}
					//是否为空
					if(StringUtil.isNotEmpty(entity.getFieldIsNull()) && entity.getFieldIsNull().equals("1")){
						sb.append(" NULL	");
					}else{
						sb.append(" NOT NULL	");
					}
					sb.append(" COMMENT	'").append(entity.getFieldAlias() == null ? "" : entity.getFieldAlias() + "',");
				}
				sb.append("	`CREATE_BY`	varchar(32)	NULL	COMMENT	'创建人',	"
						+ "`CREATE_DATE`	datetime  	NULL	COMMENT	'创建时间',"
						+ "	`UPDATE_BY`	varchar(32)	NULL	COMMENT	'最近修改人',"
						+ "	`UPDATE_DATE`	datetime	NULL	COMMENT	'最近修改时间',	"
						+ "`DEL_FLAG`	varchar(1)	DEFAULT 0	NULL	COMMENT	'逻辑删除标记(0.正常，1.删除)',");
				sb.append("	PRIMARY KEY (`"+primaryKey+"`)");
				sb.append(")	COMMENT = '"+tableComment+"';");
				params.put("sql", sb.toString());
				createTableHistoryService.executeCreate(params);
			}
			return Result.successResult(comments.toString());
		} catch (BadSqlGrammarException e) {
			if(e.toString().contains("already exists") || e.toString().contains("ORA-00955")){
				return new Result(-1, "表已经存在，不能重复创建！");
			}
		}
		return Result.errorResult();
	}
	
	
	@RequestMapping(value="generateSql", method = RequestMethod.POST)
	@ResponseBody
	public Result generateSql(@RequestParam Map<String, Object> params, HttpServletResponse response){
		StringBuilder sb = new StringBuilder();
		String dbType = params.get("dbType").toString().toUpperCase();
		String tableName = params.get("tableName").toString().toUpperCase();
		String tableComment = params.get("tableComment").toString();
		Map<String,Object> defaultValue = Maps.newHashMap();
		defaultValue.put("CREATE_BY", "创建人");
		defaultValue.put("CREATE_DATE", "创建时间");
		defaultValue.put("UPDATE_BY", "最近修改人");
		defaultValue.put("UPDATE_DATE", "最近修改时间");
		defaultValue.put("DEL_FLAG", "逻辑删除标记(0.正常，1.删除)");
		params.put("sortC", "sort");
		params.put("order", "asc");
		List<CreateTableHistory> list = createTableHistoryService.entityList(params);
		sb.append("<style>.contents {font-family: \"Times New Roman\",Georgia,Serif;}.keyWords {color: blue;font-weight: bold;}"
				+ ".text {color: #FF0080;} .type {color: #ff4006;} .comments {color: #777;font-weight: bold;font-style: italic;}</style>");
		sb.append("<pre class='contents'>");
		if(dbType.toLowerCase().equals("oracle")){
			String primaryKey = "ID";
			sb.append("<span class='comments'>--删除表</span><br>");
			sb.append("<span class='keyWords'>DROP  TABLE  </span>"+ tableName + ";<br><br>");
			sb.append("<span class='comments'>--创建表</span><br>");
			sb.append("<span class='keyWords'>CREATE  TABLE  </span>"+ tableName + "   (<br>");
			for (CreateTableHistory entity : list) {
				//主键字段
				if(StringUtil.isNotEmpty(entity.getIsPrimary()) && entity.getIsPrimary().equals("1")){
					primaryKey = entity.getFieldName().toUpperCase();
				}
				sb.append("	"+entity.getFieldName().toUpperCase());
				//字段类型
				if(entity.getFieldType().equals("1")){
					sb.append("	<span class='type'>VARCHAR2(").append(entity.getFieldLength() == null ? 25: entity.getFieldLength()).append(")	</span>");
				}else if(entity.getFieldType().equals("2")){
					sb.append("	<span class='type'>NUMBER(").append(entity.getFieldLength() == null ? 15: entity.getFieldLength()).append(")	</span>");
				}else if(entity.getFieldType().equals("3")){
					sb.append("	<span class='type'>NUMBER(15,").append(entity.getFieldDecimal() == null ? 5: entity.getFieldDecimal()).append(")	</span>");
				}else if(entity.getFieldType().equals("4")){
					sb.append("	<span class='type'>DATE	</span>");
				}else if(entity.getFieldType().equals("5")){
					sb.append("	<span class='type'>CLOB	</span>");
				}
				//默认值
				if(StringUtil.isNotEmpty(entity.getFieldDefaultValue())){
					if(entity.getFieldType().equals("1") || entity.getFieldType().equals("5")){
						sb.append("<span class='keyWords'>DEFAULT	'</span>"+entity.getFieldDefaultValue()+"' ");
					}else{
						sb.append("<span class='keyWords'>DEFAULT	</span>"+entity.getFieldDefaultValue()+" ");
					}
				}
				//是否为空
				if(StringUtil.isNotEmpty(entity.getFieldIsNull()) && entity.getFieldIsNull().equals("1")){
					sb.append("<span class='keyWords'>NULL,<br></span>");
				}else{
					sb.append("<span class='keyWords'>NOT NULL,<br></span>");
				}
			}
			sb.append("	CREATE_BY	<span class='type'>VARCHAR2(32)</span><span class='keyWords'>	NULL</span>,<br>	CREATE_DATE	<span class='type'>DATE</span>  <span class='keyWords'>	NULL</span>,<br>"
					+ "	UPDATE_BY	<span class='type'>VARCHAR2(32)</span><span class='keyWords'>	NULL</span>,<br>"
					+ "	UPDATE_DATE	<span class='type'>DATE</span><span class='keyWords'>	NULL</span>,<br>	DEL_FLAG	<span class='type'>CHAR(1)</span>	DEFAULT '0'<span class='keyWords'>	NULL</span>,<br>");
			sb.append("	<span class='keyWords'>CONSTRAINT</span>	PK_"+tableName+"	<span class='keyWords'>PRIMARY	KEY</span>("+primaryKey+") <br>");
			sb.append(");<br>");
			//添加注释
			sb.append("<span class='keyWords'>COMMENT	ON	TABLE	</span>"+tableName+"<span class='keyWords'>	IS	</span>'<span class='text'>"+tableComment+"</span>';<br>");
			for (CreateTableHistory entity : list) {
				if(StringUtil.isNotEmpty(entity.getFieldAlias())){
					sb.append("<span class='keyWords'>COMMENT	ON	COLUMN	</span>"+tableName+"."+entity.getFieldName()+"<span class='keyWords'>	IS	</span>'<span class='text'>"+entity.getFieldAlias()+"</span>';<br>");
				}
			}
			for (String key : defaultValue.keySet()) {
				sb.append("<span class='keyWords'>COMMENT	ON	COLUMN	</span>"+tableName+"."+key+"<span class='keyWords'>	IS	</span>'<span class='text'>"+defaultValue.get(key)+"</span>';<br>");
			}
			sb.append("</pre>");
		}else if(dbType.toLowerCase().equals("mysql")){
			String primaryKey = "ID";
			sb.append("<span class='comments'>-- ----------------------------<br>"
					+ "-- Table structure for "+tableName.toLowerCase()+"<br>"
					+ "-- ----------------------------<br></span>"
					+ "<span class='keyWords'>DROP TABLE IF EXISTS </span>`"+tableName.toLowerCase()+"`;<br><br>");
			sb.append("<span class='keyWords'>CREATE TABLE </span>`"+tableName.toLowerCase()+"` (<br>");
			for (CreateTableHistory entity : list) {
				//主键字段
				if(StringUtil.isNotEmpty(entity.getIsPrimary()) && entity.getIsPrimary().equals("1")){
					primaryKey = entity.getFieldName().toUpperCase();
				}
				sb.append("	`"+entity.getFieldName().toUpperCase()+"`");
				//字段类型
				if(entity.getFieldType().equals("1")){
					sb.append("	<span class='type'>varchar(").append(entity.getFieldLength() == null ? 25: entity.getFieldLength()).append(")	</span>");
				}else if(entity.getFieldType().equals("2")){
					sb.append("	<span class='type'>int(").append(entity.getFieldLength() == null ? 15: entity.getFieldLength()).append(")	</span>");
				}else if(entity.getFieldType().equals("3")){
					sb.append("	<span class='type'>double(15,").append(entity.getFieldDecimal() == null ? 5: entity.getFieldDecimal()).append(")	</span>");
				}else if(entity.getFieldType().equals("4")){
					sb.append("	<span class='type'>datetime	</span>");
				}else if(entity.getFieldType().equals("5")){
					sb.append("	<span class='type'>text	</span>");
				}
				//默认值
				if(StringUtil.isNotEmpty(entity.getFieldDefaultValue())){
					if(entity.getFieldType().equals("1") || entity.getFieldType().equals("5")){
						sb.append("<span class='keyWords'>DEFAULT	'</span>"+entity.getFieldDefaultValue()+"' ");
					}else{
						sb.append("<span class='keyWords'>DEFAULT	</span>"+entity.getFieldDefaultValue()+" ");
					}
				}
				//是否为空
				if(StringUtil.isNotEmpty(entity.getFieldIsNull()) && entity.getFieldIsNull().equals("1")){
					sb.append("<span class='keyWords'>NULL	</span>");
				}else{
					sb.append("<span class='keyWords'>NOT NULL	</span>");
				}
				sb.append("COMMENT	'<span class='text'>").append(entity.getFieldAlias() == null ? "" : entity.getFieldAlias() + "</span>',<br>");
			}
			sb.append("	`CREATE_BY`	<span class='type'>varchar(32)</span><span class='keyWords'>	NULL	</span>COMMENT	'<span class='text'>创建人</span>',<br>	"
					+ "`CREATE_DATE`	<span class='type'>datetime</span>  <span class='keyWords'>	NULL	</span>COMMENT	'<span class='text'>创建时间</span>',<br>"
					+ "	`UPDATE_BY`	<span class='type'>varchar(32)</span><span class='keyWords'>	NULL	</span>COMMENT	'<span class='text'>最近修改人</span>',<br>"
					+ "	`UPDATE_DATE`	<span class='type'>datetime</span><span class='keyWords'>	NULL	</span>COMMENT	'<span class='text'>最近修改时间</span>',<br>	"
					+ "`DEL_FLAG`	<span class='type'>varchar(1)</span>	DEFAULT 0<span class='keyWords'>	NULL	</span>COMMENT	'<span class='text'>逻辑删除标记(0.正常，1.删除)</span>',<br>");
			sb.append("	<span class='keyWords'>PRIMARY KEY </span>(`"+primaryKey+"`)<br>");
			sb.append(")	<span class='keyWords'>COMMENT</span> = '<span class='text'>"+tableComment+"</span>';");
		}
		return Result.successResult(sb.toString());
	}
}
