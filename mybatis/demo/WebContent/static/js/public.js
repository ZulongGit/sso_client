// 声明一个全局对象Namespace，用来注册命名空间
var Namespace = new Object();
// 全局对象仅仅存在register函数，参数为名称空间全路径，如"Grandsoft.GEA"
Namespace.register = function(fullNS) {
	// 将命名空间切成N部分, 比如Grandsoft、GEA等
	var nsArray = fullNS.split('.');
	var sEval = "";
	var sNS = "";
	for (var i = 0; i < nsArray.length; i++) {
		if (i != 0)
			sNS += ".";
		sNS += nsArray[i];
		// 依次创建构造命名空间对象（假如不存在的话）的语句
		// 比如先创建Grandsoft，然后创建Grandsoft.GEA，依次下去
		sEval += "if (typeof(" + sNS + ") == 'undefined') " + sNS
				+ " = new Object();"
	}
	if (sEval != "")
		eval(sEval);
};
Namespace.register("Angel");
var isIe8 = !(typeof (ie8) == "undefined");

var webHistory = Webit.history;

//解决 IE8 不支持console
window.console = window.console || (function () {
    var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile
    = c.clear = c.exception = c.trace = c.assert = function () { };
    return c;
})();

$(function() {
	//全屏按钮
	$(document).on("click.ace.widget", "a[data-action=fullscreen]", function (t) {
		t.preventDefault();
		var target = window.parent.$('.J_iframe[data-id="' + $curmenu.attr('href') + '"]');
		target.toggleClass('fullScreen');
		$.each($('table'),function(i, table){
			if($(table).attr('id') != undefined){
				try {
					$(table).bootstrapTable('resetView', {height: $(window).height() - $(".bootstrap-table").offset().top - 15});
				} catch (e) {
				}
			}
		})
	})

	/*
	 * $("html").niceScroll({autohidemode:false,zindex:1100,cursorwidth:"9px",
	 * cursorcolor:"rgb(243, 129, 149)",cursorborderradius:"0px"});
	 */
/*
	var aMenu = $("#sidebar-menu a[id]");
	var tab = $("#breadcrumb");
	 //init tab
	var uText = $("#sidebar-menu li")
			.find("[href='#" + webHistory.get() + "']").find(">span").text();
	
	if (webHistory.get() != null) {
		tab
				.append("<li class='active' data-toggle='context' data-target='#tab-menu' h='#"
						+ webHistory.get()
						+ "'>"
						+ uText
						+ "<i class='fa fa-times close'></i></li>");
	}
	 //menu点击
	aMenu.on("click", function() {
		var hash = webHistory.get(), href = $(this).attr("href");
		if (("#" + hash) == href) {
			webHistory.justShow("#");
			webHistory.go(hash);
		}
	});
	 //tab切换
	tab.on("click", "li", function() {
		webHistory.go($(this).attr("h"));
	});
	 //tab关闭
	tab.on("click", "i", function() {
		var curHash = webHistory.get(), hash = $(this).parent().attr("h");
		if (("#" + curHash) == hash) {
			var nhash = $(this).parent().next().attr("h");
			var phash = $(this).parent().prev().attr("h");
			if (nhash != undefined) {
				$(this).parent().next().addClass("active");
				hash = nhash;
			} else if (phash != undefined) {
				$(this).parent().prev().addClass("active");
				hash = phash;
			} else {
				location.href = ctxPath;
			}
			webHistory.go(hash);
		}
		$(this).parent().remove();
		return false;
	});
	 //tab右键
	$('#breadcrumb').contextmenu({
		scopes: 'li',
		target: '#tab-menu',
		onItem: function(context, e) {
			var t = $(e.target).data("right-menu");
			if (t == "all_close") {
				location.href = ctxPath;
			} else if (t == "other_close") {
				$(context).addClass("active").siblings().remove();
				webHistory.go($(context).attr("h"));
			}
		}
	});
	var $main_content = $("#fill-main-content");
	webHistory.add("ajax", function(str, action, token) {
		$main_content.html(loadHtmlPage(str));
		var curMenu = $("#sidebar-menu li").find("a[href='#" + token + "']");
		changeMenu(curMenu);
	});
	webHistory.init();
*/
	
});

function getCenterHeight() {
	try{
		return $(window).height() - $("#tree-menu-div").offset().top-15;
	} catch (e) {
		return 0;
	}
}

