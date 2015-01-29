jQuery.cookie = function(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
};
var WxchatClient={
	Wechat_Key:"WxchatClientuser",
	Cookie_CarType_Key:"cartype",
	Cookie:{
		addCookie:function(key, value){
			alert(value);
			var exdate = new Date();
			exdate.setDate(exdate.getDate()+365);
			document.cookie = key + "=" + escape(value) + ";expires="+exdate.toUTCString();
			$.cookie(key, value, {expires: 7, path: "/", domain: "jquery.com", secure: true});
		},
		getCookieVal:function(key){
			if(key == null || key == ""){
				return "";
			}
			if(document.cookie == null || document.cookie.length == 0){
				return "";
			}
			var properties = $.cookie(WxchatClient.Cookie_CarType_Key);
			alert(properties);
			if(properties != null && properties.length > 0){
				for(var i = 0; i < properties.length; i++){
					var pro = properties[i].split("=");
					if(pro[0] == key){
						var cookie_val = unescape(pro[1]);
						if(cookie_val.indexOf("[") != -1 || cookie_val.indexOf("{") != -1){
							eval("var resVal = " + cookie_val);
							return resVal;
						}
						return cookie_val;
					}
				}
			}
		},
		isEmpty:function(){
			var userinfo = this.getCookieVal(WxchatClient.Wechat_Key);
			return userinfo == null || userinfo == "";
		}
	},
	currentCarType:function(){
		return WxchatClient.Cookie.getCookieVal(WxchatClient.Cookie_CarType_Key);
	},
	setCurrentCarType:function(carType){
		WxchatClient.Cookie.addCookie(WxchatClient.Cookie_CarType_Key, carType);
	}
};
