<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>回复反馈</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">


</head>
<body>

<div class="page-group">
    <div class="page page-current">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="javascript:icesea.finish()" class="icon icon-left pull-left"></a>
            <a href="" class="icon icon-menu pull-right"></a>
            <h1 class="title">回复反馈</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="content-padded">
                <p>${fb.content}</p>
            </div>
            <form action="response_feedback.do" method="post" id="fb_form">
                <input name="managerId" value="${managerId}" type="hidden"/>
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="fid" value="${fb.id}" type="hidden"/>
                <div class="list-block">
                    <ul>
                        <li class="align-top">
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-comment"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">请回复:</div>
                                    <div class="item-input">
                                        <textarea name="resp"></textarea>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <p><a href="javascript:$('#fb_form').submit()" class="button button-big">回复用户</a></p>
                </div>
            </form>
        </div>
        <!-- 这里是页面内容区 end-->
        <!-- 你的html代码 -->
    </div>
</div>

<script type='text/javascript'
        src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script>
    $.init();
</script>
<jsp:include page="replaceToken.jsp"/>
</body>
</html>