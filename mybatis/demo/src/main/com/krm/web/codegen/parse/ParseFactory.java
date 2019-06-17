package com.krm.web.codegen.parse;

import static com.krm.web.codegen.GenCoreConstant.BEETL;

import com.krm.web.codegen.parse.impl.BeetlParseImpl;

/**
 * 
 * @author Parker
 *
 */
public class ParseFactory {

    public static IParse getParse(String parseType) {
        if (BEETL.equalsIgnoreCase(parseType)) {
            return (IParse) new BeetlParseImpl();
        }
//        if (FREEMARKER.equalsIgnoreCase(parseType)) {
//            return new FreeMarkerParseImpl();
//        }
        throw new RuntimeException("模板类型不支持");
    }

}
