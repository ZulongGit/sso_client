package com.krm.common.beetl.function;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.stereotype.Component;

import com.krm.web.sys.model.SysArea;
import com.krm.web.sys.service.SysAreaService;

@Component
public class AreaFunction implements Function{

	@Resource
	private SysAreaService sysAreaService;
	
	@Override
	public Object call(Object[] params, Context ctx) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<SysArea> list = null;
		try {
			list = sysAreaService.findAllArea();
			for(SysArea area : list){
				map.put(area.getId(), area);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

}
