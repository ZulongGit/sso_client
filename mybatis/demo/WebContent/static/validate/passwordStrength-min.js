(function (a) {
	a.fn.passwordStrength = function (b) {
		b = a.extend({}, a.fn.passwordStrength.defaults, b);
		this.each(function () {
			var d = a(this),
			e = 0,
			c = false,
			f = a(this).parents("form").find(".passwordStrength");
			var strRegex = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![!@#$%^&*\\-_]+$)[0-9a-zA-Z!@#$%^&*\\-_]{8,32}$"; 
			var reg = new RegExp(strRegex);
			
			//用来判断有非法字符的
			var strRegex1 = "^((?=[0-9a-zA-Z!@#$%^&*\\-_]).)*$";
			var reg1 = new RegExp(strRegex1);
			
			//用来判断有没有合法特殊字符
			var strRegex2 = "[!@#$%^&*\\-_]";
			var reg2 = new RegExp(strRegex2);
			
			d.bind("keyup blur", function () {
				e = a.fn.passwordStrength.ratepasswd(d.val(), b);
				e >= 0 && c == false && (c = true);
				f.find("span").removeClass("bgStrength");
				f.find("span").removeClass("bgStrength1");
				f.find("span").removeClass("bgStrength2");
				$("#n1").removeAttr("style");
				$("#y1").css('display','none');
				
				$("#n2").removeAttr("style");
				$("#y2").css('display','none');
				
				$("#y3").css('display','none');
				$("#n3").removeAttr("style");
				$("#n4").css('display','none');
				if (e < 35 && e >= 0) {
					f.find("span:first").addClass("bgStrength1");
					f.find("span").eq(0).html("弱");
				} else {
					if (e < 60 && e >= 35) {
						f.find("span:lt(2)").addClass("bgStrength2")
						f.find("span").eq(0).html("");
						f.find("span").eq(1).html("中");
					} else {
						if (e >= 60) {
							f.find("span:lt(3)").addClass("bgStrength")
							f.find("span").eq(0).html("");
							f.find("span").eq(1).html("");
							f.find("span").eq(2).html("强");
						}
					}
				}
				//监听非法字符情况
				if(!reg1.test(d.val())){//有非法字符
					$("#n3").css('display','none');
					$("#y3").css('display','none');
					$("#n4").removeAttr("style");
					b.showmsg(d, "", 2)
				}
				if (c && (d.val().length < b.minLen || d.val().length > b.maxLen)) {
					b.showmsg(d, d.attr("errormsg"), 3)
				} else {
					if (c) {
						$("#n1").css('display','none');
						$("#y1").removeAttr("style");
						b.showmsg(d, "", 2)
						
						if(reg.test(d.val())){
							$("#n2").css('display','none');
							$("#y2").removeAttr("style");
						}
						//监听非法字符情况
						if(!reg1.test(d.val())){//有非法字符
							$("#n3").css('display','none');
							$("#y3").css('display','none');
							$("#n4").removeAttr("style");
						}else{
							if(reg2.test(d.val()) ){
								$("#n3").css('display','none');
								$("#n4").css('display','none');
								$("#y3").removeAttr("style");
							}
						}
					}
				}
				b.trigger(d, !(e >= 0))
			})
		})
	};
	a.fn.passwordStrength.ratepasswd = function (c, d) {
		var b = c.length,
		e;
		if (b >= d.minLen && b <= d.maxLen) {
			e = a.fn.passwordStrength.checkStrong(c)
		} else {
			e = -1
		}
		return e / 4 * 100
	};
	a.fn.passwordStrength.checkStrong = function (d) {
		var e = 0,
		b = d.length;
		for (var c = 0; c < b; c++) {
			e |= a.fn.passwordStrength.charMode(d.charCodeAt(c))
		}
		return a.fn.passwordStrength.bitTotal(e)
	};
	a.fn.passwordStrength.charMode = function (b) {
		if (b >= 48 && b <= 57) {
			return 1
		} else {
			if (b >= 65 && b <= 90) {
				return 2
			} else {
				if (b >= 97 && b <= 122) {
					return 4
				} else {
					return 8
				}
			}
		}
	};
	a.fn.passwordStrength.bitTotal = function (b) {
		var d = 0;
		for (var c = 0; c < 4; c++) {
			if (b & 1) {
				d++
			}
			b >>>= 1
		}
		return d
	};
	a.fn.passwordStrength.defaults = {
		minLen: 0,
		maxLen: 30,
		trigger: a.noop
	}
})(jQuery);
