

package com.krm.web.sys.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.ServiceMybatis;
import com.krm.common.beetl.utils.BeetlUtils;
import com.krm.common.constant.Constant;
import com.krm.common.utils.StringUtil;
import com.krm.common.utils.TreeUtils;
import com.krm.web.sys.mapper.SysMenuMapper;
import com.krm.web.sys.model.SysMenu;
import com.krm.web.sys.model.SysUser;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author
 */

@Service("sysMenuService")
public class SysMenuService extends ServiceMybatis<SysMenu> {

	@Resource
	private SysMenuMapper sysMenuMapper;

	/**
	 * 新增or更新SysMenu
	 */
	public int saveSysMenu(SysMenu sysMenu) {
		int count = 0;
		// 新的parentIds
		sysMenu.setParentIds(sysMenu.getParentIds() + sysMenu.getParentId() + ",");
		if(StringUtil.isEmpty(sysMenu.getId())){
			sysMenu.setId(generateId());
			count = this.insertSelective(sysMenu);
		} else {
			// getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
			// 先更新parentId，此节点的parentIds以更新
			count = this.updateByPrimaryKeySelective(sysMenu);
			// 不移动节点不更新子节点的pids    oracle不需要
			if (!StringUtils.equals(sysMenu.getOldParentIds(), sysMenu.getParentIds())) {
				logger.info("批量更新子节点父id");
				sysMenuMapper.updateParentIds(sysMenu); // 批量更新子节点的parentIds
			}
		}
		if (count > 0) {
			logger.info("清除缓存");
			BeetlUtils.addBeetlSharedVars(Constant.CACHE_ALL_MENU, this.getAllMenusMap());
			SysUserUtils.clearCacheMenu();
		}
		return count;
	}

	/**
	 * 根据父id删除自身已经所有子节点
	 * 
	 * @param id
	 * @return
	 */
	public int deleteMenuByRootId(String id) {
		logger.info("删除菜单前验证是否有角色已经分配");
		int count = sysMenuMapper.beforeDeleteMenu(id);
		if (count > 0)
			return -1;
		logger.info("根据父id删除自身以及所有子节点");
		int delCount = sysMenuMapper.deleteIdsByRootId(id);
		if (delCount > 0) {
			// 重新查找全部菜单放入缓存(为了开发时候用)
			logger.info("清除缓存");
			BeetlUtils.addBeetlSharedVars(Constant.CACHE_ALL_MENU, this.getAllMenusMap());
			SysUserUtils.clearCacheMenu();
		}

		return delCount;
	}

	/**
	 * 根据用户id得到用户持有的菜单
	 * @param userId
	 * @return
	 */
	public List<SysMenu> findUserMenuByUserId(SysUser sysUser) {
		logger.info("根据用户id得到用户持有的菜单");
		return sysMenuMapper.findUserMenuByUserId(sysUser.getId());
	}

	/**
	 * 菜单管理分页显示筛选查询
	 * @param params {"name":"菜单名字","id":"菜单id"}
	 * @return
	 */
	public PageInfo<CommonEntity> findPageInfo(Map<String, Object> params) {
		logger.info("菜单管理分页查询");
		if(params.get("id") != null && !params.get("id").toString().equals("") && !params.get("id").toString().equals("0")){
			SysMenu menu = sysMenuMapper.selectByPrimaryKey(params.get("id").toString());
			params.put("parentIds", menu.getParentIds() + ""+ menu.getId());
		}
		PageHelper.startPage(params);
		List<CommonEntity> list = sysMenuMapper.findPageInfo(params);
        return new PageInfo<CommonEntity>(list);
	}

	/**
	 * 获取全部菜单map形式
	 * @return
	 */
	public LinkedHashMap<String, SysMenu> getAllMenusMap() {
		// 读取全部菜单
		List<SysMenu> resList = this.select(new SysMenu(), "sort");
		LinkedHashMap<String, SysMenu> AllMenuMap = new LinkedHashMap<String, SysMenu>();
		for (SysMenu res : resList) {
			if (StringUtils.isBlank(res.getUrl())) {
				AllMenuMap.put(res.getId().toString(), res);
			} else {
				AllMenuMap.put(res.getUrl(), res);
			}
		}
		return AllMenuMap;
	}

	/**
	 * 获取全部菜单list形式
	 * @return
	 */
	public List<SysMenu> getAllMenusList() {
		LinkedHashMap<String, SysMenu> allRes = BeetlUtils .getBeetlSharedVars(Constant.CACHE_ALL_MENU);
		List<SysMenu> resList = new ArrayList<SysMenu>(allRes.values());
		return resList;
	}
	
	/**
	 * 获取菜单树
	 */
	public List<CommonEntity> getMenuTree(){
		return TreeUtils.toTreeNodeList(getAllMenusList(),SysMenu.class);
	}

}
