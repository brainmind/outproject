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
		$("#province_selectId").on("change", function(){
			var $this = $(this);
			WxchatClient.Region.getCities($this.val(), function(c){
				WxchatClient.Select.bulid("citye_selectId","city", c);
				$("#citye_selectId").on("change", function(){
					var $city = $(this);
					WxchatClient.Region.getDistricts($city.val(), function(d){
						WxchatClient.Select.bulid("district_selectId","district", d);
					});
				});
				
				WxchatClient.Region.getDistricts($("#citye_selectId").val(), function(d){
					WxchatClient.Select.bulid("district_selectId","district", d);
				});
			});
		});
		
		WxchatClient.Region.getCities($("#province_selectId").val(), function(c){
			WxchatClient.Select.bulid("citye_selectId","city", c);
			$("#citye_selectId").on("change", function(){
				var $city = $(this);
				WxchatClient.Region.getDistricts($city.val(), function(d){
					WxchatClient.Select.bulid("district_selectId","district", d);
				});
			});
			
			WxchatClient.Region.getDistricts($("#citye_selectId").val(), function(d){
				WxchatClient.Select.bulid("district_selectId","district", d);
			});
			
		});
		
	});
	
	var weekdays = WxchatClient.DateUtil.getEveryDayOfLastWeek();
	WxchatClient.Select.bulid("orderdateselectid","orderdate", weekdays);
});


function submitOrder(){
	
}
</script>
</head>
<body>
<div class="wapper">
	<form action="<%=path + Constants.ROOT %>/order/ready" name="orderready" method="post">
	<c:forEach items="${commodities_checked }" var="com" varStatus="cStatus">
		<input type="hidden" name="commodities.id" value="${commodities_id[cStatus.index] }"/>
	</c:forEach>
	<div class="neir r_dz">
    	<p><strong>请选择地区</strong></p>
        <div class="add_sel">
	        <select name="province" id="province_selectId">
	        </select>
	        <select name="city" id="citye_selectId">
	        </select>
	        <select name="district" id="district_selectId">
	        </select>
        </div>
        <div class="add_text"><input name="" type="text" value="" tiptxt="详细地址"></div>
        <p>目前仅提供北京地区五环内及回龙观天通苑地区上门服务</p>
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
                <select name="ordertime">
                	<option value="08:00-09:00" selected>上午08:00－09:00</option>
                	<option value="09:00-10:00">上午09:00－10:00</option>
                    <option value="10:00-11:00">上午10:00－11:00</option>
                    <option value="11:00-12:00">上午11:00－12:00</option>
                    <option value="12:00-13:00">下午12:00－01:00</option>
                    <option value="13:00-14:00">下午01:00－02:00</option>
                    <option value="14:00-15:00">下午02:00－03:00</option>
                    <option value="15:00-16:00">下午03:00－04:00</option>
                    <option value="16:00-17:00">下午04:00－05:00</option>
                    <option value="17:00-18:00">下午05:00－06:00</option>
                    <option value="18:00-19:00">下午06:00－07:00</option>
                    <option value="19:00-20:00">下午07:00－08:00</option>
                </select>
            </li>
        	<li class="bor">
            	<p class="name">姓名</p>
                <p class="text"><input name="" type="text"></p>
            </li>
            <li class="bor">
            	<p class="name">手机号</p>
                <p class="text"><input name="" type="text"></p>
            </li>
            <li>
            	<p class="time"><span>29</span>秒后再次获取</p>
                <a href="javascript:;" class="yzm">获取验证码</a>
            </li>
            <li>
            	<p class="ball"></p>
                <p class="entry">验证码</p>
            </li>
            <li class="bor"><input name="validateCode" type="text"></li>
        </ul>    	
    </div>
    <a href="javascript:submitOrder();" class="ensure">提交预约</a>
    </form>
</div>
</body>
</html>