/** ***********树型CRUD页面高度调整****************** */
function resetTreeCRUDPageHeight() {
	var h = getCenterHeight();

	if (h < $("#list-page").height()) {
		h = $("#list-page").height();
	}
	if ($(window).height() - $("#list-page").offset().top-15 > $("#list-page").height()) {
		try{
			h = $(window).height() - $("#list-page").offset().top-15;
		} catch (e) {
		}
	}
	$("#list-page").height(h);
}

function operatorBtn(value, row) {
	return $("#rowBtn_edit").html()
			.replace(new RegExp("{row.id}", "g"), row.id);
}


function loadHtmlPage(path) {
	if(path.indexOf("?") != -1){
		path = path + "&tt="+ new Date().getTime();
	}else{
		path = path + "?tt="+ new Date().getTime();
	}
    path = ctxPath + "/" + path;
    var result;
    $.ajax({
        url: path,
        dataType: "text",
        async: false,
        success: function(data) {
            result = data;
        }
    });
    return result;
};

(function($){
	/**
	 * 序列号Json,将复选框，多选下来列表框序列化为数组
	 */
	$.fn.serializeJson=function(){ 
		var serializeObj={}; 
		var array=this.serializeArray();
		$(array).each(function(){  
            if(serializeObj[this.name]){  //若已经包含这个Key
                if($.isArray(serializeObj[this.name])){  
                    serializeObj[this.name].push(this.value);  
                }else{  
                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
                }  
            }else{  
                serializeObj[this.name]=this.value;   
            }
        });
		return serializeObj;  
	}
})(jQuery);


/* function changeMenu(obj) {
	$this = $curmenu = obj, pli = $this.parents("li");
	var $sibling = $this.closest("li[data-level='1']").siblings("li.open");
	if ($sibling.size() > 0) {
		$sibling.removeClass("open").find("li.open").removeClass("open");
		$sibling.find("ul.nav-show").attr("class", "submenu nav-hide").hide();
	}													
	if ($this.attr('haschild') == "false") {				
		$this.closest("li[data-level='1']").addClass("open");
		var pul = $this.parents("ul.submenu");		//所有一级菜单的子菜单对象		
		pul.attr("class", "submenu nav-show").show();
		$("#sidebar-menu").find("li").removeClass("active");
		pli.addClass("active");
		// $(".page-header h1").text($this.find(">span").text());

		 var menuArray = [];
		var cur = $this.find(".menu-text").text();
		menuArray.push(cur);
		pli = $this.parents("li").parents("li");
		if(pli.attr("data-level") == 2){
			pli = $this.closest("li[data-level='2']");
		}
		var array = [];
		while (pli.data("level") == 1) {
			cur = pli.find(".dropdown-toggle .menu-text");			
			$.each(cur,function(i,obj){
				array.push(obj.innerHTML);
			})
			pli = pli.parents("li");
			menuArray.push(array[0]);
		}
		if (pli.data("level") > 1) {
			cur = pli.find(".dropdown-toggle .menu-text").text();			
			menuArray.push(cur);
			pli = pli.parents("li");
			cur = pli.find(".dropdown-toggle .menu-text");			
			$.each(cur,function(i,obj){
				array.push(obj.innerHTML);
			})
			menuArray.push(array[0]);
		}
		
		var crumb = $("#breadcrumb");
		crumb.html("");
		crumb
				.append('<li><i class="ace-icon fa fa-home home-icon"></i><a href="'
						+ ctxPath + '/" >首页</a></li>')

		for (var i = menuArray.length - 1; i >= 0; i--) {
			if (i == 0) {
				crumb.append('<li class="active"> ' + menuArray[i] + ' </li>');
			} else {
				crumb.append('<li> <a href="#">' + menuArray[i] + '</a> </li>');
			}
		}

		//
		var $historyM = $("#historyMenu");
		var url = '$this.attr("href")';
		$historyM.find("li [href='" + url + "']").remove();
		var html = '<li class="item-orange clearfix" data-url="' + url + '">'
				+ '<span class="lbl"> ' + $this.find(".menu-text").text()
				+ '</span>' + '</li>'; 

		// $historyM.insert(html);

	}
} */

//当浏览器窗口变化时，表格自适应布局
$(window).resize(function(){
	// $.each($('table'),function(i, table){
	// 	if($(table).attr('id') != undefined){
	// 		try {
	// 			$(table).bootstrapTable('resetView', {height: $(window).height()-$(".bootstrap-table").offset().top-15});
	// 		} catch (e) {
	// 		}
	// 	}
	// })
	if($("#content-main").offset()){
		$("#content-main").height($(window).height()-$("#content-main").offset().top);
	}
});

