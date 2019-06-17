var chartdata = {};
// 数据类型
chartdata.types = {};

// 设备运行数据
chartdata.types.runData = {
		name : '设备历史数据统计',
		support: ['line', 'bar', 'bar_x'],
		// 参数
		params : {
			deviceTypeId : {
				name : '设备类型',
				required : true,
				type : 'select',
				url : '/userconsle/deviceTypes?pageSize=200',
				changeParam: 'tableId'
			},
			tableId : {
				name : '数据表',
				required : true,
				type : 'select',
				// 需要在拼接参数?deviceTypeId=xxxx
				url : '/userconsle/elementTables?pageSize=200',
				changeParam: 'elementField'
			},
			elementField : { // FIXME 这个可以多选，参数用逗号拼接
				name : '统计字段',
				required : true,
				type : 'select',
				multiple : 'multiple',
				// 需要在拼接参数?tableId=xxxx
				url : '/userconsle/elements?pageSize=200',
				valueField : 'fieldName'
			},
			fromDate : {
				name : '开始时间',
				type : 'date'
			},
			endDate : {
				name : '开始时间',
				type : 'date'
			},
			statDateInterval : {
				name : '统计间隔',
				required : true,
				type : 'select',
				options : [{name : '小时', value : '1h'}, {name : '天', value : '1d'}, {name : '周', value : '1w'}, {name : '月', value : '1M'}, {name : '季', value : '1q'}, {name : '年', value : '1y'}]
			},
			dateRange : {
				name : '时间范围',
				required : true,
				type : 'select',
				options : [{name : '过去一小时', value : 1}, {name : '过去一天', value : 24}, {name : '过去一周', value : 7 * 24}, {name : '过去一月', value : 30 * 24}, {name : '过去一季', value : 90 * 24}, {name : '过去一年', value : 365 * 24}]
			}
		},
		// 向图表中设置数据
		setData : function(chartDom, chartOption) {
			var url = this.getUrl(chartDom);
			$.get(url, function(data) {
				var datas = data.list;
				var chartData = {};
				// 数据
				chartData.series = [];
				var eFields = chartDom.attr('databind-elementField').split(',');
				var enames = chartDom.attr('databind-elementField-name').split(',');
				for (var i = 0; i < eFields.length; i++) {
					var data = {};
					data.name = enames[i];
					data.fieldName = eFields[i];
					data.data = [];
					chartData.series.push(data);
				}
				// 分类
				chartData.category = [];
				for (var i = 0; i < datas.length; i++) {
					chartData.category.push(datas[i].dateStr);
					for (var j = 0; j < chartData.series.length; j++) {
						var category = chartData.series[j];
						category.data.push(datas[i][category.fieldName]);
					}
				}
				
				var chartType = chartDom.attr('name');
				var option = chartdata.charts[chartType].getOption(chartData, chartOption);
				echartInit(chartDom[0], option);
			}, 'json');
		},
		getUrl : function(chartDom) {
			var dataurl = '/userconsle/elementTables/' + chartDom.attr('databind-tableId') + '/stats?statFields=' + chartDom.attr('databind-elementField') + '&statDateInterval=' + chartDom.attr('databind-statDateInterval');
			// 时间范围
			var dateRange = parseInt(chartDom.attr('databind-dateRange'));
			var fromDate = new Date().getTime() - dateRange * 60 * 60 * 1000;
			dataurl += '&fromDate=' + fromDate;
			return dataurl;
		}
}


chartdata.types.dataAPI = {
		name : '数据接口',
		support: ['line', 'bar', 'bar_x'],
		// 参数
		params : {
			apiURL : {
				name : '内部API',
				required : true,
				type : 'select',
				url : '/userconsle/deviceTypes?pageSize=200',
				changeParam: 'elementField'
			},
			extAPI : {
				name : 'reatAPI url',
				required : true,
				type : 'input',
				// 需要在拼接参数?deviceTypeId=xxxx
				changeParam: 'elementField'
			},
			rowField : { // FIXME 这个可以多选，参数用逗号拼接
				name : '数据唯独',
				required : true,
				type : 'select',
				multiple : 'multiple',
				// 需要在拼接参数?tableId=xxxx
				url : '/userconsle/elements?pageSize=200',
				valueField : 'fieldName'
			},
			colField : { // FIXME 这个可以多选，参数用逗号拼接
				name : '数据列',
				required : true,
				type : 'select',
				multiple : 'multiple',
				// 需要在拼接参数?tableId=xxxx
				url : '/userconsle/elements?pageSize=200',
				valueField : 'fieldName'
			},
			fromDate : {
				name : '开始时间',
				type : 'date'
			},
			endDate : {
				name : '开始时间',
				type : 'date'
			},
			statDateInterval : {
				name : '统计间隔',
				required : true,
				type : 'select',
				options : [{name : '小时', value : '1h'}, {name : '天', value : '1d'}, {name : '周', value : '1w'}, {name : '月', value : '1M'}, {name : '季', value : '1q'}, {name : '年', value : '1y'}]
			},
			dateRange : {
				name : '时间范围',
				required : true,
				type : 'select',
				options : [{name : '过去一小时', value : 1}, {name : '过去一天', value : 24}, {name : '过去一周', value : 7 * 24}, {name : '过去一月', value : 30 * 24}, {name : '过去一季', value : 90 * 24}, {name : '过去一年', value : 365 * 24}]
			}
		}
} 


// 图表类型
chartdata.charts = {
		// 柱图
		bar : {
			getOption : function(chartData, chartOption) {
				var option = $.extend(true, {}, chartOption);
				option.xAxis[0].data = chartData.category;
				option.legend[0].data = [];
				for (var i = 0; i < chartData.series.length; i++) {
					chartData.series[i].type = 'bar';
					option.legend[0].data.push(chartData.series[i].name);
					option.series = chartData.series;
				}
				return option;
			}
		},
		// 条形图
		bar_x : {
			getOption : function(chartData, chartOption) {
				var option = $.extend(true, {}, chartOption);
				option.yAxis[0].data = chartData.category;
				option.legend[0].data = [];
				for (var i = 0; i < chartData.series.length; i++) {
					chartData.series[i].type = 'bar';
					option.legend[0].data.push(chartData.series[i].name);
					option.series = chartData.series;
				}
				return option;
			}
		},
		// 线图
		line : {
			getOption : function(chartData, chartOption) {
				var option = $.extend(true, {}, chartOption);
				option.xAxis[0].data = chartData.category;
				option.legend[0].data = [];
				for (var i = 0; i < chartData.series.length; i++) {
					chartData.series[i].type = 'line';
					option.legend[0].data.push(chartData.series[i].name);
					option.series = chartData.series;
				}
				return option;
			}
		}
}

