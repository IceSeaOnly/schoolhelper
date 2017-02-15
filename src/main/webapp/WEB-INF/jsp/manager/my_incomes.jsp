<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的收入</title>
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
            <jsp:include page="right_reload.jsp"/>
            <h1 class="title">我的收入</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <c:if test="${fn:length(ins) == 0}">
                <div class="content-block" align="center">
                    <p>暂无记录</p>
                </div>
            </c:if>
            <div class="list-block media-list">
                <ul>
                    <c:forEach items="${ins}" var="in">
                    <li>
                        <div class="item-content">
                            <div class="item-media">
                                <img src="${in.valid?(in.settled?"http://image.binghai.site/data/f_60674854.jpg":"http://image.binghai.site/data/f_67083154.png"):"http://image.binghai.site/data/f_61284696.jpg"}" style='width: 2.2rem;'>
                            </div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title" >${in.info} <c:if test="${!in.valid}"><span style="color:red">【无效】</span></c:if> </div>
                                    <div class="item-after" style="color:green">+${in.money/100}</div>
                                </div>
                                <div class="item-subtitle">${in.strTime} 订单号${in.oid}</div>
                            </div>
                        </div>
                    </li>
                    </c:forEach>
                </ul>
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