//成功消息提示框
function successMsg(content, callback){
	content = content == undefined?'操作成功！':content;
	callback = callback == undefined?null:callback;
	layer.open({
		type: 0,
		shade: [ 0.05, '#000' ], // [遮罩透明度, 遮罩颜色]
		time: 800,
		icon: 1,
		content: content,
		btn: 0,
		title: false,
		closeBtn: false,
		// skin: 'layui-layer-rim',
		end: callback
	})
}

//失败消息提示框
function failMsg(content, time){
	time = time == undefined?2000:time;
	layer.msg(content, {
		time: time,
		anim:6
	});
}

//加载提示框
function loading(msg, time){
	msg = msg == undefined ? "正在加载中......":msg;
	time = time == undefined ? 10000000:time;
	var loadi = layer.msg(msg, {
		icon: 16,
		time: time
	});
	return loadi;
}

(function ($) {
	var cuslayer = function(params) {
		var defaults = {
			mode: 'page',	//打开层的模式，现封装了page(页面)，detail(详情页面)，confirm(确认窗口)，del(删除确认窗口)，alert(提示窗口)
			type: 1, // 0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）。 若采用layer.open({type: 1})方式调用，则type为必填项（信息框除外）
			//skin: 'skin-1', //应用皮肤
			title: false,	//String/Array/Boolean，默认：'信息'
			zIndex: 199206,
			shade: [ 0.05, '#000' ], // [遮罩透明度, 遮罩颜色]
//			border: [ 3, 0.5, '#666' ],//边框
			closeBtn: 1,	//layer提供了两种风格的关闭按钮，可通过配置1和2来展示，如果不显示，则closeBtn: 0
			url: undefined, // 请求回来弹窗的url
			data: {}, // 请求弹窗携带的参数
			maxmin: true, // 是否输出窗口最小化/全屏/还原按钮。
			width: '100%',	//宽度
			height: '100%',	//高度，默认自适应
			btn: false,
			btnAlign: 'c', //定义按钮的排列位置,'l':按钮左对齐,'c':按钮居中对齐,'r':按钮右对齐
			content: undefined,	//窗体内容
			msg: undefined,	//提示语，用于confirm窗口
			reloadUrl: false,//是否刷新页面
			formId: 'table-form',	//要提交的表单ID，用于有表单的页面层
			fixed:  true,	//鼠标滚动时，层是否固定在可视区域
			moveOut: true,	//是否允许拖拽到窗口外
			moveEnd: function(layero){	//窗口拖动结束回调函数
				layer.closeAll('tips'); //关闭所有的tips层  
			},	
			success: null, //当需要在层创建完毕时即执行一些语句，可以通过该回调。success会携带两个参数，分别是当前层DOM当前层索引
			yes: null,	//确定按钮回调函数
			btn2: function(index, layero){
				layer.closeAll('tips'); //关闭所有的tips层  
			},
			//确定按钮回调方法,该回调携带两个参数，分别为当前层索引、当前层DOM对象
			cancel: function(index, layero){ //右上角关闭按钮触发的回调,该回调携带两个参数，分别为：当前层索引参数（index）、当前层的DOM对象（layero），默认会自动触发关闭。如果不想关闭，return false即可
				layer.closeAll('tips'); //关闭所有的tips层  
			},
			callback: undefined	//提示框以及删除弹框确定按钮回调函数
		};
		//debugger
		params = $.extend(defaults, params);

		var mode = params.mode;
		if(mode == 'page' || mode == 'detail' || mode == 'del'){
			if (undefined == params.url) {
				failMsg("请求url未填写！");
				return false;
			}
		}
		layer.config({
			extend: 'skin-1/style.css' //加载新皮肤
		})
/*=========================================================confrim/del提示弹窗======================================================================*/
		if (mode == 'confirm' || mode == 'del') {
			params.type = 0;
			params.title = params.title == false ? '<i class="fa fa-exclamation-triangle"></i>&nbsp;温馨提示':'<i class="fa fa-desktop"></i>&nbsp;'+params.title;
			params.msg = params.msg == undefined ? '确定执行此操作吗？':params.msg;
			params.icon = 3;
			params.content = params.msg;
			params.btn = ['<i class="ace-icon fa fa-floppy-o middle-120"></i>&nbsp;确 定', '<i class="ace-icon fa fa-times-circle middle-120"></i>&nbsp;取 消'];
			if(mode == 'del'){
				params.yes = function(index){
					$.post(params.url,params.data,function(result){
						if(result.code == 1) {
							successMsg(result.msg, function() {
								if (params.callback != undefined) {
									if (typeof params.callback === "string") {
										eval(params.callback)
									} else {
										params.callback();
									}
								} else {
									var target = window.parent.$('.J_iframe[data-id="' + $curmenu.attr('href') + '"]');
									var url = target.attr('src');
									target.attr('src', url);
									/* if(params.reloadUrl == true){
										location.reload();
									}else{
									} */
								}
							}); 
						}else{
							failMsg(result.msg);
						}
					},"json"); 
				}
			}
			layer.open(params); 
		}
/*=========================================================页面层弹窗======================================================================*/
		else if (mode == 'page' || mode == 'detail') {
			//area:宽高,默认状态下，layer是宽高都自适应的,自定义时需重新组装参数
			var area, height = params.height;
			if(height == '0'){
				area = params.width;
			}else{
				area = [params.width, params.height]
			}
			params.area = area;
			//标题
			params.title = params.title == false ? '<i class="fa fa-exclamation-triangle"></i>&nbsp;信息':'<i class="fa fa-desktop"></i>&nbsp;'+params.title;
			//详情窗口只有关闭按钮
			if(mode == 'detail') {
				params.btn = ['<i class="ace-icon fa fa-times-circle middle-120"></i>&nbsp;关 闭'];
				params.yes = function(index, layero){
					layer.close(index);
				}
			}else{
				params.btn = params.btn == false ? ['<i class="ace-icon fa fa-floppy-o middle-120"></i>&nbsp;保 存', '<i class="ace-icon fa fa-times-circle middle-120"></i>&nbsp;关 闭']: params.btn;
				//定义确定按钮提交表单事件
				params.yes = params.yes != null ? params.yes: function(index, layero){
					if(layero.html().indexOf(params.formId) == -1){
						//此提示面对开发者，开发时打开提示，生产建议关闭
						failMsg("没有找到相应的表单，请检查表单ID配置是否正确！");
						console.log("没有找到相应的表单，请检查表单ID配置是否正确（没有配置ID，默认提交ID为【table-form】的表单）！");
						return false;
					}
					$("#" + params.formId).submit();
				}
			}
			$.ajax({
				url: params.url,
				data: params.data,
				type: 'post',
				dataType: 'html',
			}).done(function(result) {
				params.content = result;
				var layerIndex = layer.open(params);
				return layerIndex;
			}).fail(function(err) {
				failMsg("操作失败！");
			});
		}
/*=========================================================提示层弹窗======================================================================*/
		else if (mode == 'diy') {
			params.type = 0;
			params.btn = params.btn == false ? ['<i class="ace-icon fa fa-times-circle middle-120"></i>&nbsp;关 闭']: params.btn;
			var area, height = params.height;
			if(height == '0'){
				area = params.width;
			}else{
				area = [params.width, params.height]
			}
			params.area = area;
			//标题
			params.title = params.title == false ? '<i class="fa fa-desktop"></i>&nbsp;提示信息':'<i class="fa fa-desktop"></i>&nbsp;'+params.title;
			layer.open(params);
		}
		else if (mode == 'alert') {
			params.type = 0;
			params.btn = params.btn == false ? ['<i class="ace-icon fa fa-times-circle middle-120"></i>&nbsp;关 闭']: params.btn;
			//标题
			params.title = params.title == false ? '<i class="fa fa-exclamation-triangle"></i>&nbsp;提示信息':'<i class="fa fa-desktop"></i>&nbsp;'+params.title;
			layer.open(params);
		}
		else {
			failMsg("没有相关的操作模式，请联系管理员！");
		}
	};
	$.cuslayer = cuslayer;
})(jQuery);

