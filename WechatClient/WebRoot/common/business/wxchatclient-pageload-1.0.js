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
		$this.removeAttr("href");
		var targetId = $this.attr("target");
		var rel = $this.attr("rel");
		var container = $("#"+targetId);
		if(rel && rel != null && rel == "firstselect"){
			$this.unbind("click");
			$this.on("click", function(){
				var series = $this.parent().data("series");
				var label = $this.text();
				var logo_url = $this.children("img").attr("src");
				container.data("brand", {label:label, logourl:logo_url});
				if(series && $.type(series) == "array"){
					var dl = $("dl.che_c");
					dl.removeClass();
					dl.addClass("che_cx");
					dl.empty();
					for(var i=0; i<series.length; i++){
						var dd = $(document.createElement("dd"));
						dd.html("<a rel=\"secondselect\" target=\""+targetId+"\">"+series[i].label+"</a>");
						dl.append(dd);
						dd.data("cars", series[i].cars);
					}
					initHref();
				}
			});
		}else if(rel == "secondselect"){
			$this.on("click", function(){
				var cars = $this.parent().data("cars");
				if(cars && $.type(cars) == "array"){
					container.data("serie", {label:$this.text()});
					var dl = $("dl.che_cx");
					dl.empty();
					for(var i=0; i<cars.length; i++){
						var dd = $(document.createElement("dd"));
						dd.html("<a dataid=\""+cars[i].id+"\" rel=\"thirdselect\" target=\""+targetId+"\">"+cars[i].label+"</a>");
						dl.append(dd);
					}
					initHref();
				}
			});
		}else if(rel == "thirdselect"){
			$this.on("click",function(){
				var id = $this.attr("dataid");
				var car = $this.text();
				var brand = container.data("brand");
				var serie = container.data("serie");
				var cookiecartype = "{id:\""+id+"\",brand:\""+brand.label+"\",logourl:\""+brand.logourl+"\"," +
				"car:\""+car+"\",sername:\""+serie.label+"\",isdefault:true}";
				WxchatClient.setCurrentCarType(cookiecartype);
				window.location.href=contextPath+projectRoot+"/index";
			});
		}
	});
}