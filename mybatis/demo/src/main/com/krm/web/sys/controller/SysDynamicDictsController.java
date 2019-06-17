package com.krm.web.sys.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.constant.SysConstant;
import com.krm.common.utils.SerializeUtils;
import com.krm.common.utils.StringConvert;
import com.krm.common.utils.StringUtil;
import com.krm.web.sys.model.SysDynamicDicts;
import com.krm.web.sys.service.SysDynamicDictsService;
import com.krm.web.util.SysUserUtils;

/**
     动态数据字典控制类
   2017-10-17
*/

@Controller
@RequestMapping("sys/dynamicDicts")
public class SysDynamicDictsController extends BaseController {
	
	public static final String BASE_URL = "sys/dynamicDicts";
	private static final String BASE_PATH = "sys/dynamicDicts/";

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
		return "sys:dynamicDicts";
	}
	
	@Resource
	private SysDynamicDictsService sysDynamicDictsService;
	
	/**
	 * 跳转到模块页面
	 * @param model
	 * @return 模块html
	 */
	@RequestMapping
	public String totableDicts(Model model){
		logger.info("跳转到动态数据字典页面(" + BASE_PATH + "dynamicDicts-list)");
		checkPermission("query");
		return  BASE_PATH + "dynamicDicts-list";
	}
	
	

	
	/**
	 * 分页显示
	 * @param params
	 * @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params,Model model){
		logger.info("分页显示动态数据字典，参数：" + params.toString());
		checkPermission("query");
		//权限语句
		params.put("dynamicSQL", SysUserUtils.dataScopeFilterString1("", "",BASE_URL, "id"));
		if (params.containsKey("sortC")){
			//如果传过来的参数是驼峰式，这里需要将驼峰转成下划线式
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<CommonEntity> page = sysDynamicDictsService.queryPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	@ResponseBody
	public Result save(@ModelAttribute SysDynamicDicts sysDynamicDicts, MultipartHttpServletRequest  request){
		logger.info("开始保存动态数据字典，参数：" + sysDynamicDicts.toString());
		if(StringUtil.isEmpty(sysDynamicDicts.getId())){
			checkPermission("add");
		}else{
			checkPermission("update");
		}
		try {
			int count = 0;
			if(sysDynamicDicts.getSqlMode().equals("1")){
				sysDynamicDicts.setSqlContent("SELECT t."+sysDynamicDicts.getKeyName()+" LABLE, t."+sysDynamicDicts.getValueName()+" VALUE "
						+ "FROM "+sysDynamicDicts.getTableName() + " t where t.del_flag = 0");
			}else{
				sysDynamicDicts.setKeyName(sysDynamicDicts.getKeyName1());
				sysDynamicDicts.setValueName(sysDynamicDicts.getValueName1());
				sysDynamicDicts.setUserIdFieldBind(sysDynamicDicts.getUserIdFieldBind1());
				sysDynamicDicts.setOrganCodeFieldBind(sysDynamicDicts.getOrganCodeFieldBind1());
			}
			sysDynamicDicts.setSqlContent(SerializeUtils.converObject2String(sysDynamicDicts.getSqlContent()).toString());
			if(StringUtil.isEmpty(sysDynamicDicts.getId())){
        		sysDynamicDicts.setId(sysDynamicDictsService.generateId());
        		count = sysDynamicDictsService.save(sysDynamicDicts);
        	}else{
        		count = sysDynamicDictsService.update(sysDynamicDicts);
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
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody
	public Result del(String id){
		logger.info("开始删除动态数据字典，参数：" + id);
		checkPermission("delete");
		int count = sysDynamicDictsService.deleteTableDicts(id);
		if(count > 0){
			logger.info("删除动态数据字典成功！");
			return Result.successResult();
		}
		logger.info("删除动态数据字典失败！");
		return Result.errorResult();
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	@ResponseBody
	public Result dels(@RequestParam(value = "ids[]") String[] ids){
		logger.info("开始批量删除动态数据字典，参数：" + ids);
		checkPermission("delete");
		int count = sysDynamicDictsService.deleteTableDicts(ids);
		if(count > 0){
			logger.info("删除动态数据字典成功！");
			return Result.successResult();
		}
		logger.info("删除动态数据字典失败！");
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(String id,@PathVariable String mode, Model model){
		SysDynamicDicts sysDynamicDicts = null;
		if(StringUtils.equals("add", mode)){
			logger.info("弹窗显示【动态数据字典】添加页面(" + BASE_PATH + "dynamicDicts-add)");
			checkPermission("add");
			Map<String, Object> params = Maps.newHashMap();
			params.put("schema", SysConstant.getValue("currentSchema"));
			List<CommonEntity> list = sysDynamicDictsService.allTable(params);
			model.addAttribute("tables", list);
			return  BASE_PATH + "dynamicDicts-add";
		}else if(StringUtils.equals("edit", mode)){
			logger.info("弹窗显示【动态数据字典】编辑页面(" + BASE_PATH + "dynamicDicts-edit)");
			checkPermission("update");
			Map<String, Object> params = Maps.newHashMap();
			params.put("id", id);
			sysDynamicDicts = sysDynamicDictsService.queryOne(params);
			try {
				sysDynamicDicts.setSqlContent(SerializeUtils.converString2Object(sysDynamicDicts.getSqlContent()).toString());
			} catch (Exception e) {
			}
			params.put("tableName", "TABLE_DICTS");
			params.put("schema", SysConstant.getValue("currentSchema"));
			List<CommonEntity> list = sysDynamicDictsService.allTable(params);
			model.addAttribute("tables", list);
			model.addAttribute("entry", sysDynamicDicts);
			return  BASE_PATH + "dynamicDicts-update";
		}else if(StringUtils.equals("detail", mode)){
			logger.info("弹窗显示【动态数据字典】详情页面(" + BASE_PATH + "dynamicDicts-detail)");
			checkPermission("query");
			sysDynamicDicts = sysDynamicDictsService.queryById(id);
			sysDynamicDicts.setSqlContent(SerializeUtils.converString2Object(sysDynamicDicts.getSqlContent()).toString());
			model.addAttribute("entry", sysDynamicDicts);
		}
		return  BASE_PATH + "dynamicDicts-detail";
	}
	
	@RequestMapping(value = "getTableField", method = RequestMethod.POST)
	@ResponseBody
	public List<CommonEntity> getTableField(@RequestParam Map<String, Object> params){
		String tableName = params.get("tableName").toString();
		logger.info("获取表{}字段信息",tableName);
		params.put("schema", SysConstant.getValue("currentSchema"));
		List<CommonEntity> list = sysDynamicDictsService.selectFields(params);
		return list;
	}
}
