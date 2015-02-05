function DateUtil(){
	this.url = "";
	this.op={
		partten:{mdy:"m/d/y",ymd:"y/m/d",dmy:"d/m/y"}
	};
	this.cdate = new Date();
}

DateUtil.prototype.updateDate=function(){
	this.cdate = new Date();
};

DateUtil.prototype.setTime=function(v){
	this.cdate.setTime(v);
};

DateUtil.prototype.getYear=function(){
	return this.cdate.getFullYear();
};
DateUtil.prototype.getMonth=function(){
	return this.cdate.getMonth()+1;
};

DateUtil.prototype.getDay=function(){
	return this.cdate.getDate();
};

DateUtil.prototype.getFirstDayOfMonth=function(d){
	var currentMonth = new Date(d.getTime())||new Date();
	currentMonth.setDate(1);
	return currentMonth;
};

DateUtil.prototype.getLastDayOfMonth=function(d){
	var nextMonth = new Date(d.getTime())||new Date();
	var month = nextMonth.getMonth();
	nextMonth.setMonth(month+1);
	nextMonth.setDate(0);
	return nextMonth;
};

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
DateUtil.prototype.format = function (d,fmt) { //author: meizz 
	var o = {
		"M+": d.getMonth() + 1, //月份 
		"d+": d.getDate(), //日 
		"h+": d.getHours(), //小时 
		"m+": d.getMinutes(), //分 
		"s+": d.getSeconds(), //秒 
		"q+": Math.floor((d.getMonth() + 3) / 3), //季度 
		"S": d.getMilliseconds() //毫秒 
	};
	if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (d.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
	if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

DateUtil.prototype.getHours=function(){
	return this.cdate.getHours();
};
DateUtil.prototype.getMinutes=function(){
	return this.cdate.getMinutes();
};
DateUtil.prototype.getSeconds=function(){
	return this.cdate.getSeconds();
};

DateUtil.prototype.getToday=function(){
	return this.format(this.cdate, "yyyy-MM-dd"); 
};
/**
 * 计算出相差的时间后的日期
 * @param d 日期
 * @param s 相差的时间
 * @param t 类型：day天, month月, year年, hour, minute, second
 */
DateUtil.prototype.getNewDate=function(d, s, t){
	if("year" == t){
		var year = d.getFullYear() + s;
		var strDate = year+"/"+this.format(d, "MM/dd hh:mm:ss");
		return new Date(strDate);
	}else if("month" == t){
		var year = d.getFullYear();
		var month = d.getMonth()+1 + s;
		var strDate = year+"/"+month+"/"+this.format(d, "dd hh:mm:ss");
		return new Date(strDate);
	}else if("day" == t){
		var lt = d.getTime() + s*24*60*60*1000;
		return new Date(lt);
	}else if("hour" == t){
		var lt = d.getTime() + s*60*60*1000;
		return new Date(lt);
	}else if("minute" == t){
		var lt = d.getTime() + s*60*1000;
		return new Date(lt);
	}else if("second" == t){
		var lt = d.getTime() + s*1000;
		return new Date(lt);
	}
	return d;
};

DateUtil.prototype.parseDate=function(s){
	if(s != null && s != ""){
		try{
			if(typeof(s) == "string"){
				s = s.replace(/-/g, "/");
				return new Date(s);
			}else if(typeof(s) == "number"){
				return new Date(s);
			}
		}catch(e){
			alert(e.message);
		}
	}
	return null;
};

DateUtil.prototype.getCurrentTimeString=function(){
	return this.getYear()+""+this.getMonth()+""+
			this.getDay()+""+this.getHours()+""+
			this.getMinutes()+""+this.getSeconds();
};

DateUtil.prototype.getWeek=function(date){
	var week = 0;
	var df = "mmddyy";
	if(date==null || date =="" || typeof(date) == "undefined"){
		date = this.getToday(); 
		df = "yyMMdd";
	}
	date = date.replace(/\//g,"-");
	week = $.ajax({
		type:"get",
		url:basePath+"/dateUtil/week/"+encodeURIComponent(date)+"/"+df,
		cache:false,
		async:false,
		dataType:"text"
	}).responseText;
	return week;
};

DateUtil.prototype.getQuarter=function(date, format){
	var month = 0;
	if(date && typeof(date) == "string" && date != "" && format && format != ""){
		var p = this.op.partten[format];
		var index = p.indexOf("m");
		var d = date.split("/");
		if(index == 0){
			month = parseInt(d[0],10);
		}else{
			month = parseInt(d[1],10);
		}
	}else if(date && typeof(date) == "object"){
		month = date.getMonth();
	}else if(date && typeof(date) == "number"){
		month = date;
	}else{
		month = this.getMonth();
	}
	if(month % 3 == 0){
		return month / 3;
	}else{
		return parseInt(month / 3 + 1);
	}
};

DateUtil.prototype.getQuarterByMonth = function(m){
	if(m % 3 == 0){
		return m / 3;
	}else{
		return parseInt(m / 3 + 1);
	}
};
/**
 * 若date为空，则返回当前日期是周几，否则数据类型为string，格式都为m/d/y
 * @param date
 * @returns
 */
DateUtil.prototype.getDayOfWeek=function(date){
	if(date && typeof(date) == "string"){
		var d = date.split("/");
		var md = new Date(parseInt(d[2]), parseInt(d[0]), parseInt(d[1]));
		return md.getDay();
	}else{
		this.updateDate();
		return this.getDay();
	}
};
/**
 * 得到一个日期所在周的开始时间和结束时间，不传参数为当前周
 * @param date
 * @returns {___anonymous5423_5464}
 */
DateUtil.prototype.getStartAndEndDayOf=function(date, type){
	var today = date||new Date();
	var startDate = "", endDate = "";
	if(type == "day"){
		startDate = this.format(today, "yyyy-MM-dd");
		return {"startDate":startDate, "endDate":startDate};
	}else if(type == "week"){
		var dayOfweek = today.getDay();
		var monday = new Date(today.getFullYear(), today.getMonth(), today.getDate()-dayOfweek+1);
		var sunday = new Date(today.getFullYear(), monday.getMonth(), monday.getDate()+6);
		startDate = this.format(monday, "yyyy-MM-dd");
		endDate = this.format(sunday, "yyyy-MM-dd");
		return {"startDate":startDate, "endDate":endDate};
	}else if(type == "month"){
		var monday = this.getFirstDayOfMonth(today);
		var sunday = this.getLastDayOfMonth(today);
		startDate = this.format(monday, "yyyy-MM-dd");
		endDate = this.format(sunday, "yyyy-MM-dd");
		return {"startDate":startDate, "endDate":endDate};
	}else if(type == "quarter"){
		var quarter = this.getQuarter(today, "");
		startDate = this.format(new Date(today.getFullYear(), quarter*3-3, 1), "yyyy-MM-dd");
		endDate = this.format(new Date(today.getFullYear(), quarter*3, 0), "yyyy-MM-dd");
		return {"startDate":startDate, "endDate":endDate};
	}else if(type == "year"){
		startDate = today.getFullYear()+"-01-01";
		endDate = today.getFullYear()+"-12-31";
		return {"startDate":startDate, "endDate":endDate};
	}else{
		startDate = this.format(today, "yyyy-MM-dd");
		return {"startDate":startDate, "endDate":startDate};
	}
};

DateUtil.prototype.getPreDateForGapTime = function(date, type){
	var today = this.parseDate(date)||new Date();
	if(type.indexOf("pre") == 0){
		type = type.replace("pre","");
		var endDate = this.format(today, "yyyy-MM-dd");
		if(type.toLowerCase() == "day"){
			return {"startDate":endDate, "endDate":endDate};
		}else if(type.toLowerCase() == "week"){
			var startDate = this.format(this.getNewDate(today, -7, "day"), "yyyy-MM-dd");
			return {"startDate":startDate, "endDate":endDate};
		}else if(type.toLowerCase() == "month"){
			var startDate = this.format(this.getNewDate(today, -1, "month"), "yyyy-MM-dd");
			return {"startDate":startDate, "endDate":endDate};
		}else if(type.toLowerCase() == "quarter"){
			var startDate = this.format(this.getNewDate(today, -90, "day"), "yyyy-MM-dd");
			return {"startDate":startDate, "endDate":endDate};
		}else if(type.toLowerCase() == "year"){
			var startDate = this.format(this.getNewDate(today, -1, "year"), "yyyy-MM-dd");
			return {"startDate":startDate, "endDate":endDate};
		}else{
			return {"startDate":endDate, "endDate":endDate};
		}
	}else{
		return this.getStartAndEndDayOf(date, type);
	}
};

var DUtil = new DateUtil();