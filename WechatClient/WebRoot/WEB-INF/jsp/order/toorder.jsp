<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page import="com.wechat.client.utils.*" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
<!--date:20140121-->
<title>小马上门汽车保养服务</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<jsp:include page="../common/base.jsp" />
<script type="text/javascript">
$(function(){
	WxchatClient.Region.getProvinces(function(r){
		WxchatClient.Select.bulid("province_selectId","province",r);
		var nm = '7e9c132bcb9711e3a770025041000001';
		var cf = '5dadf856cb9b11e3a770025041000001';
		//var bjs='53ec9906cb9b11e3a770025041000001';
		$("#province_selectId").on("change", function(){
			var $this = $(this);
/* 			if($this.val()!=bj ||  $(this).val() !=nm){	
				alert("111");
				WxchatClient.Select.bulid("province_selectId","province",r);   //zhuqingsong  更改
				return false;
			} */
			WxchatClient.Region.getCities($this.val(), function(c){
				WxchatClient.Select.bulid("citye_selectId","city", c);
				$("#citye_selectId").on("change", function(){
					var $city = $(this);
					WxchatClient.Region.getDistricts($city.val(), function(d){
						//WxchatClient.Select.bulid("district_selectId","district", d);   //zhuqingsong  更改
					});
				});
				WxchatClient.Region.getDistricts($("#citye_selectId").val(), function(d){
					if($this.val()!=nm){
						WxchatClient.Select.bulid("citye_selectId","city", d);
					}
				});
			});
		});
		WxchatClient.Region.getCities($("#province_selectId").val(), function(c){
			WxchatClient.Select.bulid("citye_selectId","city", c);
			$("#citye_selectId").on("change", function(){
				var $city = $(this);
				WxchatClient.Region.getDistricts($city.val(), function(d){
					//WxchatClient.Select.bulid("district_selectId","district", d);   //zhuqingsong  更改
				});
			});
			
			WxchatClient.Region.getDistricts($("#citye_selectId").val(), function(d){
				WxchatClient.Select.bulid("citye_selectId","city", d);
			});
			
		});
		
	});
	
	var weekdays = WxchatClient.DateUtil.getEveryDayOfLastWeek();
	WxchatClient.Select.bulid("orderdateselectid","orderdate", weekdays);
});

function getCode(){
	var mobile = $("input[type=text][name=mobile]").val();
	if(mobile == ""){
		WxchatClient.Dialog.show("手机号不能为空", function(){
			$("input[type=text][name=mobile]").focus();
		});
		return;
	}
	var href = $("#getcodeahref").attr("href");
	$("#getcodeahref").attr("rel", href);
	$("#getcodeahref").removeAttr("href");
	$("#getcodeahref").addClass("yzm_disabled");
	$("input[type=text][name=CAPTCHA]").focus();
	$.ajax({
		url:"<%=path + Constants.ROOT %>/order/getCode",
		data:"mobile="+mobile,
		dataType:"json",
		type:"post",
		success:function(r){
			if(typeof(r.retry_seconds) == "undifined" || r == null || isNaN(r.retry_seconds)){
				WxchatClient.Dialog.show("获取验证码出错,检查手机号是否正确.");
				$("#getcodeahref").attr("href", href);
				$("#getcodeahref").removeClass("yzm_disabled");
				return;
			}
			var delay = parseInt(r.retry_seconds);
			setTimeout(function(){
				$("#getcodeahref").attr("href", href);
				$("#getcodeahref").removeClass("yzm_disabled");
			}, delay*1000);
			countdown(delay);
		}
	});
}

function countdown(seconds){
	if(seconds < 0){
		return false;
	}
	$("#timeoutseconds").html(seconds);
	setTimeout(function(){
		countdown(seconds-1);
	}, 1000);
}

