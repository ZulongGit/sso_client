@var formId = formId!'table-form'; //form表单id
@var reloadUrl = reloadUrl!'false'; //是否url刷新
@var isHide = isHide!'no'; //是否隐藏按钮 默认no
@var subBtnId = subBtnId!(formId+'-save');
@var validId = validId!(strutil.replace(formId,"-",""));
@var config = config!'false';
@var data = data!'';
@var okText = okText!'确 定';
@var callBack = callBack!'';
@var style = style!'bottom: 0px;left: 35%;position:absolute;';
@var url = url!'';

@/*
@if(isHide == "no"){
<div class="clearfix" tag-save-btn style="${style}">
	<span class="btn btn-primary btn-sm bigger-110"  id="${subBtnId}">
		<i class="ace-icon fa fa-floppy-o middle-120"></i>&nbsp;${okText}
	</span>&nbsp;&nbsp;
	<span class="btn btn-primary btn-sm bigger-110" id="${formId}-cancel">
		<i class="ace-icon fa fa-times-circle middle-120"></i>&nbsp;关 闭
	</span>
</div>
@}  */
<script type="text/javascript">
$(function(){
	$("#submitForm").click(function(){
		$("#${formId}").submit();
	})
	//表单校验
	$("#${formId}").validate({
		submitHandler: function(form){
			${tagBody!}
			var indexLoad = loading("系统正在提交数据，请稍后......");
			$("#${formId}").ajaxSubmit({
            	type:'post',
            	url:'${ctxPath}/${url}',
            	success:function(data){
            		layer.close(indexLoad);
            		if(data.code == 1) {
            			successMsg(data.msg, function() {
                   			if("${callBack}" != undefined && "${callBack}".length > 0){
        						${callBack};
        					}else{
        						if("${reloadUrl}" == "true"){
        							location.reload();
        						}else{
        							$curmenu.trigger('click');
        						}
        					}
        					layer.closeAll();
                   		}); 
                    }else{
                    	failMsg(data.msg);
                    }
                }
            });     
		},
		errorPlacement: function(error, element) {
			layer.tips(error.html(), element, {
				tips: [2, '#F26C4F'],
				time : 4000,
			});
		}
	});

});
</script>