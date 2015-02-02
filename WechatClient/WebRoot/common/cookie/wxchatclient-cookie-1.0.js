var WxchatClient={
	Wechat_Key:"WxchatClientuser",
	Cookie_CarType_Key:"cartype",
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
		}
	},
	currentCarType:function(){
		var resVal = this.Cookie.getCookieVal(WxchatClient.Cookie_CarType_Key);
		if($.type(resVal) == "array"){
			for(var i=0; i<resVal.length; i++){
				if(resVal[i].isdefault){
					return resVal[i];
				}
			}
		}
		return resVal;
	},
	setCurrentCarType:function(carType){
		WxchatClient.Cookie.addCookie(WxchatClient.Cookie_CarType_Key, carType);
	}
};
