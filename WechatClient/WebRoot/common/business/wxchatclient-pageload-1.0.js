$(document).ready(function () {
	$("span[rel=tab][name=carmodelvin]").each(function(){
		var $this = $(this);
		var url = $this.attr("url");
		var target = $this.attr("target");
		$this.on("click", function(event){
			$("span[rel=tab][name=carmodelvin]").removeClass("click");
			$this.addClass("click");
			if(url && url != null && url != ""){
				if(target && target != null && target != ""){
					$.ajax({
						url:url,
						type:"get",
						dataType:"html",
						success:function(r){
							$("#"+target).html(r);
						}
					});
				}else{
					window.location.href = url;
				}
			}
		});
	});
	
	initHref();
});

function initHref(){
	$("a[target]").each(function(){
		var $this = $(this);
		var url = $this.attr("href");
		$this.removeAttr("href");
		var targetId = $this.attr("target");
		var dataref = $this.attr("dataref");
		var po = $("#"+dataref);
		var param = $this.attr("param");
		if(!param || param == null){
			param = (po && po != null) ? po.attr("name")+"="+po.val() : "";
		}else{
			param += "&" + (po && po != null) ? po.attr("name")+"="+po.val() : "";
		}
		$this.on("click", function(){
			var rel = $this.attr("rel");
			$.ajax({
				url:url,
				data:param,
				type:"get",
				dataType:"html",
				success:function(r){
					var container = $("#"+targetId);
					if(rel && rel != null && rel == "firstselect"){
						var dataid = $this.attr("dataid");
						var label = $this.text();
						var logo_url = $this.children("img").first().attr("src");
						container.data("cartype", {id:dataid, label:label, logourl:logo_url});
					}
					container.html(r);
					$("a[rel=secondselect]").each(function(){
						var $subtype = $(this);
						var dataid=$subtype.attr("dataid");
						var name = $subtype.text();
						var url = $subtype.attr("href");
						$subtype.removeAttr("href");
						$subtype.parent().on("click",function(){
							var carType = container.data("cartype");
							if(carType && carType != null){
								var cookiecartype = "{id:\""+carType.id+"\",label:\""+carType.label+"\",logourl:\""+carType.logourl+"\"," +
										"serid:\""+dataid+"\",sername:\""+name+"\"}";
								WxchatClient.setCurrentCarType(cookiecartype);
							}
							window.location.href = url;
						});
					});
					
					$("div.che_vin > dl > dd").each(function(){
						var $vinlist = $(this);
						var dataid = $vinlist.attr("dataid");
						var label = $vinlist.children("span").first().text();
						var sername = $vinlist.children("span").last().text();
						$vinlist.on("click",function(){
							
						});
					});
				}
			});
		});
	});
}