function submitOrder(){
	var address = $("input[type=text][name=address]");
	if(address.val() == "" || address.val() == "详细地址"){
		WxchatClient.Dialog.show("详细地址不能为空", function(){
			address.focus();
		});
		return;
	}
	var contact = $("input[type=text][name=contact]");
	if(contact.val() == ""){
		WxchatClient.Dialog.show("姓名不能为空", function(){
			contact.focus();
		});
		return;
	}
	var mobile = $("input[type=text][name=mobile]").val();
	if(mobile == ""){
		WxchatClient.Dialog.show("手机号不能为空", function(){
			$("input[type=text][name=mobile]").focus();
		});
		return;
	}
	if(!/^(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9])\d{8}$/.test(mobile)){
		WxchatClient.Dialog.show("手机号输入不正确", function(){
			$("input[type=text][name=mobile]").focus();
		});
		return;
	}
	var validcode = $("input[type=text][name=CAPTCHA]").val();
	if(validcode == ""){
		WxchatClient.Dialog.show("验证码不能为空", function(){
			$("input[type=text][name=CAPTCHA]").focus();
		});
		return;
	}
	var reservDate = $("#orderdateselectid").val();
	var reservTime = $("#ordertimeselectid").val().split("-");
	$("input[type=hidden][name=reserve_time_string]").val(reservDate+" "+reservTime[0]+"~"+reservDate+" "+reservTime[1]);
	
	var regine_code = $("#district_selectId").val();
	if(regine_code==null || regine_code==""){
		regine_code = $("#citye_selectId").val();
	}
	$("input[type=hidden][name=region_code]").val(regine_code);
	
	var submitbut = $("a.ensure");
	var href = submitbut.attr("href");
	submitbut.attr("rel", href);
	submitbut.removeAttr("href");
	submitbut.addClass("pay_unlock");
	var form = document.orderready;
	$.ajax({
		url:"<%=path + Constants.ROOT %>/order/save",
		type:"post",
		data:$(form).serialize(),
		dataType:"json",
		success:function(r){
			if(r && r.orderid){
				var car_id = $("input[type=hidden][name=car_id]").val();
				var carName = $("input[type=hidden][name=brand]").val();
				var serName = $("input[type=hidden][name=sername]").val();
				var car = $("input[type=hidden][name=car]").val();
				var logourl = $("input[type=hidden][name=logourl]").val();
				var cookiecartype = "{id:\""+car_id+"\",brand:\""+carName+"\",logourl:\""+logourl+"\"," +
				"car:\""+car+"\",sername:\""+serName+"\",isdefault:true}";
				WxchatClient.addCarType(cookiecartype);
				WxchatClient.Dialog.show("订单提交成功！", function(){
					window.location.href="<%=path + Constants.ROOT %>/order/ready?orderId="+r.orderid;
				});
				window.setTimeout(function(){
					window.location.href="<%=path + Constants.ROOT %>/order/ready?orderId="+r.orderid;
				}, 3000);
			}else if(r && r.code && r.code == 40002){
				WxchatClient.Dialog.show("验证码输入错误,提交失败！");
			}else{
				WxchatClient.Dialog.show("订单提交失败！");
			}
			submitbut.attr("href", href);
			submitbut.removeClass("pay_unlock");
		},
		error:function(){
			WxchatClient.Dialog.show("订单提交失败！后台处理报错.");
			submitbut.attr("href", href);
			submitbut.removeClass("pay_unlock");
		}
	});
}
</script>
</head>
<body>
<div class="wapper">
	<form action="<%=path + Constants.ROOT %>/order/ready" name="orderready" method="post">
	<input type="hidden" name="type" value="${type }" />
	<input type="hidden" name="reserve_time_string" value=""/>
	<input type="hidden" name="region_code" value=""/>
	<input type="hidden" name="car_id" value="${carId }"/>
	<input type="hidden" name="brand" value="${brand }" />
	<input type="hidden" name="sername" value="${sername }" />
	<input type="hidden" name="car" value="${car }" />
	<input type="hidden" name="logourl" value="${logourl }" />
	<input type="hidden" name="isServiceOnly" value="${isServiceOnly }" />
	<c:forEach items="${service_fees_checked }" var="com" varStatus="cStatus">
		<c:if test="${com == '1'}">
			<input type="hidden" name="service_fees.type" value="${service_fees_type[cStatus.index] }"/>
			<input type="hidden" name="service_fees.title" value="${service_fees_title[cStatus.index] }"/>
			<input type="hidden" name="service_fees.price" value="${service_fees_price[cStatus.index] }"/>
			<input type="hidden" name="service_fees.category_id" value="${service_fees_cId[cStatus.index] }"/>
		</c:if>
	</c:forEach>
	<c:forEach items="${commodities_checked }" var="commodity" varStatus="cStatus">
		<c:if test="${commodity == '1' }">
			<input type="hidden" name="commodities.id" value="${commodities_id[cStatus.index] }"/>
			<input type="hidden" name="commodities.label" value="${commodities_label[cStatus.index] }"/>
			<input type="hidden" name="commodities.number" value="${commodities_number[cStatus.index] }"/>
			<input type="hidden" name="commodities.price" value="${commodities_price[cStatus.index] }"/>
			<input type="hidden" name="commodities.category_id" value="${commoditys_cId[cStatus.index] }"/>
		</c:if>
	</c:forEach>
	<div class="neir r_dz">
    	<p><strong>请选择地区</strong></p>
        <div class="add_sel">
	        <select name="province" id="province_selectId">
	        </select>
	        <select name="city" id="citye_selectId">
	        </select>
	        <!-- <select name="district" id="district_selectId">
	        </select> -->
        </div>
        <div class="add_text"><input name="address" type="text" value="" tiptxt="详细地址"></div>
        <p>目前仅提供北京地区五环内及回龙观天通苑及内蒙古赤峰市地区上门服务</p>
        <p><strong>预约服务时间</strong></p>
    </div>
	<div class="neir ser_nr"> 
    	<ul>
        	<li class="bor">
            	<select name="orderdate" id="orderdateselectid">
                    <option selected>2月&nbsp;5日&nbsp;星期四</option>
                    <option>2月&nbsp;6日&nbsp;星期五</option>
                    <option>2月&nbsp;7日&nbsp;星期六</option>
                    <option>2月&nbsp;8日&nbsp;星期日</option>
                    <option>2月&nbsp;9日&nbsp;星期一</option>
                    <option>2月&nbsp;10日&nbsp;星期二</option>
                    <option>2月&nbsp;11日&nbsp;星期三</option>
                    <option>2月&nbsp;12日&nbsp;星期四</option>
                </select>
                <select name="ordertime" id="ordertimeselectid">
                	<option value="08:00-8:30" selected>上午</option>
                	<option value="13:00-13:30" >下午</option>
                </select>
            </li>
        	<li class="bor">
            	<p class="name">姓名</p>
                <p class="text"><input name="contact" type="text"></p>
            </li>
            <li class="bor">
            	<p class="name">手机号</p>
                <p class="text"><input name="mobile" type="text"></p>
            </li>
            <li>
            	<p class="time"><span id="timeoutseconds">60</span>秒后再次获取</p>
                <a id="getcodeahref" href="javascript:getCode();" class="yzm">获取验证码</a>
            </li>
            <li>
            	<p class="ball"></p>
                <p class="entry">验证码</p>
            </li>
            <li class="bor"><input name="CAPTCHA" type="text"></li>
        </ul>    	
    </div>
    <a href="javascript:submitOrder();" class="ensure">提交预约</a>
    </form>
</div>
</body>
</html>
