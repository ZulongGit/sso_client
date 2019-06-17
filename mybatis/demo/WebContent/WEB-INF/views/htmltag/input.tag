@var islayer = islayer!'true';//是否是弹窗形式,否则是表格toolbar形式
@var text = text!'';//中文名
@var name = name!'';//name属性
@var readonly = readonly!'';
@var value = value!'';//元素值
@var nullFlag = nullFlag!'1';//是否非空,是否要校验
@var class = class!'width-100';//自定义样式
@var alterFlag = alterFlag!'';//可更改标记
@var id = id!'';
@var specFlag = specFlag!'false';
@var dollar = '$';
@var maxLength = maxLength!;
					<tr class="FormData">
    					<td class="CaptionTD">${text}:</td>
    					<td class="DataTD ${class}">
				   	@//非空标记  	 1:非空
					@if(nullFlag == '1'){
						@//可更改标记 	0:不可更改
				   	 	@if(alterFlag == '0'){
						     <input type="text" name="${name}" id="${name}" readonly value="${value! }" maxlength="${maxLength}" required/>
				   	 	@}else{
						     <input type="text" name="${name}" id="${name}" value="${value! }" maxlength="${maxLength}" required/>
				   	 	@}
				    @}else{
				    	@if(alterFlag == '0'){
						     <input type="text" name="${name}" id="${name}" maxlength="${maxLength}" readonly value="${value! }"/>
				   	 	@}else{
						     <input type="text" name="${name}" id="${name}" maxlength="${maxLength}" value="${value! }"/>
				   	 	@}
				    @}
					    </td>
				    @if(nullFlag == '1'){
					    <td><font color="red" size="+1">*</font></td>
				    @}
					</tr>
