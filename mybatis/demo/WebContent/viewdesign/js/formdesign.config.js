fd.config = {
    viewPanel: ".views", //视图组件面板
    propertiesPanel: ".properties", //属性面板
    containerPanel: ".containers",
    viewTag: "fd-view", //组件标示
    viewDragTag: ".fd-view-drag", //组件可拖曳标示
    viewDragInTag: ".fd-view-drag-in",
    viewCurrentTag: ".fd-view-current", //当前视图标示
    viewSelectedClass: "fd-view-selected",
    propertiesTag: "fd-property", //属性组件标示
    layoutTag: "fd-layout", //布局标示
    labelTag: "fd-label", //标签
    moveTag: "fd-move", //需要出现移动标签的视图标记
    supportType: "input,button,select,textarea", //属性支持类型
    view: { //视图组件
        /*=========布局控件============*/
        formtitle: {
            id: "v_formtitle",
            name: "标题",
            properties: ["text", "id", "style", "$class", "css_align", "css_padding", "css_margin", "editable"]
        },
        table: {
            id: "v_table",
            name: "表格",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        table_tr: {
            id: "v_table_tr",
            name: "表格行",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        table_th: {
            id: "v_table_th",
            name: "表格头",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        table_td: {
            id: "v_table_td",
            name: "表格列",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        col12: {
            id: "v_col12",
            name: "col-12",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        col66: {
            id: "v_col66",
            name: "col-6-6",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        col444: {
            id: "v_col444",
            name: "col-4-4-4",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        col3333: {
            id: "v_col3333",
            name: "col-3-3-3-3",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        col48: {
            id: "v_col48",
            name: "col-4-8",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        col39: {
            id: "v_col39",
            name: "col-3-9",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        col84: {
            id: "v_col84",
            name: "col-8-4",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        col93: {
            id: "v_col93",
            name: "col-9-3",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin", "editable"]
        },
        collapsePanel: {
            id: "v_collapsePanel",
            name: "折叠面板",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin"],
            events: {
                init: function(view) {
                    view.find(".collapse_handler").bind("click", function() {
                        if ($(this).attr("collapsed")) { //如果是已折叠
                            view.find(".collapse_content").removeClass("hide");
                            $(this).removeAttr("collapsed");
                            $(this).find("i").attr("class", "fa fa-chevron-circle-up");
                        } else {
                            view.find(".collapse_content").addClass("hide");
                            $(this).attr("collapsed", true);
                            $(this).find("i").attr("class", "fa fa-chevron-circle-down");
                        }
                    });
                }
            }
        },
        dynamicPanel: {
            id: "v_dynamicPanel",
            name: "动态面板",
            properties: ["style", "$class", "css_align", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin"],
            events: {
                init: function(view) {
                    view.find(".dynamic_handler").bind("click", function() {
                        var content = view.find(".dynamic_content");
                        var item = content.find(".item:eq(0)").clone();
                        item.find(".dynamic_closer").removeClass("hide");
                        item.find(".dynamic_closer").bind("click", function() {
                            $(this).parents(".item").remove();
                        });
                        var views = item.find("[" + fd.config.viewTag + "]");
                        views.val("");

                        content.append(item);
                    });
                    view.find(".dynamic_closer").bind("click", function() {
                        $(this).parents(".item").remove();
                    });
                }
            }
        },
        line: {
            id: "v_line",
            name: "横向线条",
            properties: ["style", "$class", "css_width", "css_height"]
        },
        br: {
            id: "v_br",
            name: "换行符",
            properties: []
        },
        /*=========基本控件============*/
        label: {
            id: "v_label",
            name: "标签",
            properties: ["text", "id", "style", "$class", "css_width", "css_height", "css_fontColor", "css_backgroundColor", "css_padding", "css_margin"]
        },
        input: {
            id: "v_input",
            name: "文本输入框",
            properties: ["field", "id", "style", "$class", "placeholder", "type", "rules", "required", "css_align", "css_width", "css_height", "css_fontColor", "css_backgroundColor", "readonly"]
        },
		
        password: {
            id: "v_password",
            name: "密码输入框",
            properties: ["field", "id", "style", "$class", "placeholder", "type", "rules", "required", "css_width", "css_height", "readonly"]
        },
        textarea: {
            id: "v_textarea",
            name: "多行文本框",
            properties: ["field", "id", "style", "$class", "placeholder", "required", "css_width", "css_height", "css_backgroundColor", "readonly"]
        },
        select: {
            id: "v_select",
            name: "单选下拉",
            properties: ["field", "id", "style", "$class", "required", "datalist", "remote", "css_width", "css_height", "readonly"]
        },
        mult_select: {
            id: "v_mult_select",
            name: "多选按钮",
            properties: ["field", "id", "style", "$class", "required", "datalist", "remote", "css_width", "css_height", "readonly"]
        },
        radio: {
            id: "v_radio",
            name: "单选按钮",
            properties: ["field", "id", "name", "style", "$class", "label_text", "checked"]
        },
        checkbox: {
            id: "v_checkbox",
            name: "复选按钮",
            properties: ["field", "id", "name", "style", "$class", "label_text", "checked"]
        },
        fileupload: {
            id: "v_fileupload",
            name: "文件上传",
            properties: ["field", "id", "style", "$class", "css_width", "css_height"]
        },
        image: {
            id: "v_image",
            name: "图片",
            properties: ["style", "$class", "url", "css_width", "css_height", "css_padding", "css_margin"]
        },
        datepicker: {
            id: "v_datepicker",
            name: "日期选择",
            properties: ["field", "id", "style", "$class", "placeholder", "required", "css_width", "css_height", "dateFormat", "readonly"],
            events: {
                init: function(view) {
                    if (typeof($(document).datepicker) != "function") {
                        $('#v_datepicker').datepicker();
                        //ns.requireJS("../date.js");
                    } else {
                        ns.form.initDatePicker();
                    }
                }
            }
        },
        button: {
            id: "v_button",
            name: "普通按钮",
            properties: ["name", "text", "style", "$class", "css_width", "css_height", "css_padding", "css_margin", "script"]
        },
        submit: {
            id: "v_submit",
            name: "提交按钮",
            properties: ["name", "text", "style", "$class", "css_width", "css_height", "css_padding", "css_margin"]
        },

		 /*=========卡片============*/
		cardva:{
			id:"v_cardva",
			name:"基本卡片",
			properties: ["style" ,"$class","css_bgstyle", "css_align","css_fontColor", "css_width", "css_height", "css_backgroundColor", "css_padding", "css_margin"]
			},
        /*=========组合控件============*/
        label_input: {
            id: "v_label_input",
            name: "标签+文本框",
            properties: ["style", "$class", "css_width", "css_height", "label_text", "css_align", "editable"]
        },
        label_password: {
            id: "v_label_password",
            name: "标签+密码框",
            properties: ["style", "$class", "css_width", "css_height", "label_text", "css_align", "editable"]
        },
        label_textarea: {
            id: "v_label_textarea",
            name: "标签+多行文本",
            properties: ["style", "$class", "css_width", "css_height", "label_text", "css_align", "editable"]
        },
        label_select: {
            id: "v_label_select",
            name: "标签+单选下拉",
            properties: ["style", "$class", "css_width", "css_height", "label_text", "css_align", "editable"]
        },
        radio_group: {
            id: "v_radio_group",
            name: "单选按钮组",
            properties: ["style", "$class", "number", "css_align", "css_padding", "css_margin", "editable"],
            events: {
                init: function(view) {
                    if (!fd.run) { //运行模式下，不执行此函数
                        var num = $(fd.config.containerPanel + " [fd-view='radio_group']").length;
                        view.find("input").attr("name", "radio" + num);
                    }
                }
            }
        },
        checkbox_group: {
            id: "v_checkbox_group",
            name: "复选按钮组",
            properties: ["style", "$class", "number", "css_align", "css_padding", "css_margin", "editable"],
            events: {
                init: function(view) {
                    if (!fd.run) { //运行模式下，不执行此函数
                        var num = $(fd.config.containerPanel + " [fd-view='checkbox_group']").length;
                        view.find("input").attr("name", "checkbox" + num);
                    }
                }
            }
        },
        datepickerrange: {
            id: "v_daterange",
            name: "日期区间控件",
            properties: ["style", "$class", "css_width", "css_height", "editable"],
            events: {
                init: function(view) {
                    if (fd.run) { //只在运行模式下执行此函数
                        view.find("input:first").bind("change", function() {
                            view.find("input:last").focus();
                        });
                        view.find("input:last").bind("change", function() {
                            fd.config.view.datepickerrange.events.validate(view);
                        });
                    }
                },
                validate: function(view) { //验证
                    var start = view.find("input:first");
                    var end = view.find("input:last");
                    if (new Date(start.val()).getTime() > new Date(end.val()).getTime()) {
                        start.tooltip({ title: "开始时间不可大于结束时间", placement: fd.run.tooltip.placement });
                        start.tooltip("show");
                        start.parent().removeClass("has-success").addClass("has-error");
                        //start.focus();
                        //end.datepicker("hide");
                        return false;
                    } else {
                        start.parent().removeClass("has-error").addClass("has-success");
                        start.tooltip("destroy");
                        return true;
                    }
                }
            }
        },
        moneyrange: {
            id: "v_moneyrange",
            name: "金额区间控件",
            properties: ["style", "$class", "css_width", "css_height", "editable"],
            events: {
                init: function(view) {
                    if (fd.run) { //只在运行模式下执行此函数
                        //自动格式化金额
                        view.find("input").bind("blur", function() {
                            if (jQuery.trim(this.value).length > 0 && fd.run.validator.money.regex.test(this.value)) {
                                this.value = parseFloat(this.value).toFixed(2);
                            }
                        });
                        view.find("input:first").bind("change", function() {
                            view.find("input:last").focus();
                        });
                        view.find("input:last").bind("change", function() {
                            fd.config.view.moneyrange.events.validate(view);
                        });
                    }
                },
                validate: function(view) { //验证方法
                    var start = view.find("input:first");
                    var end = view.find("input:last").val();
                    if (parseFloat(start.val()) > parseFloat(end)) {
                        start.tooltip({ title: "起始金额不可大于截至金额", placement: fd.run.tooltip.placement });
                        start.tooltip("show");
                        start.parent().removeClass("has-success").addClass("has-error");
                        start.focus();
                        return false;
                    } else {
                        start.parent().removeClass("has-error").addClass("has-success");
                        start.tooltip("destroy");
                        return true;
                    }
                }
            }
        },
        dynamictable: {
            id: "v_moneyrange",
            name: "动态下拉表格",
            properties: ["style", "$class", "css_width", "css_height", "editable", "datasource"],
            events: {
                init: function(view) {
                    if (fd.run) {
                        if (view.attr("datasource")) {
                            view.find("button").bind("click", function() {
                                ns.view.showModal(ns.getBasePath() + "/bi/form/ds/" + view.attr("datasource"));
                            });
                        }
                    }
                }
            }
        },
        /*=========图表控件============*/
        charts: {
            id: "main"
        },
        /*=========扩展控件============*/
        macros: {
            id: "v_macro",
            name: "宏控件",
            properties: ["style", "$class", "css_width", "css_height", "macros", "css_padding", "css_margin"]
        },
        variable: {
            id: "v_variable",
            name: "流程变量",
            properties: ["style", "css_width", "css_height", "css_padding", "css_margin"]
        },
        list: {
            id: "v_list",
            name: "列表控件",
            properties: ["style", "css_width", "css_height", "css_padding", "css_margin"]
        },
        alert: {
            id: "v_alert",
            name: "提示控件",
            properties: ["style", "css_width", "css_height", "css_padding", "css_margin"]
        }
    },
    properties: { //属性组件
        field: {
            id: "p_field",
            name: "数据库字段",
            events: {
                init: function(opt) {
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.field) {
                        control.val(opt.data.field);
                        control.select2("val", opt.data.field);
                    } else {
                        control.val(opt.view.attr("field"));
                        control.select2("val", opt.view.attr("field"));

                    }
                },
                onchange: function(opt) {
                    opt.view.attr("field", opt.control.val());
                    opt.data.field = opt.control.val();
                }
            }
        },
        id: {
            id: "p_id",
            name: "ID",
            events: {
                init: function(opt) {
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.name)
                        control.val(opt.data.id);
                    else
                        control.val(opt.view.attr("id"));
                },
                onblur: function(opt) {
                    opt.view.attr("id", opt.control.val());
                    //设置data属性
                    opt.data.id = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.attr("id", opt.control.val());
                }
            }
        },
        name: {
            id: "p_name",
            name: "名称",
            events: {
                init: function(opt) { //初始化属性面板方法
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.name)
                        control.val(opt.data.name);
                    else
                        control.val(opt.view.attr("name"));
                },
                onblur: function(opt) {
                    opt.view.attr("name", opt.control.val());
                    //设置data属性
                    opt.data.name = opt.control.val();
                },
                onkeyup: function(opt) {
                    //opt.view.attr("name", opt.control.val());
                }
            }
        },
        value: {
            id: "p_value",
            name: "值",
            events: {
                init: function(opt) { //初始化属性面板方法
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.value)
                        control.val(opt.data.value);
                    else
                        control.val("");
                },
                onblur: function(opt) {
                    opt.view.attr("value", opt.control.val());
                    //设置data属性
                    opt.data.value = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.attr("value", opt.control.val());
                }
            }
        },
        text: {
            id: "p_text",
            name: "显示文本",
            events: {
                init: function(opt) { //初始化属性面板方法
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.text)
                        control.val(opt.data.text);
                    else
                        control.val(opt.view.text());
                },
                onblur: function(opt) {
                    opt.view.text(opt.control.val());
                    //设置data属性
                    opt.data.text = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.text(opt.control.val());
                }
            }
        },
        label_text: {
            id: "p_label_text",
            name: "组合控件文本标签",
            events: {
                init: function(opt) { //初始化属性面板方法
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.label_text)
                        control.val(opt.data.text);
                    else
                        control.val(opt.view.find("[" + fd.config.labelTag + "]").text());
                },
                onblur: function(opt) {
                    opt.view.find("[" + fd.config.labelTag + "]").text(opt.control.val());
                    //设置data属性
                    opt.data.label_text = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.find("[" + fd.config.labelTag + "]").text(opt.control.val());
                }
            }
        },
        placeholder: {
            id: "p_placeholder",
            name: "提示文本",
            events: {
                init: function(opt) { //初始化属性面板方法
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.placeholder)
                        control.val(opt.data.placeholder);
                    else
                        control.val(opt.view.attr("placeholder"));
                },
                onblur: function(opt) {
                    opt.view.attr("placeholder", opt.control.val());
                    //设置data属性
                    opt.data.placeholder = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.attr("placeholder", opt.control.val());
                }
            }
        },
        url: {
            id: "p_url",
            name: "URL地址",
            events: {
                init: function(opt) {
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.src)
                        control.val(opt.data.src);
                    else
                        control.val(opt.view.attr("src"));
                },
                onblur: function(opt) {
                    opt.view.attr("src", opt.control.val());
                    //设置data属性
                    opt.data.src = opt.control.val();
                }
            }
        },
        number: {
            id: "p_number",
            name: "数量",
            events: {
                init: function(opt) {
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.number)
                        control.val(opt.data.number);
                    else
                        control.val(opt.view.children().length);
                },
                onblur: function(opt) {
                    var num = parseInt(opt.control.val());
                    if (num <= 0) {
                        alert("数量不能小于或等于0！");
                        return opt.control.val(opt.data.number);
                    }
                    var first = opt.view.find(":first").clone(true);
                    opt.view.children().remove();
                    for (var i = 0; i < num; i++) {
                        opt.view.append(first.clone(true));
                    }
                    //opt.view.attr("n", opt.control.val());
                    //设置data属性
                    opt.data.number = opt.control.val();
                }
            }
        },
        type: {
            id: "p_type",
            name: "类型",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.type)
                        opt.control.find(fd.config.supportType).val(opt.data.type);
                    else
                        opt.control.find(fd.config.supportType).val(opt.view.attr("type"));
                },
                onchange: function(opt) {
                    opt.view.attr("type", opt.control.val());
                    opt.data.type = opt.control.val();
                }
            }
        },
        dateFormat: {
            id: "p_dateFormat",
            name: "日期格式",
            events: {
                init: function(opt) {
                    if (opt.data.dateFormat)
                        opt.control.find(fd.config.supportType).val(opt.data.dateFormat);
                    else
                        opt.control.find(fd.config.supportType).val(opt.view.attr("data-date-format"));
                },
                onchange: function(opt) {
                    var view = opt.view.clone();
                    opt.view.addClass("hide");
                    view.attr("data-date-format", opt.control.val());
                    opt.data.dateFormat = opt.control.val();
                    //重新初始化日期控件
                    opt.view.after(view);
                    view.datepicker();
                    view.datepicker("update", view.val()); //更新时间显示
                    fd.design.bindViewEvent(view); //重新绑定事件
                    opt.view.remove(); //删除原有的
                    view.focus();
                }
            }
        },
        maxlength: {
            id: "p_maxlength",
            name: "最大长度",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.maxlength)
                        opt.control.find(fd.config.supportType).val(opt.data.maxlength);
                    else
                        opt.control.find(fd.config.supportType).val("");
                },
                onblur: function(opt) {
                    opt.view.attr("maxlength", opt.control.val());
                    opt.data.maxlength = opt.control.val();
                }
            }
        },
        readonly: {
            id: "p_readonly",
            name: "只读",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.readonly)
                        opt.control.find(fd.config.supportType).val(opt.data.readonly);
                    else
                        opt.control.find(fd.config.supportType).val("false");
                },
                onchange: function(opt) {
                    if (opt.control.val() == "true")
                        opt.view.attr("readonly", "readonly");
                    else
                        opt.view.removeAttr("readonly");
                    opt.data.readonly = opt.control.val();
                }
            }
        },
        checked: {
            id: "p_checked",
            name: "选中",
            events: {
                init: function(opt) { //初始化属性面板方法
                    var checked = opt.view.find("input").is(":checked") || "false";
                    opt.control.find(fd.config.supportType).val(checked.toString());
                },
                onchange: function(opt) {
                    if (opt.control.val() == "true") {
                        opt.view.find("input").prop("checked", true);
                    } else {
                        opt.view.find("input").prop("checked", false);
                    }
                }
            }
        },
        css_align: {
            id: "p_css_align",
            name: "对齐方式",
            events: {
                init: function(opt) { //初始化属性面板方法
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.css_align)
                        control.val(opt.data.css_align);
                    else
                        control.val("left");
                },
                onchange: function(opt) {
                    opt.view.css("textAlign", opt.control.val());
                    //设置data属性
                    opt.data.css_align = opt.control.val();
                }
            }
        },
		 css_bgstyle: {
            id: "p_css_bgstyle",
            name: "北京样式",
            events: {
                init: function(opt) { //初始化属性面板方法
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.css_bgstyle)
                        control.val(opt.data.css_bgstyle);
                    else
                        control.val("");
                },
                onchange: function(opt) {
                    opt.view.attr("class", opt.control.val());
                    //设置data属性
                    opt.data.css_bgstyle = opt.control.val();
                }
            }
        },
        css_fontColor: {
            id: "p_css_fontColor",
            name: "文字颜色",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.css_fontColor)
                        opt.control.find(fd.config.supportType).val(opt.data.css_fontColor);
                    else
                        opt.control.find(fd.config.supportType).val(fd.design.util.rgb2hex(opt.view.css("color")));
                },
                onblur: function(opt) {
                    opt.view.css("color", opt.control.val());
                    opt.data.css_fontColor = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.css("color", opt.control.val());
                }
            }
        },
        css_backgroundColor: {
            id: "p_css_backgroundColor",
            name: "文字颜色",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.css_backgroundColor)
                        opt.control.find(fd.config.supportType).val(opt.data.css_backgroundColor);
                    else
                        opt.control.find(fd.config.supportType).val(fd.design.util.rgb2hex(opt.view.css("backgroundColor")));
                },
                onblur: function(opt) {
                    opt.view.css("backgroundColor", opt.control.val());
                    opt.data.css_backgroundColor = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.css("backgroundColor", opt.control.val());
                }
            }
        },
        css_width: {
            id: "p_css_width",
            name: "宽度",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.css_width)
                        opt.control.find(fd.config.supportType).val(opt.data.css_width);
                    else
                        opt.control.find(fd.config.supportType).val(opt.view.css("width"));
                },
                onblur: function(opt) {
                    opt.view.css("width", opt.control.val());
                    opt.data.css_width = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.css("width", opt.control.val());
                }
            }
        },
        css_height: {
            id: "p_css_height",
            name: "高度",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.css_height)
                        opt.control.find(fd.config.supportType).val(opt.data.css_height);
                    else
                        opt.control.find(fd.config.supportType).val(opt.view.css("height"));
                },
                onblur: function(opt) {
                    opt.view.css("height", opt.control.val());
                    opt.data.css_height = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.css("height", opt.control.val());
                }
            }
        },
        css_padding: {
            id: "p_css_padding",
            name: "内填充",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.css_padding)
                        opt.control.find(fd.config.supportType).val(opt.data.css_padding);
                    else
                        opt.control.find(fd.config.supportType).val(opt.view.css("padding"));
                },
                onblur: function(opt) {
                    opt.view.css("padding", opt.control.val());
                    opt.data.css_padding = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.css("padding", opt.control.val());
                }
            }
        },
        css_margin: {
            id: "p_css_margin",
            name: "外填充",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.css_margin)
                        opt.control.find(fd.config.supportType).val(opt.data.css_margin);
                    else
                        opt.control.find(fd.config.supportType).val(opt.view.css("margin"));
                },
                onblur: function(opt) {
                    opt.view.css("margin", opt.control.val());
                    opt.data.css_margin = opt.control.val();
                },
                onkeyup: function(opt) {
                    opt.view.css("margin", opt.control.val());
                }
            }
        },
        $class: {
            id: "p_class",
            name: "CSS样式名",
            events: {
                init: function(opt) {
                    if (opt.data.$class)
                        opt.control.find(fd.config.supportType).val(opt.data.$class);
                    else
                        opt.control.find(fd.config.supportType).val(opt.view.attr("class"));
                },
                onblur: function(opt) {
                    opt.view.attr("class", opt.control.val());
                    opt.data.$class = opt.control.val();
                }
            }
        },
        style: {
            id: "p_style",
            name: "样式",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.style)
                        opt.control.find(fd.config.supportType).val(opt.data.style);
                    else
                        opt.control.find(fd.config.supportType).val(opt.view.attr("style"));
                },
                onblur: function(opt) {
                    opt.view.attr("style", opt.control.val());
                    opt.data.style = opt.control.val();
                }
            }
        },
        title: {
            id: "p_title",
            name: "样式",
            events: {
                init: function(opt) { //初始化属性面板方法
                    if (opt.data.title)
                        opt.control.find(fd.config.supportType).val(opt.data.title);
                    else
                        opt.control.find(fd.config.supportType).val(opt.view.attr("title"));
                },
                onblur: function(opt) {
                    opt.view.attr("title", opt.control.val());
                    opt.data.title = opt.control.val();
                }
            }
        },
        required: {
            id: "p_required",
            name: "必填",
            events: {
                init: function(opt) { //初始化属性面板方法
                    var control = opt.control.find(fd.config.supportType);
                    if (opt.data.required)
                        control.val(opt.data.required);
                    else
                        control.val("false");
                },
                onchange: function(opt) {
                    opt.view.attr("required", opt.control.val());
                    //设置data属性
                    opt.data.required = opt.control.val();
                }
            }
        },
        rules: {
            id: "p_rules",
            name: "验证规则",
            events: {
                init: function(opt) {
                    if (opt.data.rules)
                        opt.control.find("select").val(opt.data.rules);
                    else
                        opt.control.find("select").val(opt.view.attr("rules"));
                },
                onchange: function(opt) {
                    opt.data.rules = opt.control.val();
                    opt.view.attr("rules", opt.control.val());
                }
            }
        },
        datalist: {
            id: "p_datalist",
            name: "数据源设置",
            events: {
                init: function(opt) {

                },
                onclick: function(opt) {
                    var selecteds = fd.design.view.currentViews();
                    if (selecteds.length > 0) {
                        var modal = $("#listDataModal");
                        var obj = $(selecteds[0]);
                        modal.find("h4").text("下拉菜单");
                        var tbody = modal.find("table tbody");
                        tbody.html("");
                        //获取当前值并添加到表格中
                        obj.find("option").each(function() {
                            var tr = "<tr>";
                            tr += "<td><input type='text' value='" + $(this).val() + "'/></td>";
                            tr += "<td><input type='text' value='" + $(this).text() + "'/></td>";
                            if ($(this).attr("selected"))
                                tr += "<td><input type='radio' name='selected' checked='checked'/></td>";
                            else
                                tr += "<td><input type='radio' name='selected'/></td>";
                            tr += "<td><a href='javascript:;'><i class='fa fa-close'></i></a></td>";
                            tbody.append(tr);
                        });
                        tbody.find("a").bind("click", function() {
                            $(this).parents("tr").remove();
                        });
                        //添加一项按钮事件
                        modal.find("#btnAddItem").unbind("click");
                        modal.find("#btnAddItem").bind("click", function() {
                            var tr = "<tr>";
                            tr += "<td><input type='text' value=''/></td>";
                            tr += "<td><input type='text' value=''/></td>";
                            tr += "<td><a href='javascript:;'><i class='fa fa-close'></i></a></td>";
                            tr = $(tr);
                            tr.find("a").bind("click", function() {
                                $(this).parents("tr").remove();
                            });
                            tbody.append(tr);
                        });
                        //保存按钮事件
                        modal.find("#btnOk").unbind("click");
                        modal.find("#btnOk").bind("click", function() {
                            obj.html("");
                            modal.find("table tbody tr").each(function() {
                                var inputs = $(this).find("input");
                                if ($(inputs[2]).is(":checked")) {
                                    obj.append("<option value='" + inputs[0].value + "' selected='selected'>" + inputs[1].value + "</option>");
                                } else {
                                    obj.append("<option value='" + inputs[0].value + "'>" + inputs[1].value + "</option>");
                                }
                            });
                            modal.modal("hide");
                        });
                        modal.modal("show");
                    }
                }
            }
        },
        remote: {
            id: "p_remote",
            name: "远程数据",
            events: {
                init: function(opt) {
                    if (opt.data.remote)
                        opt.control.find("textarea").val(opt.data.remote);
                    else
                        opt.control.find("textarea").val(opt.view.attr("remote"));
                },
                onblur: function(opt) {
                    opt.view.attr("remote", opt.control.val());
                    opt.data.remote = opt.control.val();
                }
            }
        },
        script: {
            id: "p_script",
            name: "执行脚本",
            events: {
                init: function(opt) {
                    opt.control.find("textarea").val(opt.data.onclick);
                },
                onchange: function(opt) {
                    opt.view.attr("onmouseup", opt.control.val());
                    opt.data.onclick = opt.control.val();
                }
            }
        },
        macros: {
            id: "p_macros",
            name: "宏类型",
            events: {
                init: function(opt) {
                    opt.control.find("select").val(opt.view.attr("macro"));
                },
                onchange: function(opt) {
                    opt.view.attr("macro", opt.control.val());
                }
            }
        },
        table_header_show: {
            id: "p_table_header_show",
            name: "表头显示",
            events: {
                init: function(opt) {
                    opt.control.find("select").val(opt.view.attr("table_header_show"));
                },
                onchange: function(opt) {
                    opt.view.attr("table_header_show", opt.control.val());
                    opt.data.table_header_show = opt.control.val();
                }
            }
        },
        editable: {
            id: "p_editable",
            name: "可编辑",
            events: {
                init: function(opt) {
                    opt.control.find("select").val(opt.view.attr("editable"));
                },
                onchange: function(opt) {
                    opt.view.attr("editable", opt.control.val());
                    opt.data.editable = opt.control.val();
                }
            }
        },
        datasource: {
            id: "p_datasource",
            name: "数据源",
            events: {
                init: function(opt) {
                    opt.control.find("select").val(opt.view.attr("datasource"));
                },
                onchange: function(opt) {
                    opt.view.attr("datasource", opt.control.val());
                    opt.data.datasource = opt.control.val();
                }
            }
        }
    },
    layouts: { // 布局容器
        collapsible: {
            name: "折叠面板"
        },
        table: {
            name: "表格布局"
        },
        flowLayout: {
            name: "流式布局"
        },
        dynamicRow: {
            name: "动态添加添加行"
        }
    }
};