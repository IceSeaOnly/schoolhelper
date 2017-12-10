<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>

    <link rel="stylesheet" href="http://image.binghai.site/data/f_47664789.css">
    <link rel="stylesheet" href="http://image.binghai.site/data/f_52678278.css">
    <link rel="stylesheet" href="http://image.binghai.site/data/f_71304471.css">
    <style>
        .show_msg {
            width: 100%;
            height: 35px;
            text-align: center;
            position: fixed;
            left: 0;
            z-index: 999;
        }

        .show_span {
            display: inline-block;
            height: 35px;
            padding: 0 15px;
            line-height: 35px;
            background: rgba(0, 0, 0, 0.8);
            border-radius: 50px;
            color: #fff;
            font-size: 1em;
        }
    </style>
    <title id="id_title"></title>
</head>
<body ontouchstart="" class="open-body">

<div class="wrapper">

    <div class="bg rotate"></div>

    <div class="open-has " id="id_open-has">
        <h3 class="title-close"><span class="user" id="id_time_has"></span></h3>
        <div class="mod-chest">
            <div class="chest-close show">
                <div class="gift"></div>
                <div class="tips">
                    <i class="arrow"></i>
                </div>
            </div>
            <div class="chest-open ">
                <div class="mod-chest-cont open-cont">
                    <div class="content">
                        <div class="gift">
                            <span id="id_position">${notice}</span></div>
                        <c:if test="${result}">
                            <div class="func" id="id_myBtn">
                                <a href="/user/my_coupons.do">
                                    <button class="chest-btn">我的优惠券</button>
                                </a>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <div class="open-none" style="display:none">
        <h3>你来晚啦，下次早点吧！</h3>
        <div class="mod-chest">
            <div class="chest-open show"></div>
        </div>
        <div class="func">
            <button class="chest-btn">查看领取详情</button>
        </div>
    </div>
</div>

<script type="text/javascript"
        src="http://s3.mogucdn.com/mlcdn/c45406/171130_0h40a8dh6ei2269b0ga86f2dalj96.js"></script><!--js/zepto.min.js-->
<script src="http://s3.mogucdn.com/mlcdn/c45406/171127_2fddc534a9ef4k1f842i08l3kljff.js"></script><!--jquery-->

<script>
    $(".chest-close").click(function () {

        $(this).addClass("shake");
        var that = this;

        this.addEventListener("webkitAnimationEnd", function () {

            $(that).closest(".open-has").addClass("opened");
            setTimeout(function () {
                $(that).removeClass("show");
                $(that).closest(".mod-chest").find(".chest-open").addClass("show");
                setTimeout(function () {
                    $(".chest-open").addClass("blur");
                }, 500)
            }, 200)
        }, false);

    });
</script>

</body>
</html>