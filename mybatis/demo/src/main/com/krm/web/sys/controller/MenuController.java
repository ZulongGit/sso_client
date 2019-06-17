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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.krm.common.base.BaseController;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.Result;
import com.krm.common.constant.Constant;
import com.krm.common.utils.StringUtil;
import com.krm.common.utils.TreeUtils;
import com.krm.web.sys.model.SysMenu;
import com.krm.web.sys.model.SysMenuCategory;
import com.krm.web.sys.service.SysMenuCategoryService;
import com.krm.web.sys.service.SysMenuService;

/**
 * 菜单管理
 * 
 * @ClassName: MenuController
 * @author
 * @date 2014年10月11日 上午11:38:28
 *
 */

@Controller
@RequestMapping("menu")
public class MenuController extends BaseController {

	public static final String BASE_URL = "menu";
	private static final String BASE_PATH = "sys/menu/";
	
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
		return "menu";
	}
	
	@Resource
	private SysMenuService sysMenuService;
	@Resource
	private SysMenuCategoryService sysMenuCategoryService;

	/**
	 * 跳转到菜单管理页面
	 * 
	 * @param model
	 * @return 菜单管理模块html
	 */
	@RequestMapping
	public String toMenu(Model model) {
		logger.info("跳转到菜单管理页面");
		checkPermission("query");
		Map<String, Object> params = Maps.newHashMap();
		params.put(Constant.FIELD_DEL_FLAG, Constant.DEL_FLAG_NORMAL);
		List<CommonEntity> categories = sysMenuCategoryService.commonList(params);
		List<SysMenu> allMenus = sysMenuService.getAllMenusList();
		for (CommonEntity entity : categories) {
			SysMenu menu = new SysMenu();
			menu.setId(entity.getString("id"));
			menu.setParentId("0");
			menu.setName(entity.getString("name"));
			menu.setIcon(entity.getString("icon"));
			menu.setUrl(entity.getString("url"));
			menu.setDescription(entity.getString("description"));
			allMenus.add(menu);
		}
		model.addAttribute("treeList", JSON.toJSONString(allMenus));
		return BASE_PATH + "menu-list";
	}

	/**
	 * 菜单树
	 * 
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.POST)
	public @ResponseBody List<CommonEntity> tree() {
		logger.info("获取菜单树");
		checkPermission("query");
		List<CommonEntity> menus = Lists.newArrayList();
		Map<String, Object> params = Maps.newHashMap();
		params.put(Constant.FIELD_DEL_FLAG, Constant.DEL_FLAG_NORMAL);
		List<CommonEntity> categories = sysMenuCategoryService.commonList(params);
		List<SysMenu> allMenus = sysMenuService.getAllMenusList();
		for (CommonEntity entity : categories) {
			SysMenu menu = new SysMenu();
			menu.setId(entity.getString("id"));
			menu.setParentId("0");
			menu.setName(entity.getString("name"));
			menu.setIcon(entity.getString("icon"));
			menu.setUrl(entity.getString("url"));
			menu.setDescription(entity.getString("description"));
			allMenus.add(menu);
		}
		menus.addAll(TreeUtils.toTreeNodeList(allMenus,SysMenu.class));
		return menus;
	}
	
	/**
	 * 菜单树
	 * 
	 * @return
	 */
	@RequestMapping(value = "childMenus", method = RequestMethod.POST)
	public @ResponseBody List<CommonEntity> tree(String category) {
		logger.info("获取菜单树");
		List<CommonEntity> menus = Lists.newArrayList();
		Map<String, Object> params = Maps.newHashMap();
		params.put(Constant.FIELD_DEL_FLAG, Constant.DEL_FLAG_NORMAL);
		List<CommonEntity> categories = sysMenuCategoryService.commonList(params);
		for (CommonEntity entity : categories) {
			if(entity.getString("id").equals(category)){
				menus.addAll(TreeUtils.toTreeNodeListWithRoot(sysMenuService.getAllMenusList(),SysMenu.class, entity.getString("id")));
			}
		}
		return menus;
	}
	
	

	/**
	 * 分页显示菜单table
	 * 
	 * @param params
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public PageInfo<CommonEntity> list(@RequestParam Map<String, Object> params) {
		logger.info("进入到菜单列表页面");
		checkPermission("query");
		PageInfo<CommonEntity> page = sysMenuService.findPageInfo(params);
		return page;
	}

	/**
	 * 添加或更新菜单
	 * 
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Result save(@ModelAttribute SysMenu sysMenu) {
		logger.info("开始添加或更新菜单");
		if(StringUtil.isEmpty(sysMenu.getId())){
			checkPermission("add");
		}else{
			checkPermission("update");
		}
		int count = sysMenuService.saveSysMenu(sysMenu);
		if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}

	/**
	 * 删除菜单及其子菜单
	 * 
	 * @param resourceId
	 *            菜单id
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody Result dels(String id) {
		logger.info("删除菜单及其子菜单");
		checkPermission("delete");
		Integer count = 0;
		if (null != id) {
			count = sysMenuService.deleteMenuByRootId(id);
		}
		if(count == -1){
			return new Result(0, "此菜单已被分配，无法删除！");
		}
		if(count > 0){
			return Result.successResult();
		}
		return Result.errorResult();
	}

	/**
	 * 弹窗
	 * 
	 * @param id
	 * @param parentId
	 *            父类id
	 * @param mode
	 *            模式(add,edit,detail)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showLayer(String id, String parentId, @PathVariable("mode") String mode, Model model) {
		SysMenu menu = null, pMenu = null;
		if (StringUtils.equalsIgnoreCase(mode, "add")) {
			logger.info("弹窗显示添加菜单页面");
			checkPermission("add");
			pMenu = sysMenuService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "edit")) {
			logger.info("弹窗显示修改菜单页面");
			checkPermission("update");
			menu = sysMenuService.selectByPrimaryKey(id);
			if(parentId != null){
				pMenu = sysMenuService.selectByPrimaryKey(parentId);
				if(pMenu == null) {
					pMenu = new SysMenu();
					SysMenuCategory category = sysMenuCategoryService.selectByPrimaryKey(parentId);
					if(category != null){
						pMenu.setId(category.getId());
						pMenu.setName(category.getName());
					}
				}
			}
		} else if (StringUtils.equalsIgnoreCase(mode, "detail")) {
			logger.info("弹窗显示菜单详情页面");
			checkPermission("query");
			menu = sysMenuService.selectByPrimaryKey(id);
			pMenu = sysMenuService.selectByPrimaryKey(menu.getParentId());
		}
		model.addAttribute("pMenu", pMenu).addAttribute("sysMenu", menu);
		return mode.equals("detail") ? BASE_PATH + "menu-detail" : BASE_PATH + "menu-save";
	}

}
