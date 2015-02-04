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
			type:"post",
			success:function(r){
				if($.type(r.cars) == "array" && r.cars.length > 0){
					WxchatClient.Page.load("<%=path %>/<%=Constants.ROOT %>/car/selVin","","vinselectdivid", function(container){
						$("#vinspanid").html(vin);
						var carlist = $("#carlistdlid");
						for(var i=0; i<r.cars.length; i++){
							var car = r.cars[i];
							var dd = $(document.createElement("dd"));
							var label = car.label.split(" ");
							var logourl = car.logo_url;
							dd.html("<img src=\"<%=path%>/styles/images/1.jpg\"><p><span>"+label[0]+"</span><span>"+label[1]+"</span><span>"+label[2]+label[3]+"</span></p>");
							dd.attr("dataid", car.id);
							carlist.append(dd);
						}
						renderSelectByVIN();
						$("a.ensure", container).on("click", function(){
							var dd = $("dd.che_click", carlist);
							var id = dd.attr("dataid");
							var logourl = dd.children("img").attr("src");
							var brand = $("span", dd)[0].innerText;
							var serName = $("span", dd)[1].innerText;
							var car = $("span", dd)[2].innerText;
							var carType = "{id:\""+id+"\",brand:\""+brand+"\",logourl:\""+logourl+"\"," +
							"car:\""+car+"\",sername:\""+serName+"\",isdefault:true}";
							WxchatClient.setCurrentCarType(carType);
							window.location.href="<%=path %>/<%=Constants.ROOT %>/index";
							return false;
						});
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
    <div class="che_pic">
    	<ul>
            <li id="vinselectdivid">
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
