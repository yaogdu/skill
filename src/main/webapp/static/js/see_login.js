define(function(require, exports) {
	var $ = require("jquery");
	var tag = {
		"start" : function(obj, content, arrow) {
			$(obj).siblings().removeClass("select");
			$(obj).addClass("select");
			this.content(content, $(obj).index());
			this.arrow(arrow, $(obj).index());
		},
		"content" : function(obj, i) {
			var s = 0;
			if (i == 1) {
				s = -540;
			}
			;
			$(obj).animate({
				left : s + "px"
			}, 300)
		},
		"arrow" : function(obj, i) {
			var s = 127;
			if (i == 1) {
				s = 397;
			}
			;
			$(obj).animate({
				marginLeft : s + "px"
			}, 300)
		}
	}

	var verification = {
		form : function() {

		}
	}

	$("#issLoginTag li").click(function() {
		tag.start(this, "#issLoginCA", "#issArrowDown");
	})
	
	
	$("#loginBtn").click(function() {
		$.ajax({
			type : "POST",
			url : "/smart/login",
			data : {
				forward : $("#forward").val(),
				username : $("#user").val(),
				password : $("#pwd").val()
			},
			error : function(msg) {
				$("#error").text(JSON.parse(msg.responseText).message);
			},
			success : function() {
				window.location.reload();
			}
		});
	})

	
	$(document).keyup(function(event) {
		if (event.keyCode == 13) {
			$("#loginBtn").trigger("click");
		}
	});

});