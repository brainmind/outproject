<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.wechat.client.utils.*" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
<title>小马上门汽车保养服务</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" Content="no-cach" />
<meta name="robots" content="all" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="stylesheet" type="text/css" href="<%=path %>/styles/css/reset.min.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/styles/css/style.css">
<script type="text/javascript" src="<%=path %>/common/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	$.ajax({
		url:"<%=path + Constants.ROOT %>/car/report.json?recordId=${recordId}",
		type:"get",
		dataType:"json",
		success:function(r){
			if(r && r.data){
				var order = r.data;
				$("#ordernoid").html("编号："+order["order_number"]);
				$("#servicetimeid").html(order["service_time"]);
				$("#workmanid").html(order["worker_name"]);
				$("#milesid").html(order["miles"]);
				$("#lastmilesid").html(order["last_miles"]);
				var commodities = order.commodities;
				if(commodities && commodities.length > 0){
					var commoditiestable = $("#commoditiestableid");
					for(var i=0; i<commodities.length; i++){
						var category_label = commodities[i]["category_label"];
						var label = commodities[i]["label"];
						commoditiestable.append("<tr><td><strong>"+category_label+"：</strong></td><td colspan=\"3\">"+label+"</td></tr>");
					}
				}
				var records = order.records;
				if(records && records.length > 0){
					var recordstable = $("#table_list").children("table");
					for(var i=0; i<records.length; i++){
						var label = records[i]["label"];
						var items = records[i]["items"];
						if(items && items.length > 0){
							recordstable.append("<tr><td colspan=\"4\" class=\"table_list_text_center\"><span>"+label+"</span></td></tr>");
							for(var j=0; j<items.length; j++){
								var sublabel = items[j]["label"];
								var value = items[j]["referench_value"];
								var result = items[j]["check_result"];
								recordstable.append("<tr class=\"gray\"><td width=\"5%\">"+(j+1)+"</td><td>"+sublabel+"</td><td>"+value+"</td><td>"+result+"</td></tr>");
							}
						}
					}
				}
			}
		}
	});
});
</script>
</head>
<body>
<div class="wapper">
	<div class="submit_order testing_result">
    	<h1 class="result">爱车检测报告</h1> 
		<ul>
        	<li class="text_center">
            	<div class="car_num"><span id="ordernoid">编号：20150989778</span><span class="float_right" id="servicetimeid">2015/2/15</span></div>
            	<table width="100%" border="0" id="commoditiestableid">
                  <tr>
                    <td width="25%"><strong>爱车型号：</strong></td>
                    <td colspan="3">北京现代ix25 6速手自动一体 1.6T尊享版</td>
                  </tr>
                  <tr>
                    <td><strong>车牌号：</strong></td>
                    <td>京A81D0</td>
                    <td><strong>检测技师：</strong></td>
                    <td id="workmanid">李二军</td>
                  </tr>
                  <tr>
                    <td><strong>当前里程：</strong></td>
                    <td id="milesid">125090</td>
                    <td><strong>上次里程：</strong></td>
                    <td id="lastmilesid">115090</td>
                  </tr>
                  <tr>
                    <td colspan="4"><strong>此次保养更换配件：</strong></td>
                  </tr>
                </table>
            </li>
          	<li class="tips">
               	<div class="">根据小马上门本次保养检测，您的爱车存在以下问题，请注意保养并持续关注此检测报告。</div>
               	<span>1、刹车片厚度不足，为了您的安全，建议更换。</span>
            </li>
            <li class="table_list" id="table_list">
            	<table width="100%" border="0">
                  <tr>
                    <td colspan="4" class="table_list_text_center">小马上门检查记录表（46项）</td>
                  </tr>
                </table>
            </li>
        </ul>
    </div>
</div>
</body>
</html>
