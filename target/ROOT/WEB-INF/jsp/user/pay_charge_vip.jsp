<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/31
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="refresh" content="0;http://weixin.qdxiaogutou.com/payloading.php?orderKey=${key}&type=1">
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no" />
    <meta charset="utf-8">
    <title>跳转中</title>
    <style>
        .success{ width:92%; height:120px; background:#fff; position:fixed; top:40%; left:4%; z-index:1000;text-align:center; color:#191919; font-size:18px; padding-top:20px;}
        .success button{ display:block; margin:auto; color:#fff; background:#01AEF0; font-size:18px; width:150px; height:50px; border-radius:3px; margin-top:20px;}
    </style>
</head>
<body style="background-color: #f9f9f9;">

<!--充值成功：立即下单-->
<div class="black"></div>
<div class="success">如果浏览器没有自动跳转请点击：
    <a href="http://weixin.qdxiaogutou.com/payloading.php?orderKey=${key}&type=1"><button>前往支付</button></a>
</div>
</body>
</html>

