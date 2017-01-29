<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/1
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no"/>
    <title>电脑服务</title>
    <style>
        /*service服务*/
        .service_box {
            height: 48px;
            position: relative;
            margin-top: 14px;
            padding-top: 7px;
            box-sizing: border-box
        }

        .service {
            width: 92%;
            height: 33px;
            line-height: 33px;
            color: #191919;
            border: 1px solid #d3d3d3;
            border-radius: 3px;
            box-sizing: border-box;
            text-align: center;
            font-size: 14px;
            margin: auto;
        }

        .checkbox {
            border: 1px solid #01AFF0;
        }

        .add_service {
            clear: both;
            line-height: 37px;
            height: 37px;
            width: 20%;
            margin-left: 40%;
            position: relative;
            text-align: center;
        }

        /*合计*/
        .total_box {
            padding-top: 24px;
            height: 168px;
            box-sizing: border-box
        }

        .total_box h1 {
            font-size: 12px;
            text-align: center;
            line-height: 18px;
            color: #65646b;
        }

        .total_box p {
            font-size: 22px;
            color: #c40000;
            text-align: center;
            line-height: 33px;
        }

        /*address地址*/
        .address {
            padding-left: 4%;
            position: relative;
            background-color: #fff;
            padding-top: 9px;
            padding-bottom: 9px;
            box-sizing: border-box
        }

        .address img {
            float: left;
            margin-top: 14px;
            width: 18px;
            height: auto
        }

        .address_text {
            float: left;
            margin-left: 12px;
        }

        .address h1 {
            font-size: 14px;
            line-height: 21px;
            color: #191919;
        }

        .address h2 {
            font-size: 14px;
            line-height: 18px;
            color: #65646b;
        }

        .address h3 {
            font-size: 14px;
            line-height: 18px;
            color: #b2bab8;
        }

        /***********选择服务时间:弹出层**********/
        #option_time {
            width: 100%;
            height: 100%;
            top: 0;
            right: 0;
            position: absolute;
            background: #fff;
        }

        .week_box {
            height: 62px;
            background-color: #fff;
            position: relative;
            margin-bottom: 8px;
        }

        .week {
            height: 62px;
            width: 14.3%;
            float: left
        }

        .week h1 {
            line-height: 22px;
            font-size: 14px;
            text-align: center;
            margin-top: 11px;
        }

        .week h2 {
            line-height: 19px;
            font-size: 12px;
            color: #65646b;
            text-align: center;
        }

        .blue {
            width: 14.3%;
            position: absolute;
            left: 0;
            bottom: 0;
            height: 2px;
            background-color: #01aff0;
        }

        .time_box {
            margin-bottom: 80px;
        }

        .time {
            width: 20%;
            float: left;
            margin-left: 4%;
            margin-top: 6px;
            border: 1px solid #ccc;
            border-radius: 3px;
            text-align: center;
            line-height: 40px;
            height: 40px;
            color: #b2bab8;
            box-sizing: border-box;
        }

        .option {
            border: 1px solid #01aff0;
            color: #191919;
            background-image: url(../images/checkbox.png);
            background-size: 12px 12px;
            background-position: right bottom;
            background-repeat: no-repeat;
        }

        /*保洁剂弹出层*/
        .detergent {
            position: absolute;
            width: 100%;
            height: 100%;
            top: 0px;
            left: 0px;
            z-index: 2000;
            background: #fff;
        }

        .hot_title {
            height: 20px;
            line-height: 20px;
            color: #65646b;
            padding-left: 14px;
            box-sizing: border-box;
            position: relative;
            font-size: 14px;
            background-color: #f9f9f9;
        }

        .agentia_box {
            width: 100%;
            position: relative;
            float: left
        }

        .agentia {
            height: 75px;
            background-color: #fff;
            position: relative
        }

        .agentia_img {
            width: 47px;
            height: 47px;
            margin-left: 4%;
            background-color: #737cd9;
            float: left;
            margin-top: 14px;
        }

        .agentia_text {
            float: left;
            margin-left: 13px;
        }

        .agentia_text h1 {
            font-size: 14px;
            line-height: 18px;
            color: #191919;
            margin-top: 11px;
        }

        .agentia_text h2 {
            font-size: 12px;
            line-height: 16px;
            color: #65646b;
        }

        .agentia_text h3 {
            font-size: 14px;
            line-height: 18px;
            color: #c40000;
        }

        .reduce, .add {
            width: 20px;
            height: 20px;
            position: absolute;
            right: 14px;
            top: 27px;
        }

        .amount {
            width: 20px;
            height: 20px;
            position: absolute;
            right: 14px;
            top: 27px;
            color: #191919;
            line-height: 20px;
            text-align: center
        }

        /*cart底部购物车*/
        .cart_box {
            position: absolute;
            bottom: 0;
            left: 0;
            background-color: #f9f9f9;
            width: 100%;
        }

        .cart_title {
            padding-left: 4%;
            line-height: 33px;
            color: #191919;
            position: relative;
            background-color: #fff;
        }

        .cart_pro {
            height: 42px;
        }

        .cart_pro h1 {
            float: left;
            line-height: 42px;
            margin-left: 4%;
            color: #191919;
        }

        .cart_pro h2 {
            float: left;
            line-height: 42px;
            margin-left: 20%;
            color: #c40000;
        }

        .cart_pro img {
            float: right;
            width: 20px;
            height: auto;
            margin-top: 11px;
        }

        .cart_pro p {
            float: right;
            width: 25px;
            height: 20px;
            margin-top: 11px;
            color: #191919;
            text-align: center
        }

        .cart {
            height: 47px;
            position: relative;
            margin-top: 16px;
            background-color: #fff;
        }

        .cart img {
            width: 46px;
            height: 46px;
            position: absolute;
            left: 4%;
            bottom: 17px;
        }

        .sure {
            float: right;
            width: 25%;
            height: 47px;
            background-color: #999;
            color: white;
            line-height: 47px;
            text-align: center;
            font-size: 18px;
        }

        .cart p {
            color: #c40000;
            font-size: 18px;
            line-height: 47px;
            float: right;
            margin-right: 6px;
        }

        .cart h1 {
            color: #191919;
            font-size: 14px;
            line-height: 47px;
            float: right
        }

        .number {
            position: absolute;
            left: 4%;
            top: -15px;
            margin-left: 34px;
            height: 18px;
            width: 18px;
            background-color: #c40000;
            border-radius: 50%;
            line-height: 18px;
            text-align: center;
            color: white;
            font-size: 12px;
        }

        .margin {
            margin-top: 16px;
        }
    </style>
    <script type="text/javascript">
        function check_form(){
            var na = document.myform.name.value;
            var qq = document.myform.qq.value;
            var hw = document.myform.helpwhat.value;
            var tm = document.myform.time.value;
            if(na.length<1 || qq.length<1 || hw.length<1 || tm.length < 1){
                alert('请先填写完整');
                return false;
            }
            return false;
        }
    </script>
