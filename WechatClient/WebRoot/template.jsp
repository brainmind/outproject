<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>lrz3 demo</title>
</head>
<style>
    ul, li {
        margin: 0;
        padding: 0;
        list-style: none;
    }

    .text-cyan {
        color: cyan;
    }

    .text-muted {
        color: #999;
    }

    .item {
        position: relative;
        margin: 8px 0;
    }

    .item ul {
        font-size: 15px;
        position: absolute;
        top: 0;
        left: 0;
        color: #fff;
        text-shadow: 1px 1px 5px #000;
        border:1px dotted rgba(255, 255, 255, 0.3);
        background: rgba(255, 255, 255, 0.3);
    }

    .item ul li {
        margin: 10px 15px;
    }

    .item img {
        max-width: 100%;
        box-shadow: 1px 1px 5px #ccc;
    }

    footer {
        width: 100%;
        font-size: 12px;
        text-align: center;
        position: absolute;
        left: 0;
        bottom: 0;
        margin: 20px 0;
    }

    footer a {
        color: #777;
    }

    input {
        display: none;
    }
</style>
<script type="text/javascript">
	var root = "<%=path %>";
</script>
<body>

<input type="file" capture="camera" />
<!--<input type="file" accept="image/*" capture="camera" /> 部分手机无法正确限制图片格式 -->

<hr/>
<div id="tip"><!-- 演示提示 --></div>
<ul id="report"><!-- 演示报告 --></ul>

<footer>
    <p style=""><a href="https://github.com/think2011/localResizeIMG3">localResizeIMG3 by think2011</a></p>
</footer>

<script src="common/ws/mobileFix.mini.js"></script>
<script src="common/ws/exif.js"></script>
<script src="common/ws/lrz.js"></script>
<script src="common/js/index.upload.js"></script>
</body>
</html>