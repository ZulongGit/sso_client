package com.krm.web.sys.controller;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.krm.common.base.BaseController;
import com.krm.common.base.Result;
import com.krm.common.constant.Constant;
import com.krm.common.utils.SerializeUtils;
import com.krm.common.utils.StringUtil;
import com.krm.web.sys.model.SysDict;
import com.krm.web.sys.model.SysDynamicDicts;
import com.krm.web.sys.service.SysDictService;
import com.krm.web.sys.service.SysDynamicDictsService;
import com.krm.web.util.SysUserUtils;

@Controller
@RequestMapping("dict")
public class DictController extends BaseController{

	public static final String BASE_URL = "dict";
	private static final String BASE_PATH = "sys/dict/";
	
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
		return "dict";
	}
	
	@Resource
	private SysDictService sysDictService;
	@Resource
	private SysDynamicDictsService sysDynamicDictsService;
	
	@RequestMapping
	public String toDict(Model model){
		logger.info("跳转到字典管理页面");
		checkPermission("query");
		return BASE_PATH + "dict-list";
	}
	
	/**
	 * 添加或更新字典
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Result save(@ModelAttribute SysDict sysDict) {
		logger.info("开始添加或更新字典");
		if(StringUtil.isEmpty(sysDict.getId())){
			checkPermission("add");
		}else{
			checkPermission("update");
		}
		int count = sysDictService.saveSysdict(sysDict);
		if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	@ResponseBody 
	public Result del(@ModelAttribute SysDict sysDict){
		logger.info("开始删除字典");
		checkPermission("delete");
		int count = sysDictService.deleteSysDict(sysDict);
		if(count == -1){
			return new Result(0, "该字典数据正在使用中，无法删除！");
		}
		if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<SysDict> list(@RequestParam Map<String, Object> params) {
		logger.info("进入到字典列表页面");
		checkPermission("query");
		PageInfo<SysDict> page = sysDictService.findPageInfo(params);
		return page;
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(String id, Model model, @PathVariable String mode, @ModelAttribute SysDict sysDict){
		if (StringUtils.equalsIgnoreCase(mode, "add")){
			logger.info("弹窗显示添加字典页面");
			checkPermission("add");
			sysDict.setSort(10);
			model.addAttribute("entry", sysDict);
		}else if (StringUtils.equalsIgnoreCase(mode, "formAdd")){
			logger.info("弹窗显示添加字典页面（带参数）");
			checkPermission("add");
			Integer sort = 0;
			List<SysDict> list = (List<SysDict>) sysDictService.findAllMultimap().get(sysDict.getType());
			for (SysDict temp : list) {
				if(temp.getSort().intValue() >= sort.intValue()){
					sort = temp.getSort() + 10;
				}
			}
			sysDict.setSort(sort);
			model.addAttribute("entry", sysDict);
		}else if (StringUtils.equalsIgnoreCase(mode, "edit")){
			logger.info("弹窗显示修改字典页面");
			checkPermission("update");
			SysDict dict = sysDictService.selectByPrimaryKey(id);
			model.addAttribute("entry", dict);
		}
		return BASE_PATH + "dict-save";
	}
	
	
//	/**
//	 * 根据类型和键值得到字典实体
//	* @param type 如sys_data_scope等
//	* @param value 
//	 */
//	public SysDict getDictByTypeAndValue(String type,String value){
//		Table<String,String, SysDict> tableDicts = sysDictService.findAllDictTable();
//		return tableDicts.get(type, value);
//	}
//	
	/**
	 * 根据类型得到字典列表
	* @param type 如sys_data_scope等
	 */
	@RequestMapping(value="getDictListByType",method=RequestMethod.POST)
	@ResponseBody
	public List<SysDict> getDictListByType(String type, @RequestParam Map<String, Object> params){
		logger.info("开始根据类型得到字典列表");
		if(type.indexOf("_dynamic") == -1){
			return (List<SysDict>) sysDictService.findAllMultimap().get(type);
		}
		params.put("type", type.substring(0, type.indexOf("_dynamic")));
		SysDynamicDicts sysDynamicDicts = sysDynamicDictsService.queryOne(params);
		String sql = SerializeUtils.converString2Object(sysDynamicDicts.getSqlContent()).toString();
		sql = StringEscapeUtils.unescapeHtml4(sql);
		try {
			sql = StringUtil.transfer(sql, params);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		String[] group = sql.split("where|WHERE");
		if(group.length > 0){
			sb.append(group[0]);
		}
		for (int i = 1; i < group.length; i++) {
			if(StringUtil.isNotEmpty(sysDynamicDicts.getUserIdFieldBind())){
				sb.append(" left join SYS_USER u on t."+sysDynamicDicts.getUserIdFieldBind()+" = u.ID");
			}
			if(StringUtil.isNotEmpty(sysDynamicDicts.getOrganCodeFieldBind())){
				sb.append(" left join SYS_ORGAN o on t."+sysDynamicDicts.getOrganCodeFieldBind()+" = o.CODE");
			}
			sb.append(" WHERE ").append(group[i]);
			if(!SysUserUtils.getSessionLoginUser().isAdmin()){
				if(sysDynamicDicts.getDataScope().equals(Constant.DATA_SCOPE_SELF)){
					sb.append(" and t."+sysDynamicDicts.getUserIdFieldBind()+" = #{"+StringUtil.underlineToCamelhump(sysDynamicDicts.getUserIdFieldBind())+"}");
				}else if(sysDynamicDicts.getDataScope().equals(Constant.DATA_SCOPE_SELF)){
					sb.append(" and t."+sysDynamicDicts.getUserIdFieldBind()+" = #{"+StringUtil.underlineToCamelhump(sysDynamicDicts.getUserIdFieldBind())+"}");
				}
			}
		}
		params.put("sql", sb.toString());
		params.put("key", sysDynamicDicts.getKeyName());
		params.put("value", sysDynamicDicts.getValueName());
		try {
			params.put(StringUtil.underlineToCamelhump(sysDynamicDicts.getUserIdFieldBind()), SysUserUtils.getSessionLoginUser().getId());
			params.put(StringUtil.underlineToCamelhump(sysDynamicDicts.getOrganCodeFieldBind()), SysUserUtils.getSessionLoginUser().getOrganId());
		} catch (Exception e) {
		}
		return sysDynamicDictsService.exeuteSql(params);
	}
	
//	
//	/**
//	 * 全部字典
//	 */
//	public Collection<String> getAllDictType(){
//		return sysDictService.findAllMultimap().keySet();
//	}
//	
//	
//	/**
//	 * 全部字典(描述)
//	 */
//	public Collection<String> getAllDicts(){
//		return sysDictService.getAllDicts().keySet();
//	}
//	
//	
//	public Map<String, String> findAllType(){
//		Map<String, String> map = sysDictService.findAllType();
//		Map<String, Object> params = Maps.newHashMap();
//		List<SysDynamicDicts> list = sysDynamicDictsService.entityList(params);
//		for (SysDynamicDicts tableDicts : list) {
//			map.put(tableDicts.getType() + "_dynamic", tableDicts.getRemarks());
//		}
//		return map;
//	}
}
