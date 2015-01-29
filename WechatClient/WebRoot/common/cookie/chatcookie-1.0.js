var WechatClient={
	Wechat_Key:"wechatclientuser",
	Cookie_CarType_Key:"cartype",
	Cookie:{
		addCookie:function(key, value){
			if(document.cookie == ""){
				document.cookie = key+":"+value;
			}else{
				document.cookie = ";"+key+":"+value;
			}
		},
		getCookie:function(){
			return document.cookie;
		},
		getCookieVal:function(key){
			if(key || key == null || key == ""){
				return "";
			}
			if(document.cookie == null || document.cookie == ""){
				return "";
			}
			var properties = document.cookie.split(";");
			if(properties.length > 0){
				for(var i = 0; i < properties.length; i++){
					var pro = properties[i].split(":");
					if(pro[0] == key){
						if(pro[1].indexOf("[") != -1 || pro[1].indexOf("{") != -1){
							eval("var resVal = " + pro[1]);
							return resVal;
						}
						return pro[1];
					}
				}
			}
		},
		isEmpty:function(){
			var userinfo = this.getCookieVal(WechatClient.Wechat_Key);
			return userinfo == null || userinfo == "";
		}
	},
	currentCarType:null,
	setCurrentCarType:function(carType){
		this.currentCarType = carType;
	}
};
