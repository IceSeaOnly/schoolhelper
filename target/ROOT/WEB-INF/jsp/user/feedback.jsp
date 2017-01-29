<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/30
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <script type="text/javascript" src="../js/jquery.min.js" ></script>
    <script type="text/javascript" src="../js/amazeui.min.js"></script>
    <link rel="stylesheet" href="../css/amazeui.min.css" type="text/css" />
    <link rel="stylesheet" href="../css/style.css" type="text/css" />
</head>
<body>
<header data-am-widget="header" class="am-header am-header-default jz">
    <h1 class="am-header-title">
        <a href="#title-link" class="">反馈</a>
    </h1>
</header>
<form action="/user/feedback.do" method="post">
    <textarea class="ts" name="content" placeholder="请输入反馈内容"></textarea>
    <button class="login-btn" type="button" onclick="javascript:this.form.submit();">提交</button>
</form>


</body>
</html>
