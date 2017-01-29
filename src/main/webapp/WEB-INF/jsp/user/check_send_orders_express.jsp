<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/9
  Time: 9:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" %>

<!doctype html>
<html>
<head>
    <!-- 声明文档使用的字符编码 -->
    <meta charset='utf-8'>

    <!-- 为移动设备添加 viewport -->
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=3,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui"/>


    <title>${exname}订单</title>
    <link rel="stylesheet" href="../manager_style/css/common.css">
    <link rel="stylesheet" href="../manager_style/css/font.css">
    <link rel="stylesheet" href="../manager_style/css/header.css">
    <link rel="stylesheet" href="../manager_style/css/footer.css">
    <link rel="stylesheet" href="../manager_style/css/index.css">
    <link rel="stylesheet" href="../manager_style/css/responsive.css">
</head>

<body ontouchstart="return true;">

<!--内容区-->
<article class="main-container">
    <!--列表-->
    <c:if test="${fn:length(orders)==0}">
        <h1>暂无订单</h1>
    </c:if>
    <c:forEach items="${orders}" var="order">
        <div class="memberOrder-list line">
            <p>
                下单时间：${order.orderTime}
            </p>
            <div class="order-product line">
                <ul>
                    <li>
                        <h2>${order.name} ${order.address}</h2>
                    </li>
                </ul>
            </div>
            <p>
                    ${order.phone}<a href="tel:${order.phone}"><button>拨打电话</button></a>
            </p>
        </div>
    </c:forEach>
</article>

</body>
<script type="text/javascript" src="../manager_style/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../manager_style/js/swipe-min.js"></script>
<script type="text/javascript" src="../manager_style/js/common.js"></script>

