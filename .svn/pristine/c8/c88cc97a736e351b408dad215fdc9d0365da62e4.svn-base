
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/31
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no"/>
    <meta charset="utf-8">
    <title>付款详情</title>
    <style>
        /**********使用优惠券***********/
        .box {
            padding-left: 4%;
            padding-right: 4%;
            margin-top: 16px;
            margin-bottom: 46px;
        }

        .box .box_headline {
            float: left;
        }

        .box .rule {
            float: right;
        }

        .box .rule h3 {
            background: url(../images/payment.jpg) 0px 0px no-repeat;
            line-height: 20px;
            background-size: 20px;
            width: 80px;
            text-align: right;
        }

        #privilege {
            margin-top: 20px;
        }

        #privilege img {
            margin: auto;
            margin-top: 5px;
            width: 36px;
            height: auto;
        }

        #privilege ul {
            width: 92%;
            height: 60px;
            margin: auto;
            list-style: none;
            overflow: hidden;
            border-top: 5px #01AEF0 solid;
            border-radius: 6px 6px 0 0;
            position: relative;
            background: #fff;
        }

        #privilege ul .div_li {
            width: 100%;
            height: 50px;
            background: #fff;
            padding-left: 16px;
            padding-top: 10px;
            position: relative;
        }

        #privilege ul .div_li h3 {
            color: #B0BCB8;
            font-size: 16px;
        }

        #privilege ul .div_li p {
            color: #B0BCB8;
        }

        #privilege ul .div_li span {
            display: block;
            position: absolute;
            right: 40px;
            height: 40px;
            width: 60px;
            top: 10px;
            line-height: 40px;
            text-align: right;
            color: #B0BCB8;
            font-size: 20px;
            border-left: 1px #cccccc dashed
        }

        #privilege ul li {
            width: 100%;
            height: 50px;
            background: #fff;
            padding-left: 16px;
            padding-top: 10px;
            position: relative;
        }

        #privilege ul li h3 {
            color: #65646A;
            font-size: 16px;
        }

        #privilege ul li p {
            color: #B0BCB8;
        }

        #privilege ul li span {
            display: block;
            position: absolute;
            right: 40px;
            height: 40px;
            width: 60px;
            top: 10px;
            line-height: 40px;
            text-align: right;
            color: #E60002;
            font-size: 20px;
            border-left: 1px #cccccc dashed
        }

        /**********付费方式***********/
        .pay {
            line-height: 40px;
            font-size: 16px;
            color: #65646A;
            margin-left: 4%;
        }

        /*付款详情*/
        .payment_box {
            height: 210px;
            background-color: #fff;
            width: 100%;
            position: relative;
            margin-bottom: 55px;
        }

        .payment_title {
            height: 44px;
            line-height: 44px;
            font-size: 16px;
            color: #65646b;
            text-align: center;
            position: relative;
        }

        .payment_title img {
            width: 13px;
            height: auto;
            position: absolute;
            left: 4%;
            top: 15px;
        }

        .payment_title p {
            line-height: 44px;
            position: absolute;
            right: 4%;
            top: 0;
            font-size: 14px;
            color: #01aff0;
        }

        .pay_type {
            width: 92%;
            margin-left: 4%;
            height: 46px;
            position: relative
        }

        .pay_type img {
            width: 20px;
            height: 20px;
            float: left;
            margin-top: 13px;
        }

        .pay_type h1 {
            line-height: 17px;
            font-size: 14px;
            color: #191919;
        }

        .pay_type h2 {
            line-height: 16px;
            font-size: 12px;
            color: #65646b;
            float: left
        }

        .pay_type p {
            line-height: 16px;
            font-size: 12px;
            color: #01aff0;
            float: left;
            margin-left: 11px;
        }

        .pay_type h3 {
            line-height: 46px;
            font-size: 14px;
            color: #191919;
            float: left;
            margin-left: 12px;
        }

        .pay_type input {
            float: right;
            width: 13px;
            height: 13px;
            margin-top: 14px;
        }

        .payment_box > h1 {
            font-size: 14px;
            color: #191919;
            float: left;
            margin-left: 4%;
            margin-top: 10px;
        }

        .payment_box > h2 {
            font-size: 16px;
            color: #E82627;
            float: right;
            margin-right: 4%;
            margin-top: 10px;
        }

        .payment_box > p {
            font-size: 12px;
            color: #B1BAB7;
            clear: both;
            float: right;
            margin-right: 4%;
        }

        .pay_btn {
            background-color: #FF7F00;
            color: white;
            border-radius: 3px;
            width: 92%;
            height: 33px;
            line-height: 33px;
            text-align: center;
            font-size: 16px;
            position: fixed;
            left: 4%;
            bottom: 16px;
            z-index: 2000;
        }

        /***已支付完成与重新支付**/
        .succeed {
            width: 100%;
            height: 224px;
            background: #fff;
            position: fixed;
            bottom: 0;
            left: 0;
        }

        .succeed .symbol_x {
            position: absolute;
            left: 4%;
            font-size: 40px;
            color: #7E7E7E;
        }

        .succeed h3 {
            font-size: 18px;
            color: #191919;
            text-align: center;
            margin-top: 30px;
            margin-bottom: 30px;
            position: relative
        }

        .succeed h3 img {
            width: 23px;
            height: 23px;
            margin: auto;
            position: absolute;
            top: 0px;
            right: 50%;
            margin-right: 130px;
        }

        .warn {
            padding-top: 50px;
        }

        .succeed p {
            font-size: 14px;
            color: #7C7B6C;
            width: 308px;
            margin: auto;
            text-align: center;
            line-height: 25px;
        }

        .succeed p span {
            color: #01AFF0;
            font-size: 14px;
        }

        .succeed .btn_box {
            position: absolute;
            width: 100%;
            height: 60px;
            bottom: 0;
            left: 0;
        }

        .succeed .btn_box .btn_succeed {
            width: 50%;
            height: 60px;
            text-align: center;
            line-height: 60px;
            float: left;
            color: #01AFF0;
            font-size: 18px;
        }

        .succeed .btn_box .btn_defeated {
            width: 50%;
            height: 60px;
            text-align: center;
            line-height: 60px;
            float: left;
            color: #C40000;
            font-size: 18px;
        }
    </style>
