@var detailList = detailList!'';
@var bxgId;
@for(detail in detailList){
	@bxgId = detail.bxgId;
@}
@var dollar = '$';

<table id="detail-table"></table>



<script type="text/javascript">
	$(function() {
		@var fields; 
		@for(detail in detailList){
			@if(detail.dataType == '03'){
				@fields = fields + detail.enName + 'Name,';
			@}else{
				@fields = fields + detail.enName + ',';
			@}
		@}
		
		$("#detail-table").bootstrapTable({
			inconSize : 'sm',
			url : '${dollar!}{ctxPath!}/pt/menuwinconf/getExtDetDataList?groupId=${bxgId!}&projectId=${dollar}{projectId!}&fields=${fields}',
			cache: false,
			clickToSelect: true,
			columns : [
			{
				field : "id",
				title : '序号',
				formatter : function(value, row, index) {
					return index + 1;
				}
			},
			@for(detail in detailList){
				{
					field:"${detail.enName}${decode(strutil.toString(detail.dataType!0),'03','Name','')}",
					title:'${detail.mapCnName}',
					sortable:true
				}
				@if(!detailLP.last){
					,
				@}
			@}
			]
		});
	});
	
	
	
</script>