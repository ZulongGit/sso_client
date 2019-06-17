package com.krm.web.codegen.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.proxy.jdbc.ClobProxyImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.beetl.function.DictFunction;
import com.krm.common.constant.Constant;
import com.krm.common.spring.utils.SpringContextHolder;
import com.krm.common.utils.FileUtils;
import com.krm.common.utils.SerializeUtils;
import com.krm.common.utils.StringUtil;
import com.krm.web.codegen.model.CodeGenModel;
import com.krm.web.codegen.model.CommonParams;
import com.krm.web.codegen.model.ProParams;
import com.krm.web.codegen.model.ProTemplates;
import com.krm.web.codegen.model.TableConfig;
import com.krm.web.codegen.model.TableFieldConfig;
import com.krm.web.codegen.parse.ParseFactory;
import com.krm.web.codegen.service.DbManagService;
import com.krm.web.codegen.service.ProParamsService;
import com.krm.web.codegen.service.ProTemplatesService;
import com.krm.web.codegen.service.ProjectsService;
import com.krm.web.codegen.service.TableConfigService;
import com.krm.web.codegen.service.TableFieldConfigService;
import com.krm.web.codegen.util.ConnectionUtil;
import com.krm.web.codegen.util.DateUtils;
import com.krm.web.codegen.util.read.IReadTable;
import com.krm.web.codegen.util.read.ReadTableFactory;
import com.krm.web.util.SysUserUtils;

@Controller
@RequestMapping("create")
public class MainController extends BaseController{
	
	@Override
	protected String getBaseUrl() {
		return null;
	}

	@Override
	protected String getBasePath() {
		return null;
	}
	
	static DictFunction dictFunction = SpringContextHolder.getBean("dictFunction");
	@Resource
	private TableConfigService tableConfigService;
	@Resource
	private TableFieldConfigService tableFieldConfigService;
	@Resource
	private BeetlGroupUtilConfiguration beetlConfig;
	@Resource
	private DbManagService dbManagService;
	@Resource
	private ProParamsService proParamsService;
	@Resource
	private ProTemplatesService proTemplatesService;
	@Resource
	private ProjectsService projectsService;
	
