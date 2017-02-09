<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/1
  Time: 18:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no" />
    <meta charset="utf-8">
    <title>消息中心</title>
    <style>
        .news{ width:92%; height:105px; background:#fff; position:relative; padding-left:4%; padding-right:4%;}
        .news h3{ font-size:16px; color:#191919;padding-top:10px; padding-bottom:10px;}
        .news h3 span{ color:rgb(200,200,200); float:right; }
        .news p{ color:rgb(200,200,200);}
    </style>
</head>

<body style="background-color: #f9f9f9;">
<!--header顶部标题-->
<div class="header">消息中心
    <div class="header_left" onClick="window.history.go(-1)"><img src="../images/return.png"></div>
</div>
<c:forEach items="${msgs}" var="msg">
<div class="news">
    <h3>${msg.title}<span>${msg.timeStr}</span></h3>
    <p>${msg.content}</p>
    <div class="border_bottom"></div>
</div>
</c:forEach>

<script>

</script>
</body>
</html>

