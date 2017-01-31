<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>客服工单</title>
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
            <h1 class="title">客服工单</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="buttons-tab">
                <a href="#tab1" class="tab-link active button">待接入</a>
                <a href="#tab2" class="tab-link button">服务中(${fn:length(incservice)})</a>
                <a href="#tab3" class="tab-link button">已结束</a>
            </div>
            <div class="content-block">
                <div class="tabs">
                    <div id="tab1" class="tab active">
                        <c:if test="${fn:length(waitcservice) == 0}">
                            <div class="content-block" align="center">
                                <p>暂无工单</p>
                            </div>
                        </c:if>
                        <div class="list-block">
                            <ul>
                                <c:forEach items="${waitcservice}" var="wt">
                                <li id="cswt_${wt.id}">
                                    <a href="connect_service.do?token=${Stoken}&managerId=${managerId}&csid=${wt.id}&schoolId=${schoolId}" class="item-link item-content external">
                                        <div class="item-media"><i class="icon icon-f7"></i></div>
                                        <div class="item-inner">
                                            <div class="item-title" onclick="javascript:$('#cswt_${wt.id}').hide()">工单${wt.id}号 点击接入</div>
                                        </div>
                                    </a>
                                </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div id="tab2" class="tab">
                        <c:if test="${fn:length(incservice) == 0}">
                            <div class="content-block" align="center">
                                <p>暂无工单</p>
                            </div>
                        </c:if>
                        <div class="list-block">
                            <ul>
                                <c:forEach items="${incservice}" var="csin">
                                    <li>
                                        <a href="${csin.getServerEnter()}" class="item-link item-content">
                                            <div class="item-media"><i class="icon icon-f7"></i></div>
                                            <div class="item-inner">
                                                <div class="item-title">工单${csin.id}号正在服务，点击继续</div>
                                            </div>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                    <div id="tab3" class="tab">
                        <c:if test="${fn:length(completecservice) == 0}">
                            <div class="content-block" align="center">
                                <p>暂无工单</p>
                            </div>
                        </c:if>
                        <div class="list-block  media-list">
                            <ul>
                                <c:forEach items="${completecservice}" var="csc">
                                    <li>
                                        <a href="${csc.getServerEnter()}" class="item-link item-content">
                                            <div class="item-inner">
                                                <div class="item-title-row">
                                                    <div class="item-title">工单${csc.id}号</div>
                                                    <div class="item-after">${csc.getCreateTime2String()}</div>
                                                </div>
                                                <div class="item-subtitle">客户评分:${csc.score}</div>
                                                <div class="item-text">${csc.endText}</div>
                                            </div>
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
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