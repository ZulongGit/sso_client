@var islayer = islayer!'true';//是否是弹窗形式,否则是表格toolbar形式
@var id = id!"chosen-select";
@var text = text!'';//中文名
@var name = name!;
@var width = width!"100%";
@var class = class!;
@var options = options!'';
@var nullFlag = nullFlag!'1';//是否非空,是否要校验
@var value = value!'';
@var dollar = '$';
@var alterFlag = alterFlag!'';//可更改标记
@var dataType = dataType!'';
@var hiddenValue = hiddenValue!'';//数据类型为03的时候,新增的值:updateObj.xxxxName
@var datasource = datasource!'';
@var isAdd = isAdd!'false';
@var withNo = withNo!'false';
					<tr class="FormData">
						<td class="CaptionTD">${text! }:</td>
						<td class="DataTD width-100">
					@if(nullFlag == '1'){
						@//可更改标记
					  	 	@if(alterFlag == '0'){
							<select class="${class!}" name="${name!}" id="${id!}" disabled="disabled" select="true">
						@}else{
							<select class="${class!}" name="${name!}" id="${id!}" select="true">
						@}
					@}else{
						@if(alterFlag == '0'){
							<select class="${class!}" name="${name!}" id="${id!}" disabled="disabled">
						@}else{
							<select class="${class!}" name="${name!}" id="${id!}">
						@}
					@}
		            			<option value="">--请选择--</option>
								\@selectOptions = dict.getDictListByType("${datasource }");
								\@if(selectOptions != ""){
								   	\@for(op in selectOptions){
						   		@if('${isAdd}' == 'true'){
						   			@if(withNo == 'true'){
							   			<option value="${dollar!}{op.value! }">${dollar!}{op.label! }【${dollar!}{op.label! }】</option>
						   			@}else{
							   			<option value="${dollar!}{op.value! }">${dollar!}{op.label! }</option>
						   			@}
						   		@}else{
						   			@if(withNo == 'true'){
							   			<option value="${dollar!}{op.value!}" ${dollar!}{decode(strutil.toString(op.value!""),strutil.toString(${tableName}.${name!}!""),"selected","")}>${dollar!}{op.value! }【${dollar!}{op.label! }】</option>
						   			@}else{
							   			<option value="${dollar!}{op.value!}" ${dollar!}{decode(strutil.toString(op.value!""),strutil.toString(${tableName}.${name!}!""),"selected","")}>${dollar!}{op.label! }</option>
						   			@}
						   		@}
							  		\@}
								\@}
				        	</select> 
		    			</td>
				     @if(nullFlag == '1'){
					    <td><font color="red" size="+1">*</font></td>
				    @}
					</tr>
