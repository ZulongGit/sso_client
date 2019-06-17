package com.krm.common.spring.listener;

import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import com.krm.common.beetl.utils.BeetlUtils;
import com.krm.common.constant.Constant;
import com.krm.common.constant.SysConstant;
import com.krm.web.sys.model.SysMenu;
import com.krm.web.sys.service.SysMenuService;

@Component
public class ApplicationContextInitListener implements
		ApplicationListener<ContextRefreshedEvent>, ServletContextAware {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private ServletContext servletContext;

	@Resource
	private SysMenuService sysMenuService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		ApplicationContext parentContext = ((ContextRefreshedEvent) event)
				.getApplicationContext().getParent();
		
		// 子容器初始化时(spring-mvc)
		if (null != parentContext) {
			
			/*RequestMappingHandlerMapping rmhp = event.getApplicationContext()
					.getBean(RequestMappingHandlerMapping.class);
			
			Map<RequestMappingInfo, HandlerMethod> map = rmhp.getHandlerMethods();
			
			Iterator<RequestMappingInfo> iterator = map.keySet().iterator();
			
			while(iterator.hasNext()){
				RequestMappingInfo info = iterator.next();
				Set<String> set = info.getPatternsCondition().getPatterns();
			}*/
			
			String ctxPath = servletContext.getContextPath();
			
			//读取全部资源
			LinkedHashMap<String, SysMenu> AllMenuMap = sysMenuService.getAllMenusMap();
			BeetlUtils.addBeetlSharedVars(Constant.CACHE_ALL_MENU,AllMenuMap);
			
			
			logger.info("根路径: "+ctxPath);
			logger.info("数据库类型: "+SysConstant.getValue("jdbc.type"));
			logger.info("初始化系统资源: (key: " + Constant.CACHE_ALL_MENU + ",value: Map<菜单url, SysMenu>)");
		}
		
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
