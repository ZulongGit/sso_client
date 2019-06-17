

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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.utils.StringUtil;
import com.krm.common.utils.TreeUtils;
import com.krm.web.sys.model.SysMenu;
import com.krm.web.sys.model.SysRole;
import com.krm.web.sys.model.SysRolePermission;
import com.krm.web.sys.model.SysUser;
import com.krm.web.sys.service.SysMenuService;
import com.krm.web.sys.service.SysOrganService;
import com.krm.web.sys.service.SysRoleService;
import com.krm.web.sys.service.SysUserService;

/**
 * 
 * @author 
 */

@Controller
@RequestMapping("role")
public class RoleController extends BaseController {
	
	public static final String BASE_URL = "role";
	private static final String BASE_PATH = "sys/role/";
	
	@Resource
	private SysRoleService sysRoleService;
	@Resource
	private SysUserService sysUserService;
	@Resource
	private SysOrganService sysOrganService;
	@Resource
	private SysMenuService sysMenuService;
	
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
		return "role";
	}
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toSysRole(Model model){
		logger.info("跳转到角色页面");
		checkPermission("query");
		return BASE_PATH + "role";
	}
	
	/**
	 * 绑定用户界面
	* @return
	 */
	@RequestMapping(value="binduser",method=RequestMethod.POST)
	public String toBindUser(String id,Model model){
		logger.info("跳转到分配角色页面页面");
		checkPermission("update");
		List<SysUser> users = sysRoleService.findUserByRoleId(id);
		model.addAttribute("users", users).addAttribute("roleId", id);
		return BASE_PATH + "role-user";
	}
	
	/**
	 * 部门的人员
	* @param organId
	* @return
	 */
	@RequestMapping(value="organuser",method=RequestMethod.POST)
	public @ResponseBody List<SysUser> organUser(String organId){
		logger.info("获取机构/部门下的用户");
		SysUser sysUser = new SysUser();
		sysUser.setOrganId(organId);
		return sysUserService.select(sysUser);
	}
	
	/**
	 * 保存角色绑定的用户
	* @return
	 */
	@RequestMapping(value="saveuser",method=RequestMethod.POST)
	public @ResponseBody Result saveUserRole(@ModelAttribute SysRole sysRole){
		logger.info("开始保存角色绑定的用户");
		checkPermission("update");
		int count = sysRoleService.saveUserRole(sysRole);
		if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	/**
	 * 分页显示
	* @param params
	* @return
	 */
	@RequestMapping(value="list",method=RequestMethod.POST)
	@ResponseBody
	public PageInfo<SysRole> list(@RequestParam Map<String, Object> params,Model model){
		logger.info("进入角色列表页面");
		checkPermission("query");
		PageInfo<SysRole> page = sysRoleService.findPageInfo(params);
		return page;
	}
	
	/**
	 * 添加或更新
	* @param params
	* @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Result save(@ModelAttribute SysRole sysRole){
		logger.info("开始添加或更新角色");
		checkPermission("delete");
		if(StringUtil.isEmpty(sysRole.getId())){
			checkPermission("add");
		}else{
			checkPermission("update");
		}
		int count = sysRoleService.saveSysRole(sysRole);
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
	public @ResponseBody Result del(String id){
		logger.info("开始删除角色");
		checkPermission("delete");
		int count = sysRoleService.deleteSysRole(id);
		if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}
	
	/**
	 * 弹窗显示
	* @param params {"mode":"1.add 2.edit 3.detail}
	* @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(String id,@PathVariable String mode, Model model){
		SysRole sysRole = null;
		if(StringUtils.equals("add", mode)){
			logger.info("弹窗显示添加角色页面");
			checkPermission("add");
			return BASE_PATH + "role-add";
		}
		if(StringUtils.equals("edit", mode)){
			logger.info("弹窗显示修改角色页面");
			checkPermission("update");
			sysRole = sysRoleService.selectByPrimaryKey(id);
			List<String> resIds = sysRoleService.findMenuIdsByRoleId(id);
			if(sysRole.getDataScope().equals("9")){
				List<String> organIds = sysRoleService.findOrganIdsByRoleId(id);
				model.addAttribute("organIds", JSON.toJSON(organIds));
			}
			if(sysRole.getOrganId() != null){
				model.addAttribute("pOrgan",sysOrganService.getOrganByCode(sysRole.getOrganId()));
			}
			model.addAttribute("resIds",JSON.toJSONString(resIds))
				 .addAttribute("sysrole", sysRole);
			return BASE_PATH + "role-update";
		}
		if(StringUtils.equals("opRights", mode)){
			logger.info("弹窗显示修改角色页面");
			checkPermission("update");
			Map<String, Object> params = Maps.newHashMap();
			params.put("roleId", id);
			List<SysRolePermission> list = sysRoleService.queryRolePermissions(params);
			List<CommonEntity> menus = TreeUtils.toTreeNodeListCommon(list, SysRolePermission.class, "menuId", "0");
			model.addAttribute("menus", menus);
			model.addAttribute("roleId", id);
			return BASE_PATH + "role-opRights";
		}
		if(StringUtils.equals("detail", mode)){
			logger.info("弹窗显示角色详情页面");
			checkPermission("query");
			sysRole = sysRoleService.selectByPrimaryKey(id);
			List<SysUser> users = sysRoleService.findUserByRoleId(id);
			List<SysMenu> SysMenu = sysRoleService.findMenuByRoleId(id);
			model.addAttribute("users", users)
				.addAttribute("menus", SysMenu);
			model.addAttribute("sysrole", sysRole);
		}
		return BASE_PATH + "role-detail";
	}
	
	/**
	 * 添加或更新角色操作权限
	 * @param sysRolePermission
	 * @return
	 */
	@RequestMapping(value="saveOpRights",method=RequestMethod.POST)
	public @ResponseBody Result saveOpRights(@ModelAttribute SysRolePermission sysRolePermission){
		logger.info("开始添加或更新角色操作权限");
		checkPermission("update");
		List<SysRolePermission> list = sysRolePermission.getList();
		Map<String, Object> params = Maps.newHashMap();
		params.put("roleId", sysRolePermission.getRoleId());
		sysRoleService.deleteRolePermissionByRoleId(params);
		int count = sysRoleService.saveRolePermission(list);
		if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}
}
