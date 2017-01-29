<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/4
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <!-- 为移动设备添加 viewport -->
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=3,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui"/>
    <title>配送端</title>
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

    <div class="member-header">
        <p>${rider.name},欢迎回来</p>
        <div class="memberhead">
            <img src="../manager_style/img/head01.jpg" alt="">
            <i class="icon-woman"></i>
        </div>
        <ul>
            <li class="line-w"><b>${waiting_to_take}</b><a href="/rider/to_take_order.do">待接订单</a></li>
            <li class="line-w"><b>${waiting_to_bring}</b><a href="/rider/to_bring_order_select.do">待取订单</a></li>
            <li class="line-w"><b>${waiting_to_send}</b><a href="/rider/to_send_order.do?part=-1">待送订单</a></li>
            <li><b>${rider.salary/100.0}</b>可取现</li>
        </ul>
        <div class="waves1 icon-waves"></div>
        <div class="waves2 icon-waves"></div>
        <div class="waves3 icon-waves"></div>
    </div>

    <div class="list">
        <ul class="line">
            <li onclick="location.href='/rider/want_salary.do'"><i class="icon-allorders"></i>申请提现<span
                    class="icon-arrowright"></span></li>
        </ul>
        <ul class="line">
            <li onclick="location.href='/rider/salary_record.do'"><i class="icon-head"></i>提现记录<span
                    class="icon-arrowright"></span></li>
            <li onclick="location.href='/rider/send_again.do'"><i class="icon-pwd"></i>重新配送失败快件<span
                    class="icon-arrowright"></span></li>
            <%--<li onclick="location.href='/manager/sys_msg.do'"><i class="icon-coupons"></i>已完成<span--%>
                    <%--class="icon-arrowright"></span></li>--%>
            <%--<li onclick="location.href='/manager/history_orders.do?next_page=0'"><i class="icon-address"></i>历史订单<span--%>
                    <%--class="icon-arrowright"></span></li>--%>
        </ul>
        <%--<ul class="line">--%>
        <%--<li><i class="icon-out"></i>退出</li>--%>
        <%--</ul>--%>
    </div>

</article>

</body>
</html>
<script type="text/javascript" src="../manager_style/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../manager_style/js/swipe-min.js"></script>
<script type="text/javascript" src="../manager_style/js/common.js"></script>

