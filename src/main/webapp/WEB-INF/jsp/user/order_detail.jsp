<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="../weixin_style/weui.css"/>
    <link rel="stylesheet" href="../weixin_style/example.css"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <meta charset="utf-8">
    <title>订单详情</title>
    <style>
        .move_box{ width:100%; clear:both; position:relative; left:0; overflow:hidden}
        .move{ width:200%; clear:both; position:relative; left:0; }
        /*服务流程or订单详情*/
        .information,.evaluate{ height:41px; width:50%; line-height:41px; color:#65646b; text-align:center; float:left; font-size:14px;}
        .blue_block{ width:50%; height:2px; background-color:#01aff0; position:absolute; left:0; bottom:0;}
        /*服务流程*/
        .service_box{  background-color:#fff; position:relative; width:50%; float:left}
        .blue{ width:14px; height:14px; position:absolute; z-index:2; left:4%; top:20px; background-color:#a5dcfa; border-radius:50%; padding-top:2px; padding-left:2px; box-sizing:border-box}
        .blue div{ height:10px; width:10px; background-color:#01aff0; border-radius:50%;}
        .gray{ width:14px; height:14px; position:absolute; z-index:0; left:4%; top:20px; padding-top:1px; padding-left:1px; box-sizing:border-box}
        .gray div{ height:12px; width:12px; background-color:#ccc; border-radius:50%;}
        .gray p{ position:absolute; width:2px; height:59px; background-color:#ccc; left:50%; margin-left:-1px; top:-50px;}
        .service{ height:60px; padding-top:11px; box-sizing:border-box; position:relative; box-sizing:border-box; background:#fff;}
        .service h1{ line-height:18px; color:#191919; font-size:14px; margin-left:11%;}
        .service h2{ line-height:16px; color:#65646b; font-size:12px; margin-left:11%}
        .service > p{ line-height:42px; color:#65646b; font-size:12px; position:absolute; top:0; right:4%;}
        /*订单详情*/
        .order_box{ width:50%; float:left}
        .order_title{ height:39px; line-height:39px; font-size:14px; color:#65646b; padding-left:4%; box-sizing:border-box}
        .order{ height:42px; background-color:#fff; position:relative; padding-left:4%; padding-right:4%; padding-top:9px; box-sizing:border-box; margin-bottom:100px;}
        .order_line{ height:26px;}
        .order_line h1{ line-height:26px; font-size:14px; color:#191919; float:left}
        .order_line p{ line-height:26px; font-size:14px; color:#EA5858; float:right}
        /*订单信息*/
        .order_msg{ background-color:#fff; position:relative; padding-left:4%; padding-right:4%; padding-top:9px; padding-bottom:9px; box-sizing:border-box}
        .msg_line h1{ line-height:26px; font-size:14px; color:#65646b; float:left; width:20%;}
        .msg_line p{ line-height:26px; font-size:14px; color:#191919; float:left; width:78%;}
        /*底部按钮*/
        .confirm_box{ font-size:18px; height:40px; line-height:40px;}
        /*分享框*/
        .black{ z-index:3}
        .share_box{ width:100%; height:126px; background-color:#fff; position:fixed; left:0; bottom:0; z-index:4}
        .share_title{ position:relative; height:41px; line-height:41px; color:#65646b; text-align:center; font-size:14px;}
        .share_title img{ width:9px; height:9px; position:absolute; left:4%; top:14px;}
        .share,.share1{ width:50%; float:left}
        .share img,.share1 img{margin-top:15px; width:33px; height:auto}
        .share img{float:right; margin-right:22%;}
        .share1 img{float:left; margin-left:22%;}
        .share p,.share1 p{ position:relative; bottom:0; font-size:12px; color:#65646b; line-height:36px;}
        .share p{ clear:right; text-align:right; right:22%; margin-right:-5px;}
        .share1 p{ clear:left; text-align:left; left:22%; margin-left:-2px;}
        /*取消规则*/
        .cancel_box{ z-index:4}
        .cancel_title{ line-height:33px; height:33px; color:#01aff0; border-bottom:1px solid #01aff0; padding-left:14px;}
        .cancel_text{ padding-left:14px; padding-right:14px; margin-top:4px; height:150px;}
        .cancel_text h1{ font-size:14px; line-height:18px; color:#01aff0;}
        .cancel_text p{ font-size:12px; line-height:15px; color:#65646b;}
        .cancel{ height:24px;}
        .cancel p{ float:left; line-height:24px; color:#191919; font-size:12px; margin-left:14px;}
        .can{ padding-left:14px; padding-right:14px;}
        .can textarea{ border:1px #cccccc solid; width:99%; padding-left:1%; color:#B1BCB8;}
        .cancel input{ float:right; margin-right:14px; margin-top:5px;}
        .confirm{ height:26px; line-height:26px; font-size:12px; margin-top:10px; background:#FF7F00;}
        /*取消提示*/
        .hint{ width:90%; height:112px; background:#fff; position:fixed; top:40%; left:5%; z-index:1000;}
        .hint h3{ margin:16px; color:#191919; font-size:18px;}
        .hint p{ color:#191919; font-size:14px; margin-left:16px;}
        .hint span{ color:#58C0F3; margin:10px;}
    </style>
</head>

<body style="background-color: #f9f9f9;">
<!--header顶部标题-->
<div class="header">订单详情
</div>

<!--服务流程or订单详情-->
<div style="position:relative; height:42px;">
    <div class="information" style="color:#01aff0">订单状态</div>
    <div class="evaluate">订单详情</div>
    <div class="blue_block"></div>
    <div class="border_bottom"></div>
</div>
<!--服务流程-->
<div class="move_box">
    <div class="move">
        <div class="service_box">
            <c:if test="${order.order_state == 3||order.order_state == -3}">
                <!--服务流程（上）-->
                <div class="service">
                    <div class="blue">
                        <div></div>
                        <p></p>
                    </div>
                    <h1>${order.order_state == 3?"配送成功":"配送失败"}</h1>
                </div>
            </c:if>
            <c:if test="${order.order_state == 2 || order.order_state <= -2 || order.order_state > 2}">
                <!--服务流程（中）-->
                <div class="service">
                    <div class="${(order.order_state == 2||order.order_state == -2)?"blue":"gray"}">
                        <div></div>
                        <p></p>
                    </div>
                    <h1>${order.order_state == -2?"快件不存在":"已取件，正在配送"}</h1>
                </div>
            </c:if>
            <c:if test="${order.order_state != 0 || order.order_state <=-1 || order.order_state > 1}">
                <!--服务流程（中）-->
                <div class="service">
                    <div class="${(order.order_state == 1||order.order_state == -1)?"blue":"gray"}">
                        <div></div>
                        <p></p>
                    </div>
                    <h1>${order.order_state == -1?"订单已取消":"正在安排取件"}</h1>
                </div>
            </c:if>

                <!--服务流程（下）-->
                <div class="service">
                    <div class="${order.order_state == 0?"blue":"gray"}">
                        <div></div>
                            ${order.order_state == 0?"":"<p></p>"}
                    </div>
                    <h1>已支付</h1>
                </div>

            <div class="border_bottom"></div>
            <div class="bd spacing">
                <span style="color:gray;font-size: small">该订单需要帮助？</span>
            <a href="order_need_help.do?oid=${order.id}"><button class="weui_btn weui_btn_primary">戳我召唤客服</button></a>
            </div>
        </div>
        <!--订单详情-->
        <div class="order_box">
            <!--订单信息-->
            <div class="order_title">订单信息</div>
            <div class="order_msg">
                <div class="border_top"></div>
                <div class="msg_line">
                    <h1>订单号</h1>
                    <p>${order.orderKey}</p>
                    <div class="clearfix"></div>
                </div>
                <div class="msg_line">
                    <h1>下单时间</h1>
                    <p>${order.orderTime}</p>
                    <div class="clearfix"></div>
                </div>
                <div class="msg_line">
                    <h1>订单费用</h1>
                    <p>${order.shouldPay/100.0}</p>
                    <div class="clearfix"></div>
                </div>
                <div class="msg_line">
                    <h1>配送员</h1>
                    <p>${order.rider_name}</p>
                    <div class="clearfix"></div>
                </div>
                <div class="clearfix"></div>
                <div class="border_bottom"></div>
            </div>

        </div>
    </div>
</div>
<script type='text/javascript'
        src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>

<script>
    $.init();
</script>
<script>
    //点击服务流程，蓝色滑块向左移动，切换到服务流程页
    $(".information").click(
            function () {
                $(".blue_block").animate({left: '0%'});
                $(".move").animate({left: '0%'});
            }
    )
    //点击订单详情，蓝色滑块向右移动，切换到订单详情页
    $(".evaluate").click(
            function () {
                $(".blue_block").animate({left: '50%'});
                $(".move").animate({left: '-100%'});
            }
    )
    //隐藏黑色半透明背景和分享页
    $(".black").hide();
    $(".share_box").hide();
    //点击分享按钮，弹出分享页
    $(".share_btn").click(
            function () {
                $(".black").show();
                $(".share_box").show();
            }
    )
    //点击分享页左上角的关闭按钮
    $(".close_share").click(
            function () {
                $(".black").hide();
                $(".share_box").hide();
            }
    )
    //隐藏取消规则
    $(".cancel_box").hide();
    $(".confirm_box").click(function () {
        $(".cancel_box").show();
        $(".black").show();
    })
    $(".close").click(function () {
        $(".cancel_box").hide();
        $(".black").hide();
    })
    $(".confirm").click(function () {
        $(".cancel_box").hide();
        $(".black").hide();
    })
    //隐藏取消提示
    $(".hint").hide();
    $(".confirm").click(function () {
        $(".hint").show();
        $(".black").show();
    })
</script>
</body>
</html>


