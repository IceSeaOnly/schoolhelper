<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/4
  Time: 14:35
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
    <title>确认提现</title>
    <style>
        .success{ width:92%; height:120px; background:#fff; position:fixed; top:40%; left:4%; z-index:1000;text-align:center; color:#191919; font-size:18px; padding-top:20px;}
        .success button{ display:block; margin:auto; color:#fff; background:#01AEF0; font-size:18px; width:150px; height:50px; border-radius:3px; margin-top:20px;}
    </style>
</head>
<body style="background-color: #f9f9f9;">

<!--充值成功：立即下单-->
<div class="black"></div>
<c:if test="${salary_sum != 0}">
    <div class="success">确认提现${salary_sum/100.0}元?
        <a href="/rider/confirm_want_salary.do"><button>确认提现</button></a>
    </div>
</c:if>
<c:if test="${salary_sum == 0}">
    <div class="success">暂无可提现金额
        <a href="/rider/index.do"><button>返回首页</button></a>
    </div>
</c:if>
</body>
</html>
