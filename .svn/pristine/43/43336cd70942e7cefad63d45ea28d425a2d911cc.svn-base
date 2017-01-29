<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/1
  Time: 22:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no"/>
    <meta charset="utf-8">
    <title>今日订单</title>
    <c:if test="${fn:length(orders) != 0}">
        <style>
            /*订单状态*/
            .state_box {
                height: 43px;
                position: relative
            }

            .state {
                float: left;
                width: 50%;
                line-height: 43px;
                color: #65646b;
                font-size: 14px;
                text-align: center
            }

            .blue_block {
                width: 50%;
                position: absolute;
                left: 0;
                bottom: 0;
                height: 2px;
                background-color: #01aff0;
            }

            /*订单信息*/
            .move_box {
                width: 100%;
                overflow: hidden
            }

            .move {
                width: 500%;
                position: relative;
                clear: both;
                left: 0
            }

            .order_box {
                float: left;
                width: 20%;
            }

            .order {
                height: 133px;
                position: relative;
                background-color: #fff;
                margin-bottom: 14px;
            }

            .order_title {
                line-height: 40px;
                color: #191919;
                padding-left: 4%;
                font-size: 16px;
            }

            .order_text {
                width: 92%;
                margin-left: 4%;
                position: relative;
                height: 53px;
                padding-top: 5px;
                box-sizing: border-box
            }

            .order_text h1, .order_text h2 {
                line-height: 20px;
                color: #191919;
                font-size: 14px;
            }

            .order_text p {
                position: absolute;
                top: 0;
                right: 0;
                line-height: 53px;
                color: #65646b;
                font-size: 16px;
            }

            .cancel {
                width: 65px;
                height: 25px;
                border: 1px solid #ccc;
                text-align: center;
                line-height: 25px;
                border-radius: 3px;
                float: right;
                margin-top: 6px;
                margin-right: 4%;
                color: #65646b;
            }

            /*底部导航*/
            .nav_box {
                width: 100%;
                height: 51px;
                position: fixed;
                left: 0;
                bottom: 0;
                background-color: #fff;
            }

            .nav_index, .nav_order, .nav_my {
                width: 33.3%;
                float: left;
            }

            .nav_box img {
                width: 18px;
                height: auto;
                position: relative;
                left: 50%;
                margin-left: -9px;
                margin-top: 8px;
            }

            .nav_box h1 {
                font-size: 12px;
                text-align: center;
                color: #65646b;
                line-height: 25px;
            }

            .nav_box .current_nav {
                color: #01aff0;
            }
        </style>
    </c:if>
    <c:if test="${fn:length(orders) == 0}">
        <style>
            /*订单状态*/
            .state_box {
                height: 43px;
                position: relative
            }

            .state {
                float: left;
                width: 50%;
                line-height: 43px;
                color: #65646b;
                font-size: 14px;
                text-align: center
            }

            .blue_block {
                width: 50%;
                position: absolute;
                left: 0;
                bottom: 0;
                height: 2px;
                background-color: #01aff0;
            }

            .move_box {
                width: 100%;
                text-align: center;
                padding-top: 100px;
            }

            .move_box img {
                width: 80px;
                height: 80px;
                margin: auto
            }

            .move_box p {
                font-size: 16px;
                color: #9A9A9A;
                padding-top: 16px;
                padding-bottom: 16px;
            }

            .move_box button {
                width: 80px;
                height: 30px;
                background: #01AEF0;
                color: #fff;
                border-radius: 5px;
                font-size: 18px;
            }

            /*底部导航*/
            .nav_box {
                width: 100%;
                height: 51px;
                position: fixed;
                left: 0;
                bottom: 0;
                background-color: #fff;
            }

            .nav_index, .nav_order, .nav_my {
                width: 33.3%;
                float: left;
            }

            .nav_box img {
                width: 18px;
                height: auto;
                position: relative;
                left: 50%;
                margin-left: -9px;
                margin-top: 8px;
            }

            .nav_box h1 {
                font-size: 12px;
                text-align: center;
                color: #65646b;
                line-height: 25px;
            }

            .nav_box .current_nav {
                color: #01aff0;
            }
        </style>
    </c:if>
</head>

<body style="background-color: #f9f9f9;">
<!--header顶部标题-->
<div class="header">今日订单
</div>
<!--订单状态-->
<div class="state_box">
    <div class="state">进行中</div>
    <div class="state">已完成</div>
    <div class="blue_block"></div>
    <div class=" border_bottom"></div>
</div>
<!--订单信息-->
<div class="move_box">
    <c:if test="${fn:length(orders) == 0}">
        <img src="../images/order-none.fw.png">
        <p>没有相关订单</p>
    </c:if>
    <c:if test="${fn:length(orders) != 0}">
        <div class="move">
            <!--未完成-->
            <div class="order_box">
                <c:forEach items="${orders}" var="order">
                    <c:if test="${order.order_state != -1 && order.order_state != 3}">
                        <div class="order">
                            <div class="border_top"></div>
                            <div class="order_title">${order.express}/${order.express_name}/${order.express_phone}</div>
                            <div class="order_text">
                                <div class="border_top"></div>
                                <h1>【${order.rider_name} ${order.state_toString()}】</h1>
                                <h2>${order.sendTo} ${order.receive_name} ${order.receive_phone} ${order.otherinfo}</h2>
                                <p>￥${order.shouldPay/100.0}</p>
                                <div class="border_bottom"></div>
                            </div>
                            <a href="/manager/refund.do?sid=${sid}&orderKey=${order.orderKey}">
                                <div class="cancel" style="border:#ff7f00 1px solid; color:#ff7f00;">退款操作</div>
                            </a>
                            <div class="border_bottom"></div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>

            <!--已完成-->
            <div class="order_box">
                <c:forEach items="${orders}" var="order">
                    <c:if test="${order.order_state == -1 || order.order_state == 3}">
                        <div class="order">
                            <div class="border_top"></div>
                            <div class="order_title">${order.express}/${order.express_name}/${order.express_phone}</div>
                            <div class="order_text">
                                <div class="border_top"></div>
                                <h1>【${order.rider_name} ${order.state_toString()}】</h1>
                                <h2>${order.sendTo} ${order.receive_name} ${order.receive_phone} ${order.otherinfo}</h2>
                                <p>￥${order.shouldPay/100.0}</p>
                                <div class="border_bottom"></div>
                            </div>
                                <div class="cancel" style="border:1px solid #01aff0; color:#01aff0;">已完成</div>
                            <div class="border_bottom"></div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </c:if>
</div>


<script>
    $(".state:eq(0)").click(
            function () {
                $(".blue_block").animate({left: '0%'})
                <c:if test="${fn:length(orders) != 0}">
                $(".move").css("left", "0%")
                </c:if>
            }
    )
    $(".state:eq(1)").click(
            function () {
                $(".blue_block").animate({left: '50%'})
                <c:if test="${fn:length(orders) != 0}">
                $(".move").css("left", "-100%")
                </c:if>
            }
    )

</script>
</body>
</html>


