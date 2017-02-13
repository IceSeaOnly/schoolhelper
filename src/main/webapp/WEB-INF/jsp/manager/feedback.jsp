<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>处理用户反馈</title>
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
            <h1 class="title">处理用户反馈</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="buttons-tab">
                <a href="#tab1" class="tab-link active button">未处理的</a>
                <a href="#tab2" class="tab-link button">我处理的</a>
            </div>

            <div class="tabs">
                <div id="tab1" class="tab active">
                    <div class="list-block media-list">
                        <ul>
                            <c:forEach items="${feedbacks}" var="fb">
                            <li>
                                <a href="resp_feedback.do?fid=${fb.id}&managerId=${managerId}&token=${Stoken}" class="item-link item-content external">
                                    <div class="item-inner">
                                        <div class="item-title-row">
                                            <div class="item-title">【${fb.id}】${fb.strTime}</div>
                                        </div>
                                        <div class="item-text">${fb.content}</div>
                                    </div>
                                </a>
                            </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div id="tab2" class="tab">
                    <c:forEach items="${my_fbs}" var="mfb">
                    <div class="card">
                        <div class="card-header">【${mfb.id}】 ${mfb.strTime}</div>
                        <div class="card-content">
                            <div class="card-content-inner">【客户】${mfb.content}</div>
                        </div>
                        <div class="card-footer">【回复】${mfb.resp} [${mfb.strRespTime}]</div>
                    </div>
                    </c:forEach>
                </div>
            </div>
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