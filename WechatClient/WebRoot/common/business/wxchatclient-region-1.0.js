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
				var tempDay = DUtil.getNewDate(today, i+1, "day");
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

			var bj = '7b6fefc9cb9711e3a770025041000001';
			var nm = '7e9c132bcb9711e3a770025041000001';
			if(dl.length > 0){
				for(var i=0; i<dl.length; i++){
					var d = dl[i];
					if(i>0 && id=='province_selectId'){   // zhuqingsong  更改 其他省份先隐藏\
						if(  d.id==bj  || d.id==nm){
						}else{
							continue;		
						}																		
					}
					var option = $(document.createElement("option"));
					option.attr("value", d.id);
					option.html(d.name);
					c.append(option);
				}
			}
		}
	}
});