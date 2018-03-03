<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/1
  Time: 16:55
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
    <title>支付失败</title>
    <style>
        .success{ width:92%; height:120px; background:#fff; position:fixed; top:40%; left:4%; z-index:1000;text-align:center; color:#191919; font-size:18px; padding-top:20px;}
        .success button{ display:block; margin:auto; color:#fff; background:#01AEF0; font-size:18px; width:150px; height:50px; border-radius:3px; margin-top:20px;}
    </style>
</head>
<body style="background-color: #f9f9f9;">

<!--充值成功：立即下单-->
<div class="black"></div>
<div class="success">余额不足:￥<fmt:formatNumber value="${user.my_money>0?user.my_money/100:0}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>
    <a href="/user/charge_vip.do"><button>立即充值</button></a>
    <a href="http://pay.bigdata8.xin/payloading.php?orderKey=${order_key}&type=${type}"><button>微信支付</button></a>
</div>
</body>
</html>


