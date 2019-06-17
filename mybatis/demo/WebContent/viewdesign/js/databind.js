$('#databind').click(function() {
	
	var databindProperties = $('#databindProperties');
	databindProperties.html('');
	
	// 获取当前选中的视图
	var viewSelected = $('.fd-view-selected');
	// 如果不是图表不做处理 FIXME 这里的判断可能不合适
	if (!viewSelected.attr('_echarts_instance_')) {
		return;
	}
	
	// 设备类型
	var dataTypeDiv = formGroupDiv('数据类型'); 
	var dataTypeSelect = $('<select name="dataType" class="form-control" ></select>');
	dataTypeSelect.append('<option></option>');
	// 循环添加数据类型
	for (var type in chartdata.types) {
		alert(type);
		dataTypeSelect.append('<option value="' + type + '">' + chartdata.types[type].name + '</option>');
	}
	dataTypeDiv.append(dataTypeSelect);
	databindProperties.append(dataTypeDiv);
	
	// 设备类型选择
	dataTypeSelect.change(function() {
		// 清空之后之前的条件 FIXME 图表上的属性
		$(this).parent().siblings().remove();
		var dataTypeField = $(this).val();
		// 添加数据绑定需要的条件
		var dataType = chartdata.types[dataTypeField];
		//if (!dataType) return;
		alert(dataType);
		var params = dataType.params;
		for (var fieldName in params) {
			var param = params[fieldName];
			if (param.type == 'select') {
				databindProperties.append(generateSelect(fieldName, param, dataType));
			} else {
				databindProperties.append(generateInput(fieldName, param, dataType));
			}
		}
	});
	setInputValue(dataTypeSelect);
});

// 参数值变化记录到对应的图表DOM上
$(document).on("change","#databindProperties input,select",function(){
	var viewSelected = $('.fd-view-selected');
	var input = $(this);
	viewSelected.attr('databind-' + input.attr('name'), input.val());
	if (input[0].tagName.toLowerCase() == 'select') {
		var texts = [];
		input.find("option:selected").text(function(index, text){
			texts.push(text);
	    });
		viewSelected.attr('databind-' + input.attr('name') + '-name', texts.join(','));
	}
}); 

function formGroupDiv(name) {
	return $('<div class="form-group"><label><i class="fa fa-puzzle-piece"></i>' + name + '</label></div>');
}

function withformGroupDiv(dom, name) {
	var div = formGroupDiv(name);
	div.append(dom);
	return div;
}

/**
 * 生成select控件
 * @param fieldName 控件的参数名称
 * @param param 控件对应的参数
 * @param dataType 控件参数所属的设备类型
 * @returns
 */
function generateSelect(fieldName, param, dataType) {
	var select = $('<select id="' + fieldName + '" name="' + fieldName + '" class="form-control"></select>');
	// FIXME 多选临时处理
	if (param.multiple) {
		select.removeClass('form-control');
		select.attr('multiple', 'multiple');
	}
	// 添加option
	addSelectOptions(select, param.url, param);
	// 需要改变其他下拉框的值
	if (param.changeParam) {
		select.change(function() {
			var $this = $(this);
			var changeSelect = $('#' + param.changeParam);
			changeSelect.html('');
			var cParam = dataType.params[param.changeParam];
			var url = cParam.url + '&' + $this.attr('id') + '=' + $this.val();
			addSelectOptions(changeSelect, url, cParam);
		});
	}
	return withformGroupDiv(select, param.name);
}

function generateInput(fieldName, param, dataType) {
	var input = $('<input type="text" id="' + fieldName + '" name="' + fieldName + '" class="form-control">');
	// FIXME 多选临时处理
	
	// 设置值
	//addSelectOptions(select, param.url, param);
	
	return withformGroupDiv(input, param.name);
}

/**
 * 从当前选中的图表获取之前设置的参数值
 * @param input
 */
function setInputValue(input) {
	var viewSelected = $('.fd-view-selected');
	var value = viewSelected.attr('databind-' + input.attr('name'));
	if (value) {
		input.val(value);
	}
	input.trigger('change');
}

/**
 * 添加选项
 * @param select 控件
 * @param url 获取option的地址
 * @param param 控件对应的参数
 */
function addSelectOptions(select, url, param) {
	// 需要请求option
	if (url) {
		$.get(url, function(data) {
			data = data.list ? data.list : data;
			// 获取value字段的名称
			var valueField = param.valueField ? param.valueField : 'id';
			select.append('<option></option>');
			for (var i = 0; i < data.length; i++) {
				select.append('<option value="' + data[i][valueField] + '">' + data[i].name + '</option>');
			}
			setInputValue(select);
		});
	}
	// 需参数中设置了option
	if (param.options) {
		select.append('<option></option>');
		for (var i = 0; i < param.options.length; i++) {
			select.append('<option value="' + param.options[i].value + '">' + param.options[i].name + '</option>');
		}
		setInputValue(select);
	}
}