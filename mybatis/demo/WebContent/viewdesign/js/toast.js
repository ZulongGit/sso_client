/**
 * 消息提示
 * @type {{success: Toast.success, info: Toast.info, warning: Toast.warning, error: Toast.error, _show: Toast._show, init: Toast.init}}
 * @msg 消息内容 必填
 * @title 消息标题 可选
 * @fn 点击消息触发 可选
 * @center 是否居中显示 true/false 可选，默认false
 */
var Toast = {
    success: function (msg, title, fn, center) {
        Toast._show("success", msg, title, fn, center);
    },
    successCenter: function (msg, title, fn) {
        Toast._show("success", msg, title, fn, true);
    },
    info: function (msg, title, fn, center) {
        Toast._show("info", msg, title, fn, center);
    },
    infoCenter: function (msg, title, fn) {
        Toast._show("info", msg, title, fn, true);
    },
    warning: function (msg, title, fn, center) {
        Toast._show("warning", msg, title, fn, center);
    },
    warningCenter: function (msg, title, fn) {
        Toast._show("warning", msg, title, fn, true);
    },
    error: function (msg, title, fn, center) {
        Toast._show("error", msg, title, fn, center);
    },
    errorCenter: function (msg, title, fn) {
        Toast._show("error", msg, title, fn, true);
    },
    _show: function (type, msg, title, fn, center) {
        if (fn)
            top.toastr.options.onclick = fn;
        if (center) {
            top.toastr.options.positionClass = "toast-center";
            top.toastr.options.showMethod = "fadeIn";
            top.toastr.options.hideMethod = "fadeOut";
        } else {
            top.toastr.options.positionClass = "toast-top-right";
            top.toastr.options.showMethod = "show";
            top.toastr.options.hideMethod = "hide";
        }
        if (title)
            top.toastr[type](msg, title);
        else
            top.toastr[type](msg);
    },
    init: function () {
        //引入必要的样式+JS文件
        top.ns.requireCSS("/framework/plugins/toastr/toastr.min.css");
        top.ns.requireJS("/framework/plugins/toastr/toastr.min.js");
        top.toastr.options = {
            closeButton: false,
            debug: false,
            positionClass: "toast-top-right",
            onclick: null,
            showDuration: 300,
            hideDuration: 300,
            timeOut: 3000,
            extendedTimeOut: 1000,
            showEasing: 'swing',
            hideEasing: 'linear',
            showMethod: 'show',
            hideMethod: 'hide'
        };
    }
};
ns.ready(function () {
    try {
        ns.tip.toast = Toast;
        ns.tip.toast.init();
    } catch (e) {
        alert("请引入plugins/toastr/toastr.min.js");
    }
});