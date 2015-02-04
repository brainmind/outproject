<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
<jsp:include page="common/base.jsp" />
<script type="text/javascript">
	function searchVin(){
		var url = "<%=path %>/<%=Constants.ROOT %>/car/searchVin.json";
		var vin = $("#vincode").val();
		if(vin == null || vin == ""){
			WxchatClient.Dialog.show("vin码不能为空.", function(){
				$("#vincode").focus();
			});
			return;
		}
		$.ajax({
			url:url,
			data:"vin17="+vin,
			dataType:"json",
			type:"get",
			success:function(r){
				if($.type(r.cars) == "array" && r.cars.length > 0){
					WxchatClient.Page.load("<%=path %>/<%=Constants.ROOT %>/car/selVin","","vinselectdivid", function(){
						$("#vinspanid").html(vin);
						var carlist = $("#carlistdlid");
						for(var i=0; i<r.cars; i++){
							var car = r.cars[i];
							var dd = $(document.createElement("dd"));
							dd.html("<img src=\"<%=path%>/styles/images/1.jpg\"><p><span>广汽本田传祺2014款</span><span>1.6T手自动一体版</span></p>");
							dd.attr("dataid", "");
							carlist.append(dd);
						}
					});
				}else{
					WxchatClient.Dialog.show("查询结果为空.");
				}
			}
		});
	}
</script>
</head>
<body>
<div class="wapper">
    <div class="che_btn">
    	<span rel="tab" name="carmodelvin" url="<%=path %>/<%=Constants.ROOT %>/car/sel">车型选择</span>
        <span class="click" rel="tab" name="carmodelvin" url="<%=path %>/<%=Constants.ROOT %>/car/toVin">VIN码</span>
    </div>
    <div class="che_pic" id="vinselectdivid">
    	<ul>
            <li>
            	<div class="vinbox">
					<p>请输入VIN码，以精确匹配您的爱车</p>
                	<p><input name="vin" type="text" id="vincode"></p>
                </div>
                <a href="javascript:searchVin()" class="ensure">确定</a>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
