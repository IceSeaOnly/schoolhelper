<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/1
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
    <!-- 为移动设备添加 viewport -->
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=3,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui"/>
    <title>管理员</title>
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
        <p>${manager.name},欢迎回来</p>
        <div class="memberhead">
            <img src="../manager_style/img/head01.jpg" alt="">
            <i class="icon-woman"></i>
        </div>
        <ul>
            <li class="line-w"><b>${today_order_number}</b>今日订单</li>
            <li class="line-w"><b>${history_order_number}</b>历史订单</li>
            <li class="line-w"><b><fmt:formatNumber value="${today_income}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber> </b>今日收入</li>
            <%--<li><b>${history_income}/<fmt:formatNumber value="${this_month_incomes/100}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></b>历史/当月</li>--%>
            <li><b>${history_income}</b>历史收入</li>
        </ul>
        <div class="waves1 icon-waves"></div>
        <div class="waves2 icon-waves"></div>
        <div class="waves3 icon-waves"></div>
    </div>

    <div class="list">
        <ul class="line">
            <li onclick="location.href='/manager/today_orders.do?sid=${sid}'"><i class="icon-allorders"></i>今日订单<span
                    class="icon-arrowright"></span></li>
            <li onclick="location.href='/user/check_send_orders.do?sid=${sid}'"><i class="icon-allorders"></i>代寄订单<span
                    class="icon-arrowright"></span></li>
        </ul>
        <ul class="line">
            <li onclick="location.href='/manager/manage_rider.do?sid=${sid}'"><i class="icon-head"></i>配送员管理<span
                    class="icon-arrowright"></span></li>
            <li onclick="location.href='/manager/system_setting.do?sid=${sid}'"><i class="icon-pwd"></i>系统设置<span
                    class="icon-arrowright"></span></li>
            <li onclick="location.href='/manager/sys_msg.do?sid=${sid}'"><i class="icon-coupons"></i>系统消息<span
                    class="icon-arrowright"></span></li>
            <li onclick="location.href='/manager/history_orders.do?next_page=0&sid=${sid}'"><i class="icon-address"></i>历史订单<span
                    class="icon-arrowright"></span></li>
            <li onclick="location.href='/manager/vip_charge_list.do?sid=${sid}'"><i class="icon-address"></i>充值订单<span
                    class="icon-arrowright"></span></li>
            <li onclick="location.href='/manager/vip_list.do?sid=${sid}'"><i class="icon-address"></i>会员列表<span
                    class="icon-arrowright"></span></li>
        </ul>
        <ul class="line">
            <li><i class="icon-out"></i><a href="/manager/pay_rider_salary.do?sid=${sid}">配送员工资支付</a></li>
        </ul>
    </div>
    <div style="height: 100px;"></div>

</article>

</body>
</html>
<script type="text/javascript" src="../manager_style/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../manager_style/js/swipe-min.js"></script>
<script type="text/javascript" src="../manager_style/js/common.js"></script>

