$(function(){
	$.ajaxSetup({ cache: false });
	addRequireFlag();
	$("input[data-defVal]").each(function() {
		$(this).val($(this).attr("data-defVal"));
	});
})

/**
 * 重置查询表单参数
 * @param formId
 * @param opts
 */
function resetValue(formId, opts){
	var defaults = {
		params : $("#"+formId).serializeArray()
	};
	var params;
	if(opts == undefined){
		params = defaults.params;
	}else{
		params = $.merge(defaults.params, opts.params);
	}
    for( x in params){  
        $("#"+params[x].name).val('');
        $("#"+params[x].name).select2("val", ["请选择",""]);
    }  
}

/**
 * 返回方法
 * @param url
 */
function goBack(url){
    window.location.href=url;
}

/**
 * 跳转贷款产品页面
 * @param url
 */
function goBackcp(url){
	window.parent.frames.location.href=url;
}

/**
 * 给require="true"的input输入框后面加上*号标识
 */
function addRequireFlag(){
	$("input[required='true']").parents(".control-group").find(".control-label").css("color", "red");

	var labelTextLength=$(".searchForm >.container-fluid:first").find("label").first().text().length;
	if(labelTextLength!=null) {
		if(labelTextLength==5){
			$("#searchDiv").css("margin-left","48px");
		}else if(labelTextLength==4){
			$("#searchDiv").css("margin-left","60px");
		}else if(labelTextLength==3){
			$("#searchDiv").css("margin-left","70px");
		}else if(labelTextLength==6){
			$("#searchDiv").css("margin-left","32px");
		}else if(labelTextLength==7){
			$("#searchDiv").css("margin-left","41px");
		}
	}

	var form_controls = $(".searchForm").find(".control-group");
	for(var i=0;i<form_controls.length;i++) {
		var labelLength =$(form_controls[i]).find("label:first").text().length;
		if(labelLength!=null){
			if(labelLength==7){
				$(form_controls[i]).find("label:first").css("width","100px");
				$(form_controls[i]).find(".controls:first").css("margin-left","110px");
			}
		}
	}
}

if (!Array.prototype.indexOf)
{
	Array.prototype.indexOf = function(elt /*, from*/)
	{
		var len = this.length >>> 0;

		var from = Number(arguments[1]) || 0;
		from = (from < 0)
			? Math.ceil(from)
			: Math.floor(from);
		if (from < 0)
			from += len;

		for (; from < len; from++)
		{
			if (from in this &&
				this[from] === elt)
				return from;
		}
		return -1;
	};
}