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
	renderingInputText();
});

function initHref(){
	$("a[target]").each(function(){
		var $this = $(this);
		var url = $this.attr("href");
		$this.removeAttr("href");
		var targetId = $this.attr("target");
		var rel = $this.attr("rel");
		var container = $("#"+targetId);
		if(rel && rel != null && rel == "firstselect"){
			$this.unbind("click");
			$this.parent().on("click", function(){
				var label = $this.text();
				var logo_url = $this.children("img").attr("src");
				var id = $(this).attr("dataid");
				container.data("brand", {id:id, label:label, logourl:logo_url});
				WxchatClient.loadJson(url, "brandId="+id, function(series){
					var dl = $("dl.che_c");
					dl.removeClass();
					dl.addClass("che_cx");
					dl.empty();
					for(var i=0; i<series.length; i++){
						var dd = $(document.createElement("dd"));
						dd.html("<a href=\""+ contextPath + projectRoot +"/car/car.json\" rel=\"secondselect\" target=\""+targetId+"\">"+series[i].label+"</a>");
						dd.attr("id", series[i].id);
						dl.append(dd);
					}
					initHref();
				});
			});
		}else if(rel == "secondselect"){
			$this.parent().on("click", function(){
				var serieId = $this.parent().attr("id");
				WxchatClient.loadJson(url, "serieId="+serieId, function(cars){
					if(cars && $.type(cars) == "array"){
						container.data("serie", {id:serieId, label:$this.text()});
						var dl = $("dl.che_cx");
						dl.empty();
						for(var i=0; i<cars.length; i++){
							var dd = $(document.createElement("dd"));
							var remark = cars[i].remark != null ? cars[i].remark : "";
							dd.html("<a dataid=\""+cars[i].id+"\" label=\""+cars[i].label+"\" rel=\"thirdselect\" target=\""+targetId+"\">"+cars[i].label+" "+remark+"</a>");
							dl.append(dd);
						}
						initHref();
					}
				});
			});
		}else if(rel == "thirdselect"){
			$this.parent().on("click",function(){
				var id = $this.attr("dataid");
				var car = $this.attr("label");
				var brand = container.data("brand");
				var serie = container.data("serie");
				var cookiecartype = "{id:\""+id+"\",brand:\""+brand.label+"\",logourl:\""+brand.logourl+"\"," +
				"car:\""+car+"\",sername:\""+serie.label+"\",isdefault:true}";
				WxchatClient.setCurrentCarType(cookiecartype);
				
				var backurl = $("input[type=hidden][name=backurl]").val();
				if(backurl == ""){
					backurl = "/index";
				}else{
					backurl += "&car_id="+id;
				}
				window.location.href=contextPath+projectRoot+backurl;
			});
		}
	});
}

function renderingInputText(){
	$("input[type=text][tiptxt]").each(function(){
		var $this = $(this);
		var tip = $this.attr("tiptxt");
		$this.val(tip);
		$this.on("focus", function(){
			if($this.val() == tip){
				$this.val("");
			}
		});
		$this.on("blur", function(){
			if($this.val() == ""){
				$this.val(tip);
			}
		});
	});
}