  var fd = {};
  var urlFix = '/demoPlat/pageset/kbisPage';
  $('#tool-menu .list-li p').on('click', function(event) {
      $(this).parent().addClass('active').siblings().removeClass('active');
      $(this).parent().find('.panel-body').css('display', 'block').animate({
              left: "60px",
              opacity: 1
          }, 300)
          .parent().siblings().find('.panel-body').animate({
              left: "45px",
              opacity: 0
          }, 200).fadeOut(1);
      event.stopPropagation();
  });
  $('#tool-menu .list-li .panel-body').on('dragout', function() {
      hideList();
  });
  $(document).on('click', function() {
      hideList();
  });

  function hideList() {
      $('#tool-menu .list-li').removeClass('active');
      $('#tool-menu').find('.panel-body').animate({
          left: "45px",
          opacity: 0
      }, 300).fadeOut(1);
  };

  //设计页列表操作按钮事件
  $('#resetModal .table').on('click', 'button.load-reset', function() {
      fd.design.restore_layout.call(this);
  });
  $('#resetModal .table').on('click', 'button.del-reset', function() {
      fd.design.remove_reset.call(this);
  });
  $('#resetModal .table').on('click', 'button.preview-reset', function() {
      fd.design.item_preview.call(this);
  });

  //首页列表操作按钮事件
  $('#table-list .table').on('click', 'button.load-reset', function() {
      var id = $(this).parent().parent().children().eq(0).html();
      window.open("formdesign.html?" + id, "_blank");
  });
  $('#table-list .table').on('click', 'button.del-reset', function() {
      var id = $(this).parent().parent().children().eq(0).html();
      del_page(id);
  });
  $('#table-list .table').on('click', 'button.preview-reset', function() {
      var id = $(this).parent().parent().children().eq(0).html();
      preview_page(id);
  });

  var bodyHeight = $('#content-box .panel-body').height();
  $('#content-box .panel-body').height(bodyHeight);

  toastr.options = {
      closeButton: true,
      debug: false,
      progressBar: false,
      positionClass: "toast-top-center",
      onclick: null,
      showDuration: "300",
      hideDuration: "1000",
      timeOut: "2000",
      extendedTimeOut: "1000",
      showEasing: "swing",
      hideEasing: "linear",
      showMethod: "fadeIn",
      hideMethod: "fadeOut"
  };
//初始化列表页
function init_list(){
  var resetList = JSON.parse(localStorage.getItem('resetList'));
  var table_list = $('#table-list tbody');
  $(table_list).html('');
  var i, list_item;
  for (i = 0; i < resetList.length; i++) {
      list_item = '<tr>' +
          '   <td>' + resetList[i].id + '</td>' +
          '   <td>' + resetList[i].name + '</td>' +
          '   <td>' + resetList[i].time + '</td>' +
          '   <td><button class="btn btn-success load-reset">导入</button> <button class="btn btn-primary preview-reset">预览</button> <button class="btn btn-danger del-reset">删除</button></td>' +
          '</tr>';
      table_list.append(list_item);
  }
  // $.ajax({  
  //     type: "GET",  
  //     url: urlFix,   
  //     contentType: "application/json; charset=utf-8",  
  //     dataType: "json",  
  //     success: function(data) {  
  //         var table_list = $('#table-list tbody');
  //         $(table_list).html('');
  //         var i, list_item, len = data.length;
  //         for (i = 0; i < len; i++) {
  //             list_item = '<tr>' +
  //                 '   <td>' + data[i].id + '</td>' +
  //                 '   <td>' + data[i].name + '</td>' +
  //                 '   <td>' + data[i].time + '</td>' +
  //                 '   <td><button class="btn btn-success load-reset">导入</button> <button class="btn btn-primary preview-reset">预览</button> <button class="btn btn-danger del-reset">删除</button></td>' +
  //                 '</tr>';
  //             table_list.append(list_item);
  //         }
  //     }
  // });
}
//列表页预览
function preview_page(page){
  var id, _html, dom_box;
  var resetList = JSON.parse(localStorage.getItem('resetList'));
  var item;
  for (i = 0; i < resetList.length; i++) {
      if (resetList[i].id == page) {
          item = resetList[i];
      }
  }
  _html = item.html;
  dom_box = item.domOpt;
  $('#mask').fadeIn(200);
  $('#mask .content').html(_html);
  $.each($('#mask .charts'), function(i) {
      $(this).attr('id', $(this).attr('id') + 'mask');
      id = $(this).attr('id');
      ecinit(id, dom_box[i]);
  });
 
  
}

function loadPage(pageId)
{
	//designpage
	  $.ajax({  
	       type: "GET",  
	       url: urlFix + "/designpage/"+pageId,   
	       contentType: "application/json; charset=utf-8",  
	       dataType: "json",  
	       success: function(data) {
	    	   debugger;
	           var id, _html, dom_box;
	           _html = data.pageContent;
	           dom_box = JSON.parse(data.pageData);//
	           $('#containers').fadeIn(200); 
	           $('#containers').html(_html);
	           alert(1);
	           $.each($('#containers .charts'), function(i) {
	        	   alert(3);
	               $(this).attr('id', $(this).attr('id') + 'mask');
	               id = $(this).attr('id');
	              
	               ecinit(id, dom_box[i]);
	               fd.design.chartsInit($(this));//
	              // alert("初始化图表");
	           });
	           $.each($('#containers .tag'), function(i) {
	        	   
	              
	               fd.design.bindViewEvent($(this));
	               ////fd.design.disabledrag(1, 2, $(this));
	               //fd.design.enabledrag(1, 2, $(this));
	               
	           });
	          // fd.design.bindViewEvent();
	          fd.design.charts_bind($('#containers'));//
	           
	       }
	   });

}

function del_page(page){
    var resetList = JSON.parse(localStorage.getItem('resetList'));
    swal({
        title: "",
        text: "确定删除？",
        type: "warning",
        showCancelButton: "true",
        showConfirmButton: "true",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        animation: "slide-from-top"
    }, function() {
        for (i = 0; i < resetList.length; i++) {
            if (resetList[i].id == page) {
                resetList.splice(i, 1);
            }
        }
        localStorage.setItem('resetList', JSON.stringify(resetList));
        init_list();
        toastr.error('已删除');
    });
    // ajax
    // swal({
    //     title: "",
    //     text: "确定删除？",
    //     type: "warning",
    //     showCancelButton: "true",
    //     showConfirmButton: "true",
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     animation: "slide-from-top"
    // }, function() {
    //     $.ajax({  
    //         type: "DELETE",  
    //         url: urlFix + page,   
    //         contentType: "application/json; charset=utf-8",  
    //         dataType: "json",  
    //         success: function(data) {  
    //            init_list();
    //            toastr.error('已删除');
    //         }
    //     });
    //     
    //     
    // });
}
function close_mask(){
  $('#mask .content').html('').parent().fadeOut(200);
}
