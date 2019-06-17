fd.design = {
    view: { //视图组件
        $currentView: undefined,
        $currentViews: [], //选择的视图集合
        currentView: function() { //当前操作的视图
            return fd.design.view.$currentView || $(fd.config.viewCurrentTag + ":eq(0)");
        },
        currentViews: function() { //当前操作的视图集合
            return fd.design.view.$currentViews || $(fd.config.viewCurrentTag);
        },
        setCurrentView: function(view) {
            if (view) {
                fd.design.view.$currentView = view;
                fd.design.view.setCurrentViews([]);
                fd.design.view.addCurrentView(view);
            }
        },
        addCurrentView: function(view) {
            if (view) fd.design.view.$currentViews.push(view);
        },
        setCurrentViews: function(views) {
            if (views) fd.design.view.$currentViews = views;
        }
    },
    properties: { //属性组件
        hideAll: function() { //隐藏所有控件
            $(fd.config.propertiesPanel + " [" + fd.config.propertiesTag + "]").addClass("hide");
            //关闭无子项的选项卡
            $("#pro_content2 .panel").each(function(i, panel) {
                panel = $(panel);
                var len = panel.find("[fd-property]:not(.hide)").length;
                if (len == 0)
                    panel.addClass("hide");
                else
                    panel.removeClass("hide");
            });
        },
        get: function(name) { //获取控件
            return $(fd.config.propertiesPanel + " [" + fd.config.propertiesTag + "='" + name + "']");
        },
        hide: function(name) {
            fd.design.properties.get(name).addClass("hide");
        },
        show: function(name) {
            fd.design.properties.get(name).removeClass("hide");
        },
        open: function(cView) {
            cView = cView || fd.design.view.currentView(); //设置当前视图
            if (!cView.attr(fd.config.viewTag)) return false; //确保是视图组件
            var viewName = jQuery.trim(cView.attr(fd.config.viewTag));
            cView.attr(fd.config.viewTag, viewName);
            var view = fd.config.view[viewName]; //视图配置
            fd.design.properties.hideAll();
            $.each(view.properties, function(i, n) {
                var prop = fd.config.properties[n];
                if (prop) {
                    fd.design.properties.show(n);
                    //初始化方法
                    var control = fd.design.properties.get(n);
                    var opt = fd.design.util.opt(control, prop, cView);

                    prop.events.init(opt);
                }
            });
            //关闭无子项的选项卡
            $("#pro_content2 .panel").each(function(i, panel) {
                panel = $(panel);
                var len = panel.find("[fd-property]:not(.hide)").length;
                if (len == 0)
                    panel.addClass("hide");
                else
                    panel.removeClass("hide");
            });
        }
    },
    init: function() { //属性编辑器初始化
        console.log(urlFix);
        //#1 初始化拖拽
        fd.design.drag_view_content = undefined;
        $(fd.config.viewDragTag).dragg();
        var content;
        // 开始拖动
        $(document).on('dragstart', fd.config.viewDragTag, function(e) {
            content = $(this);
        });
        // 拖动过程中
        // $(document).on('drag', 'clone', function(e, ui) {
        //     $(this).css('opacity', 0.5);
        // });
        // 拖进指定区域
        $(document).on('dragin', fd.config.viewDragInTag, function(e) {
            $(this).addClass("bg-default");
            e.stopPropagation();
        });
        // 拖出指定区域
        $(document).on('dragout', fd.config.viewDragInTag, function(e) {
            $(this).removeClass("bg-default");
        });
        // 拖动结束
        $(document).on('drop', fd.config.viewDragInTag, function(e) {
            var that = this;
            if (fd.design.drag_view_content && fd.design.dragmode == true) {
                var view = fd.design.drag_view_content.clone();
                $(this).append(view);
                $(this).removeClass("bg-default");
                fd.design.bindViewEvent(view); // 绑定视图触发属性事件
                fd.design.drag_view_content.remove();
                fd.design.drag_view_content = undefined;
                fd.design.disabledrag(1, 2, view);
                fd.design.enabledrag(1, 2, view);
            } else {
                var element = $(content).parent().next();
                var view = element.clone().children();
                if ($(this).attr('id') == 'containers' && !$(view).hasClass('col')) {
                    toastr.warning('请放置在虚线布局容器中');
                    $(this).removeClass("bg-default");
                    return;
                }
                $(this).append(view);
                $(this).removeClass("bg-default");
                fd.design.bindViewEvent(view); // 绑定视图触发属性事件
                //初始化图表
                if ($(view['0']).hasClass('charts')) {
                    fd.design.chartsInit(view);
                }
            }
            return false;
        });
        //#2 初始化属性控件事件
        $(fd.config.propertiesPanel + " " + fd.config.supportType).each(function() {
            var proptype = $(this).parent().attr(fd.config.propertiesTag);
            var prop = fd.config.properties[proptype];
            if (!prop) return;
            var property = $(this);
            $.each(prop.events, function(i, event) {
                if (i.length > 1 && i.substr(0, 2) == "on") {
                    property.bind(i.substr(2), function() {
                        var opt = fd.design.util.opt($(this), prop);
                        event(opt);
                        opt.data.save(); //保存属性值
                    });
                }
            });
        });
        $(fd.config.propertiesPanel + " .colorPicker").colorpicker();
        $(".charts-color").colorpicker();
        $('.charts-color').colorpicker().on('changeColor', function(ev) {
            $(this).attr('value', ev.color.toHex()).change();
            $(this).css('background-color', ev.color.toHex());
        });
        //#3 初始化视图组件事件，用来触发属性面板
        fd.design.bindViewEvent();
        //#4 初始化表单视图面板事件，点击表单视图，切换到表单属性选项卡
        $(fd.config.containerPanel).bind("click", function() {
            fd.design.unselecteAllView();
            $(this).addClass(fd.config.viewSelectedClass);
            $("#propertyTab a:first").tab("show");
        });
        //#5 增加删除按钮事件监听
        // $(document).bind("keydown", function(e) {
        //     if (e.keyCode == 46 || e.keyCode == 8) return fd.design.remove();
        // });

        //#6 美化
        //设置属性编辑器滚动条
        // $(fd.config.propertiesPanel).slimScroll({
        // 	height:($(document).height()-100)+"px"
        // });
        //隐藏所有属性，点击后出现
        fd.design.properties.hideAll();
        var page_num = location.href.split('?')[1];
        if(page_num){
            fd.design.restore_layout(page_num);
        }
    },
    chartsInit: function(view) {
        var len = $('#containers .charts').length,
        	cid = 'chart-box' + len,
        	optType = $(view).attr('name');
        $(view).attr('id', cid);
        ecinit(cid, ec_opts[optType]);
        $(view).on("click", function() { //绑定菜单开关事件
            $('.tab-pane').removeClass('active').addClass('fade');
            $('#pro_content3').addClass('active').removeClass('fade');
            $('#propertyTab li:nth-of-type(2)').addClass('active').siblings().removeClass('active');
            loadValue(view);
        });
    },
    bindViewEvent: function($views) {
        if ($views) {
            if (!$views.length)
                $views = [$views];
            $.each($views, function(i, $views) {
                var childViews = $($views).find("[" + fd.config.viewTag + "]"); //获取下属子试图
                $views = [$($views)];
                //初始化子视图
                $views.push(childViews);
                $.each($views, function(i, $view) {
                    $view = $($view);
                    var view = fd.config.view[$view.attr(fd.config.viewTag)];
                    if (view)
                        if (view.events && view.events.init) view.events.init($view);
                    fd.design._bindViewEvent($view);
                });
                //fd.design._bindViewEvent($view.find("["+fd.config.viewTag+"]"));
            });
        } else {
            $(fd.config.containerPanel + " [" + fd.config.viewTag + "]").each(function() {
                if ($(this).attr("editable") != "false") {
                    var view = fd.config.view[$(this).attr(fd.config.viewTag)];
                    if (view)
                        if (view.events && view.events.init) view.events.init($(this));
                    fd.design._bindViewEvent($(this));
                }
            });
        }
    },
    _bindViewEvent: function(view) { //内部方法
        view.unbind("click");
        view.bind("click", function(e) {
            if (e.ctrlKey) { // @TODO 多选操作
                fd.design.view.addCurrentView($(this));
                fd.design.selectViews();
            } else if (e.altKey) { //选择父容器
                fd.design.selectView($(this).parents("[" + fd.config.viewTag + "][id]"));
            } else {
                fd.design.selectView($(this));
                $('#pro_content3').removeClass('active').addClass('fade'); //属性编辑菜单切换
                $('#pro_content2').removeClass('fade').addClass('active');
            }
            return false;
        });
    },
    disabledrag: function(btn1, btn2, $view) {
        if ($view) $view = [$view];
        fd.design.dragmode = false;
        fd.design.drag_view_content = undefined;
        $(btn1).removeClass("hide");
        $(btn2).addClass("hide");

        $(fd.config.containerPanel + " [" + fd.config.viewTag + "]").each(function(i, view) {
            view = $(view);
            view.off('mousedown');
        });
        $(fd.config.containerPanel + " [" + fd.config.moveTag + "]").removeClass("move");
    },
    enabledrag: function(btn1, btn2, $view) {
        if ($view) $view = [$view];
        fd.design.dragmode = true;
        $(btn1).addClass("hide");
        $(btn2).removeClass("hide");
        $(fd.config.containerPanel + " [" + fd.config.viewTag + "]").each(function(i, view) {
            view = $(view);
            view.dragg();
            view.on('dragstart', view, function(e) {
                if (!fd.design.drag_view_content)
                    fd.design.drag_view_content = $(this);
            });
            view.on('mousedown');
        });
        $(fd.config.containerPanel + " [" + fd.config.moveTag + "]").addClass("move");
    },
    // editMode: function(target) { //源码编辑模式
    //     var modal = $(target);
    //     var textarea = modal.find("textarea");
    //     textarea.height($(document).height() * 0.6); //设置textarea的高度
    //     var html = $(fd.config.containerPanel).html();
    //     //html = html_beautify(html);//代码格式化
    //     textarea.html(html); // 设置显示的html代码

    //     modal.find("#btnOk").unbind("click");
    //     modal.find("#btnOk").bind("click", function(e) {
    //         var textarea = modal.find("textarea");
    //         var newHtml = html_beautify(textarea.val());
    //         $(fd.config.containerPanel).html(newHtml);
    //         //重新绑定事件
    //         fd.design.bindViewEvent();
    //         modal.modal("hide");
    //     });
    //     modal.modal("show");
    // },
    // editScript: function(target) { //脚本编辑模式 
    //     return alert("功能未开放！");
    //     var modal = $(target);
    //     var textarea = modal.find("textarea");
    //     textarea.height($(document).height() * 0.6); //设置textarea的高度
    //     var html = $(fd.config.containerPanel + " #script").html();
    //     textarea.html(html); // 设置显示的html代码

    //     modal.find("#btnOk").unbind("click");
    //     modal.find("#btnOk").bind("click", function(e) {
    //         var textarea = modal.find("textarea");
    //         //测试脚本
    //         try {
    //             var newHtml = textarea.val();
    //             eval(newHtml);
    //             $(fd.config.containerPanel + " #script").html(newHtml);
    //             modal.modal("hide");
    //         } catch (e) {
    //             alert("脚本有错误！");
    //         }

    //     });
    //     modal.modal("show");
    // },
    move: function(arrow) { //移动
        var view = fd.design.view.currentView();
        if (!view[0]) return alert("请先选择要移动的组件！");
        if (arrow == 'up') {
            view = $(view[0]);
            var prevView = view.prev();
            if (prevView[0]) {
                prevView.before(view);
            }
        } else if (arrow == 'down') {
            view = $(view[0]);
            var nextView = view.next();
            if (nextView[0]) {
                nextView.after(view);
            }
        }
    },
    selectView: function(view) { //选中视图
        fd.design.unselecteAllView();
        fd.design.properties.open(view);
        fd.design.view.setCurrentView(view);
        view.addClass(fd.config.viewSelectedClass); //增加选中样式
        view.addClass(fd.config.viewCurrentTag.replace(".", "")); //增加当前视图标识
        $("#propertyTab a:last").tab("show");
    },
    selectViews: function(views) {
        views = views || fd.design.view.currentViews();
        $.each(views, function(i, view) {
            fd.design.properties.open(view);
            if (!fd.design.view.currentView() || fd.design.view.currentView().length == 0)
                fd.design.view.setCurrentView(view);
            view.addClass(fd.config.viewSelectedClass); //增加选中样式
            view.addClass(fd.config.viewCurrentTag.replace(".", "")); //增加当前视图标识
        });
        $("#propertyTab a:last").tab("show");
    },
    unselecteAllView: function() { //清除所有选中的view
        $(fd.config.viewCurrentTag).removeClass(fd.config.viewCurrentTag.replace(".", "")); //移除当前视图标识
        $("." + fd.config.viewSelectedClass).removeClass(fd.config.viewSelectedClass); //移除选中样式
        //清理内存数据
        fd.design.view.setCurrentView(undefined);
        fd.design.view.setCurrentViews([]);
        fd.design.properties.hideAll();
    },
    table: {
        merge: function() {
            var selecteds = fd.design.view.currentViews();
            if (selecteds.length < 2) {
                alert("选中2个单元格以上才可以合并！\n（按住Ctrl可以多选）");
                return;
            }
            $.each(selecteds, function(i, item) {
                if (!item.is("td")) {
                    alert("选中的不全是单元格！");

                }
            });
            // 判断是否是同一行
            var sameRow = true;
            var _index;
            for (var i = 0; i < selecteds.length; i++) {
                if (_index != undefined) {
                    var _index2 = $(selecteds[i]).parents("tr").index();
                    if (_index != _index2) {
                        sameRow = false;
                    }
                } else {
                    _index = $(selecteds[i]).parents("tr").index();
                }
            }
            if (sameRow) {
                var firstTd = $(selecteds[0]);
                var colspan = firstTd.attr("colspan") ? parseInt(firstTd
                        .attr("colspan")) +
                    selecteds.length - 1 : selecteds.length;
                firstTd.attr("colspan", colspan);
            } else {
                var firstTd = $(selecteds[0]);
                var colspan = firstTd.attr("rowspan") ? parseInt(firstTd
                        .attr("rowspan")) +
                    selecteds.length - 1 : selecteds.length;
                firstTd.attr("rowspan", colspan);
            }
            for (var i = 1; i < selecteds.length; i++) {
                $(selecteds[i]).remove();
            }
            //fd.design.bindViewEvent(newTr);
            fd.design.unselecteAllView();
        },
        insertRow: function(arrow) {
            var selecteds = fd.design.view.currentView();
            if (selecteds.length <= 0) {
                alert("请先选中单元格！");
                return;
            }
            var tagName = selecteds[0].tagName.toLowerCase();
            var tr;
            switch (tagName) {
                case "td":
                    tr = $(selecteds[0]).parents("tr");
                    break;
                case "tr":
                    tr = $(selecteds[0]);
                    break;
                case "table":
                    tr = $(selecteds[0]).find("tr:last");
                    break;
            }
            if (tr) {
                var newTr = $(tr.clone());
                newTr.find("." + fd.config.viewSelectedClass).removeClass(fd.config.viewSelectedClass);
                if (arrow == "down")
                    tr = newTr.insertAfter(tr);
                else
                    tr = newTr.insertBefore(tr);
                tr.find("td").removeClass("selected");
                tr.find("td").text("");
            }
            fd.design.bindViewEvent(newTr);
        },
        insertCol: function(arrow) {
            var selecteds = fd.design.view.currentView();
            if (selecteds.length <= 0) {
                alert("先选中单元格！");
                return;
            }
            var tagName = selecteds[0].tagName.toLowerCase();
            var newTds = [];
            switch (tagName) {
                case "td":
                    var index = $(selecteds[0]).index();
                    var table = $(selecteds[0]).parents("table");
                    table.find("tr").each(function() {
                        var td = $(this).find("td:eq(" + index + ")");
                        var newTd = $(td.clone());
                        newTd.removeClass(fd.config.viewSelectedClass);
                        if (arrow == "right")
                            td = newTd.insertAfter(td);
                        else
                            td = newTd.insertBefore(td);
                        td.removeClass("selected");
                        td.text("");

                        newTds.push(newTd);
                    });
                    break;
                case "tr":
                    var table = $(selecteds[0]).parents("table");
                    table.find("tr").each(function() {
                        var td = $(this).find("td:first");
                        var newTd = $(td.clone());
                        newTd.removeClass(fd.config.viewSelectedClass);
                        if (arrow == "right")
                            td = newTd.insertAfter(td);
                        else
                            td = newTd.insertBefore(td);
                        td.removeClass("selected");
                        td.text("");

                        newTds.push(newTd);
                    });
                    break;
            }
            fd.design.bindViewEvent(newTds);
        }
    },
    remove: function() {
        var element = $(fd.config.viewCurrentTag);
        if (element.parent().is("tr") && element.parent().children().length == 0) //如果tr下已经没有td，则删除该tr
            element.parent().remove();
        else
            element.remove();
        fd.design.properties.hideAll();
    },
    _html: function() { //处理HTML
        var panel = $(fd.config.containerPanel).clone(); //复制
        panel.find("[data]").removeAttr("data"); //移除data属性
        var html = panel[0].outerHTML;
        return html;
    },
    _new: function(c) { //新建
        if ($(c).find('.p5-25.tag').length != 0) {
            swal({
                title: "",
                text: "页面内容是否已保存？",
                type: "warning",
                showCancelButton: "true",
                showConfirmButton: "true",
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                animation: "slide-from-top"
            }, function() {
                fd.design.view.setCurrentView(undefined);
                fd.design.view.setCurrentViews([]);
                fd.design.unselecteAllView();
                $(c).html('');
                toastr.success('已新建页面');
            });
        } else {
            $(c).html('');
            toastr.success('已新建页面');
        }
    },
    cache_html: function(c) { //缓存html
        fd.design.unselecteAllView();
        var $cache = $('#layout-cache');
        $cache.html($(c).html());
        $cache.find('.tag').removeClass('tag');
        $cache.find('.charts').removeAttr('_echarts_instance_').children().remove();
        var _html = $cache.html();
        $cache.html('');
        return _html;
    },
    page_html: function(c) { //缓存html
        //fd.design.unselecteAllView();
        var $cache = $('#layout-cache');
        $cache.html($(c).html());
        //$cache.find('.tag').removeClass('tag');
        //$cache.find('.charts').removeAttr('_echarts_instance_').children().remove();
        var _html = $cache.html();
        $cache.html('');
        return _html;
    },
    cache_opt: function(c) { //缓存opt
        var id;
        var dom_box = [];
        var charts = $(c + ' .charts');
        $.each(charts, function() {
            id = $(this).attr('id');
            dom_box.push(echarts.getInstanceByDom(document.getElementById(id)).getOption());
        });
        return dom_box;
    },
    updata: function(){ //更新
        var id = $('#cur-id').val();
        var name = $('#form_id').val();
       
        if(id){
            var _html = fd.design.page_html('#containers');
            var dom_box = fd.design.cache_opt('#containers');
            var domOpt = JSON.stringify(dom_box);
            var now = new Date().toLocaleString();
            var o = {};
            //o.time = now;
            ////o.html = _html;
            //o.domOpt = dom_box;
            
            o.id = id;
            o.pageName = name;
            o.pageContent = _html;
            o.pageData = domOpt;
            alert(o.id);
            $.ajax({  
                type: "POST",  
                url: "/demoPlat/pageset/kbisPage/saveExt" ,  
                data: JSON.stringify(o),  
                contentType: "application/json; charset=utf-8",  
                dataType: "json",  
                success: function(data) {
                    toastr.success('已保存');
                }
            });
        }else{
            $('#save-btn').click();
        }
        
    },
    save_layout: function() { //保存布局
        var name = $('#form_id').val();
        if (!name) {
            toastr.error('标题不能为空!');
            return;
        } else {
            var _html = fd.design.page_html('#containers');
            var dom_box = fd.design.cache_opt('#containers');
            var domOpt = JSON.stringify(dom_box);
            var now = new Date().toLocaleString();
            var resetList = JSON.parse(localStorage.getItem('resetList')) || [];
            var reslen = resetList.length || 0;
            var o = {};
            //o.id = reslen + 1;
            o.pageName = name;
            //o.time = now;
            o.pageContent = _html;
            //o.domOpt = "";
            o.pageData = "";
            //alert(JSON.stringify(o));
            
             //FIXME id 由后端产生，用于查询，更新和删除
             $.ajax({  
                 type: "POST",  
                 url: "/demoPlat/pageset/kbisPage/saveExt",  
                 data: JSON.stringify(o),
                 contentType: "application/json; charset=utf-8",  
                 //dataType: "json",  
                 success: function(data) {  
                     //$('#cur-id').val(data.id);
                 }
             });
            
            resetList.push(o);
            localStorage.setItem('resetList', JSON.stringify(resetList));
            toastr.success('已保存');
            $('#cur-id').val(o.id);
            $('#form_id').val('');
            $('#closeModal').click();
        }
    },
    find_item: function() {
        var resetList = JSON.parse(localStorage.getItem('resetList'));
        var id = $(this).parent().parent().children().eq(0).html();
        var item;
        for (i = 0; i < resetList.length; i++) {
            if (resetList[i].id == id) {
                item = resetList[i];
            }
        }
        return item;
    },
    load_reset: function() { //获取项目列表
       /*
    	var resetList = JSON.parse(localStorage.getItem('resetList'));
        var table_list = $('#resetModal tbody');
        $(table_list).html('');
        var i, list_item;
        for (i = 0; i < resetList.length; i++) {
            list_item = '<tr>' +
                '   <td>' + (i + 1) + '</td>' +
                '   <td>' + resetList[i].name + '</td>' +
                '   <td>' + resetList[i].time + '</td>' +
                '   <td><button class="btn btn-success load-reset">导入</button> <button class="btn btn-primary preview-reset">预览</button> <button class="btn btn-danger del-reset">删除</button></td>' +
                '</tr>';
            table_list.append(list_item);
        }
        
        $.ajax({  
            type: "GET",  
            url: urlFix,   
            contentType: "application/json; charset=utf-8",  
            dataType: "json",  
            success: function(data) {  
                var table_list = $('#resetModal tbody');
                $(table_list).html('');
                var i, list_item, len = data.length;
                for (i = 0; i < len; i++) {
                    list_item = '<tr>' +
                        '   <td>' + data[i].id + '</td>' +
                        '   <td>' + data[i].name + '</td>' +
                        '   <td>' + data[i].time + '</td>' +
                        '   <td><button class="btn btn-success load-reset">导入</button> <button class="btn btn-primary preview-reset">预览</button> <button class="btn btn-danger del-reset">删除</button></td>' +
                        '</tr>';
                    table_list.append(list_item);
                }
            }
        });*/
    },
    remove_reset: function() { //删除项目
        var resetList = JSON.parse(localStorage.getItem('resetList'));
        var id = $(this).parent().parent().children().eq(0).html();
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
                if (resetList[i].id == id) {
                    resetList.splice(i, 1);
                }
            }
            localStorage.setItem('resetList', JSON.stringify(resetList));
            fd.design.load_reset();
            toastr.error('已删除');
        });
        // ajax
        // var id = $(this).parent().parent().children().eq(0).html();
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
        //         url: urlFix + id,   
        //         contentType: "application/json; charset=utf-8",  
        //         dataType: "json",  
        //         success: function(data) {  
                    
        //         }
        //     });
        //     fd.design.load_reset();
        //     toastr.error('已删除');
        // });
    },
    item_preview: function() { //预览
        var id, _html, dom_box;
        var item = fd.design.find_item.call(this);
        console.log(item);
        _html = item.html;
        dom_box = item.domOpt;
        $('#mask').fadeIn(200);
        $('#mask .content').html(_html);
        $.each($('#mask .charts'), function(i) {
            $(this).attr('id', $(this).attr('id') + 'mask');
            id = $(this).attr('id');
            ecinit(id, dom_box[i]);
        });
    },
	preview: function() { //预览
        var id;
        var _html = fd.design.cache_html('#maincontent');
        var dom_box = fd.design.cache_opt('#containers');
        $('#mask').fadeIn(200);
        $('#mask .content').html(_html);
        $.each($('#mask .charts'), function(i) {
            $(this).attr('id', $(this).attr('id') + 'mask');
            id = $(this).attr('id');
            ecinit(id, dom_box[i]);
        });
    },
    restore_layout: function(page) { //还原布局
        var c = '#containers';
        var i, _html, domOpt;
        var resetList = JSON.parse(localStorage.getItem('resetList'));
        var id = page || $(this).parent().parent().children().eq(0).html();
        $('#cur-id').val(id);
        if(resetList)
        	{
		        for (i = 0; i < resetList.length; i++) {
		            if (resetList[i].id == id) {
		                _html = resetList[i].html;
		                domOpt = resetList[i].domOpt;
		            }
		        }
        
        	}
        $(c).html(_html);
        $.each($(c + ' .charts'), function(k) {
            id = $(this).attr('id');
            ecinit(id, domOpt[k]);
        });
        toastr.success('已导入');
        fd.design.bindViewEvent();
        fd.design.charts_bind(c);
        $('#closeResetModal').click();
        //ajax
        // var c = '#containers';
        // var _html, domOpt;
        // var id = page || $(this).parent().parent().children().eq(0).html();
        // $.ajax({  
        //     type: "GET",  
        //     url: urlFix + id,   
        //     contentType: "application/json; charset=utf-8",  
        //     dataType: "json",  
        //     success: function(data) {  
        //         _html = data.html;
        //         domOpt = data.domOpt;
        //         $(c).html(_html);
        //         $.each($(c + ' .charts'), function(k) {
        //             id = $(this).attr('id');
        //             ecinit(id, domOpt[k]);
        //         });
        //         toastr.success('已导入');
        //         fd.design.bindViewEvent();
        //         fd.design.charts_bind(c);
        //         $('#closeResetModal').click();
        //         $('#cur-id').val(data.id);
        //     }
        // });
    },
    charts_bind: function(c) { //生成图表绑定事件
        $(c + ' .charts').unbind("click");
        $(c + ' .charts').bind("click", function(e) {
            if (e.ctrlKey) {
                fd.design.view.addCurrentView($(this));
                fd.design.selectViews();
            } else if (e.altKey) {
                fd.design.selectView($(this).parents("[" + fd.config.viewTag + "][id]"));
            } else {
                fd.design.selectView($(this));
                $('#pro_content3').removeClass('fade').addClass('active'); //属性编辑菜单切换
                $('#pro_content2').removeClass('active').addClass('fade');
                $('#propertyTab li:nth-of-type(2)').addClass('active').siblings().removeClass('active');
            }
            return false;
        });
    },
    closeMask: function() { //关闭项目列表窗口
        $('#mask .content').html('').parent().fadeOut(200);
    },
    showProperties: function(obj, target, panel) { //显示属性面板
        $(panel).removeClass("hide");
        $(obj).addClass("hide");
        $(target).removeClass("hide");
        $('#content-box').removeClass('col-md-12').addClass('col-md-10');
    },
    hideProperties: function(obj, target, panel) { //隐藏属性面板
        $(panel).addClass("hide");
        $(obj).addClass("hide");
        $(target).removeClass("hide");
        $('#content-box').removeClass('col-md-10').addClass('col-md-12');
    },
    util: { //工具类
        data: function(view) { //获取view视图的data属性，save方法保存
            view = view || fd.design.view.currentView();
            var data = view.attr("data");
            if (!data)
                data = {};
            else {
                data = data.replace(new RegExp("'", 'gm'), '"');
                data = JSON.parse(data);
            }
            data.save = function() {
                var cview = fd.design.view.currentView();
                cview.attr("data", JSON.stringify(data));
            };
            return data;
        },
        opt: function(control, property, view) { //生成事件传递参数，control：属性视图控件，prop:视图配置
            var opt = {
                control: control, //属性控件
                property: property, //属性控件配置参数
                view: view || fd.design.view.currentView(), //view视图控件
                data: fd.design.util.data(view || fd.design.view.currentView()) //view视图数据
            };
            return opt;
        },
        rgb2hex: function(rgb) { // 颜色值转换
            rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/) || rgb.match(/^rgba\((\d+),\s*(\d+),\s*(\d+),\s*(\d+)\)$/);

            function hex(x) {
                return ("0" + parseInt(x).toString(16)).slice(-2).toUpperCase();
            }
            return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]).toUpperCase();
        }
    }
};