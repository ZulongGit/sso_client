
package com.krm.web.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;

import com.krm.common.utils.config.ConfigListener;

public class ConfigUtils {
	public static String BOOTSTRAP_CONFIG_FILE = "com/krm/web/codegen/codegen_config.properties";
	public static DefaultResourceLoader DEFAULT_RESOURCE_LOADER;
	private static Logger logger;
	private static Properties CONFIG_CHACHE;
	private static List<String> LOADED_PROPERTY_FILE_LIST;
	private static Map<String, Set<ConfigListener>> CONFIGLISTENERS;
	private static Date lastUpdateDate;
	private static Date beanLastUpdateDate;
	private static boolean hasInit;

	private static synchronized void init() {
		if (ConfigUtils.hasInit) {
			return;
		}
		ConfigUtils.logger.info("开始加载系统启动配置文件：com/krm/web/codegen/codegen_config.properties");
		Resource resource = ConfigUtils.DEFAULT_RESOURCE_LOADER.getResource("com/krm/web/codegen/codegen_config.properties");
		if (null == resource || !resource.exists()) {
			throw new IllegalArgumentException("启动配置文件没有找到：com/krm/web/codegen/codegen_config.properties");
		}
		try {
			Properties properties = PropertiesLoaderUtils
					.loadProperties(new EncodedResource(resource, ConfigEnum.DEFAULT_CONFIG_FILE_ENCODING));
			loadProperties(properties);
		} catch (IOException e) {
			ConfigUtils.logger.error("加载启动配置文件失败：com/krm/web/codegen/codegen_config.properties", (Throwable) e);
			throw new RuntimeException("加载启动配置文件失败：com/krm/web/codegen/codegen_config.properties", e);
		}
		ConfigUtils.logger.info("成功加载系统启动配置文件：com/krm/web/codegen/codegen_config.properties");
		int defualtRefrechTime = 60;
		int value = getValue("config_file_refresh_second", Integer.valueOf(defualtRefrechTime));
		if (value < 0) {
			ConfigUtils.logger.error("系统配置项{}有问题，为大于等于0的正整数，请检查，暂采用默认时间{}", (Object) "config_file_refresh_second",
					(Object) defualtRefrechTime);
			value = defualtRefrechTime;
		}
		if (value > 0) {
			loadExtProperties();
			ConfigUtils.logger.info("开始启动定时刷新系统配置的定时器……");
			ExecutorUtils.getSystemScheduledExecutorService().scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
					loadExtProperties();
				}
			}, value, value, TimeUnit.SECONDS);
			ConfigUtils.logger.info("成功启动定时刷新系统配置的定时器，更新间隔为{}秒。", (Object) value);
		}
		ConfigUtils.hasInit = true;
	}

	private static synchronized void loadExtProperties() {
//		String extConfigFiles = getValue("extconfig.paths");
		String extConfigFiles = "com/krm/web/codegen/codegen_config.properties";
		ConfigUtils.logger.debug("开始加载扩展配置文件集合：" + extConfigFiles);
		Properties extProps = new Properties();
		if (StringUtils.isNotBlank((CharSequence) extConfigFiles)) {
			String[] files =  extConfigFiles.split(";");
			for (String file : files) {
				loadPropFile(file, extProps);
			}
		}
		ConfigUtils.logger.debug("完成加载扩展配置文件集合：" + extConfigFiles);
		/*
		String extConfigBeans = getValue("extconfig.beans");
		 if (StringUtils.isNotBlank((CharSequence) extConfigBeans)) {
			loadBeanProps(extConfigBeans, extProps);
		}*/
		loadProperties(extProps);
		ConfigUtils.lastUpdateDate = new Date();
	}

	/*private static void loadBeanProps(final String extConfigBeans, Properties extProps) {
		if (null != SpringContextHolder.getApplicationContext()) {
			ConfigUtils.logger.debug("开始加载扩展配置Bean：" + extConfigBeans);
			String[] beans = extConfigBeans.split(";");
			for (String bean : beans) {
				Properties properties = null;
				Label_0172: {
					try {
						ConfigDao configDao = SpringContextHolder.getBean(bean,
								ConfigDao.class);
						if (null == configDao) {
							ConfigUtils.logger.warn("系统配置持久化bean[{}]不存在，直接忽略", (Object) bean);
							break Label_0172;
						}
						properties = configDao.queryConfig(ConfigUtils.beanLastUpdateDate);
					} catch (Exception e) {
						if (ConfigUtils.logger.isWarnEnabled()) {
							ConfigUtils.logger.warn("执行系统配置持久化接口" + bean + "失败，直接忽略", (Throwable) e);
						}
						break Label_0172;
					}
					extProps.putAll(properties);
				}
			}
			ConfigUtils.beanLastUpdateDate = new Date();
			ConfigUtils.logger.debug("成功加载扩展配置Bean：" + extConfigBeans);
		} else if (StringUtils.isNotBlank((CharSequence) extConfigBeans)) {
			ConfigUtils.logger.warn("由于Spring没有初始化完成，延迟加载扩展配置Bean：" + extConfigBeans);
			ObservableManager.get().getObservable(SpringContextUtils.class)
					.addObserver((Observer) new Observer() {
						@Override
						public void update(Observable o, Object arg) {
							o.deleteObserver(this);
							Properties beanProps = new Properties();
							loadBeanProps(extConfigBeans, beanProps);
							loadProperties(beanProps);
						}
					});
		}
	}*/

	private static void loadPropFile(String configFile, Properties props) {
		Assert.notNull((Object) configFile, "configFile不能为空！");
		Assert.notNull((Object) props, "props不能为空！");
		try {
			Resource resource = ConfigUtils.DEFAULT_RESOURCE_LOADER.getResource(configFile);
			if (null == resource || !resource.exists()) {
				ConfigUtils.logger.warn("扩展配置文件不存在：{}，直接忽略", (Object) configFile);
				return;
			}
			if (null != ConfigUtils.lastUpdateDate && resource.lastModified() <= ConfigUtils.lastUpdateDate.getTime()) {
				return;
			}
			PropertiesLoaderUtils.fillProperties(props,
					new EncodedResource(resource, ConfigEnum.DEFAULT_CONFIG_FILE_ENCODING));
			ConfigUtils.logger.debug("成功加载扩展配置文件：{}", (Object) configFile);
		} catch (IOException e) {
			ConfigUtils.logger.warn("加载启动配置文件失败：com/krm/web/codegen/codegen_config.properties", (Throwable) e);
			return;
		}
		if (!ConfigUtils.LOADED_PROPERTY_FILE_LIST.contains(configFile)) {
			ConfigUtils.LOADED_PROPERTY_FILE_LIST.add(configFile);
		}
	}

	private static synchronized void loadProperties(Properties properties) {
		Assert.notNull((Object) properties, "properties不能为空！");
		for (String key : properties.stringPropertyNames()) {
			String oldVal = ConfigUtils.CONFIG_CHACHE.getProperty(key);
			String newVal = properties.getProperty(key);
			if (null == oldVal || !oldVal.equals(newVal)) {
				onConfigItemChange(key, oldVal, newVal);
				ConfigUtils.CONFIG_CHACHE.put(key, newVal);
			}
		}
	}

	private static void onConfigItemChange(String key, String oldVal, String newVal) {
		if (ConfigUtils.logger.isDebugEnabled()) {
			ConfigUtils.logger.debug("配置项变更: {}, 旧值: {}, 新值: {}", new Object[] { key, oldVal, newVal });
		}
		Set<ConfigListener> configListeners = getConfigListenersByKey(key, false);
		if (null != configListeners) {
			for (ConfigListener configListener : configListeners) {
				try {
					configListener.onChange(key, oldVal, newVal);
				} catch (Exception e) {
					if (!ConfigUtils.logger.isWarnEnabled()) {
						continue;
					}
					ConfigUtils.logger.warn("配置项" + key + "变更，监听接口" + configListener.getClass() + "处理异常",
							(Throwable) e);
				}
			}
		}
	}

	public static void registerConfigListener(String key, ConfigListener listener) {
		synchronized (ConfigUtils.CONFIGLISTENERS) {
			String[] keys = getKeys(key);
			if (null == keys) {
				getConfigListenersByKey(null, true).add(listener);
			} else {
				for (String s : keys) {
					getConfigListenersByKey(s, true).add(listener);
				}
			}
		}
	}

	public static void unRegisterConfigListener(String key, ConfigListener listener) {
		synchronized (ConfigUtils.CONFIGLISTENERS) {
			String[] keys = getKeys(key);
			if (null == keys) {
				Set<ConfigListener> configListeners = getConfigListenersByKey(null, false);
				if (null != configListeners) {
					configListeners.remove(listener);
				}
			} else {
				for (String k : keys) {
					Set<ConfigListener> configListeners2 = getConfigListenersByKey(k, false);
					if (null != configListeners2) {
						configListeners2.remove(listener);
					}
				}
			}
		}
	}

	private static Set<ConfigListener> getConfigListenersByKey(String key, boolean autoCreate) {
		synchronized (ConfigUtils.CONFIGLISTENERS) {
			Set<ConfigListener> configListeners = ConfigUtils.CONFIGLISTENERS.get(key);
			if (null == configListeners && autoCreate) {
				configListeners = new LinkedHashSet<ConfigListener>();
				ConfigUtils.CONFIGLISTENERS.put(key, configListeners);
			}
			return configListeners;
		}
	}

	private static String[] getKeys(String key) {
		if (null == key) {
			return null;
		}
		return key.trim().split(";");
	}

	public static <T> T getValue(String key, Class<? extends Object> class1, T defaultVal) {
		Assert.notNull(key, "key不能为空");
		Assert.notNull(class1, "classType不能为空");
		String strVal = ConfigUtils.CONFIG_CHACHE.getProperty(key);
		if (null == strVal || strVal.isEmpty()) {
			return defaultVal;
		}
		if (CharSequence.class.isAssignableFrom(class1)) {
			return (T) strVal;
		}
		return (T) ConvertUtils.convert(strVal, class1);
	}

	public static <T> T getValue(String key, T defaultVal) {
		Assert.notNull((Object) defaultVal, "defaultVal不能为空");
		return getValue(key, defaultVal.getClass(), defaultVal);
	}

	public static String getValue(String key, String defaultVal) {
		return getValue(key, String.class, defaultVal);
	}

	public static String getValue(String key) {
		return getValue(key, (String) null);
	}

	public static String fillConfigs(String str) {
		if (StringUtils.isBlank((CharSequence) str)) {
			return str;
		}
		boolean in = false;
		StringBuilder sb = null;
		for (int i = 0; i < str.length(); ++i) {
			char c = str.charAt(i);
			if (!in) {
				if ('$' == c) {
					in = true;
					if (null == sb) {
						sb = new StringBuilder(str.substring(0, i));
					}
				} else if (null != sb) {
					sb.append(c);
				}
			} else {
				in = false;
				if ('$' == c) {
					sb.append(c);
				} else if ('{' == c) {
					int end;
					for (end = i + 1; end < str.length() && '}' != str.charAt(end); ++end) {
					}
					String varName = str.substring(i + 1, end);
					String value = getValue(varName);
					if (null == value) {
						sb.append('$').append('{').append(varName).append('}');
					} else {
						sb.append(value);
					}
					i = end;
				} else {
					sb.append('$');
					sb.append(c);
				}
			}
		}
		return (null == sb) ? str : sb.toString();
	}

	public static Set<String> keySetStartWith(String prefix) {
		if (StringUtils.isBlank((CharSequence) prefix)) {
			prefix = null;
		}
		Set<String> keys = new LinkedHashSet<String>();
		for (Object key : ConfigUtils.CONFIG_CHACHE.keySet()) {
			if (!(key instanceof String)) {
				continue;
			}
			if (null != prefix && !((String) key).startsWith(prefix)) {
				continue;
			}
			keys.add((String) key);
		}
		return keys;
	}

	/**
     * 得到工程目录
     * @return
     */
    public static File getProjectPath() {
    	File file = null;
		String path = getValue("projectPath");
		try {
			file = new File(path, getValue("sourceFolder"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		Assert.notNull(file, "不能自动定位到源码目录，请手工设置工程源码目录位置：projectPath");
		return file;
    }

    /**
     * class名转文件路径格式
     * @param path
     * @return
     */
    private static String replacePath(String path) {
        return StringUtils.replace(path, ".", "/");
    }

    /**
     * 得到模板位置
     * @return
     */
    public static File getTplPath() {
    	String tplPath = getValue("templatePath");
        File file = new File(tplPath);
        return file;
    }

    /**
     * 获得Java基本路径
     * @return
     */
    public static File getJavaBasePath() {
        String tplPath = getValue("basePackageName");
        return new File(getProjectPath(), replacePath(tplPath));
    }

    /**
     * 获得Sql Mapper基本路径
     * @return
     */
    public static File getMapperBasePath() {
        String tplPath = getValue("sqlmapperPath");
        return new File(getProjectPath() + "", replacePath(tplPath));
    }

    /**
     * 获得Sql Mapper基本路径
     * @return
     */
    public static File getMapperDB2Path() {
        String tplPath = getValue("sqlmapperDB2Path");
        return new File(getProjectPath() + "", replacePath(tplPath));
    }

    /**
     * 获得Sql Mapper基本路径
     * @return
     */
    public static File getMapperMysqlPath() {
        String tplPath = getValue("sqlmapperMysqlPath");
        return new File(getProjectPath() + "", replacePath(tplPath));
    }
    /**
     * 获得页面基本路径
     * @return
     */
    public static File getViewBasePath() {
    	String projectPath = getValue("projectPath");
    	String htmlPath = getValue("htmlPath");
        return new File(projectPath + htmlPath);
    }

    /**
     * 获得包名
     * @return
     */
    public static String getPackageName() {
    	return getValue("basePackageName");
    }
    
    /**
     * 获得数据库名
     * @return
     */
    public static String getTableSchema() {
        return getValue("tableSchema");
    }

    /**
     * 获得表匹配模式
     * @return
     */
    public static String getTablePattern() {
        return getValue("tablePattern").toUpperCase();
    }
    /**
     * 获得模块名称
     * @return
     */
    public static String getModuleName() {
    	return getValue("moduleName").toUpperCase();
    }
    
	static {
		DEFAULT_RESOURCE_LOADER = new DefaultResourceLoader();
		logger = LoggerFactory.getLogger(ConfigUtils.class);
		CONFIG_CHACHE = new Properties();
		LOADED_PROPERTY_FILE_LIST = new ArrayList<String>();
		CONFIGLISTENERS = new HashMap<String, Set<ConfigListener>>();
		ConfigUtils.lastUpdateDate = null;
		ConfigUtils.beanLastUpdateDate = null;
		ConfigUtils.hasInit = false;
		init();
	}
}