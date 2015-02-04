$.extend(WxchatClient, {
	Dialog:{
		clientWidth:0,
		clientHeight:0,
		_init:function(){
			var bg = $(document.createElement("div"));
			bg.addClass("tc_bg");
			$(document.body).append(bg);
			var main = $(document.createElement("div"));
			main.addClass("tcmain");
			main.html("<div class=\"close\"></div><ul><li id=\"xm_dialog_content\"></li></ul>");
			$(document.body).append(main);
			// 设定首页焦点图尺寸
			var wxd = this;
			wxd.clientHeight = $(window).height();
			wxd.clientWidth = $(window).width();
		},
		show:function(content, callback){
			var contentLi = $("#xm_dialog_content");
			if(!contentLi || !contentLi.is("li")){
				this._init();
			}
			contentLi = $("#xm_dialog_content");
			contentLi.html(content);
			$(".tc_bg").fadeIn();
			var wxc = this;
			var tc_h = $(".tcmain").height();
			$(".tcmain").css({
				"top" : (wxc.clientHeight - tc_h) / 2
			});
			if (wxc.clientWidth > 640) {
				$(".tc_bg").css({
					"left" : "50%",
					"margin-left" : "-320px"
				});
			} else {
				$(".tc_bg").css({
					"left" : "0",
					"margin-left" : "0"
				});
			}
			var myCarTypeSel = $(".tcmain");
			if (wxc.clientWidth > 640) {
				myCarTypeSel.animate({
					"left" : "50%",
					"margin-left" : "-288px"
				});
			} else {
				myCarTypeSel.animate({
					"left" : "5%",
					"margin-left" : "0"
				});
			}
			$(".tc_bg,.close,.tcmain li,.other").click(function() {
				wxc.close();
				if($.type(callback) == "function"){
					callback();
				}
			});
		},
		close:function(){
			$(".tc_bg").hide();
			$(".tcmain").animate({
				"left" : "-100%"
			});
		}
	},
	Page:{
		load:function(url, param, id, callback){
			$.ajax({
				url:url,
				data:param,
				dataType:"html",
				type:"get",
				success:function(r){
					$("#"+id).html(r);
					if($.type(callback) == "function"){
						callback();
					}
				}
			});
		}
	}
});