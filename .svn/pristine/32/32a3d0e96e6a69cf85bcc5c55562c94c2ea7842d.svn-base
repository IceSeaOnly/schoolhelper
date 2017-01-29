<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no"/>
    <title>充值记录</title>
    <style>
        .money {
            width: 97px;
            height: 97px;
            background-color: #ff7f00;
            border-radius: 50%;
            margin-left: auto;
            margin-right: auto;
            padding-top: 19px;
            box-sizing: border-box;
        }

        .money h1 {
            line-height: 24px;
            color: #fff;
            font-size: 14px;
            text-align: center
        }

        .money p {
            line-height: 28px;
            color: #fff;
            font-size: 23px;
            text-align: center
        }

        .help {
            position: absolute;
            right: 4%;
            top: 14px;
            border: 1px solid #EA2320;
            width: 16px;
            height: 16px;
            border-radius: 50%;
            line-height: 16px;
            text-align: center;
            color: #EA2320;
            margin-right: 62px
        }

        .help_nav {
            position: absolute;
            right: 4%;
            top: 14px;
            line-height: 16px;
            text-align: center;
            color: #65646A;
        }

        /*余额明细*/
        .detail_title {
            width: 21%;
            height: 39px;
            line-height: 39px;
            text-align: center;
            margin-left: auto;
            margin-right: auto;
            position: relative;
            color: #65646b;
            font-size: 12px;
        }

        .detail_title .border_top {
            width: 100px;
            top: 19px;
        }

        .detail {
            height: 60px;
            position: relative;
            background-color: #fff;
            padding-left: 4%;
            padding-top: 8px;
            box-sizing: border-box
        }

        .detail h1 {
            line-height: 23px;
            font-size: 14px;
            color: #191919;
        }

        .detail h2 {
            line-height: 19px;
            font-size: 12px;
            color: #65646b;
        }

        .detail p {
            line-height: 60px;
            position: absolute;
            right: 4%;
            top: 0;
            color: #01aff0;
            font-size: 16px;
        }
    </style>
</head>

<body style="background-color:#f9f9f9;">
<!--header顶部标题-->
<div class="header">会员余额池
    <div class="header_left" onClick="window.history.go(-1)"><img src="../images/return.png"></div>
</div>
<!--余额-->
<div style="background-color:#fff; position:relative; padding-top:28px; padding-bottom:36px;">
    <div class="money">
        <h1>总余额</h1>
        <p>￥<fmt:formatNumber value="${all_rest/100}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></p>
    </div>
    <div class="border_bottom"></div>
</div>
<!--余额明细-->
<div class="detail_title">充值记录
    <div class="border_top" style="left:-100px;"></div>
    <div class="border_top" style="left:inherit; right:-100px;"></div>
</div>


<c:forEach items="${orders}" var="order">
    <div class="detail">
        <div class="border_top"></div>
        <h1>${order.username}</h1>
        <h2>${order.getTimeString()}</h2>
        <p>+&nbsp;&nbsp;<fmt:formatNumber value="${order.shouldpay/100}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></p>
        <div class="border_bottom"></div>
    </div>
</c:forEach>
<div class="detail"></div>
<!--充值按钮-->
<div class="confirm_box">
    <div class="confirm" style="background-color:#01aff0;"
         onClick="document.location='/manager/index.do'">回首页
    </div>
</div>
<script>

</script>
</body>
</html>
