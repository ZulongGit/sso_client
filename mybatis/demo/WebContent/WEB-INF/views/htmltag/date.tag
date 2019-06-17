@var islayer = islayer!'true';//是否是弹窗形式,否则是表格toolbar形式
@var isRange = isRange!'false';//是否是日期范围
@var text = text!'';//中文名
@var name = name!'';//name属性
@var require = require!'false'; //是否要校验
@var readonly = readonly!'';
@var value = value!'';//元素值
@var notNull = notNull!'false';//是否非空
@var class = class!'width-100';//自定义样式
@var alterFlag = alterFlag!'';//可更改标记
					<tr class="FormData">
					    <td class="CaptionTD">${text}:</td>
					    <td class="DataTD ${class}">
				   	@//非空标记
					@if(nullFlag == '1'){
						@//可更改标记
				   	 	@if(alterFlag == '0'){
						     <input type="text" name="${name}" id="${name}" readonly value="${value! }" class="Wdate" required date="true"/>
				   	 	@}else{
						     <input type="text" name="${name}" id="${name}"  value="${value! }" onfocus="WdatePicker()" class="Wdate" required date="true"/>
				   	 	@}
				    @}else{
				    	@if(alterFlag == '0'){
						     <input type="text" name="${name}" id="${name}" readonly value="${value! }" class="Wdate"  date="true"/>
				   	 	@}else{
						     <input type="text" name="${name}" id="${name}"  value="${value! }" onfocus="WdatePicker()" class="Wdate" date="true"/>
				   	 	@}
				    @}
					   </td>
				   @if(nullFlag == '1'){
					   <td><font color="red" size="+1">*</font></td>
				   @}
					</tr>
