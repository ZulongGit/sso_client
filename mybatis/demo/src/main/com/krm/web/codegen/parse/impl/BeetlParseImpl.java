package com.krm.web.codegen.parse.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.krm.web.codegen.model.CodeGenModel;
import com.krm.web.codegen.parse.IParse;

/**
 * 解析模板,生产代码
 * @author Parker
 *
 */
public class BeetlParseImpl implements IParse {

    private static final Logger LOGGER = LoggerFactory.getLogger(BeetlParseImpl.class);

    private static final StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();

    private static Configuration cfg;
    private static GroupTemplate gt;

    static {
    	try {
            cfg = Configuration.defaultConfiguration();
            Map<String,String> fn = cfg.getFnPkgMap();
            fn.put("stringutil", "com.krm.common.beetl.function.StrutilFunction");
            fn.put("shiro", "com.krm.common.beetl.function.ShiroFunction");
            fn.put("num", "com.krm.common.beetl.function.AutoNoGenFunction");
            fn.put("dict", "com.krm.common.beetl.function.DictFunction");
            cfg.setFnPkgMap(fn);
            cfg.setStatementStart("@");
            cfg.setStatementEnd(null);
            cfg.setErrorHandlerClass("org.beetl.core.ReThrowConsoleErrorHandler");
            gt = new GroupTemplate(resourceLoader, cfg);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public List<String> parse(CodeGenModel model) {
    	List<String> renderList = new ArrayList<String>();
        Template t;
        for (String file : model.getFiles()) {
            t = gt.getTemplate(file);
            t.binding(IParse.GEN_PARAMS, model.getParams());
            t.binding(IParse.TABLE_CONFIG, model.getTableConfig());
            t.binding(IParse.FIELD_CONFIG, model.getFieldConfigs());
            renderList.add(t.render());
        }
        return renderList;
    }

}