$(function() {
	// 数组操作
	Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val)
				return i;
		}
		return -1;
	};
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};

	// document.oncontextmenu=function(){return false;}//屏蔽右键

	// 禁用Enter键表单自动提交
	document.onkeydown = function(event) {
		var target, code, tag;
		if (!event) {
			event = window.event; // 针对ie浏览器
			target = event.srcElement;
			code = event.keyCode;
			if (code == 13) {
				tag = target.tagName;
				if (tag == "TEXTAREA") {
					return true;
				} else {
					return false;
				}
			}
		} else {
			target = event.target; // 针对遵循w3c标准的浏览器，如Firefox
			code = event.keyCode;
			if (code == 13) {
				tag = target.tagName;
				if (tag == "INPUT") {
					return false;
				} else {
					return true;
				}
			}
		}
	};

	// 属性模式
	$(document).on('click', '[data-mode]', function() {
		var data = $(this).data();
		if (undefined != data['data'] && typeof data['data'] != "object") {
			data['data'] = eval("(" + data.data + ")");
		}
		$.cuslayer(data);
	});

});

// 得到url的参数
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

// 刷新url
function reloadUrl() {
	window.location.href = (window.location.href).split("?")[0] + "?menuid="
			+ $curmenu.attr("id");
}

function tableLoadSuccess(data){
	var ps = $('#table').bootstrapTable.defaults.pageSize * 43.5;
	$('#table').bootstrapTable( 'resetView' , {height: ps} );
}

