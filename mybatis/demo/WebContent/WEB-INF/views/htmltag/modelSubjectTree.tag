@var editUrl=editUrl!; //  
@var delUrl=delUrl!;
@var addUrl=addUrl!;
@var treeData=treeData!;
@var form=form!'search-form';
@var searchBtn=searchBtn!'search-btn';
@var searchInput=searchInput!'search-input';
@var searchAllBtn=searchAllBtn!'search-all-btn';
@var height = height!'0';
@var width = width!'50%';
@var reloadUrl = reloadUrl!false;
@var rootNodeName = rootNodeName!"模型专题分类列表";
@var addTitle = addTitle!"添加模型专题";


<script type="text/javascript">
	var current_node;
	var setting = {
		view:{
			expandSpeed:100,
			selectedMulti : false,
			addHoverDom:addHoverDom,
			removeHoverDom: removeHoverDom,
			fontCss:function(treeId, treeNode) {
				return (!!treeNode.highlight) ? {"font-weight":"bold","color":"red"} : {"font-weight":"normal","color":"#333"};
			}
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: function(treeId,treeNode){
				return treeNode.level == 0 || treeNode.id.indexOf("s_") == -1? false:true;
			},
			showRenameBtn: function(treeId,treeNode){
				return treeNode.level == 0 || treeNode.id.indexOf("s_") == -1? false:true;
			},
			removeTitle : "删除",
			renameTitle : "编辑"
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "parentId"
			}
		},
		callback: {
			onClick: onClickNode,
			beforeRemove:beforeRemove,
			beforeEditName: beforeEditName,
			beforeDrag: function(){return false;}
		}
	};
	
	//编辑
	function beforeEditName(treeId, treeNode) {
		current_node = treeNode;
		$.cuslayer({
			mode:'page',
			title:(treeNode.name)+'编辑',
			height:"${height}",
			width:"${width}",
			url:"${ctxPath!}/"+"${editUrl}",
			data:{"id":treeNode.id.split("_")[1]}
		});
		return false;
	}
	
	//删除
	function beforeRemove(treeId, treeNode){
		current_node = treeNode;
		var id = treeNode.id.split("_")[1];
		$.cuslayer({
			mode:'del',
			msg:'<span class="red bigger-120">你确定删除<'+treeNode.name+'>吗?</span>',
			title:'删除操作',
			url:"${ctxPath!}/"+'${delUrl}',
			data:{"id":id},
			reloadurl:${reloadUrl}
		});
		return false;
	}
	
	//划过显示添加按钮,添加
	function addHoverDom(treeId, treeNode) {
		if(treeNode.id != 0){
			if(treeNode.id.indexOf("p_") != -1){
				if(treeNode.haveLine == 1){//如果有条线，不允许增加
					return false;
				}
			}
			current_node = treeNode;
			
			var data = [];
			var ds = treeNode.id.split("_");
			if(ds[0] == 'p'){
				data = {property:ds[1]};
			}else if(ds[0] == 'l'){
				data = {property:ds[1],line:ds[2]};
			}else if(ds[0] == 's'){
				data = {parentId:ds[1]};
			}
			
			
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
				+ "' title='添加' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				$.cuslayer({
					mode:'page',
					title:'${addTitle}',
					height:"${height}",
					width:"${width}",
					url:"${ctxPath!}/"+'${addUrl}',
					data:data
				});
				return false;
			});
		}
		return false;
	};
	
	//移除添加按钮
	function removeHoverDom(treeId, treeNode) {
		$("#addBtn_"+treeNode.tId).unbind().remove();
	};
	
	//节点点击事件
	function onClickNode(event, treeId, treeNode) {
		current_node = treeNode;
		if(treeNode.id == 0){
			$("#${searchAllBtn}").click();
		}else{
			$("#${form}").find("input[name=name]").val("");
			$("#${form}").find("input[name=property]").val("");
			$("#${form}").find("input[name=buzLine]").val("");
			$("#${form}").find("input[name=id]").val("");
			
			var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
			var ds = treeNode.id.split("_");
			if(ds[0] == 'p'){
				$("#${form}").find("input[name=property]").val(ds[1]);
			}else if(ds[0] == 'l'){
				$("#${form}").find("input[name=property]").val(ds[1]);
				$("#${form}").find("input[name=buzLine]").val(ds[2]);
			}else if(ds[0] == 's'){
				$("#${form}").find("input[name=id]").val(ds[1]);
			}
			
			query();
			
			for(var i=0, l=nodeList.length; i<l; i++) {
				nodeList[i].highlight = false;				
				treeObj.updateNode(nodeList[i]);
			}
		}
	};
	
	var key = $("#${searchInput}"),nodeList = [];
	function searchNode(e) {
		// 取得输入的关键字的值
		var value = $.trim(key.get(0).value);
		
		// 按名字查询
		var keyType = "name";
		if (key.hasClass("empty")) {
			value = "";
		}
		
		// 如果要查空字串，就退出不查了。
		if (value === "") {
			return;
		}
		updateNodes(false);
		nodeList = treeObj.getNodesByParamFuzzy(keyType, value);
		updateNodes(true);
	};
	function updateNodes(highlight) {
		for(var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = highlight;				
			treeObj.updateNode(nodeList[i]);
			treeObj.expandNode(nodeList[i].getParentNode(), true, false, false);
		}
	};
	
	var treeObj;
	$(function(){
		//树结构初始化
		nodeList=[]; //清除缓存
		var treeData = ${treeData};
		
		var root = {id:0,name:"${rootNodeName}",open:true};
		treeData[treeData.length] = root;
		
		$.fn.zTree.init($("#treeMenu"), setting,treeData);
		treeObj = $.fn.zTree.getZTreeObj("treeMenu");
		// 默认展开一级节点
		var nodes = treeObj.getNodesByParam("level", 0);
		for(var i=0; i<nodes.length; i++) {
			treeObj.expandNode(nodes[i], true, false, false);
		}
		
		
		$("#${searchBtn}").click(function(e){
			$("#${form}").find("input[name=id]").val("");
			$("#${form}").find("input[name=property]").val("");
			$("#${form}").find("input[name=buzLine]").val("");
			query();
			
			var treeObj = $.fn.zTree.getZTreeObj("treeMenu");
			treeObj.cancelSelectedNode();
			searchNode(e);
		});
		
		$("#${searchAllBtn}").click(function(){
			$("#${form}").find("input[name=id]").val("");
			$("#${form}").find("input[name=name]").val("");
			$("#${form}").find("input[name=property]").val("");
			$("#${form}").find("input[name=buzLine]").val("");
			
			queryAll();
			var node = treeObj.getNodeByParam("id", 0);
			treeObj.selectNode(node,false);
			if(undefined != nodeList) {
				for(var i=0, l=nodeList.length; i<l; i++) {
					nodeList[i].highlight = false;				
					treeObj.updateNode(nodeList[i]);
				}
			}
		})
		
		
	});
	
	function reloadTree(id){
		$.post("${ctxPath}/model/subject/tree",function(data){
			treeObj.destroy();
			
			var root = {id:0,name:"${rootNodeName}",open:true};
			data[data.length] = root;
			
			$.fn.zTree.init($("#treeMenu"), setting,data);
			treeObj = $.fn.zTree.getZTreeObj("treeMenu");
			if(current_node != null && current_node != undefined){
				var nodes = treeObj.getNodesByParam("id", current_node.id);
				for(var i=0; i<nodes.length; i++) {
					treeObj.expandNode(nodes[i], true, false, false);
				}
			}
		},"json");
		
		query();
	}
</script>
