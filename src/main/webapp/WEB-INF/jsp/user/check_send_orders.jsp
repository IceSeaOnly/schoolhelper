<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>查看快递</title>
    <link rel="stylesheet" href="../weixin_style/weui.css"/>
    <link rel="stylesheet" href="../weixin_style/example.css"/>
    <style>
        html {
            overflow-y: hidden;
        }
    </style>

</head>

<body>
<div align="center"><span style="color:red">查看需授权</span></div><br>
<c:forEach items="${expresses}" var="express">
    <div class="bd spacing">
        <a href="/user/check_send_orders_express.do?eid=${express.id}"><button class="weui_btn weui_btn_primary">${express.expressname}</button></a>
    </div>
</c:forEach>
</body>
</html>
