<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/2
  Time: 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no" />
    <meta charset="utf-8">
    <title>系统消息</title>
    <style>
        .news{ width:92%; height:105px; background:#fff; position:relative; padding-left:4%; padding-right:4%;}
        .news h3{ font-size:16px; color:#191919;padding-top:10px; padding-bottom:10px;}
        .news h3 span{ color:rgb(200,200,200); float:right; }
        .news p{ color:rgb(200,200,200);}
    </style>
</head>

<body style="background-color: #f9f9f9;">
<!--header顶部标题-->
<div class="header">系统消息
    <div class="header_left"><a href="/manager/index.do"><img src="../images/return.png"></a></div>
</div>
<div align="center">
    <p>输入新的系统消息：</p>
    <form action="/manager/add_sys_msg.do?sid=${sid}" method="post">
        <input name="title" type="text" style="width: 100%" placeholder="输入标题"><br><br>
        <textarea name="new_msg" style="width: 100%" placeholder="输入正文"></textarea>
        <br>
        <p><input type="submit" class="button" value="添加新系统消息"></p>
    </form>
</div>
<br><br>
<c:forEach items="${msgs}" var="msg">
    <div class="news">
        <h3>${msg.title}&nbsp;&nbsp;<a href="/manager/delete_sys_msg.do?sid=${sid}&id=${msg.id}">【删除此条】</a><span>${msg.addTime}</span></h3>
        <p>${msg.content}</p>
        <div class="border_bottom"></div>
    </div>
</c:forEach>

<script>

</script>
</body>
</html>


