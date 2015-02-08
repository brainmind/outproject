$.extend(WxchatClient,{
	loadJson:function(url, param, callback){
		$.ajax({
			url:url,
			data:param,
			type:"get",
			dataType:"json",
			success:function(r){
				if($.type(callback) == "function"){
					callback(r);
				}
			}
		});
	}
});