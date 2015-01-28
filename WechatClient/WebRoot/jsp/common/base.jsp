<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=path %>/styles/css/reset.min.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/styles/css/style.css">
<script type="text/javascript" src="<%=path %>/common/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.event.drag-1.5.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.touchSlider.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/zepto.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/show.js"></script>
<script type="text/javascript">
$(document).ready(function () {
	$("span[rel=tab][name=carmodelvin]").each(function(){
		var $this = $(this);
		var url = $this.attr("url");
		var target = $this.attr("target");
		$this.on("click", function(event){
			$("span[rel=tab][name=carmodelvin]").removeClass("click");
			$this.addClass("click");
			if(url && url != null && url != ""){
				if(target && target != null && target != ""){
					$.ajax({
						url:url,
						type:"get",
						dataType:"html",
						success:function(r){
							$("#"+target).html(r);
						}
					});
				}else{
					window.location.href = url;
				}
			}
		});
	});
});
</script>