	/**
	 * 进入表单配置首页
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String index(Model model){
		logger.info("进入表单配置首页");
		return "codegen/index";
	}
	
	/**
	 * 获取所有数据库名
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "getSchema", method = RequestMethod.POST)
	@ResponseBody
	public List<CommonEntity> getSchema(@RequestParam Map<String, Object> params){
		logger.info("开始获取所有数据库名");
		try {
			if(StringUtil.isEmpty(params.get("dbId"))){
				return Lists.newArrayList();
			}
			params.put("id", params.get("dbId"));
			CommonEntity entity = dbManagService.queryOneCommon(params);
			String dbAddress = ConnectionUtil.getRealUrl(entity.getString("dbType"), entity.getString("dbAddress"), entity.getString("dbPort"), entity.getString("tbSchema"));
            ConnectionUtil.init(entity.getString("driver"), dbAddress, entity.getString("userName"),
                    entity.getString("password"));
			IReadTable readTable = ReadTableFactory.getReadTable(entity.getString("dbType"));
			return readTable.getAllSchema();
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				ConnectionUtil.close();
			} catch (Exception e2) {
				throw e2;
			}
		}
	}
	
	/**
	 * 获取数据库所有表名
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "getDbTable", method = RequestMethod.POST)
	@ResponseBody
	public List<CommonEntity> getDbTable(@RequestParam Map<String, Object> params){
		logger.info("开始获取数据库表名");
		try {
			params.put("id", params.get("dbId"));
			CommonEntity entity = dbManagService.queryOneCommon(params);
			String dbAddress = ConnectionUtil.getRealUrl(entity.getString("dbType"), entity.getString("dbAddress"), entity.getString("dbPort"), entity.getString("tbSchema"));
            ConnectionUtil.init(entity.getString("driver"), dbAddress, entity.getString("userName"),
                    entity.getString("password"));
            IReadTable readTable = ReadTableFactory.getReadTable(entity.getString("dbType"));
            return readTable.getAllTable(params.get("schemaName").toString());
        } catch (Exception e) {
        	throw e;
		} finally {
			try {
				ConnectionUtil.close();
			} catch (Exception e2) {
				throw e2;
			}
        }
	}
	
	
	/**
	 * 校验代码生成配置是否存在
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "checkConfigs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkConfigs(@RequestParam Map<String, Object> params){
		logger.info("开始校验代码生成配置是否存在");
		params.put("createBy", SysUserUtils.getCacheLoginUser().getId());
		/* 管理员无法展示多条配置，故删除 */
//		if(!SysUserUtils.getCacheLoginUser().isAdmin()){
//		}
		return tableFieldConfigService.checkConfigs(params);
	}
	
	/**
	 * 初始字段信息
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "initField", method = RequestMethod.POST)
	@ResponseBody
	public List<TableFieldConfig> initField(@RequestParam Map<String, Object> params){
		String tableName = params.get("tableName").toString();
		logger.info("获取表{}初始字段信息",tableName);
		try {
			params.put("id", params.get("dbId"));
			CommonEntity entity = dbManagService.queryOneCommon(params);
			String dbAddress = ConnectionUtil.getRealUrl(entity.getString("dbType"), entity.getString("dbAddress"), entity.getString("dbPort"), entity.getString("tbSchema"));
            ConnectionUtil.init(entity.getString("driver"), dbAddress, entity.getString("userName"),
                    entity.getString("password"));
            IReadTable readTable = ReadTableFactory.getReadTable(entity.getString("dbType"));
            List<TableFieldConfig> list = readTable.initField(params.get("schemaName").toString(), tableName);
            return list;
        } catch (Exception e) {
        	throw e;
		} finally {
			try {
				ConnectionUtil.close();
			} catch (Exception e2) {
				throw e2;
			}
        }
	}
	
	
	/**
	 * 获取表字段配置信息
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "getTableFieldConfig", method = RequestMethod.POST)
	@ResponseBody
	public List<CommonEntity> getTableFieldConfig(@RequestParam Map<String, Object> params){
		logger.info("开始获取表字段信息");
		params.put("createBy", SysUserUtils.getCacheLoginUser().getId());
		/* 管理员无法展示多条配置，故删除 */
//		if(!SysUserUtils.getCacheLoginUser().isAdmin()){
//		}
		List<CommonEntity> list = tableFieldConfigService.selectConfigs(params);
		return list;
	}
	
	/**
	 * 获取表配置
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "getTableConfig", method = RequestMethod.POST)
	@ResponseBody
	public TableConfig getTableConfig(@RequestParam Map<String, Object> params){
		logger.info("开始获取表配置信息");
		params.put("createBy", SysUserUtils.getCacheLoginUser().getId());
		/* 管理员无法展示多条配置，故删除 */
//		if(!SysUserUtils.getCacheLoginUser().isAdmin()){
//		}
		return tableConfigService.queryOne(params);
	}
	
	
	/**
	 * 生成入口
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("generateFile")
	@ResponseBody
	public Result generateFile(@RequestParam Map<String, Object> params, @ModelAttribute ProParams proParams, 
			@ModelAttribute TableConfig tableConfig, String[] templates) throws Exception{
		logger.info("----------------------------------开始生成文件-----------------------------------");
		String tableId = params.get("tableName").toString();
		Map<String, Object> newParams = Maps.newHashMap();
		newParams.put("id", tableId);
		TableConfig tableConfigs = tableConfigService.queryOne(newParams);	//表配置
		newParams.clear();
		newParams.put("proId", params.get("proId"));
		newParams.put("dbId", params.get("dbId"));
		newParams.put("schemaName", params.get("schemaName"));
		newParams.put("tableName", tableConfigs.getTableName());
		newParams.put("createBy", SysUserUtils.getCacheLoginUser().getId());
		/* 管理员无法展示多条配置，故删除 */
