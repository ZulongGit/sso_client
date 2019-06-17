package com.krm.web.codegen.parse;

import java.util.List;

import com.krm.web.codegen.model.CodeGenModel;

/**
 * 模板解析接口
 * @author Parker
 *
 */
public interface IParse {

    static final String GEN_PARAMS = "p";
    static final String TABLE_CONFIG = "t";
    static final String FIELD_CONFIG = "f";

    /**
     * 解析模板 生成文件
     */
    public List<String> parse(CodeGenModel model);

}
