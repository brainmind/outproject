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
<meta http-equiv="Access-Control-Allow-Origin" content="*">
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="shortcut icon" href="<%=path %>/styles/images/favicon.ico" type="image/x-icon" />
<jsp:include page="common/base.jsp" />
<script type="text/javascript">
$(document).ready(function () {
	try{
		$.ajax({
			url:"<%=path %>/<%=Constants.ROOT %>/car/sel.json",
			dataType:"json",
			type:"get",
			success:function(result){
				alert(result);
				if(result && result.brands && result.brands.length > 0){
					for(var i=0; i<result.brands.length; i++){
						var brand = result.brands[i];
						var container = $("#cartypelistdivid > ul > li > dl");
						var groupdt = $("#groupdt_"+brand["first_letter"]);
						if(!groupdt[0] || !groupdt.is("dt")){
							groupdt = $(document.createElement("dt"));
							groupdt.attr("id", "groupdt_"+brand["first_letter"]);
							container.append(groupdt);
							groupdt.html(brand["first_letter"]);
						}
						var dd = $(document.createElement("dd"));
						dd.html("<a href=\"<%=path %>/<%=Constants.ROOT %>/car/subsel\" rel=\"firstselect\" target=\"cartypelistdivid\">"+
		                		"<img src=\""+brand["logo_url"]+"\">"+brand["label"]+"</a>");
						dd.data("series", brand.series);
						groupdt.after(dd);
					}
					initHref();
				}
			},
			error:function(r){
				alert("error:"+r.responseText);
			}
		});
	}catch(e){
		alert("JSON数据格式不正确,详细请参考:"+e.message);
	}
});
</script>
</head>
<body>
<div class="wapper">
    <div class="che_btn">
    	<span class="click" rel="tab" name="carmodelvin" url="<%=path %>/<%=Constants.ROOT %>/car/sel">车型选择</span>
        <span rel="tab" name="carmodelvin" url="<%=path %>/<%=Constants.ROOT %>/car/toVin">VIN码</span>
    </div>
    <div id="cartypelistdivid" class="che_pic">
    	<ul>
        	<li>
            	<dl class="che_c">
                	<dt id="groupdt_A">A</dt>
                	<dd>
                		<a href="<%=path %>/<%=Constants.ROOT %>/car/subsel" rel="firstselect" target="cartypelistdivid">
                		<img src="<%=path %>/styles/images/9.jpg">阿尔法罗密欧</a>
                	</dd>
                    <dd><a href="<%=path %>/<%=Constants.ROOT %>/car/subsel" target="cartypelistdivid"><img src="<%=path %>/styles/images/9.jpg">阿尔法罗密欧</a></dd>
                    <dd><a href="<%=path %>/<%=Constants.ROOT %>/car/subsel" target="cartypelistdivid"><img src="<%=path %>/styles/images/9.jpg">阿尔法罗密欧</a></dd>
                    <dt>B</dt>
                	<dd><a href="7.htm"><img src="<%=path %>/styles/images/9.jpg">阿尔法罗密欧</a></dd>
                </dl>
            </li>
        </ul>
    </div>
</div>
</body>
</html>