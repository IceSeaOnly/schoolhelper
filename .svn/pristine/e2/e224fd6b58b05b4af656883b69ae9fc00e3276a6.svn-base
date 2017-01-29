<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/1
  Time: 22:42
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
    <title>确认退款</title>
    <style>
        .success{ width:92%; height:120px; background:#fff; position:fixed; top:40%; left:4%; z-index:1000;text-align:center; color:#191919; font-size:18px; padding-top:20px;}
        .success button{ display:block; margin:auto; color:#fff; background:#01AEF0; font-size:18px; width:150px; height:50px; border-radius:3px; margin-top:20px;}
    </style>
</head>
<body style="background-color: #f9f9f9;">

<!--充值成功：立即下单-->
<div class="black"></div>
<div class="success">确认为 ${order.express_name}(${order.receive_name}) 退款￥${order.shouldPay/100.0}
    <a href="refund_order.do?sid=${sid}&out_trade_no=${order.orderKey}&refund_fee=${order.shouldPay}&validate=${validate}"><button>确认退款</button></a>
</div>
</body>
</html>
