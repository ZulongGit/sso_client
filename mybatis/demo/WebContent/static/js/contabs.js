$(function () {
	var clicked = false;
    function highLightMenuItem(activeId) {
        var $this = $('a[href="' + activeId + '"]'), pli = $this.parents("li");
        var $sibling = $this.closest("li[data-level='1']").siblings("li.open");
        if($this.size() == 0){
            $("#sidebar-menu").find("li").removeClass("active");
            return;
        }
        if ($sibling.size() > 0) {
            $sibling.removeClass("open").find("li.open").removeClass("open");
            $sibling.find("ul.nav-show").attr("class", "submenu nav-hide").hide();
        }													
        if ($this.attr('haschild') == "false") {
            $this.closest("li[data-level='1']").addClass("open");
            var pul = $this.parents("ul.submenu");		//所有一级菜单的子菜单对象		
            pul.attr("class", "submenu nav-show").show();
            $("#sidebar-menu").find("li").removeClass("active");
            pli.addClass("active");
        }
    }
    //计算元素集合的总宽度
    function calSumWidth(elements) {
        var width = 0;
        $(elements).each(function () {
            width += $(this).outerWidth(true);
        });
        return width;
    }
    //滚动到指定选项卡
    function scrollToTab(element) {
        var marginLeftVal = calSumWidth($(element).prevAll()), marginRightVal = calSumWidth($(element).nextAll());
        // 可视区域非tab宽度
        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
        //可视区域tab宽度
        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
        //实际滚动宽度
        var scrollVal = 0;
        if ($(".page-tabs-content").outerWidth() < visibleWidth) {
            scrollVal = 0;
        } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
            if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
                scrollVal = marginLeftVal;
                var tabElement = element;
                while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                    scrollVal -= $(tabElement).prev().outerWidth();
                    tabElement = $(tabElement).prev();
                }
            }
        } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
            scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
        }
        $('.page-tabs-content').animate({
            marginLeft: 0 - scrollVal + 'px'
        }, "fast");
    }
    //查看左侧隐藏的选项卡
    function scrollTabLeft() {
        var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
        // 可视区域非tab宽度
        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
        //可视区域tab宽度
        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
        //实际滚动宽度
        var scrollVal = 0;
        if ($(".page-tabs-content").width() < visibleWidth) {
            return false;
        } else {
            var tabElement = $(".J_menuTab:first");
            var offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            offsetVal = 0;
            if (calSumWidth($(tabElement).prevAll()) > visibleWidth) {
                while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                    offsetVal += $(tabElement).outerWidth(true);
                    tabElement = $(tabElement).prev();
                }
                scrollVal = calSumWidth($(tabElement).prevAll());
            }
        }
        $('.page-tabs-content').animate({
            marginLeft: 0 - scrollVal + 'px'
        }, "fast");
    }
    //查看右侧隐藏的选项卡
    function scrollTabRight() {
        var marginLeftVal = Math.abs(parseInt($('.page-tabs-content').css('margin-left')));
        // 可视区域非tab宽度
        var tabOuterWidth = calSumWidth($(".content-tabs").children().not(".J_menuTabs"));
        //可视区域tab宽度
        var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
        //实际滚动宽度
        var scrollVal = 0;
        if ($(".page-tabs-content").width() < visibleWidth) {
            return false;
        } else {
            var tabElement = $(".J_menuTab:first");
            var offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) <= marginLeftVal) {//找到离当前tab最近的元素
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            offsetVal = 0;
            while ((offsetVal + $(tabElement).outerWidth(true)) < (visibleWidth) && tabElement.length > 0) {
                offsetVal += $(tabElement).outerWidth(true);
                tabElement = $(tabElement).next();
            }
            scrollVal = calSumWidth($(tabElement).prevAll());
            if (scrollVal > 0) {
                $('.page-tabs-content').animate({
                    marginLeft: 0 - scrollVal + 'px'
                }, "fast");
            }
        }
    }

    //通过遍历给菜单项加上data-index属性
    $(".J_menuItem").each(function (index) {
        if (!$(this).attr('data-index')) {
            $(this).attr('data-index', index);
        }
    });

    function menuItem(menu) {
    	if($(menu).hasClass('J_menuItem') || $(menu).hasClass('J_subMenuItem')){
            var list = document.getElementById("page-tabs-content");
            Sortable.create(list); // That's all.
            // 获取标识数据
            var parentUrl = $('li.active').children('.category-menu').data('url');
            var dataUrl = $(menu).attr('href'),
                dataIndex = $(menu).data('index'),
                menuName = $.trim($(menu).text()),
                flag = true;
            /*if(parentUrl != dataUrl) {
                dataUrl = (parentUrl == undefined || parentUrl == '' ? '' : parentUrl + '/') + dataUrl;
            }*/
            $curmenu = $(menu);
            if (dataUrl == undefined || $.trim(dataUrl).length == 0)return false;
    
            // 选项卡菜单已存在
            $('.J_menuTab').each(function () {
                if ($(this).data('id') == dataUrl) {
                    if (!$(this).hasClass('active')) {
                        $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                        scrollToTab(menu);
                        // 显示tab对应的内容区
                        $('.J_mainContent .J_iframe').each(function () {
                            if ($(this).data('id') == dataUrl) {
                                $(this).show().siblings('.J_iframe').hide();
                                return false;
                            }
                        });
                    }else {
                    }
                    flag = false;
                    return false;
                }
            });
    
            // 选项卡菜单不存在
            if (flag) {
                var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle closable"></i></a>';
                $('.J_menuTab').removeClass('active');
    
                // 添加选项卡对应的iframe
                var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
                $('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);
    
                //显示loading提示
               var loading = layer.msg('拼命加载中。。。', {
                    icon: 16,
                    time: 10000000
                });
    
               $('.J_mainContent iframe:visible').load(function () {
                   //iframe加载完成后隐藏loading提示
                   layer.close(loading);
               });
                // 添加选项卡
                $('.J_menuTabs .page-tabs-content').append(str);
                scrollToTab($('.J_menuTab.active'));
            }
            highLightMenuItem(dataUrl);  //高亮对应的tab菜单
            return false;
        }
    }
    
    function subMenuItem() {
        // 获取标识数据
        var dataUrl = $(this).attr('href'),
            dataIndex = $(this).data('index'),
            menuName = $.trim($(this).text()),
            flag = true;
        $curmenu = $(this);
        if (dataUrl == undefined || $.trim(dataUrl).length == 0)return false;

        // 选项卡菜单已存在
        $('.J_menuTab').each(function () {
            if ($(this).data('id') == dataUrl) {
                if (!$(this).hasClass('active')) {
                    $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
                    scrollToTab(this);
                    // 显示tab对应的内容区
                    $('.J_mainContent .J_iframe').each(function () {
                        if ($(this).data('id') == dataUrl) {
                            $(this).show().siblings('.J_iframe').hide();
                            return false;
                        }
                    });
                }else {
                }
                flag = false;
                return false;
            }
        });

        // 选项卡菜单不存在
        if (flag) {
            var str = '<a href="javascript:;" class="active J_menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle closable"></i></a>';
            $('.J_menuTab').removeClass('active');

            // 添加选项卡对应的iframe
            var str1 = '<iframe class="J_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
            $('.J_mainContent').find('iframe.J_iframe').hide().parents('.J_mainContent').append(str1);

            //显示loading提示
           var loading = layer.msg('拼命加载中。。。', {
                icon: 16,
                time: 10000000
            });

           $('.J_mainContent iframe:visible').load(function () {
               //iframe加载完成后隐藏loading提示
               layer.close(loading);
           });
            // 添加选项卡
            $('.J_menuTabs .page-tabs-content').append(str);
            scrollToTab($('.J_menuTab.active'));
        }
        highLightMenuItem(dataUrl);  //高亮对应的tab菜单
        return false;
    }

    //$('.J_menuItem').on('click', menuItem);

    // 关闭选项卡菜单
    function closeTab() {
        var closeTabId = $(this).parents('.J_menuTab').data('id');
        var currentWidth = $(this).parents('.J_menuTab').width();

        // 当前元素处于活动状态
        if ($(this).parents('.J_menuTab').hasClass('active')) {

            // 当前元素后面有同辈元素，使后面的一个元素处于活动状态
            if ($(this).parents('.J_menuTab').next('.J_menuTab').size()) {

                var activeId = $(this).parents('.J_menuTab').next('.J_menuTab:eq(0)').data('id');
                highLightMenuItem(activeId);  //高亮对应的tab菜单
                $(this).parents('.J_menuTab').next('.J_menuTab:eq(0)').addClass('active');

                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == activeId) {
                        $(this).show().siblings('.J_iframe').hide();
                        return false;
                    }
                });

                var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
                if (marginLeftVal < 0) {
                    $('.page-tabs-content').animate({
                        marginLeft: (marginLeftVal + currentWidth) + 'px'
                    }, "fast");
                }

                //  移除当前选项卡
                $(this).parents('.J_menuTab').remove();

                // 移除tab对应的内容区
                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                    }
                });
            }

            // 当前元素后面没有同辈元素，使当前元素的上一个元素处于活动状态
            if ($(this).parents('.J_menuTab').prev('.J_menuTab').size()) {
                var activeId = $(this).parents('.J_menuTab').prev('.J_menuTab:last').data('id');
                $(this).parents('.J_menuTab').prev('.J_menuTab:last').addClass('active');
                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == activeId) {
                        $(this).show().siblings('.J_iframe').hide();
                        return false;
                    }
                });

                //  移除当前选项卡
                $(this).parents('.J_menuTab').remove();

                // 移除tab对应的内容区
                $('.J_mainContent .J_iframe').each(function () {
                    if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                    }
                });
               highLightMenuItem(activeId);//高亮对应的tab菜单
            }
        }
        // 当前元素不处于活动状态
        else {
            //  移除当前选项卡
            $(this).parents('.J_menuTab').remove();

            // 移除相应tab对应的内容区
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
            scrollToTab($('.J_menuTab.active'));
        }
        return false;
    }

    $('.J_menuTabs').on('click', '.J_menuTab i.closable', closeTab);

    //关闭其他选项卡
    function closeOtherTabs(){
        $('.page-tabs-content').children("[data-id]").not(":first").not(".active").each(function () {
            $('.J_iframe[data-id="' + $(this).data('id') + '"]').remove();
            $(this).remove();
        });
        $('.page-tabs-content').css("margin-left", "0");
    }
    $('.J_tabCloseOther').on('click', closeOtherTabs);

    //滚动到已激活的选项卡
    function showActiveTab(){
        scrollToTab($('.J_menuTab.active'));
    }
    $('.J_tabShowActive').on('click', showActiveTab);


    // 点击选项卡菜单
    function activeTab() {
        if (!$(this).hasClass('active')) {
            var currentId = $(this).data('id');
           highLightMenuItem(currentId);  //高亮对应的tab菜单
            // 显示tab对应的内容区
            $('.J_mainContent .J_iframe').each(function () {
                if ($(this).data('id') == currentId) {
                    $(this).show().siblings('.J_iframe').hide();
                    return false;
                }
            });
            $(this).addClass('active').siblings('.J_menuTab').removeClass('active');
            scrollToTab(this);
        }
    }

    $('.J_menuTabs').on('click', '.J_menuTab', activeTab);

    // 左移按扭
    $('.J_tabLeft').on('click', scrollTabLeft);

    // 右移按扭
    $('.J_tabRight').on('click', scrollTabRight);

    // 关闭全部
    $('.J_tabCloseAll').on('click', function () {
        $('.page-tabs-content').children("[data-id]").not(":first").each(function () {
            $('.J_iframe[data-id="' + $(this).data('id') + '"]').remove();
            $(this).remove();
        });
        $('.page-tabs-content').children("[data-id]:first").each(function () {
            $('.J_iframe[data-id="' + $(this).data('id') + '"]').show();
            $(this).addClass("active");
        });
        $('.page-tabs-content').css("margin-left", "0");
    });
    
    var htmlStr = '';
    
    /**
     * 获取子系统菜单
     * menu：菜单对象
     */
    function getMenu(menu){
    	$.ajax({
    		url: ctxPath + '/menu/childMenus',
    		type: 'post',
    		dataType: 'json',
    		data: {
    			category: $(menu).data('id')
    		},
    		success: function(result){
    			htmlStr = '';
    			menuTree(result, '');
    			if(htmlStr != ''){
    				$('#menu-category').html($(menu).html())
    				$('#sidebar-menu').html(htmlStr);
    				$('#sidebar').removeClass('hidden');
    				$('#main-content').addClass('sidebar-left');
    				$('#main-content').removeClass('sidebar-hidden');
    			}else {
    				$('#sidebar').addClass('hidden');
    				$('#main-content').removeClass('sidebar-left');
    				$('#main-content').addClass('sidebar-hidden');
    			}
    			//通过遍历给菜单项加上data-index属性
    	        $(".J_menuItem").each(function (index) {
    	            if (!$(this).attr('data-index')) {
    	                $(this).attr('data-index', index);
    	            }
    	        });
                // $('.J_subMenuItem').on('click', function(e){
                //     e.preventDefault();
                //     // subMenuItem();
                //     menuItem(e.target);
                // });
                $('.J_subMenuItem').on('click', subMenuItem);
                menuItem(menu)
    		}
    	})
    }
    $(function () {
      /**
       * 绑定左侧栏菜单点击事件
       * e：菜单对象
       */
      $('#categories').on('click', '.category-item', function (e) {
            e.preventDefault();
            $('li.active').removeClass('active');
            // li
            if($(e.target).hasClass('category-item')){
                $(e.target).addClass('active');
                getMenu($(e.target).children('.category-menu'));
                // a
            }else if($(e.target).hasClass('category-menu')){
                $(e.target).parent().addClass('active');
                getMenu(e.target);
                // i
            }else if($(e.target).hasClass('fa')){
                $(e.target).parent().parent().addClass('active');
                getMenu($(e.target).parent());
            }
        })
    })
    /**
     * 解析菜单树
     * menuList：菜单json数据
     */
    function menuTree(menuList) {
      $(menuList).each(function(i, menu){
        htmlStr += '<li data-level="'+menu.level+'">';
        htmlStr += '<a ';
        if(menu.hasChild){
          htmlStr += 'class="dropdown-toggle" href="javascript:void(0);" haschild="'+ menu.hasChild +'"';
        }else if(menu.url != '' && menu.url != undefined){
  				htmlStr += 'class="J_subMenuItem" href=\''+ (menu.url == "" ? "javascript:void(0);" : menu.url) + '\' '+
  				  'id="menu_'+ menu.url.replace(new RegExp("/", "g"), "") + '" haschild="'+ menu.hasChild +'"';
  			}
        htmlStr += '>';
  			if(menu.level == 1) {
  				htmlStr += '<i class="menu-icon '+ menu.icon +' fa-caret-left"></i>';
  			}
  			htmlStr += '<span class="menu-text" title="'+ menu.description +'">'+ menu.name +'</span>';
  			if(menu.hasChild){
  				htmlStr += '<b class="arrow fa fa-angle-down"></b>';
  			}
  		  htmlStr += '</a>';
  		
  		  if(menu.hasChild){
          htmlStr += '<ul class="submenu nav-hide" style="display:none;">';
  			  menuTree(menu.children);
          htmlStr += '</ul>';
  		  }
  	    htmlStr += '</li>';
      })
    }
});
