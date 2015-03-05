var WxchatClient={
	Wechat_Key:"WxchatClientuser",
	Cookie_CarType_Key:"cartype",
	CurrentCar_Key:"currentcar",
	Cookie:{
		addCookie:function(key, value){
			var exdate = new Date();
			exdate.setTime(exdate.getTime()+365*24*60*1000*1000);
			document.cookie = key + "=" + escape(value) + ";expires="+exdate.toTimeString()+";path=/";
		},
		getCookieVal:function(key){
			if(key == null || key == ""){
				return null;
			}
			if(document.cookie == null || document.cookie.length == 0){
				return null;
			}
			var properties = document.cookie.split(";");
			if(properties != null && properties.length > 0){
				for(var i = 0; i < properties.length; i++){
					var pro = properties[i].split("=");
					pro[0] = pro[0].replace(/ /g, "");
					if(pro[0] == key){
						var cookie_val = unescape(pro[1]);
						if(cookie_val.indexOf("[") != -1 || cookie_val.indexOf("{") != -1){
							eval("var resVal = " + cookie_val.replace(/\r\n|\r|\n|\s/g, "") + ";");
							return resVal;
						}
						return cookie_val;
					}
				}
			}
			return null;
		},
		isEmpty:function(){
			var userinfo = this.getCookieVal(WxchatClient.Wechat_Key);
			return userinfo == null || userinfo == "";
		},
		isExits:function(carId){
			var resVal = this.getCookieVal(WxchatClient.Cookie_CarType_Key);
			if($.type(resVal) == "array"){
				for(var i=0; i<resVal.length; i++){
					if(resVal[i].id == carId){
						return true;
					}
				}
			}
			if(resVal && resVal.id && resVal.id == carId){
				return true;
			}
			return false;
		},
		isCarTypeExits:function(car){
			var carType = null;
			if($.type(car) == "string" && car.indexOf("{") != -1){
				eval("carType = "+car+";");
			}else if($.type(car) == "object"){
				carType = car;
			}
			var resVal = this.getCookieVal(WxchatClient.Cookie_CarType_Key);
			if($.type(resVal) == "array"){
				for(var i=0; i<resVal.length; i++){
					if(resVal[i].id == carType.id){
						return true;
					}
				}
			}
			if(resVal && resVal.id && resVal.id == carType.id){
				return true;
			}
			return false;
		}
	},
	currentCarType:function(){
		var resVal = this.Cookie.getCookieVal(WxchatClient.CurrentCar_Key);
		return resVal;
	},
	setCurrentCarType:function(carType){
		WxchatClient.Cookie.addCookie(WxchatClient.CurrentCar_Key, carType);
	},
	addCarType:function(carType){
		if(document.cookie == null || document.cookie.length == 0){
			return null;
		}
		if(WxchatClient.Cookie.isCarTypeExits(carType)){
			return carType;
		}
		var properties = document.cookie.split(";");
		var isNotExits = true;
		if(properties != null && properties.length > 0){
			for(var i = 0; i < properties.length; i++){
				var pro = properties[i].split("=");
				pro[0] = pro[0].replace(/ /g, "");
				if(pro[0] == WxchatClient.Cookie_CarType_Key){
					isNotExits = false;
					var cookie_val = unescape(pro[1]);
					cookie_val = cookie_val.replace("isdefault:true", "isdefault:false");
					if(cookie_val.indexOf("[") != -1){
						cookie_val = cookie_val.substring(0, cookie_val.length-1)+","+carType+"]";
					}else{
						cookie_val = "["+cookie_val+","+carType+"]";
					}
					WxchatClient.Cookie.addCookie(WxchatClient.Cookie_CarType_Key, cookie_val);
					break;
				}
			}
		}
		if(isNotExits){
			WxchatClient.Cookie.addCookie(WxchatClient.Cookie_CarType_Key, carType);
		}
	},
	getDefaultCarType:function(){
		var carList = WxchatClient.Cookie.getCookieVal(WxchatClient.Cookie_CarType_Key);
		if($.type(carList) == "array"){
			for(var i=0; i<carList.length; i++){
				var car = carList[i];
				if(car.isdefault){
					return car;
				}
			}
		}
		return carList;
	}
};
