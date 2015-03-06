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
	DateUtil:{
		getEveryDayOfLastWeek:function(){
			var today = new Date();
			var weekdays = new Array();
			for(var i=0; i<7; i++){
				var tempDay = DUtil.getNewDate(today, i, "day");
				weekdays[i] = {id:DUtil.format(tempDay, "yyyy-MM-dd"), name:DUtil.format(tempDay,"M月d日 星期w")};
			}
			return weekdays;
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
					if(i>0 && id=='province_selectId'){   // zhuqingsong  更改 其他省份先隐藏
						continue;										//
					}														//
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