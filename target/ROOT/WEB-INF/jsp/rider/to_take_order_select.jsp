<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>选择快递</title>
    <link rel="stylesheet" href="../weixin_style/weui.css"/>
    <link rel="stylesheet" href="../weixin_style/example.css"/>
    <style>
        html {
            overflow-y: hidden;
        }
    </style>

</head>

<body>
<c:if test="${fn:length(expresses) == 0}">
    <h1>暂无订单</h1>
</c:if>
<c:if test="${fn:length(expresses) != 0}">
    <br>
    <div class="bd spacing">
        <a href="/rider/to_take_selected_order.do?eid=-10">
            <button class="weui_btn weui_btn_primary">仅查看上午单 ${morningSum}单</button>
        </a>
    </div>
    <br>
</c:if>

<c:forEach items="${expresses}" var="express">
    <div class="bd spacing">
        <a href="/rider/to_take_selected_order.do?eid=${express.id}">
            <button class="weui_btn weui_btn_plain_default">${express.expressName} ${express.curSum}单</button>
        </a>
    </div>
    <br>
</c:forEach>
</body>
</html>
