<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/30
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <script src="../js/swiper.min.js"></script>
    <script src="../SpryAssets/SpryTabbedPanels.js" type="text/javascript"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <link href="../css/swiper.min.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no" />

    <meta charset="utf-8">
    <title>首页</title>
    <style>
        .header_left img{ width:18px; height:auto; position:absolute; left:4%; top:2px; margin-left:36px;}
        .header_left{ font-size:16px;}
        /*****banner焦点图*********/
        .banner_box{ width:320px; height:140px; margin:auto;}
        .banner_box #banner img{ width:320px; height:100%}
        .swiper-pagination-bullet{ background:#fff; opacity:0.6;}
        .swiper-pagination-bullet-active{ background:#fff; opacity:1;}
        .swiper-container-horizontal>.swiper-pagination-bullets{ bottom:3px; left:auto; right:4%; width:55px;}

        @media screen and (max-width: 768px) {
            .banner_box{ width:100%; }
            .banner_box #banner img{ width:100%;}
        }
        /*常用服务*/
        .regularly_title{ width:22%; height:41px; line-height:41px; position:relative; margin-left:auto; margin-right:auto; text-align:center; color:#b2bab8;}
        .regularly_title .border_top{ width:160%; top:20px;}
        /*日常保洁*/
        .daily{ height:105px; background-color:#fff; position:relative; background-image:url(images/hot.png); background-size:40px auto; background-position:left top; background-repeat:no-repeat;}
        .daily_img{ width:72px; height:auto; float:left; margin-left:7.5%; margin-top:17px;}
        .daily h1{ line-height:30px; font-size:18px; color:#191919; margin-top:23px;}
        .daily p{ line-height:28px; font-size:16px; color:#b2bab8;}
        /*地板打蜡、窗户全面清洁、皮具打蜡*/
        .other{ height:105px; position:relative; background-color:#fff; margin-top:12px;}
        .floor,.window,.leather{ width:33.3%; float:left; position:relative;}
        .other img{ width:48px; height:auto; position:relative; left:50%; margin-left:-24px; margin-top:14px;}
        .other_title{ line-height:43px; text-align:center; color:#191919; font-size:14px;}
        .other .border_right{ height:77px; top:14px;}
        /*服务介绍、会员充值*/
        .introduce_box{ height:46px; background-color:#fff; position:relative; margin-bottom:60px;}
        .introduce_box .border_right{ height:33px; top:7px;}
        .member,.introduce{ float:left; position:relative; width:50%;}
        .introduce_box img{ width:25px; height:auto; float:left; margin-left:27%; margin-top:12px;}
        .introduce_box h1{ line-height:46px; float:left; margin-left:6%; font-size:14px; color:#65646b;}
        /*底部导航*/
        .nav_box{ width:100%; height:51px; position:fixed; left:0; bottom:0; background-color:#fff;}
        .nav_index,.nav_order,.nav_my{ width:33.3%; float:left;}
        .nav_box img{ width:18px; height:auto; position:relative; left:50%; margin-left:-9px; margin-top:8px;}
        .nav_box h1{ font-size:12px; text-align:center; color:#65646b; line-height:25px;}
        .nav_box .current_nav{ color:#01aff0;}
    </style>
    <link href="SpryAssets/SpryTabbedPanels.css" rel="stylesheet" type="text/css">
</head>

<body style="background-color: #f9f9f9;">

<!--banner广告-->
<div class="banner_box swiper-container">
    <div id="banner" class="swiper-wrapper">
        <div class="swiper-slide"><a href="https://union-click.jd.com/jdc?d=67DO1O"><img src="http://image.binghai.site/data/f_96459925.jpg"></a></div>
        <div class="swiper-slide"><a href="https://weidian.com/item.html?itemID=1824023062&wfr=qfriend&ifr=itemdetail"><img src="http://image.binghai.site/data/f_82540228.jpg"></a></div>
        <div class="swiper-slide"><a ><img src="http://image.binghai.site/data/f_39636923.jpg"></a></div>
    </div>
    <div class="swiper-pagination"></div>
</div>
<!--常用服务-->
<div class="regularly_title">常用服务
    <div class="border_top" style="left:-160%;"></div>
    <div class="border_top" style="left:inherit;right:-160%;"></div>
</div>
<!--日常保洁-->
<div class="daily" onclick="document.location='/index.jsp';">
    <div class="border_top"></div>
    <img src="../images/express.png" class="daily_img">
    <div style="float:left; margin-left:13px;">
        <h1>代取快递</h1>
        <p>全新5.0，等你体验</p>
    </div>
    <div class="border_bottom"></div>
</div>

<c:forEach items="${adGroups}" var="group">
    <div class="other">
        <div class="floor" onclick="document.location='${group.ad1.url}';">
            <div class="border_right"></div>
            <img src="${group.ad1.image_url}">
            <div class="other_title">${group.ad1.title}</div>
        </div>
        <div class="window" onclick="document.location='${group.ad2.url}';">
            <div class="border_right"></div>
            <img src="${group.ad2.image_url}">
            <div class="other_title">${group.ad2.title}</div>
        </div>
        <div class="leather" onclick="document.location='${group.ad3.url}';">
            <img src="${group.ad3.image_url}">
            <div class="other_title">${group.ad3.title}</div>
        </div>
        <div class="border_top"></div>
        <div class="border_bottom"></div>
    </div>
</c:forEach>
<div style="height: 100px;">
</div>
<!--底部导航-->
<div class="nav_box">
    <div class="border_top"></div>
    <div class="nav_index chenge" onclick="document.location='/user/index.do';">
        <img src="../images/index_blue.png">
        <h1 style=" color:#01AFF0">首页</h1>
    </div>
    <div class="nav_order chenge" onclick="document.location='/user/my_orders.do';" >
        <img src="../images/order_gray.png">
        <h1>订单</h1>
    </div>
    <div class="nav_my chenge" onclick="document.location='/user/user_center.do';" >
        <img src="../images/my_gray.png">
        <h1>我的</h1>
    </div>
</div>
<script>
    $(".statement").hide();
    $(".black").hide();
    $(".introduce").click(function(){
        $(".statement").show();
        $(".black").show();
    });
    $(".close").click(function(){
        $(".statement").hide();
        $(".black").hide();
    })


    //banner
    var swiper=new Swiper('.swiper-container',{
                pagination: '.swiper-pagination',
                paginationClickable: true,
                autoplay : 3000,
                speed:300,
                autoplayDisableOnInteraction : false,
                loop : true,
            }

    );
    //底部导航
    $(".chenge:eq(0)").click(function(){
        $(".chenge:eq(0) img").attr("src","../images/index_blue.png");
        $(".chenge:eq(0) h1").css({color:'#01AFF0'});
        $(".chenge:eq(1) img").attr("src","../images/order_gray.png");
        $(".chenge:eq(1) h1").css({color:''});
        $(".chenge:eq(2) img").attr("src","../images/my_gray.png");
        $(".chenge:eq(2) h1").css({color:''});
    })
    $(".chenge:eq(1)").click(function(){
        $(".chenge:eq(1) img").attr("src","../images/order_blue.png");
        $(".chenge:eq(1) h1").css({color:'#01AFF0'});
        $(".chenge:eq(0) img").attr("src","../images/index_gray.png");
        $(".chenge:eq(0) h1").css({color:''});
        $(".chenge:eq(2) img").attr("src","../images/my_gray.png");
        $(".chenge:eq(2) h1").css({color:''});

    })
    $(".chenge:eq(2)").click(function(){
        $(".chenge:eq(2) img").attr("src","../images/my_blue.png");
        $(".chenge:eq(2) h1").css({color:'#01AFF0'});
        $(".chenge:eq(0) img").attr("src","../images/index_gray.png");
        $(".chenge:eq(0) h1").css({color:''});
        $(".chenge:eq(1) img").attr("src","../images/order_gray.png");
        $(".chenge:eq(1) h1").css({color:''});
    })
    var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1");
</script>
</body>
</html>