// 分页
function paging(formId, pageNo) {
	var $form = $("#" + formId), $target = $("#" + $form.attr('target')), spinner;
	var pageNoInput = $form.find('input[name="pageNum"]');
	var pageSize = $form.find('input[name="pageSize"]');
	if (pageNoInput.size() == 0) {
		$form.append("<input type='hidden'  name = 'pageNum' value='1'/>");
		pageNoInput = $form.find('input[name="pageNum"]');
	}
	if (pageSize.size() == 0) {
		$form.append("<input type='hidden'  name = 'pageSize' value='10'/>");
	}
	pageNoInput.val(pageNo);
	var loadIndex;
	$.ajax({
		url: $form.attr('action'),
		type: 'post',
		dataType: 'html',
		data: $form.serialize(),
		beforeSend: function() {
			loadIndex = loading("正在拼命加载数据.....");
		}
	}).done(function(data) {
		layer.close(loadIndex);
		if ($target) {
			$target.stop();
		}
		$target.html(data);
	}).fail(function(error) {
		//alert("请求错误!");
	})
	return false;
};

// 条件查询分页
;
(function($) {
	$.fn.getPageList = function(settings) {
		return this.each(function() {
			var $this = $(this);
			this.opt = $.extend({}, $.fn.getPageList.defaults, settings);
			$("#" + this.opt.submitBtnId).on('click', function() {
				paging($this.attr("id"), 1);
				return false;
			});
			if (this.opt.trigger)
				$("#" + this.opt.submitBtnId).trigger('click');
		});
	}

	$.fn.getPageList.defaults = {
		submitBtnId: "", // 提交按钮
		trigger: true
	}
})(jQuery);

// 提示tip
var tip = {
	errorTip: function(msg, obj, style, time) {
		style = style == undefined ? [ 'background-color:#F26C4F; color:#fff',
				'#F26C4F' ]: style;
		time = time == undefined ? 40000000 : time;
		layer.tips(msg, obj, {
			tips: [2, '#F26C4F'],
			time: time,
		});
	}
};


Angel.downloadFile = function(formid, action) {
	var curForm = $("#" + formid);
	var queryParams = curForm.serializeJson();
	var $tempForm = $("<form style='display:none;'></form>");
	$.each(queryParams, function(key, value){
		var $input = $("<input name='" + key + "' value='" + value + "'/>");
		$tempForm.append($input);
	})
	$("body").append($tempForm);
	$tempForm.attr("action", action);
	$tempForm.attr("method", "post");
	$tempForm.submit();
	$tempForm.remove();
};




function downloadAllFile(formid, action) {
	var table = $("#" + formid);
	var rows = table.bootstrapTable("getData");
	var ids = [];
	for (var i = 0; i < rows.length; i++) {
		ids.push(rows[i].id);
	}
	var $tempForm = $("<form style='display:none;'></form>");
	var $input = $("<input name=id value='" + ids+ "'/>");
	$tempForm.append($input);
	$("body").append($tempForm);
	$tempForm.attr("action", action);
	$tempForm.attr("method", "post");
	$tempForm.submit();
	$tempForm.remove();
}