//		if(!SysUserUtils.getCacheLoginUser().isAdmin()){
//		}
		List<CommonEntity> fieldConfigs = tableFieldConfigService.selectConfigs(newParams);	//字段配置
		//非固定字典sql密文转换
		for (CommonEntity entity : fieldConfigs) {
			if (entity.containsKey("sqlContent")) {
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
				String sql = SerializeUtils.converString2Object(sqlContent).toString();
				String[] group = sql.split("where|WHERE");
				if (group.length > 0) {
					entity.put("sqlContent", group[0]);
				}
			}
		}
		//封装所有参数
		String moduleName = params.get("moduleName").toString().toLowerCase().trim();
		moduleName = moduleName.equals("")?"modules":moduleName;
		tableConfigs.setModuleName(moduleName);
		CommonParams commonParams = new CommonParams();
		commonParams.setPackageName(proParams.getCodePath() + "." + moduleName);
		commonParams.setEntityName(tableConfigs.getClassName());
		commonParams.setEntityNameU(StringUtil.firstCharToUpper(tableConfigs.getClassName()));
		commonParams.setDate(DateUtils.getDate());
		commonParams.setAuthor(proParams.getAuthor());
		CodeGenModel model = new CodeGenModel();
		model.setParams(commonParams);
		model.setTableConfig(tableConfigs);
		model.setFieldConfigs(fieldConfigs);
		//获取模板，为空查询所有模板
		List<ProTemplates> templateList = null;
		if(templates.length == 0){
			newParams.clear();
			newParams.put("proId", params.get("proId"));
			newParams.put(Constant.FIELD_DEL_FLAG, Constant.DEL_FLAG_NORMAL);
			templateList = proTemplatesService.entityList(newParams);
		}else{
			templateList = proTemplatesService.getTemplateByIds(templates);
		}
		List<String> templateFileList = proTemplatesService.loadTemplateFile(templateList);
		model.setFiles(templateFileList);
		List<String> fileList = new ArrayList<String>();
		//渲染模板
		fileList.addAll(ParseFactory.getParse("beetle").parse(model));
		if(fieldConfigs.size() > 0){
			//生成后的代码写到本地文件中
			writeThisFileList(model, proParams, StringUtil.isEmpty(proParams.getCoding()) ? "UTF-8" :proParams.getCoding(), fileList, templateList);
			logger.info("----------------------------------生成代码成功-----------------------------------");
			logger.info("----------------------------------结束生成文件-----------------------------------");
			return new Result(1, "生成成功，重启服务生效！");
		}else{
			return new Result(0, "请先保存配置！");
		}
	}
	
	/**
	 * 生成入口
	 * @param params
	 * @param model
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("generateFile/{mode}")
	public void downloadFile(@RequestParam Map<String, Object> params, @PathVariable String mode, @ModelAttribute ProParams proParams, 
			@ModelAttribute TableConfig tableConfig, String[] templates, HttpServletResponse res) throws Exception{
		logger.info("----------------------------------开始生成文件-----------------------------------");
		String tableId = params.get("tableName").toString();
		Map<String, Object> newParams = Maps.newHashMap();
		newParams.put("id", tableId);
		TableConfig tableConfigs = tableConfigService.queryOne(newParams);	//表配置
		newParams.clear();
		newParams.put("proId", params.get("proId"));
		newParams.put("dbId", params.get("dbId"));
		newParams.put("schemaName", params.get("schemaName"));
		newParams.put("tableName", tableConfigs.getTableName());
		newParams.put("createBy", SysUserUtils.getCacheLoginUser().getId());
		/* 管理员无法展示多条配置，故删除 */