</head>

<!--header顶部标题-->
<div class="header">电脑服务
    <div class="header_left" onclick="window.history.go(-1)"><img src="../images/return.png"></div>
</div>

<div class="margin"></div>
<form action="/user/take_computer_help_order.do" method="post" name="myform" id="myform" onsubmit="return check_form();">
    <div class="leather">
        <div class="border_top"></div>
        <div class="leather_text">
            <h1>姓名：</h1>
        </div>
        <input placeholder="输入你的名字" name="name" id="name"/>
    </div>
    <div class="leather">
        <div class="border_top"></div>
        <div class="leather_text">
            <h1>联系QQ：</h1>
        </div>
        <input placeholder="输入联系QQ" name="qq" id="phone"/>
    </div>
    <div class="leather">
        <div class="border_top"></div>
        <div class="leather_text">
            <h1>问题描述：</h1>
        </div>
        <input placeholder="描述一下需要什么帮助" name="helpwhat" id="qq"/>
        <div class="border_bottom"></div>
    </div>
    <div class="leather">
        <div class="border_top"></div>
        <div class="leather_text">
            <h1>联系时间：</h1>
        </div>
        <input placeholder="期望什么时间接到我们的联系？" name="time" id="qq"/>
        <div class="border_bottom"></div>
    </div>
    <input type="submit" class="confirm" value="提交预约">
</form>
</body>
</html>
