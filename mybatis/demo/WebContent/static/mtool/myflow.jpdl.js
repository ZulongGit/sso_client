(function($){
var myflow = $.myflow;

$.extend(true,myflow.config.rect,{
	attr : {
	r : 8,
	fill : '#F6F7FF',
	stroke : '#03689A',
	"stroke-width" : 2
}
});




$.extend(true,myflow.config.tools.states,{
	start : {
				showType: 'image',
				type : 'start',
				name : {text:'<<start>>'},
				text : {text:'开始'},
				img : {src : ctxPath + '/static/mtool/img/48/start_event_empty.png',width : 48, height:48},
				attr : {width:50 ,heigth:50 },
				props : {
					text: {name:'text',label: '显示', value:'', editor: function(){return new myflow.editors.textEditor();}, value:'开始'}
					}
				},
			end : {showType: 'image',type : 'end',
				name : {text:'<<end>>'},
				text : {text:'结束'},
				img : {src : ctxPath + '/static/mtool/img/48/end_event_terminate.png',width : 48, height:48},
				attr : {width:50 ,heigth:50 },
				props : {
					text: {name:'text',label: '显示', value:'', editor: function(){return new myflow.editors.textEditor();}, value:'结束'}
				}},
			task1 : {showType: 'text',type : 'task1',
				name : {text:'<<task>>'},
				text : {text:'基本数据'},
				img : {src : ctxPath + '/static/mtool/img/48/task_empty.png',width :48, height:48},
				props : {
					id: {name:'id', label: 'id', value:''},
					name: {name:'name', label: '步骤名称', value:'', editor: function(){return new myflow.editors.inputEditor();}},
					tableName: {name:'tableName', label : '中间表名', value:'', editor: function(){return new myflow.editors.inputEditor();}},
					stepDesc: {name:'stepDesc', label : '步骤描述', value:'', editor: function(){return new myflow.editors.inputEditor();}},
					sqlCode: {name:'sqlCode', label : 'SQL脚本', value:'', editor: function(){return new myflow.editors.inputEditor();}}
				}},
		    task2 : {showType: 'text',type : 'task2',
				name : {text:'<<task>>'},
				text : {text:'数据过滤'},
				img : {src : ctxPath + '/static/mtool/img/48/task_empty.png',width :48, height:48},
				props : {
					text: {name:'text', label: '显示', value:'', editor: function(){return new myflow.editors.textEditor();}, value:'任务'},
					assignee: {name:'assignee', label: '用户', value:'', editor: function(){return new myflow.editors.inputEditor();}},
					form: {name:'form', label : '表单', value:'', editor: function(){return new myflow.editors.inputEditor();}},
					desc: {name:'desc', label : '描述', value:'', editor: function(){return new myflow.editors.inputEditor();}}
				}}
				,
		    task3 : {showType: 'text',type : 'task3',
				name : {text:'<<task>>'},
				text : {text:'数据分组'},
				img : {src : ctxPath + '/static/mtool/img/48/task_empty.png',width :48, height:48},
				props : {
					text: {name:'text', label: '显示', value:'', editor: function(){return new myflow.editors.textEditor();}, value:'任务'},
					assignee: {name:'assignee', label: '用户', value:'', editor: function(){return new myflow.editors.inputEditor();}},
					form: {name:'form', label : '表单', value:'', editor: function(){return new myflow.editors.inputEditor();}},
					desc: {name:'desc', label : '描述', value:'', editor: function(){return new myflow.editors.inputEditor();}}
				}}
				,
			task4 : {showType:'image&text',type : 'task4',
				name : {text:''},
				text : {text:'生成的最终结果'},
				img : {src : ctxPath + '/static/mtool/img/48/task_hql.png',width :48, height:48},
				attr : {width:200,height:100},
				props : {
					text: {name:'text', label: '显示', value:'', editor: function(){return new myflow.editors.textEditor();}, value:'任务'},
					temp1: {name:'temp1', label: '文本', value:'', editor: function(){return new myflow.editors.inputEditor();}},
					temp2: {name:'temp2', label : '选择', value:'', editor: function(){return new myflow.editors.selectEditor('select.json');}}
				}}
});
})(jQuery);