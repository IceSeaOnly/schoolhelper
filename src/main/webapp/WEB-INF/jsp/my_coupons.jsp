<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no" />
    <meta charset="utf-8">
    <title>我的优惠券</title>
    <style>
        /*是否过期*/
        .term_box{ height:46px; position:relative; background-color:#fff;}
        .term{ height:46px; line-height:46px; text-align:center; width:50%; float:left; font-size:14px; color:#65646b;}
        .blue{ width:50%; height:2px; position:absolute; bottom:0; left:0; background-color:#01aff0;}
        /*coupon优惠券*/
        .move_box{ width:100%; overflow:hidden}
        .move{ width:200%; position:relative;}
        .coupon_box{ width:50%; float:left}
        .coupon{ width:92%; margin-left:4%; margin-top:14px; height:74px;}
        .coupon_num{ width:33%; height:74px; line-height:74px; text-align:center; background-color:#ff7f00; float:left; color:white; font-size:33px;}
        .coupon_text{ float:left; position:relative; width:67%; height:74px; background-color:#fff; padding-left:5%; padding-top:14px; box-sizing:border-box}
        .coupon_text h1{ line-height:26px; font-size:18px; color:#65646b}
        .coupon_text span{ line-height:26px; font-size:18px; color:#c40000}
        .coupon_text h2{ line-height:21px; font-size:12px; color:#65646b}
        .coupon_text p{ line-height:24px; font-size:12px; color:#ff7f00; position:absolute;top:0;right:10px;}
        .overdue .coupon_num{background-color:#b2bab8}
        .overdue .coupon_text span,.overdue .coupon_text h1,.overdue .coupon_text h2,.overdue .coupon_text p{color:#b2bab8}
    </style>
</head>

<body style="background-color: #f9f9f9;">
<!--header顶部标题-->
<div class="header">优惠券
    <div class="header_left" onClick="window.history.go(-1)"><img src="../images/return.png"></div>
    <div class="header_right" id="open_sta" onClick="statement()">使用细则</div>
</div>
<!--term是否过期-->
<div class="term_box">
    <div class="term" style="color:#01aff0;">${fn:length(valid) == 0?"失效券":"未使用"}</div>
    <div class="term">${fn:length(valid) != 0?"失效券":"未使用"}</div>
    <div class="border_bottom"></div>
    <div class="blue"></div>
</div>
<!--coupon优惠券-->
<div class="move_box">
    <div class="move">
        <!--未使用的优惠券-->
        <div class="coupon_box">
            <c:forEach items="${valid}" var="va">
            <div class="coupon">
                <div class="coupon_num">${va.ctype == 0?'免单':'立减'}</div>
                <div class="coupon_text">
                    <div class="border_top"></div>
                    <div class="border_right"></div>
                    <div class="border_bottom"></div>
                    <h1><span  style="color: red">${va.ctype == 0 ? '限代取快递使用':'代取快递立减'+va.lijian/100.0}</span></h1>
                    <c:if test="${schoolConfig.enableCoupon}">
                        <h2>使用期限：${va.outOfDateStr}</h2>
                    </c:if>
                    <c:if test="${!schoolConfig.enableCoupon}">
                        <h2>暂不可用</h2>
                    </c:if>
                </div>
            </div>
            </c:forEach>
        </div>
        <!--已过期的优惠券-->
        <div class="coupon_box  overdue">
        <c:forEach items="${invalid}" var="iva">
            <div class="coupon">
                <div class="coupon_num">${iva.ctype == 0?'免单':'立减'}</div>
                <div class="coupon_text">
                    <div class="border_top"></div>
                    <div class="border_right"></div>
                    <div class="border_bottom"></div>
                    <h1><span>${iva.used?"已使用":"已过期"}</span></h1>
                    <h2>${iva.used?"使用时间":"使用期限"}：${iva.used?iva.usedTimeStr:iva.outOfDateStr}</h2>
                </div>
            </div>
        </c:forEach>

        </div>
    </div>
</div>
<!--使用细则-->
<div class="black" id="black"></div>
<div class="statement" id="statement">
    <div class="statement_header">使用细则
        <div class="border_bottom"></div>
    </div>
    <img class="close" onClick="close_sta()" src="../images/close.png">
    <div class="statement_text">
        <h1>1.什么是免单券？</h1>
        <p>免单券是在小骨头中下单抵扣下单费用的一种优惠券，目前免单券仅支持在代取快递中使用。</p>
    </div>
    <div class="statement_text">
        <h1>2.如何获得免单券？</h1>
        <p>小骨头不定期推出活动，可用参加活动获得免单券。</p>
    </div>
    <div class="statement_text">
        <h1>3.免单券会过期吗？</h1>
        <p>会过期，请在过期前使用。</p>
    </div>
    <div class="statement_text">
        <h1>4.免单券有使用限制吗？</h1>
        <p>在部分试营业校区，可能会有每天可用的免单券限制。</p>
    </div>
    <div class="statement_text">
        <h1>5.免单券失效的原因有哪些？</h1>
        <p>过期或使用都会使免单券失效。</p>
    </div>
    <div class="statement_text">
        <h1>6.免单券怎么使用？</h1>
        <p>如果有可用的免单券，下单时会自动使用。</p>
    </div>
</div>
<script>
    //隐藏使用细则
    document.getElementById('black').style.display="none";
    document.getElementById('statement').style.display="none";
    //点击使用细则按钮，显示使用细则
    function statement(){
        document.getElementById('black').style.display="block";
        document.getElementById('statement').style.display="block";
    }
    //点击关闭按钮，关闭使用细则
    function close_sta(){
        document.getElementById('black').style.display="none";
        document.getElementById('statement').style.display="none";
    }
    $(".term:eq(0)").click(
        function(){
            $(".blue").animate({left:'0%'});
            $(".move").animate({left:'0%'});
        }
    )
    $(".term:eq(1)").click(
        function(){
            $(".blue").animate({left:'50%'});
            $(".move").animate({left:'-100%'});
        }
    )
</script>
</body>
</html>