Angel.uploadFile = {
	init: function(fileInput) {
		if (!fileInput.parent().is("form")) {
			var url = fileInput.data("url");
			fileInput.wrap("<form action='" + url
					+ "' method='post' enctype='multipart/form-data'></form>");
		}
	},
	excel: function(target) {
		var $this = $(target), url = $this.data("url"), progress = $($this
				.data("progressid")), oldTxt = $this.closest(".btn").find(
				"span").text();
		this.init($this);
		var form = $this.parent();
		form.ajaxSubmit({
			dataType: 'html',
			beforeSend: function() { // 开始上传
				$this.css({
					"top": "-1000px"
				});
				progress.attr("data-percent", "0%");
				progress.children().eq(0).width("0%");
				$this.closest(".btn").find("span").text("处理中,请稍后…");
				if (!isIe8) {
					progress.show();
				}
			},
			uploadProgress: function(event, position, total, percentComplete) {
				var percentVal = percentComplete + '%'; // 获得进度
				progress.attr("data-percent", percentVal);
				progress.children().eq(0).width(percentVal);
			},
			success: function(data) { // 成功
				$this.css({
					"top": "0px"
				});
				progress.hide();
				$this.closest(".btn").find("span").text(oldTxt);
				$this.replaceWith($this.clone());
				layer.alert(data, 1, function(index) {
					layer.close(index);
					var target = window.parent.$('.J_iframe[data-id="' + $curmenu.attr('href') + '"]');
					var url = target.attr('src');
					target.attr('src', url);
				});
			},
			error: function(xhr) { // 上传失败
				$this.css({
					"top": "0px"
				});
				progress.hide();
				$this.closest(".btn").find("span").text(oldTxt);
				$this.replaceWith($this.clone());
				// alert(xhr.responseText); //返回失败信息
			}
		});
	}
};

// Find the right method, call on correct element
function launchFullscreen(element) {
	if (!$("body").hasClass("full-screen")) {

		$("body").addClass("full-screen");

		if (element.requestFullscreen) {
			element.requestFullscreen();
		} else if (element.mozRequestFullScreen) {
			element.mozRequestFullScreen();
		} else if (element.webkitRequestFullscreen) {
			element.webkitRequestFullscreen();
		} else if (element.msRequestFullscreen) {
			element.msRequestFullscreen();
		}

	} else {

		$("body").removeClass("full-screen");

		if (document.exitFullscreen) {
			document.exitFullscreen();
		} else if (document.mozCancelFullScreen) {
			document.mozCancelFullScreen();
		} else if (document.webkitExitFullscreen) {
			document.webkitExitFullscreen();
		}

	}

}

/*------------------------------------------------------------------时间格式化函数---------------------------------------------------------------*/
	function datetimeFormat(longTypeDate){
		if(longTypeDate != undefined){
			var datetimeType = "";  
			var date = new Date();  
			date.setTime(longTypeDate);  
			datetimeType+= date.getFullYear();   //年  
			datetimeType+= "-" + getMonth(date); //月   
			datetimeType += "-" + getDay(date);   //日  
			datetimeType+= "&nbsp;&nbsp;" + getHours(date);   //时  
			datetimeType+= ":" + getMinutes(date);      //分
			datetimeType+= ":" + getSeconds(date);      //分
			return datetimeType;
		}else{
			return null;
		}
	} 
	//返回 01-12 的月份值   
	function getMonth(date){  
	    var month = "";  
	    month = date.getMonth() + 1; //getMonth()得到的月份是0-11  
	    if(month<10){  
	        month = "0" + month;  
	    }  
	    return month;  
	}  
	//返回01-30的日期  
	function getDay(date){  
	    var day = "";  
	    day = date.getDate();  
	    if(day<10){  
	        day = "0" + day;  
	    }  
	    return day;  
	}
	//返回小时
	function getHours(date){
	    var hours = "";
	    hours = date.getHours();
	    if(hours<10){  
	        hours = "0" + hours;  
	    }  
	    return hours;  
	}
	//返回分
	function getMinutes(date){
	    var minute = "";
	    minute = date.getMinutes();
	    if(minute<10){  
	        minute = "0" + minute;  
	    }  
	    return minute;  
	}
	//返回秒
	function getSeconds(date){
	    var second = "";
	    second = date.getSeconds();
	    if(second<10){  
	        second = "0" + second;  
	    }  
	    return second;  
	}

/*----------------------------------------------------------------html符号反转义----------------------------------------------------------------------------*/
	function html(str) {
        return str ? str.replace(/&((g|l|quo)t|(ld|rd)quo|amp|#39|nbsp);/g, function (m) {
            return {
                '&lt;':'<',
                '&amp;':'&',
                '&quot;':'"',
                '&gt;':'>',
                '&#39;':"'",
                '&nbsp;':' ',
                '&ldquo;':'“',
                '&rdquo;':'”'
            }[m]
        }) : '';
    }