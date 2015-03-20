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
			url:"<%=path %>/<%=Constants.ROOT %>/car/brand.json",
			dataType:"json",
			type:"get",
			success:function(result){
				if(result && result.length > 0){
					for(var i=0; i<result.length; i++){
						var brand = result[i];
						var container = $("#cartypelistdivid > ul > li > dl");
						var groupdt = $("#groupdt_"+brand["first_letter"]);
						if(!groupdt[0] || !groupdt.is("dt")){
							groupdt = $(document.createElement("dt"));
							groupdt.attr("id", "groupdt_"+brand["first_letter"]);
							groupdt.html(brand["first_letter"]);
							container.append(groupdt);
						}
						var dd = $(document.createElement("dd"));
						dd.html("<a href=\"<%=path + Constants.ROOT %>/car/serie.json\" rel=\"firstselect\" target=\"cartypelistdivid\">"+
		                		"<img src=\""+brand["logo_url"]+"\" onerror=this.src='<%=path %>/styles/images/img_error.png' >"+brand["label"]+"</a>");
						dd.attr("dataid", brand.id);
						groupdt.after(dd);
					}
					initHref();
				}
			},
			error:function(r){
				WxchatClient.Dialog.show("加载车型库失败,请检查网络或后台服务.");
			}
		});
	}catch(e){
		alert("JSON数据格式不正确,详细请参考:"+e.message);
	}
});
</script>
</head>
<body>
<input type="hidden" name="backurl" value="${backurl }" />
<div class="wapper">
    <div class="che_btn">
    	<span class="click" rel="tab" name="carmodelvin" url="<%=path %>/<%=Constants.ROOT %>/car/sel">车型选择</span>
        <span rel="tab" name="carmodelvin" url="<%=path %>/<%=Constants.ROOT %>/car/toVin">VIN码</span>
    </div>
    <div id="cartypelistdivid" class="che_pic">
    	<ul>
        	<li>
            	<dl class="che_c">
                </dl>
            </li>
        </ul>
    </div>
</div>
</body>
</html>