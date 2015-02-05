$.extend(WxchatClient, {
	Region:{
		url:contextPath+projectRoot+"/region",
		type:{
			province : "provinces",
			city : "cities",
			district : "districts",
		},
		getProvinces:function(callback){
			var t = this.type.province;
			this._getData("1", t, callback);
		},
		getCities:function(id, callback){
			var t = this.type.city;
			this._getData(id, t, callback);
		},
		getDistricts:function(id, callback){
			var t = this.type.district;
			this._getData(id, t, callback);
		},
		_getData:function(id, type, callback){
			var _this = this;
			$.ajax({
				url:_this.url+"/"+type+"/"+id,
				dataType:"json",
				type:"get",
				success:function(r){
					if($.type(callback) == "function"){
						callback(r);
					}
				}
			});
		}
	},
	Select:{
		bulid:function(id, name, dl){
			var c = $("#"+id);
			if(c[0] && !c.is("select")){
				var s = $(document.createElement("select"));
				s.attr("name", name);
				c.append(s);
				c = s;
			}
			c.empty();
			if(dl == false || $.type(dl) != "array"){
				return;
			}
			if(dl.length > 0){
				for(var i=0; i<dl.length; i++){
					var d = dl[i];
					var option = $(document.createElement("option"));
					option.attr("value", d.id);
					option.html(d.name);
					c.append(option);
				}
			}
		}
	}
});