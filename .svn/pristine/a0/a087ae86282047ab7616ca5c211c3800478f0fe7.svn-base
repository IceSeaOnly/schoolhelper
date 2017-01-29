<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/30
  Time: 9:40
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
    <meta name="format-detection" content="telephone=no" />
    <title>个人中心</title>
    <style>
        .right{ height:100%; width:100%; background-color:#f9f9f9; position:absolute;top:0; right:0;}
        /*头像、点击登录*/
        .login{ height:128px; background-color:#01aff0; position:relative}
        .login > img{ width:9px; height:auto; position:absolute; left:5%; top:15px;}
        .login_photo{ width:48px; height:48px; background-color:#fff; float:left; margin-left:5%; margin-top:55px; border-radius:50%; overflow:hidden;}
        .login_photo img{ width:48px; height:48px;}
        .login p{ float:left; line-height:48px; margin-top:55px; font-size:16px;color:#fff; margin-left:13px;}
        /*菜单栏*/
        .column{ height:46px; position:relative; background:#fff;}
        .col_img{ width:18px; height:18px; float:left; margin-left:5%; margin-top:14px;}
        .col_img img{ width:18px; height:18px;}
        .column p{ line-height:46px; float:left; color:#191919; font-size:16px; margin-left:14px;}
        .amount{ width:8px; height:8px; position:absolute; left:5%; top:11px; background-color:#c40000; border-radius:50%; margin-left:15px;}
        /*选择头像*/
        .photo_box{ width:100%; height:138px; background-color:#fff; position:fixed; left:0; bottom:0;}
        .photo{ height:46px; line-height:46px; text-align:center; position:relative}
        /*底部导航*/
        .nav_box{ width:100%; height:51px; position:fixed; left:0; bottom:0; background-color:#fff;}
        .nav_index,.nav_order,.nav_my{ width:33.3%; float:left;}
        .nav_box img{ width:18px; height:auto; position:relative; left:50%; margin-left:-9px; margin-top:8px;}
        .nav_box h1{ font-size:12px; text-align:center; color:#65646b; line-height:25px;}
        .nav_box .current_nav{ color:#01aff0;}

    </style>
    <style>
        .success{ width:92%; height:120px; background:#fff; position:fixed; top:40%; left:4%; z-index:1000;text-align:center; color:#191919; font-size:18px; padding-top:20px;}
        .success button{ display:block; margin:auto; color:#fff; background:#01AEF0; font-size:18px; width:150px; height:50px; border-radius:3px; margin-top:20px;}
    </style>

</head>

<body style="background-color: #f9f9f9;">
<div class="right">
    <!--头像、点击登录-->
    <div class="login">
        <div class="login_photo" ><img src="../images/${user.my_money>0?"vip":"no_vip"}.png"></div>
        <p>${user.username} 会员余额:￥<fmt:formatNumber value="${(user.my_money/100)}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></p>
        <div class="border_bottom"></div>
    </div>
    <c:if test="${should_complete_user_info}">
        <!--需要更新信息-->
        <div class="black"></div>
        <div class="success">请先完善信息
            <a href="/user/change_my_info.do"><button>点我完善</button></a>
        </div>
    </c:if>
    <!--菜单栏-->
    <div class="column" onclick="document.location='/user/charge_vip.do';">
        <div class="col_img"><img src="../images/wallet.png"></div>
        <p>会员充值</p>
        <div class="border_bottom"></div>
    </div>
    <div class="column" onclick="document.location='/user/change_my_info.do';">
        <div class="col_img"><img src="../images/people.png"></div>
        <p>我的信息</p>
        <div class="border_bottom"></div>
    </div>
    <div class="column" onclick="document.location='/user/my_orders.do';">
        <div class="col_img"><img src="../images/order_3.png"></div>
        <p>我的订单</p>
        <div class="border_bottom"></div>
    </div>
    <div class="margin"></div>
    <%--<div class="column"  onclick="document.location='address.html';">--%>
        <%--<div class="col_img"><img src="../images/adrress.png"></div>--%>
        <%--<p>搬运订单</p>--%>
        <%--<div class="border_bottom"></div>--%>
    <%--</div>--%>
    <div class="column" onclick="document.location='/user/system_msg.do';">
        <div class="col_img"><img src="../images/message.png"></div>
        <p>消息中心</p>
        <div class="amount"></div>
        <div class="border_bottom"></div>
        <div class="border_top"></div>
    </div>

    <div class="margin"></div>
    <%--<div class="column" onclick="javascript:alert('开发中，敬请期待');">--%>
        <%--<div class="col_img"><img src="../images/share.png"></div>--%>
        <%--<p>邀请分享</p>--%>
        <%--<div class="border_bottom"></div>--%>
        <%--<div class="border_top"></div>--%>
    <%--</div>--%>
</div>
<!--底部导航-->
<div class="nav_box">
    <div class="border_top"></div>
    <div class="nav_index chenge" onclick="document.location='/user/index.do';">
        <img src="../images/index_gray.png"/>
        <h1 style="">首页</h1>
    </div>
    <div class="nav_order chenge" onclick="document.location='/user/my_orders.do';" >
        <img src="../images/order_gray.png">
        <h1>订单</h1>
    </div>
    <div class="nav_my chenge" onclick="document.location='/user/user_center.do';" >
        <img src="../images/my_blue.png">
        <h1 style=" color:#01AFF0">我的</h1>
    </div>
</div>
</body>
</html>
