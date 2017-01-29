<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no"/>
    <meta charset="utf-8">
    <title>会员列表</title>
    <style>
        /*address地址*/
        .address {
            height: 78px;
            padding-left: 4%;
            padding-right: 4%;
            position: relative;
            margin-top: 14px;
            background-color: #fff;
            padding-top: 9px;
            box-sizing: border-box
        }

        .address h1 {
            font-size: 14px;
            line-height: 21px;
            color: #191919;
        }

        .address h2 {
            font-size: 14px;
            color: #65646b;
        }

        .address h3 {
            font-size: 14px;
            line-height: 18px;
            color: #b2bab8;
        }

        .address p {
            line-height: 41px;
            position: absolute;
            right: 4%;
            top: 0;
            background-image: url(../images/block.png);
            background-size: 13px 13px;
            background-position: left center;
            background-repeat: no-repeat;
            padding-left: 20px;
        }

        .delete {
            width: 20%;
            height: 74px;
            line-height: 74px;
            background-color: #c40000;
            color: white;
            font-size: 18px;
            text-align: center;
            position: absolute;
            top: 0;
            right: 0
        }
    </style>
</head>

<body style="background-color: #f9f9f9;">
<!--header顶部标题-->
<div class="header">会员列表
</div>

<c:forEach items="${vips}" var="vip">
    <div class="address">
        <div class="border_top"></div>
        <h1>OpenId:${vip.open_id}</h1>
        <h2>姓名：<span>${vip.username}</span>&nbsp;&nbsp;&nbsp;&nbsp;电话：<span>${vip.phone}</span></h2>
        <h2>余额：<span>￥<fmt:formatNumber value="${vip.my_money/100}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></span></h2>
        <div class="border_bottom"></div>
    </div>
</c:forEach>
</body>
</html>

