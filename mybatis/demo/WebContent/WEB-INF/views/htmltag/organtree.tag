@var order = order!'';
@var class = class!'form-group'; //样式
@var width = width!'350px'; //弹窗宽度
@var height = height!'400px'; //弹窗的高度
@var modelId = modelId!'parentId'; //隐藏要提交的id (实体属性)
@var modelName = modelName!''; //要提交的name (实体属性)
@var modelIdValue = modelIdValue!; //id初始值value 
@var modelNameValue = modelNameValue!''; //name初始值value
@var id = id!('treeselectid'+order); //隐藏要添加的input的id名称,页面多个时候需要指定
@var nameId = nameId!('treeselectname'+order);  //名称
@var url = ctxPath+"/"+url!''; //树数据url,必填,从管理路径之后添起，如menu/tree
@var idKey = idKey!'id'; //model中属性名字
@var pIdKey = pIdKey!'parentId'; //父级的model中属性名字
@var selectIds = selectIds!''; //默认选择节点
@var curId = curId!'-1'; //当前节点的id,如要验证不能选择当前节点需要填写,多个时必须填写，详见organ-save页面
@var isCheck = isCheck!''; //是否验证不为空
@var style = style!'';
@var checked = checked!'false'; //是都显示复选框
@var isLayer = isLayer!'true'; //是否为弹窗
@var treeSelectId = treeSelectId!'tree'; //树id,即是树对象名称
@var rootNodeName = rootNodeName!""; //虚拟顶级节点名称
@var chkboxType = chkboxType!""; //1不选择父节点
@var level = level!'0'; //默认按几级菜单展开列表

@if(isLayer == "true"){
	<input class="form-control" type="text" onfocus="display${nameId}()"
	id="${nameId}" name="${modelName}" value="${modelNameValue}" placeholder="请选择"
	@if(!isEmpty(isCheck)){
		datatype="*"  nullmsg=${isCheck}
	@}
	>
	<input type="hidden" name="${modelId}" class="form-control w70" id="${id}" value="${modelIdValue}"/>
${tagBody!}
@}else{
	${tagBody!}
	<script>
		$.ajax({
			url:"${ctxPath}/tag/organtree",
			type:"post",
			data:{url:'${url}',id:'${id}',nameId:'${nameId}',idKey:'${idKey}',pIdKey:'${pIdKey}',
				selectIds:'${selectIds}',curId:'${curId}',checked:'${checked}',isLayer:"false",
				treeSelectId:'${treeSelectId}',rootNodeName:'${rootNodeName}',chkboxType:'${chkboxType}',level:'${level}'},
			success:function(data){
				$("#${treeSelectId}").html(data);
			}
		});
		
		
	</script>
@}
	
	<script>
	function display${nameId}(){
		$.cuslayer({
			mode:'page',
			height:'400px',
			width:'350px',
			title:'请选择',
			url:'${ctxPath}/tag/organtree',
			style:'34px',
			data:{url:'${url}',id:'${id}',nameId:'${nameId}',idKey:'${idKey}',pIdKey:'${pIdKey}',
				selectIds:'${selectIds}',curId:'${curId}',checked:'${checked}',isLayer:"true",
				treeSelectId:'${treeSelectId}',rootNodeName:'${rootNodeName}',chkboxType:'${chkboxType}',level:'${level}'}
		});
			

	}
	</script>