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
<link rel="stylesheet" href="<%=path %>/styles/css/jquery.mobile-1.4.5.min.css">
<link rel="stylesheet" href="<%=path %>/styles/css/jqm-demos.css">
<script type="text/javascript" src="<%=path %>/common/js/index.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript">
	function recommendOrder(){
		var form = $(document.orderForm);
		var submitBut = $("#submitcomment");
		var href = submitBut.attr("href");
		submitBut.attr("rel", href);
		submitBut.removeAttr("href");
		submitBut.addClass("pay_unlock");
		$.ajax({
			url:form.attr("action"),
			type:"post",
			data:form.serialize(),
			dataType:"json",
			success:function(r){
				if(r && r.status && r.status == 200){
					WxchatClient.Dialog.show("评价提交成功！", function(){
						window.location.href="<%=path + Constants.ROOT %>/order/ready?orderId="+r.orderid;
					});
					window.setTimeout(function(){
						window.location.href="<%=path + Constants.ROOT %>/order/ready?orderId="+r.orderid;
					}, 2000);
				}else{
					WxchatClient.Dialog.show("评价提交失败！", function(){
						submitBut.attr("href", href);
						submitBut.removeClass("pay_unlock");
					});
				}
			}
		});
	}
</script>
</head>
<body>
<div class="wapper">
	<div class="submit_order">
    	<h1 class="my_order">评价</h1>
    	<form name="orderForm" action="<%=path + Constants.ROOT %>/order/comment" method="post">
    	<input type="hidden" name="orderId" value="${orderId }"/>
    	<input type="hidden" name="commentid" value="${appr.commentid }"/>
    	<input type="hidden" name="comment_time" value="${appr.comment_time }"/>
        <div class="process_bar">
        	<ul>
        	<c:forEach items="${appr.fields }" var="item">
       			<input type="hidden" name="field_id" value="${item.field_id }"/>
       			<input type="hidden" name="type" value="${item.type }"/>
        		<c:choose>
        		<c:when test="${item.type == 'rate' }">
        		<li>
        			<div class="margin"><span><strong>${item.label }(分)：</strong></span><span class="fr"><strong><!--5分--></strong></span></div>
                    <div  class="margin">
                       <input type="range" name="value" id="range${item.field_id }" data-highlight="true" min="0" max="10" value="5">
                    </div>
        		</li>
        		</c:when>
        		<c:when test="${item.type == 'select' }">
        		<li class="margin">
        			<input type="hidden" name="value" id="value${item.field_id }" value=""/>
                 	<table width="80%" border="0">
                      <tr>
                        <td colspan="2" class=""><strong>配送完好度：</strong></td>
                      </tr>
                      <tr>
                      <c:forEach items="${item.options }" var="op" varStatus="ops">
                      	<td class="assessment_choice"><a href="javascript:void(0);" field-id="${item.field_id }" id="${op.option_id }">${op.label }</a></td>
                      	<c:if test="${ops.index % 2 == 1 }"></tr><tr></c:if>
                      </c:forEach>
                      </tr>
                    </table>
                 </li>
        		</c:when>
        		<c:when test="${item.type == 'photo' }">
        		<li>
                 	<div class="add_pic margin"><strong>${item.label }：</strong></div>
                    <div class="pic_list margin">
                    	<img src="<%=path %>/styles/images/pic.jpg">
                        <img src="<%=path %>/styles/images/pic01.jpg">
                    </div>
                 </li>
        		</c:when>
        		</c:choose>
        	</c:forEach>
            </ul>
        </div>
        <a id="submitcomment" href="javascript:recommendOrder();" class="ensure" style="color:#fff; font-weight:normal;">提交评价</a>
        </form>
    </div>
</div>
</body>
</html>
