<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${user.username}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <script type="text/javascript" src="../js/jquery.min.js"></script>
    <script type="text/javascript" src="../js/amazeui.min.js"></script>
    <link rel="stylesheet" href="../css/amazeui.min.css" type="text/css"/>
    <link rel="stylesheet" href="../css/style.css" type="text/css"/>
</head>
<body>
<div class="wo">
    <img src="../20161224_images/tx.png"/>
    <p><a href="/user/change_my_school.do">${schoolname} <i class="am-icon-angle-right"></i></a></p>
</div>
<ul class="member">
    <li>
        <a href="/user/charge_vip.do">
            <h2><fmt:formatNumber value="${(user.my_money/100)}" pattern="##.##"
                                  minFractionDigits="2"></fmt:formatNumber></h2>
            <p>余额</p>
        </a>
    </li>
    <li>
        <a href="/user/my_coupons.do">
            <h2>${freeSum}</h2>
            <p>优惠券</p>
        </a>
    </li>
    <li>
        <a href="/user/myCoinPage.do">
            <h2>${user.cloudCoin}</h2>
            <p>云币</p>
        </a>
    </li>
</ul>
<ul class="nav">
    <li>
        <a href="/user/makeMyRefereeQRcode.do">
            <img src="http://cdn.binghai.site/o_1cb74noka1kndk7t1i73tht1otea.jpg"/>
            <span style="color:red">推荐新用户得云币!</span>
            <i class="am-icon-angle-right"></i>
        </a>
    </li>
    <li>
        <a href="/user/my_orders.do">
            <img src="../images/order_3.png"/>
            <span>我的订单</span>
            <i class="am-icon-angle-right"></i>
        </a>
    </li>
    <li>
        <a href="/user/system_msg.do">
            <img src="../20161224_images/i2.png"/>
            <span>消息中心</span>
            <i class="am-icon-angle-right"></i>
        </a>
    </li>
</ul>
<ul class="nav">
    <li>
        <a href="/user/to_feedback.do">
            <img src="../20161224_images/i3.png"/>
            <span>意见反馈</span>
            <i class="am-icon-angle-right"></i>
        </a>
    </li>
    <li>
        <a href="/user/change_my_info.do">
            <img src="../20161224_images/i4.png"/>
            <span>我的信息</span>
            <i class="am-icon-angle-right"></i>
        </a>
    </li>
    <li>
        <a href="tel:${sconfig.servicePhone}">
            <img src="../20161224_images/i5.png"/>
            <span>呼叫客服</span>
            <i class="am-icon-angle-right"></i>
            <em>${sconfig.servicePhone}</em>
        </a>
    </li>

</ul>
<div style="height: 49px;"></div>
<div data-am-widget="navbar" class="am-navbar  gm-foot am-no-layout" id="">
    <ul class="am-navbar-nav am-cf am-avg-sm-4">
        <li>
            <a href="/user/index.do">
                <span class="am-icon-home"></span>
                <span class="am-navbar-label">下单</span>
            </a>
        </li>
        <li>
            <a href="/user/my_orders.do">
                <span class="am-icon-th-large"></span>
                <span class="am-navbar-label">订单</span>
            </a>
        </li>
        <li class="curr">
            <a href="/user/user_center.do">
                <span class="am-icon-user"></span>
                <span class="am-navbar-label">我的</span>
            </a>
        </li>

    </ul>
</div>
</body>
</html>
