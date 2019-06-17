package com.krm.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.krm.common.base.BaseEntity;
import com.krm.common.base.CommonEntity;

public class TreeUtils {
	
	
	/**
	 * 转换成List形式树结构 (如果是缓存的list，请务必深度copy一个)
	* @param list
	* @return
	 */
	public static <T extends BaseEntity<T>> List<CommonEntity> toTreeNodeListCommon(List<T> source,Class<T> bean, String idField, String rootId){
		
		final Map<String, CommonEntity> nodes = new HashMap<String, CommonEntity>();

		// 深度copy一个，防止源list内部结构改变
		List<T> list = Collections3.copyTo(source, bean);
		List<CommonEntity> resultList = Lists.newArrayList();
		// 所有节点记录下来
		for (T node : list) {
			CommonEntity entity = BeanUtils.beanToCommon(node);
			entity.put("level", -1);
			entity.put("hasChild", false);
			entity.put("children", new ArrayList<CommonEntity>());
			if(idField == null){
				nodes.put(BeanUtils.getProperty(node, "id").toString(), entity);
			}else{
				nodes.put(BeanUtils.getProperty(node, idField).toString(), entity);
			}
			resultList.add(entity);
		}

		final CommonEntity root = new CommonEntity();
		try {
			if(StringUtil.isEmpty(rootId)){
				root.put("level", "0");
				root.put("children", new ArrayList<CommonEntity>());
				root.put("hasChild", false);
				nodes.put("0", root);
			}else {
				root.put("level", "0");
				root.put("children", new ArrayList<CommonEntity>());
				root.put("hasChild", false);
				nodes.put(rootId, root);
			}
			for (CommonEntity node : resultList) {
				final CommonEntity parent = nodes.get(node.get("parentId"));
				if (node.get("parentId").toString().equals(rootId)) {
					((ArrayList<CommonEntity>)root.get("children")).add(node);
					continue;
					// throw new RuntimeException("子节点有父级id，却没有找到此父级的对象");
				} else {
					// 添加子节点
					if(StringUtil.isNotEmpty(parent)){
						((List<CommonEntity>)parent.get("children")).add(node);
					}
				}
			}

			int max = 0;
			for (CommonEntity node : resultList) {
				max = Math.max(resolveLevel(node, nodes), max);
			}

			return (List<CommonEntity>) root.get("children");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	//递归找level
	private static  int resolveLevel(final CommonEntity node, final Map<String,CommonEntity> nodes){
		//System.out.println(node.getIntValue("level"));
		int level = 1;
		if(node != null){
		    level = node.getIntValue("level");
		    if(level == -2){
		        throw new RuntimeException("Node循环了, id=" + node.get("id"));
		    }
		    if(level == -1){
		    	node.put("level", -2);
		        level = resolveLevel(nodes.get(node.get("parentId")),nodes) +1;
		        node.put("level", level);
		    }else{
		    	node.put("hasChild", true);
		    }
		}
	    return level;
	}
	
	
	public static <T extends BaseEntity<T>> List<CommonEntity> toTreeNodeList(List<T> source,Class<T> bean){
		return toTreeNodeListCommon(source, bean, null, "0");
	}
	public static <T extends BaseEntity<T>> List<CommonEntity> toTreeNodeList(List<T> source,Class<T> bean, String idField){
		return toTreeNodeListCommon(source, bean, idField, "0");
	}
	public static <T extends BaseEntity<T>> List<CommonEntity> toTreeNodeListWithRoot(List<T> source,Class<T> bean, String rootId){
		return toTreeNodeListCommon(source, bean, null, rootId);
	}
}
