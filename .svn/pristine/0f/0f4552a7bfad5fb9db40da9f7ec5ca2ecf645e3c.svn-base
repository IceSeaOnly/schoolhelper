<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/9
  Time: 9:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" %>

<!doctype html>
<html>
<head>
    <!-- 声明文档使用的字符编码 -->
    <meta charset='utf-8'>

    <!-- 为移动设备添加 viewport -->
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=3,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui"/>


    <title>配送订单</title>
    <link rel="stylesheet" href="../manager_style/css/common.css">
    <link rel="stylesheet" href="../manager_style/css/font.css">
    <link rel="stylesheet" href="../manager_style/css/header.css">
    <link rel="stylesheet" href="../manager_style/css/footer.css">
    <link rel="stylesheet" href="../manager_style/css/index.css">
    <link rel="stylesheet" href="../manager_style/css/responsive.css">
	<script type="text/javascript">
        function failed_confirm()
        {
            var r=confirm("确定为取件失败？")
            if (r==true)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    </script>
</head>

<body ontouchstart="return true;">

<!--内容区-->
<article class="main-container">

    <!--选项卡-->
    <nav class="memberOrder-nav line">
        <c:forEach items="${parts}" var="p_part">
            <a href="to_send_order.do?part=${p_part.id}" class="line${p_part.id == part?" select":""}">${p_part.partName}</a>
        </c:forEach>
    </nav>
    <div class="memberOrder-header"></div>

    <!--列表-->
    <c:forEach items="${orders}" var="order">
        <div class="memberOrder-list line">
            <p>
                下单时间：${order.orderTime}
            </p>
            <div class="order-product line">
                <ul>
                    <li>
                        <h2>${order.sendTo} ${order.receive_name} ${order.receive_phone}</h2>
                        <span>备注:${order.otherinfo}</span>
                    </li>
                </ul>
            </div>
            <p>
                <a href="/rider/send_order_result.do?result=3&id=${order.id}&part=${part}"><button>配送成功</button></a>
                <a href="/rider/send_order_result.do?result=-3&id=${order.id}&part=${part}" onclick="return failed_confirm();"><button>配送失败</button></a>
                <a href="tel:${order.receive_phone}"><button>拨打电话</button></a>
            </p>
        </div>
    </c:forEach>
</article>
<div align="center">
    <a href="/rider/index.do">回首页</a>
</div>
</body>
<script type="text/javascript" src="../manager_style/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../manager_style/js/swipe-min.js"></script>
<script type="text/javascript" src="../manager_style/js/common.js"></script>

