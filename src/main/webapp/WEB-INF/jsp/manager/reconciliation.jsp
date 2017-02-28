<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>电子对账</title>
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
            <a href="Reconciliation.do?managerId=${managerId}&token=${Stoken}&date=${perDate}" class="icon icon-clock pull-right external"></a>
            <h1 class="title">${date}电子对账</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="content-padded">
                <p>总支出:￥${outSum/100}</p>
                <p>今日支出:￥${todayOutSum/100}</p>
                <p><span style="color: green">■ 取件数</span></p>
                <p><span style="color: red">■ 配送数</span></p>
            </div>
            <div class="list-block">
                <ul>
                    <c:forEach items="${payLogs}" var="log">
                    <li class="item-content">
                        <div class="item-inner">
                            <div class="item-title">${log.mName} <span style="color: green">${log.pdesc}</span> <span style="color: red">${log.passwd}</span></div>
                            <div class="item-after">￥${log.amount/100}</div>
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