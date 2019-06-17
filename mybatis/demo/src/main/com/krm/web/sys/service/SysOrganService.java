

package com.krm.web.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.krm.common.base.CommonEntity;
import com.krm.common.base.ServiceMybatis;
import com.krm.common.constant.Constant;
import com.krm.common.utils.StringUtil;
import com.krm.web.sys.mapper.SysOrganMapper;
import com.krm.web.sys.mapper.SysRoleMapper;
import com.krm.web.sys.model.SysOrgan;
import com.krm.web.sys.model.SysRole;
import com.krm.web.sys.model.SysUser;
import com.krm.web.util.SysUserUtils;

/**
 * 
 * @author 
 */

@Service("sysOrganService")
@CacheConfig(cacheNames="sysOrgan_cache")
public class SysOrganService extends ServiceMybatis<SysOrgan> {

	@Resource
	private SysOrganMapper sysOrganMapper;
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	/**
	 *新增或更新SysOrgan
	 */
	@CacheEvict(key="'allOrgan'")
	public int saveSysOrgan(SysOrgan organ){
		int count = 0;
		//新的parentIds
		organ.setParentIds(organ.getParentIds()+organ.getParentId()+","); 
		int grade = organ.getParentIds().split(",").length;
		organ.setGrade(String.valueOf(grade));
		if(StringUtil.isEmpty(organ.getId())){
			organ.setId(generateId());
			count = this.insertSelective(organ);
			//自动赋权
			String roleId = SysUserUtils.autoAddOrganToRole();
			if(roleId != null){
				SysRole sysRole = new SysRole();
				sysRole.setId(roleId);
				sysRole.setOrganIds(new String[]{organ.getId()});
				logger.info("添加角色-机构配置信息");
				sysRoleMapper.insertRoleOrgan(sysRole);
			}
		}else{
			//getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
			//先更新parentId，此节点的parentIds以更新
			count = this.updateByPrimaryKeySelective(organ); 
			//不移动节点不更新子节点的pids
			if(!StringUtils.equals(organ.getOldParentIds(), organ.getParentIds())){
				logger.info("批量更新子节点父id");
				sysOrganMapper.updateParentIds(organ); //批量更新子节点的parentIds
			}
		}
		logger.info("清除缓存");
		SysUserUtils.clearCacheOrgan(Lists.newArrayList(SysUserUtils.getCacheLoginUser().getId()));
		return count;
	}
	
	@CacheEvict(key="'allOrgan'")
	public int deleteOrganByRootId(String code){
		try {
			int roleCount = this.beforeDeleteTreeStructure(code, "organId","code", SysRole.class,SysOrgan.class);
			if(roleCount<0) return -1;
			int userOrganCount = this.beforeDeleteTreeStructure(code, "organId","code",  SysUser.class,SysOrgan.class);
			int userCompanyCount = this.beforeDeleteTreeStructure(code, "deptId", "code",  SysUser.class,SysOrgan.class);
			if(userOrganCount+userCompanyCount<0) return -1;
			SysUserUtils.clearCacheOrgan(Lists.newArrayList(SysUserUtils.getCacheLoginUser().getId()));
			logger.info("根据机构号递归向下删除");
			return sysOrganMapper.deleteOrganByRootId(code);
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * 根据用户id查询用户的数据范围
	 */
	public List<String> findUserDataScopeByUserId(String userId){
		logger.info("根据用户id查询用户的数据范围");
		return sysOrganMapper.findUserDataScopeByUserId(userId);
	}
	
	/**
	 * 根据根节点查询自身及其子孙节点
	 */
	public List<String> findOrganIdsByRootId(String rootId){
		logger.info("根据根节点查询机构自身及其子孙节点");
		return sysOrganMapper.findOrganIdsByRootId(rootId);
	}
	
	
	/**
	 * 根据条件分页查询SysOrgan列表
	 * @param {"pageNum":"页码","pageSize":"条数","isCount":"是否生成count sql",......}
	 */
	public PageInfo<CommonEntity> findPageInfo(Map<String,Object> params) {
		logger.info("根据条件分页查询机构信息");
		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("t1", null));
		if(params.get("id") != null && !params.get("id").toString().equals("") && !params.get("id").toString().equals("0")){
			SysOrgan organ = sysOrganMapper.selectByPrimaryKey(params.get("id").toString());
			params.put("parentIds", organ.getParentIds() + ""+ organ.getCode());
		}
        PageHelper.startPage(params);
        List<CommonEntity> list=sysOrganMapper.findPageInfo(params); 
        return new PageInfo<CommonEntity>(list);
	}
	
	/**
	 * 根据机构号查询单条机构信息
	 * @param code
	 * @return
	 */
	public SysOrgan getOrganByCode(String code){
		logger.info("根据机构号查询单条机构信息");
		SysOrgan query = new SysOrgan();
		query.setCode(code);
		List<SysOrgan> list = this.select(query);
		SysOrgan organ = list.size() > 0?list.get(0):null;
		return organ;
	}
}
