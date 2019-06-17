
$('#searchbtn').click(function(){
});

function searchcloumns(){
	var searchval = $('#searchval').val();
	$("#tableselectcolumn option").each(function(n,node){
		node.selected=false;
		
	});
	$("#tableselectcolumn option").each(function(n,node){
		if(node.text == searchval){
			node.selected=true;
		}
	});
}

function searchcloumns2(){
	var searchval = $('#searchval2').val();
	$("#tablesecselectcolumn option").each(function(n,node){
		node.selected=false;
	});
	$("#tablesecselectcolumn option").each(function(n,node){
		if(node.text == searchval){
			node.selected=true;
		}
	});
}

function searchleft(){
	if(clickcnt<0){
		clickcnt=searcharray.length-1;
	}
	if(clickcnt == searcharray.length){
		clickcnt=searcharray.length-2;
	}
	for(var i=searcharray.length-1;i>=0;i-- ){
		if(i== clickcnt){
			$("#tableselectcolumn option").each(function(n,node){
				node.selected=false;
				if(node.text == searcharray[i]){
					node.selected=true;
				}
			});
		}
	}
	clickcnt--;
}
var clickcnt=0;
function searchright(){
	if(clickcnt>=searcharray.length){
		clickcnt=0;
	}
	for(var i=0;i<searcharray.length;i++ ){
		if(i== clickcnt){
			$("#tableselectcolumn option").each(function(n,node){
				node.selected=false;
				if(node.text == searcharray[i]){
					node.selected=true;
					$('#currentcnt').show();
					$('#currentcnt').html("第"+(clickcnt+1)+"个");
				}
			});
		}
	}
	clickcnt++;
}
var searcharray;
function autosearch(obj){
	
	searcharray = new Array();
	if(obj.value!= ''){
		var cnt=0;
		clickcnt=0;
		$('#currentcnt').html("");
		$("#tableselectcolumn option").each(function(n,node){
			node.selected=false;
			if(node.text.indexOf(obj.value)>-1){
				node.selected=true;
				cnt = ++cnt;
				searcharray.push(node.text);
			}
		});
		if(cnt>0){
			$("#cnt").html(cnt);
		}
	}else{
		$("#cnt").html('0');
		$('#currentcnt').html("");
		$("#tableselectcolumn option").each(function(n,node){
			node.selected=false;
		});
	}
}

var conditonfieldarray;
function autosearch_condition(obj){
	conditonfieldarray = new Array();
	if(obj.value!= ''){
		var cnt=0;
		clickcnt=0;
		$("#tablecollist option").each(function(n,node){
			node.selected=false;
			if(node.text.indexOf(obj.value)>-1){
				node.selected=true;
				conditonfieldarray.push(node.text);
			}
		});
	}else{
		$("#tablecollist option").each(function(n,node){
			node.selected=false;
		});
	}
}



var searcharray2;
var clickcnt2=0;
function autosearch2(obj){
	
	searcharray2 = new Array();
	if(obj.value!= ''){
		var cnt=0;
		clickcnt2=0;
		$('#currentcnt2').html("");
		$("#tablesecselectcolumn option").each(function(n,node){
			node.selected=false;
			if(node.text.indexOf(obj.value)>-1){
				node.selected=true;
				cnt = ++cnt;
				searcharray2.push(node.text);
			}
		});
		if(cnt>0){
			$("#cnt2").html(cnt);
		}
	}else{
		$("#cnt2").html('0');
		$('#currentcnt2').html("");
		$("#tablesecselectcolumn option").each(function(n,node){
			node.selected=false;
		});
	}
}


function searchright2(){
	if(clickcnt2>=searcharray2.length){
		clickcnt2=0;
	}
	for(var i=0;i<searcharray2.length;i++ ){
		if(i== clickcnt2){
			$("#tablesecselectcolumn option").each(function(n,node){
				node.selected=false;
				if(node.text == searcharray2[i]){
					node.selected=true;
					$('#currentcnt2').show();
					$('#currentcnt2').html("第"+(clickcnt2+1)+"个");
				}
			});
		}
	}
	clickcnt2++;
}



function doZoom(size){  
    document.getElementById('zoom').style.fontSize=size+'px'  
} 

var DOM = (document.getElementById) ? 1 : 0;  
var NS4 = (document.layers) ? 1 : 0;  
var IE4 = 0;  
if (document.all)  
{  
    IE4 = 1;  
    DOM = 0;  
} 
var win = window;     
var n   = 0; 
function findIt() {  
    if (document.getElementById("searchstr").value != "")  
        findInPage(document.getElementById("searchstr").value);  
} 

function findInPage(str) {  
var txt, i, found; 
if (str == "")
    return false; 
if (DOM)  
{  
//	alert(window.pageYOffset);
    win.find(str, false, true);  
    return true;  
} 
if (NS4) {
    if (!win.find(str))  
        while(win.find(str, false, true))  
            n++;  
    else  
        n++; 
    if (n == 0)  
        alert("未找到指定内容.");  
}
if (IE4) {
    txt = win.document.body.createTextRange();
    for (i = 0; i <= n && (found = txt.findText(str)) != false; i++) {
        txt.moveStart("character", 1);
        txt.moveEnd("textedit");
    }
if (found) {
    txt.moveStart("character", -1);
    txt.findText(str);
    txt.select();
    txt.scrollIntoView();
    n++;
}
else {
    if (n > 0) {
        n = 0;
        findInPage(str);
    }
    else
        alert("未找到指定内容.");
    }
}
return false;  
}  

