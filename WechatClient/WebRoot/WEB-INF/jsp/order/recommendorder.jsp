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
<jsp:include page="../common/base.jsp" />
<link rel="stylesheet" href="<%=path %>/styles/css/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" href="<%=path %>/styles/css/jqm-demos.css">
<script src="<%=path %>/common/js/index.js"></script>
<script src="<%=path %>/common/js/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
<div class="wapper">
	<div class="submit_order">
    	<h1 class="my_order">评价</h1>
        <div class="process_bar">
        	<ul>
               	 <li>
               		<div class="margin"><span><strong>服务态度：</strong></span><span class="fr"><strong><!--5分--></strong></span></div>
                    <div  class="margin">
                       <input type="range" name="slider-2" id="slider-2" data-highlight="true" min="0" max="10" value="5">
                    </div>
                 </li>
                 <li>
               		<div class="margin"><span><strong>服务水平：</strong></span><span class="fr"><strong><!--9分--></strong></span></div>
                    <div  class="margin">
                       <input type="range" name="slider-2" id="slider-2" data-highlight="true" min="0" max="10" value="5">
                    </div>
                 </li>
                 <li class="margin">
                 	<table width="80%" border="0">
                      <tr>
                        <td colspan="2" class=""><strong>配送完好度：</strong></td>
                      </tr>
                      <tr>
                        <td class="assessment_choice"><a href="javascript:;">完好密封</a></td>
                        <td class="assessment_choice"><a href="javascript:;">包装有破损</a></td>
                      </tr>
                      <tr>
                        <td class="assessment_choice"><a href="javascript:;">配件有损坏</a></td>
                        <td class="assessment_choice"><a href="javascript:;">弄错订单</a></td>
                      </tr>
                    </table>
                 </li>
                 <li>
               		<div class="margin"><span><strong>综合评价：</strong></span><span class="fr no_assessment"><strong>未评分</strong></span></div>
                    <div  class="margin">
                    	<form>
                            <input type="range" name="slider-2" id="slider-2" data-highlight="true" min="0" max="10" value="5">
                    	</form>
                    </div>
                 </li>
                 <li>
                 	<div class="add_pic margin"><strong>添加照片：</strong></div>
                    <div class="pic_list margin">
                    	<img src="<%=path %>/styles/images/pic.jpg">
                        <img src="<%=path %>/styles/images/pic01.jpg">
                    </div>
                 </li>
              </ul>
        </div>
        <a href="javascript:;" class="ensure" style="color:#fff; font-weight:normal;">提交评价</a>
    </div>
</div>
</body>
</html>
