

package com.krm.web.sys.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.utils.StringUtil;
import com.krm.web.sys.model.SysArea;
import com.krm.web.sys.model.SysOrgan;
import com.krm.web.sys.service.SysAreaService;
import com.krm.web.sys.service.SysOrganService;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author 
 */

@Controller
@RequestMapping("organ")
public class OrganController extends BaseController {
	
	public static final String BASE_URL = "organ";
	private static final String BASE_PATH = "sys/organ/";

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
		return "organ";
	}
	
	@Resource
	private SysOrganService sysOrganService;
	@Resource
	private SysAreaService sysAreaService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toSysOrgan(Model model){
		logger.info("跳转到机构页面");
		checkPermission("query");
		model.addAttribute("treeList", JSON.toJSONString(SysUserUtils.getUserOrgan()));
		return BASE_PATH + "organ-list";
	}
	
	@RequestMapping(value="tree",method = RequestMethod.POST)
	public @ResponseBody List<SysOrgan> getOrganTreeList(@ModelAttribute SysOrgan sysOrgan){
		logger.info("获取机构树");
		checkPermission("query");
		return SysUserUtils.getUserOrgan();
	}
	
	/**
	 * 分页显示
	* @param params
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params){
		logger.info("跳转到机构列表页面");
		checkPermission("query");
		PageInfo<CommonEntity> page = sysOrganService.findPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	* @param params
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Result save(@ModelAttribute SysOrgan sysOrgan){
		logger.info("开始保存/更新机构");
		if(StringUtil.isEmpty(sysOrgan.getId())){
			checkPermission("add");
		}else{
			checkPermission("update");
		}
		int count = sysOrganService.saveSysOrgan(sysOrgan);
		if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	
	/**
	 * 删除
	* @param 
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Result del(@RequestParam Map<String, Object> params, String id){
		logger.info("开始删除机构");
		checkPermission("delete");
		int count = sysOrganService.deleteOrganByRootId(id);
		if(count > 0){
			return Result.successResult();
		}
		if(count == -1){
			return new Result(0, "删除的机构列表中已有部分绑定给用户，请先解除相关绑定重试！");
		}
		return Result.errorResult();
	}
	
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(String id,String parentId,@PathVariable("mode") String mode, Model model){
		SysOrgan organ = null, pOrgan = null;
		SysArea area = null;
		if(StringUtils.equalsIgnoreCase(mode, "add")){
			logger.info("弹窗显示添加机构页面");
			checkPermission("add");
			pOrgan = parentId==null?null:sysOrganService.getOrganByCode(parentId);
		}else if(StringUtils.equalsIgnoreCase(mode, "edit")){
			logger.info("弹窗显示修改机构页面");
			checkPermission("update");
			organ = sysOrganService.selectByPrimaryKey(id);
			pOrgan = parentId==null?null:sysOrganService.getOrganByCode(parentId);
//			area = sysAreaService.selectByPrimaryKey(organ.getAreaId());
		}else if(StringUtils.equalsIgnoreCase(mode, "detail")){
			logger.info("弹窗显示机构详情页面");
			checkPermission("query");
			organ = sysOrganService.selectByPrimaryKey(id);
			pOrgan = sysOrganService.getOrganByCode(organ.getParentId());
//			area = sysAreaService.selectByPrimaryKey(organ.getAreaId());
		}
		model.addAttribute("pOrgan", pOrgan)
			.addAttribute("organ", organ)
			.addAttribute("area", area);
		return mode.equals("detail")?BASE_PATH + "organ-detail":BASE_PATH + "organ-save";
	}
	
	
	@RequestMapping(value="checkCode",method=RequestMethod.POST)
	public @ResponseBody boolean checkName(@ModelAttribute SysOrgan organ){
		logger.info("开始验证机构号是否重复");
		SysOrgan result = null;
		try {
			SysOrgan temp = (SysOrgan) BeanUtils.cloneBean(organ);
			temp.setId(null);
			result = sysOrganService.selectOne(temp);
		} catch (Exception e) {
			logger.info("此机构号已存在!");
			return false;
		}
		if(result == null){
			return true;
		}else{
			//id没有值的时候，说明是添加操作,有值则是修改操作，并排除自己
			if(organ.getId() != null && !organ.getId().equals("undefined") && !organ.getId().equals("")){
				String id = result.getId();
				if(id.trim().equals(organ.getId().trim())){
					logger.info("校验通过!");
					return true;
				}
			}
		}
		logger.info("此机构号已存在!");
		return false;
	}
}
