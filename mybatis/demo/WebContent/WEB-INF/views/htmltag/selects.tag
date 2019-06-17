@var id = id!"chosen-select";
@var name = name!;
@var width = width!"100%";
@var class = class!;
@var nullFlag = nullFlag!;

@if(nullFlag == '0'){		//允许为空
	<select class="chosen-select ${class}" name="${name}" id="${id}">
@}else{
	<select class="chosen-select ${class}" name="${name}" id="${id}" datatype="*" nullmsg="请选择一条数据！" select="true">
@}
	${tagBody!}
</select>
<script>
$(function(){
	$("#${id}").chosen({width: "${width}",search_contains: true,disable_search_threshold:10}); 
});
</script>