</head>

<body style="background-color: #f9f9f9;">
<!--header顶部标题-->
<div class="header">付款详情
    <div class="header_left"><img src="../images/return.png"></div>
</div>
<%--<!--privilege优惠券下拉列表-->--%>
<%--<div class="box">--%>
<%--<div class="box_headline">使用优惠券</div>--%>
<%--<div class="rule">--%>
<%--<h3>使用细则</h3>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div id="privilege">--%>
<%--<ul class="ul">--%>
<%--<div class="div_li">--%>
<%--<h3>保洁卷一</h3>--%>
<%--<p>有效期至2016-2-30</p>--%>
<%--<span>￥10</span>--%>
<%--<div class="border_bottom"></div>--%>
<%--</div>--%>

<%--<div class="border_left"></div>--%>
<%--<div class="border_right"></div>--%>
<%--</ul>--%>
<%--<img class="arrows" src="../images/select.jpg">--%>
<%--</div>--%>
<h3 class="pay">付费方式</h3>
<!--点击确认订单，弹出付款详情-->
<form action="/user/pay_school_move.do" method="post">
    <input name="orderKey" value="${smo.orderKey}" type="hidden"/>
    <div class="payment_box" style=" z-index:1500;">
        <div class="pay_type">
            <img src="../images/huiyuan.png">
            <div style="float:left; margin-left:12px; margin-top:7px;">
                <h1>会员支付</h1>
                <h2>会员余额：<fmt:formatNumber value="${user.my_money/100}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>元</h2>
                <p><a href="/user/charge_vip.do">立即充值</a></p>
            </div>
            <input type="radio" value="vippay" name="pay">
            <div class="border_bottom"></div>
        </div>
        <div class="pay_type">
            <img src="../images/weixin2.png">
            <h3>微信支付</h3>
            <input type="radio" value="wxpay" name="pay" checked="checked">
            <div class="border_bottom"></div>
        </div>

        <h1>支付金额</h1>
        <h2>￥5.0</h2>

        <div class="border_top" style="left:0px; top:0px;"></div>
    </div>
    <a>
        <input type="submit" class="pay_btn" value="立即支付"/>
    </a>
</form>

</body>
</html>

