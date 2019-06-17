(function($) {
	
	var selects = [];
	//所有联动多下拉框进行排序，级别高的排前面
	var sorts = function(arr){
		for (var i = 0; i < arr.length - 1; i++) {
			for (var j = i + 1; j < arr.length; j++) {
				if ($(arr[i]).attr('linkage-level') > $(arr[j]).attr('linkage-level')) {//如果前面的数据比后面的大就交换  
					var temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr;
	}
	
	/**
	 * 序列号Json,将复选框，多选下来列表框序列化为数组
	 */
	$.fn.serializeJson = function(){ 
		var serializeObj = {}; 
		var array = this.serializeArray();
		$(array).each(function(){  
            if(serializeObj[this.name]){  //若已经包含这个Key
                if($.isArray(serializeObj[this.name])){  
                    serializeObj[this.name].push(this.value);  
                }else{  
                    serializeObj[this.name] = [serializeObj[this.name],this.value];  
                }  
            }else{  
                serializeObj[this.name] = this.value;   
            }
        });
		return serializeObj;  
	}
	
	//为所有的下拉框注册change事件
	var onchange = function(i, sel, options){
		var params = {};
		options.selects.each(function(j, obj){
			if(j <= i){
				params = $.extend(params, $(obj).serializeJson());
			}
		})
		params = $.extend(params, options.params[i+1]);
		if(options.url[i+1] != undefined && options.url[i+1] != ''){
			options.selects.each(function(j, obj){
				if(j > i){
					$(obj).empty();
					$(obj).append("<option value=''>"+options.placeholder_text+"</option>");  //为Select追加一个Option(下拉项)
					$(obj).trigger('chosen:updated');//更新选项
				}
			})
			var loadi = loading("系统正在获取下一级联数据，请稍后......");
    		$.ajax({
    			url: options.url[i+1],
    			type: 'post',
    			data: params,
    			dataType: 'json',
    			success: function(result){
    				layer.close(loadi);
    				options.selects.each(function(j, obj){
    					if(j > i){
    						if(j == i + 1){	//下一级改变下拉项
    							$.each(result, function(k, dict){
    								$(obj).append("<option value='"+dict.value+"'>"+dict.label+"</option>");  //为Select追加一个Option(下拉项)
    							})
    							if(options.follow_linkage){
    								$(obj).change();
    							}
    						}
    						$(obj).trigger('chosen:updated');//更新选项
    					}
    				})
    			}
    		})
		}
	}
	
	var Linkage = function (el, options) {
        this.options = options;
        this.$el = $(el);
        this.$el_ = this.$el.clone();
        
    };
    
    Linkage.DEFAULTS = {
		url: [],
		params: [],
		placeholder_text: '--请选择--',
		follow_linkage: false
    }
    
    Linkage.EVENTS = {
    		
    }
    
    Linkage.prototype.init = function () {
    	
    }
    
	$.fn.linkage = function(option) {
		var $this = $(this);
		var options = $.extend({}, Linkage.DEFAULTS, 
				typeof option === 'object' && option);
		var selects = sorts(this);
		options.selects = selects;
		selects.each(function(i, sel){
//			$(sel).data('linkage', new Linkage(this, options));
			$(sel).change(function(){
				onchange(i, this, options);
			})
		})
	}
})(jQuery);