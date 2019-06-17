var tooltip = function() {
	return {
		init : function() {
			try {
				$("[data-toggle=tooltip]").tooltip();
				$("[data-toggle=popover]").popover().click(function(e) {
					e.preventDefault()
				})
			} catch (e) {
				
			}
		}
	}
}();
$(function() {
	tooltip.init();
	if(typeof(ns) != "undefined")
		ns.tip.tooltip = tooltip;
});