//		if(!SysUserUtils.getCacheLoginUser().isAdmin()){
//		}
		List<CommonEntity> fieldConfigs = tableFieldConfigService.selectConfigs(newParams);	//字段配置
		//非固定字典sql密文转换
		for (CommonEntity entity : fieldConfigs) {
			if(entity.containsKey("sqlContent")){
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
				String sql = SerializeUtils.converString2Object(sqlContent).toString();
				String[] group = sql.split("where|WHERE");
				if(group.length > 0){
					entity.put("sqlContent", group[0]);
				}
			}
		}
		//封装所有参数
		String moduleName = params.get("moduleName").toString().toLowerCase().trim();
		moduleName = moduleName.equals("")?"modules":moduleName;
		tableConfigs.setModuleName(moduleName);
		CommonParams commonParams = new CommonParams();
		commonParams.setPackageName(proParams.getCodePath() + "." + moduleName);
		commonParams.setEntityName(tableConfigs.getClassName());
		commonParams.setEntityNameU(StringUtil.firstCharToUpper(tableConfigs.getClassName()));
		commonParams.setDate(DateUtils.getDate());
		commonParams.setAuthor(proParams.getAuthor());
		CodeGenModel model = new CodeGenModel();
		model.setParams(commonParams);
		model.setTableConfig(tableConfigs);
		model.setFieldConfigs(fieldConfigs);
		//获取模板，为空查询所有模板
		List<ProTemplates> templateList = null;
		if(templates.length == 0){
			newParams.clear();
			newParams.put("proId", params.get("proId"));
			newParams.put(Constant.FIELD_DEL_FLAG, Constant.DEL_FLAG_NORMAL);
			templateList = proTemplatesService.entityList(newParams);
		}else{
			templateList = proTemplatesService.getTemplateByIds(templates);
		}
		List<String> templateFileList = proTemplatesService.loadTemplateFile(templateList);
		model.setFiles(templateFileList);
		List<String> fileList = new ArrayList<String>();
		//渲染模板
		fileList.addAll(ParseFactory.getParse("beetle").parse(model));
		if(fieldConfigs.size() > 0){
			//生成后的代码写到本地文件中
			downThisFileList(res, model, proParams, StringUtil.isEmpty(proParams.getCoding()) ? "UTF-8" :proParams.getCoding(), fileList, templateList);
			logger.info("----------------------------------生成代码成功-----------------------------------");
			logger.info("----------------------------------结束生成文件-----------------------------------");
		}else{
		}
	}
	
	
	/**
	 * 生成入口
	 * @param params
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("generateAll")
	@ResponseBody
	public Result generateAll(@RequestParam Map<String, Object> params, Model model) throws Exception{
		logger.info("----------------------------------开始生成所有表-----------------------------------");
		
		logger.info("----------------------------------结束生成所有表-----------------------------------");
		return Result.successResult();
	}
	
	@RequestMapping(value = "getProParam", method = RequestMethod.POST)
	@ResponseBody
	public Result getProParam(@RequestParam Map<String, Object> params) throws Exception{
		try {
			ProParams entry = proParamsService.queryOne(params);
			if(StringUtil.isNotEmpty(entry)){
				return Result.successResult(entry);
			}
		} catch (Exception e) {
			return Result.successResult(new ProParams());
		}
		return Result.errorResult();
	}
	
	
	/**
     * 本地输出代码
     */
    private void writeThisFileList(CodeGenModel model, ProParams proParams, String encoded, List<String> fileList, List<ProTemplates> templateList) {
        for (int i = 0; i < fileList.size(); i++) {
            // 项目根路径\源码路径\CODE父路径（HTML路径或XML路径或JS路径）\单表所在模块\模板配置路径
            String filePath = getFileName(model, proParams, templateList.get(i));
            File path = new File(filePath);
            if (!path.exists()) {
                FileUtils.createFile(filePath);
            }else{
            	FileUtils.deleteFile(filePath);
            	FileUtils.createFile(filePath);
            }
            try {
                File file = new File(filePath);
                FileUtils.writeStringToFile(file, fileList.get(i), encoded);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
    
    private void downThisFileList(HttpServletResponse res, CodeGenModel model, ProParams proParams, String encoded, List<String> fileList, List<ProTemplates> templateList) {
    	String proPath = proParams.getProPath().replace(".", File.separator).replace("\\", File.separator).replace("/", File.separator);
    	proParams.setProPath(proPath.substring(proPath.lastIndexOf(File.separator) + 1));
        ZipOutputStream out = null;
        try {
        	res.setContentType("application/octet-stream");
        	String realName = new String(model.getTableConfig().getRemarks().getBytes("GBK"), "ISO8859-1");
        	res.setHeader("Content-Disposition", "attachment;filename=" + realName + ".zip");
            out = new ZipOutputStream(res.getOutputStream());
            for (int i = 0; i < fileList.size(); i++) {
                out.putNextEntry(new ZipEntry(getFileName(model, proParams, templateList.get(i))));
                out.write(fileList.get(i).getBytes(), 0, fileList.get(i).getBytes().length);
                out.closeEntry();
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
        } finally {
            try {
                out.close();
                res.getOutputStream().flush();
                res.getOutputStream().close();
            } catch (IOException e) {
            	logger.error(e.getMessage(), e);
            }
        }
    }
    
    private String getFileName(CodeGenModel model, ProParams proParams, ProTemplates templates){
    	String completePath = null;
    	String proPath = proParams.getProPath().replace(".", File.separator).replace("\\", File.separator).replace("/", File.separator);
    	String sourceFolder = proParams.getSourceFolder().replace(".", File.separator).replace("\\", File.separator).replace("/", File.separator);
    	String codePath = proParams.getCodePath().replace(".", File.separator).replace("\\", File.separator).replace("/", File.separator);
    	String xmlPath = proParams.getXmlPath().replace(".", File.separator).replace("\\", File.separator).replace("/", File.separator);
    	String htmlPath = proParams.getHtmlPath().replace(".", File.separator).replace("\\", File.separator).replace("/", File.separator);
    	String jsPath = proParams.getJsPath().replace(".", File.separator).replace("\\", File.separator).replace("/", File.separator);
    	String fileName = templates.getFileName();
    	switch(templates.getTempLanguage()){
    	case "java":
    		completePath = proPath + File.separator + sourceFolder + File.separator + codePath + File.separator + 
    				model.getTableConfig().getModuleName() + File.separator + templates.getPath() + File.separator +
    				transferFileName(model.getParams().getEntityNameU(), fileName, "java");
    		break;
    	case "xml":
    		if(StringUtil.isEmpty(xmlPath)){
    			completePath = proPath + File.separator + sourceFolder + File.separator + codePath + File.separator + 
    					model.getTableConfig().getModuleName() + File.separator + templates.getPath() + File.separator +
    					transferFileName(model.getParams().getEntityNameU(), fileName, "xml");
    		}else{
    			completePath = proPath + File.separator + sourceFolder + File.separator + xmlPath + File.separator + 
    					templates.getPath() + File.separator + model.getTableConfig().getModuleName() + File.separator + 
    					transferFileName(model.getParams().getEntityNameU(), fileName, "xml");
    		}
    		break;
    	case "html":
    		completePath = proPath + File.separator + htmlPath + File.separator + model.getTableConfig().getModuleName() + 
    				File.separator + model.getParams().getEntityName() + File.separator + 
    				transferFileName(model.getParams().getEntityName(), fileName, "html");
    		break;
    	case "jsp":
    		completePath = proPath + File.separator + htmlPath + File.separator + model.getTableConfig().getModuleName() + 
    		File.separator + model.getParams().getEntityName() + File.separator + 
    		transferFileName(model.getParams().getEntityName(), fileName, "jsp");
    		break;
	    case "js":
	    	completePath = proPath + File.separator + jsPath + File.separator + templates.getPath() + 
			    	File.separator + transferFileName(model.getParams().getEntityName(), fileName, "js");
	    	break;
	    }
    	return completePath;
    }
    
    private String transferFileName(String entityName, String fileName, String type){
    	boolean containSuffix = fileName.matches(".*[\\.java|\\.xml|\\.js|\\.html|\\.jsp|]$");
    	if(containSuffix){
    		return String.format(fileName, entityName);
    	}else{
    		return String.format(fileName, entityName) + "." + type;
    	}
    }
}
