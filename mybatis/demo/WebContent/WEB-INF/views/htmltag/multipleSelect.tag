@var id = id!"";
@var text = text!'';//中文名
@var name = name!;
@var options = options!'';
@var nullFlag = nullFlag!'1';//是否非空,是否要校验
@var values = values!'';
@var dollar = '$';
@var alterFlag = alterFlag!'';//可更改标记
@var dataType = dataType!'';
@var hiddenValue = hiddenValue!'';//数据类型为03的时候,新增的值:${tableName}.xxxxName
@var datasource = datasource!'';
@var isAdd = isAdd!'false';
					\@var ${name}Arr = [];
				@if(values != ''){
					\@if(${values!}!0 != 0){
						\@${name}Arr = strutil.split(${values!},",");
					\@}
				@}
					<tr class="FormData">
						<td class="CaptionTD">${text!}:</td>
						<td class="DataTD width-100">
						@if(alterFlag == '0'){
							<select id="${id }" class="multiple" multiple="multiple" disabled="disabled">
						@}else{
							<select id="${id }" class="multiple" multiple="multiple">
						@}
						   	\@var ${id }Flag = '0';//0：默认不选中
						    \@selectOptions = dict.getDictListByType('${datasource }');
							\@for(op in selectOptions){
					   		 @if(isAdd == 'true'){
					   			<option value="${dollar}{op.value! }">${dollar}{op.label! }</option>
					   		 @}else{
				   			\@if(${tableName}.${name!}!0 != 0){
				   				\@for(var i=0;i<${name}Arr.~size;i++){
				   					\@if(strutil.toString(op.value) == strutil.toString(${name}Arr[i])){
						   				\@${id }Flag = '1'; //1：有值选中
									\@}
								\@}
								\@if(${id }Flag == '0'){
					         		<option value="${dollar}{op.value!}">${dollar}{op.label!}</option>
								\@}else{
					         		<option value="${dollar}{op.value!}" selected>${dollar}{op.label!}</option>
								\@}
								\@${id }Flag = '0';
				       		\@}else{
					       			<option value="${dollar}{op.value!}">${dollar}{op.label!}</option>
					       		\@}
					       	 @}
					        \@}
							</select>
							<input type="hidden" name="${name!}" id="${id }Hidden" value="${dollar}{${tableName}.${name!}!}"> 
						</td>
			       @if(nullFlag == '1'){
						<td><font color="red" size="+1">*</font></td>
				   @}
					</tr>
<!-- ---------------------------------------------------------下拉多选初始化---------------------------------------------------------------- -->
					<script>
						$('#${id}').multiselect({
							onChange : function(element, checked) {
								$('#${id }Hidden').val();//先清空值
								$('#${id }Hidden').val($('#${id}').val());//赋值
							}
						});
					</script>