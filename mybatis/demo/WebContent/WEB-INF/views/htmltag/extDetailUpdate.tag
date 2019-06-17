@var detailList = detailList!'';
@var random = random!'';
\@var groups;

@var dollar = '$';
<div id="toolbar" style="padding: 5px 0;text-align: center;">
	        	<button type="button" id="BtAdd${random}" class="btn btn-primary btn-sm" >添加</button>&nbsp;
	        	<button type="button" id="BtDel${random}" class="btn btn-primary btn-sm">删除</button>
		</div>
        <table id="tb${random}"  class="table table-bordered" data-toolbar="#toolbar" width="100%" style="white-space: nowrap;">
        					<tr></tr>
				            <tr align="center">
				                <td style="width:5%;">序号</td>
				                
				                <td style="width:5%;">
				                    <input id="CKA${random}" name="CKA${random}" type="checkbox"/>
				                </td>
				                @var width;
				                @var bxgId;//组id
				                @for(detail in detailList){
				                	@var length = detailLP.size;
				                	@width = 90/length;
				                	@bxgId = detail.bxgId;
					                <td style="width:${width!}%;">${detail.mapCnName!}</td>
		            			@}
				            </tr>
				            <tr>
				                <td style="text-align: center"></td>
				                <td style="text-align: center"><input id="CK${random}" type="checkbox" name="CK${random}"/></td>
				                <td style="display:none">
				                	<input type="hidden" name="extendGroupId" value="${bxgId }">
				                </td>
				                  @for(detail in detailList){
				                 	@// 0:文本框
				                 	@if(detail.collectType == '0'){
			                 			<td style="text-align: center;">
			                 				@if(detail.nullFlag == '0'){
				                 				<input  type="text" name="${detail.enName!}" maxlength="${detail.dataLength!}" class="validate[required,custom[invalidChar]]" />&nbsp;<font color="red" size="+1">*</font>
			                 				@}else{
			                 					<input  type="text" name="${detail.enName!}"/>
			                 				@}
			                 			</td>
				                 	@}
				                 	@// 1:下拉框
				                 	@if(detail.collectType == '1'){
				                 	<td style="text-align: center;">
				                 		
				                 		@if(detail.nullFlag == '0'){
					                 		<select name="${detail.enName!}${decode(strutil.toString(detail.dataType!0),'03','Name','')}" class="validate[funcCall[validSelect]]" style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;"> 
				                 		@}else{
					                 		<select name="${detail.enName!}${decode(strutil.toString(detail.dataType!0),'03','Name','')}" style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;"> 
				                 		@}
				                 			\@groups = wins.getSelGroups('${detail.datasource }');
			                 				<option>--请选择--</option>
				                 			\@for(gro in groups){
				                 				<option value="${dollar}{gro.mapName!}">${dollar}{gro.mapName!}</option>
				                 			\@}
				                 			
				                 		@if(detail.nullFlag == '0'){
				                 		</select>&nbsp;<font color="red" size="+1">*</font>
				                 		@}else{
				                 		</select>
				                 		@}
				                 	</td>
				                 	@}
				                 	@// 2:日期框
				                 	@if(detail.collectType == '2'){
				                 		<td style="text-align: center;">
			                 				@if(detail.nullFlag == '0'){
				                 				<input  type="text" name="${detail.enName!}" onFocus="WdatePicker()" class="validate[required]"/>&nbsp;<font color="red" size="+1">*</font>
			                 				@}else{
			                 					<input  type="text" name="${detail.enName!}" onFocus="WdatePicker()"/>
			                 				@}
			                 			</td>
				                	@}
				                	@// 3:数字框
				                	@if(detail.collectType == '3'){
				                 		<td style="text-align: center;">
			                 				@if(detail.nullFlag == '0'){
					                 				<input  type="text" name="${detail.enName!}" maxlength="${detail.dataLength!}" class="validate[required,custom[integer]]" />&nbsp;<font color="red" size="+1">*</font>
				                 				@}else{
				                 					<input  type="text" name="${detail.enName!}"/>
				                 				@}
			                 			</td>
				                	@}
				                	@// 4：金额框
				                	@if(detail.collectType == '4'){
					            		<td style="text-align: center;">
			                 				@if(detail.nullFlag == '0'){
				                 				<input  type="text" name="${detail.enName!}" maxlength="11" onblur="chkvalue(this)" placeholder="0.00(精度：两位小数)" class="validate[required]"/>&nbsp;<font color="red" size="+1">*</font>
			                 				@}else{
			                 					<input  type="text" name="${detail.enName!}" maxlength="11" onblur="chkvalue(this)" placeholder="0.00(精度：两位小数)"/>
			                 				@}
			                 			</td>
					            	@}
					            @}
				            </tr>
			    		\@if(projectId!0 != 0){
        					\@var groupId = "${bxgId}";
        					\@var extDetDataList = wins.getExtDetDataList(projectId!,groupId);
				            \@for(detailData in extDetDataList){
				            	\@var index = detailDataLP.index-1;
				            	\@var newIndex = index+1;
				            
				           <tr>
			                <td style="text-align: center">${dollar!}{newIndex! }</td>
				                <td style="text-align: center"><input id="CK${random}" type="checkbox" name="CK${random}"/></td>
				                <td style="display:none">
				                	<input type="hidden" name="detailForm[${dollar!}{index! }].extendGroupId" value="${bxgId }">
				                </td>
				                 @for(detail in detailList){
				                 	@// 0:文本框
				                 	@if(detail.collectType == '0'){
			                 			<td style="text-align: center;">
			                 				@if(detail.nullFlag == '0'){
			                 					@//可更改标记
								    	 	@if(detail.alterFlag == '0'){
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" readonly class="validate[required,custom[invalidChar]]" maxlength="${detail.dataLength!}" 
											     	value="${dollar!}{detailData.${detail.enName!}! }" />&nbsp;<font color="red" size="+1">*</font>
								    	 	@}else{
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" class="validate[required,custom[invalidChar]]" maxlength="${detail.dataLength!}" 
											     	value="${dollar!}{detailData.${detail.enName!}! }"/>&nbsp;<font color="red" size="+1">*</font>
								    	 	@}
									    @}else{
									    	@if(detail.alterFlag == '0'){
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" readonly value="${dollar!}{detailData.${detail.enName!}! }" maxlength="${detail.dataLength!}" />
								    	 	@}else{
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" value="${dollar!}{detailData.${detail.enName!}! }" maxlength="${detail.dataLength!}" />
								    	 	@}
									    @}
			                 			</td>
				                 	@}
				                 	@// 1:下拉框
				                 	@if(detail.collectType == '1'){
				                 	<td style="text-align: center;">
				                 		@if(detail.nullFlag == '0'){
											@//可更改标记
									   	 	@if(detail.alterFlag == '0'){
												<select name="detailForm[${dollar!}{index! }].${detail.enName!}${decode(strutil.toString(detail.dataType!0),'03','Name','')}" class="validate[funcCall[validSelect]]" disabled="disabled" 
													style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;"> 
											@}else{
												<select name="detailForm[${dollar!}{index! }].${detail.enName!}${decode(strutil.toString(detail.dataType!0),'03','Name','')}" class="validate[funcCall[validSelect]]" 
													style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;"> 
											@}
										@}else{
											@if(detail.alterFlag == '0'){
												<select name="detailForm[${dollar!}{index! }].${detail.enName!}${decode(strutil.toString(detail.dataType!0),'03','Name','')}" disabled="disabled"  
												style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;">  
											@}else{
												<select name="detailForm[${dollar!}{index! }].${detail.enName!}${decode(strutil.toString(detail.dataType!0),'03','Name','')}"  
												style="height: 26px;width: 150px;line-height: 26px;border: 1px solid #e0e0e0;">  
											@}
										@}
								            \@groups = wins.getSelGroups('${detail.datasource }');
								            @var value = 'detailData.'+detail.enName;
			                 				<option>--请选择--</option>
				                 			\@for(gro in groups){
				                 				<option value="${dollar}{gro.mapName!}" 
				                 				${dollar!}{decode(strutil.toString(gro.mapName!0),strutil.toString(detailData.${detail.enName!}${decode(strutil.toString(detail.dataType!0),'03','Name','')}!0),"selected","")}>${dollar}{gro.mapName!}</option>
				                 			\@}
								        </select> 
								        @if(detail.nullFlag == '0'){
								        &nbsp;<font color="red" size="+1">*</font>
								        @}
				                 	</td>
				                 	@}
				                 	@// 2:日期框
				                 	@if(detail.collectType == '2'){
				                 		<td style="text-align: center;">
				                 			@if(detail.nullFlag == '0'){
			                 					@//可更改标记
								    	 	@if(detail.alterFlag == '0'){
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" readonly  class="Wdate validate[required]" 
											     	value="${dollar!}{detailData.${detail.enName!}! }"/>&nbsp;<font color="red" size="+1">*</font>
								    	 	@}else{
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" class="Wdate" onFocus="WdatePicker()" class="validate[required]" 
											     	value="${dollar!}{detailData.${detail.enName!}! }"/>&nbsp;<font color="red" size="+1">*</font>
								    	 	@}
									    @}else{
									    	@if(detail.alterFlag == '0'){
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" class="Wdate" readonly  value="${dollar!}{detailData.${detail.enName!}! }"/>
								    	 	@}else{
											     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" class="Wdate" onFocus="WdatePicker()" value="${dollar!}{detailData.${detail.enName!}! }"/>
								    	 	@}
									    @}
			                 			</td>
				                	@}
				                	@// 3:数字框
				                	@if(detail.collectType == '3'){
				                 		<td style="text-align: center;">
				                 			@if(detail.nullFlag == '0'){
			                 					@//可更改标记
									    	 	@if(detail.alterFlag == '0'){
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" readonly class="validate[custom[integer]]" maxlength="${detail.dataLength!}" 
												     	value="${dollar!}{detailData.${detail.enName!}! }"/>&nbsp;<font color="red" size="+1">*</font>
									    	 	@}else{
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" class="validate[custom[integer]]" maxlength="${detail.dataLength!}" 
												     	value="${dollar!}{detailData.${detail.enName!}! }"/>&nbsp;<font color="red" size="+1">*</font>
									    	 	@}
										    @}else{
										    	@if(detail.alterFlag == '0'){
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" readonly maxlength="${detail.dataLength!}"  value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}else{
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" maxlength="${detail.dataLength!}"  value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}
										    @}
			                 			</td>
				                	@}
				                	@// 4：金额框
				                	@if(detail.collectType == '4'){
				                 		<td style="text-align: center;">
				                 			@if(detail.nullFlag == '0'){
			                 					@//可更改标记
									    	 	@if(detail.alterFlag == '0'){
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" placeholder="0.00(精度：两位小数)" readonly maxlength="11" 
												     	class="money validate[required]" value="${dollar!}{detailData.${detail.enName!}! }"/>&nbsp;<font color="red" size="+1">*</font>
									    	 	@}else{
												     <input  type="text" name="detailForm[${dollar!}{index! }].${detail.enName!}" placeholder="0.00(精度：两位小数)" maxlength="11" 
												     	class="money validate[required]" value="${dollar!}{detailData.${detail.enName!}! }"/>&nbsp;<font color="red" size="+1">*</font>
									    	 	@}
										    @}else{
										    	@if(detail.alterFlag == '0'){
												     <input  type="text" class="money" maxlength="11"  name="detailForm[${dollar!}{index! }].${detail.enName!}" placeholder="0.00(精度：两位小数)" readonly  value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}else{
												     <input  type="text" class="money" maxlength="11" name="detailForm[${dollar!}{index! }].${detail.enName!}" placeholder="0.00(精度：两位小数)" value="${dollar!}{detailData.${detail.enName!}! }"/>
									    	 	@}
										    @}
			                 			</td>
				                 	@}
				                	
		            			 @}
				            </tr>
				          \@}  
						\@}     
		</table>
		
		<script type="text/javascript">
		
		function chkvalue(obj)
		{
			var moneys=obj.value;
			var temp_money=accounting.toFixed(moneys,2);
			obj.value=temp_money;
		}
		
        $(document).ready(function() {
        	//金额框
		   // $(".money").blur(function(){
		    //	var money=$(this).val();
		    	//var temp_money=accounting.formatMoney(money,{symbol:"",format:"%v %s"});
		    	//	var temp_money=accounting.toFixed(money,2);
		    	//$(this).val($.trim(temp_money));
		  //  });
        	
        	var index = layer.index;
            //隐藏模板tr
            $("#tb${random} tr").eq(2).hide();
            var i = trs = $("#tb${random} tr").length-3;
            
         /*--------------添加一行-------------------------------------------------------------------------------------------------------------------------------------- */
            $("#BtAdd${random}").click(function() {
    　　　　　		//复制一行
         		var trs = $("#tb${random} tr");
    　　　　　		var index = trs.length-3;
                    var tr = $("#tb${random} tr").eq(2).clone();
    　　　　　			var seq = ++i;
                    tr.find("td").get(0).innerHTML = seq;
                    var tds = tr.find("td");
                    $.each(tds,function(index1){
                    	//alert(index1);
    					var td =tds.eq(index1);
    					var input = td.find("input,select").eq(0);
    					var name = input.attr("name");
    					if(name != "CK${random}"){
    						input.attr("name","detailForm["+index+"]."+name);
    					}
    				});
                    tr.show();
                    tr.appendTo("#tb${random}");
                });
            
          /*--------------删除一行-------------------------------------------------------------------------------------------------------------------------------------- */
            $("#BtDel${random}").click(function() {
            	var rows=$("input[name='CK${random}']:checked");
        		if(rows.length==0||rows.length==null){
        			layer.msg("请选择要删除的数据！",2,5);
        		}else{
	                $("#tb${random} tr:gt(2)").each(function() {
	                    if ($(this).find("#CK${random}").get(0).checked == true) {
	                        $(this).remove();
	                    }
	                });
	                i = 0;
	                $("#tb${random} tr:gt(2)").each(function() {
	                    $(this).find("td").get(0).innerHTML = ++i;
	                });
	                $("#CKA${random}").attr("checked", false);
        		}
            });
            $("#CKA${random}").click(function() {
                $("#tb${random} tr:gt(2)").each(function() {
                    $(this).find("#CK${random}").get(0).checked = $("#CKA${random}").get(0).checked;
                });
            });
        })    
            
        function validSelect(field, rules, i, options){
		var flag = false;
		 $.each(field[0],function(j,obj){
			 if(j != 0 && obj.selected){
				flag = true;
			}
		})
		if(!flag){
			rules.push('required');
		    return options.allrules.validSelect.alertText;
		}
	}
    </script>