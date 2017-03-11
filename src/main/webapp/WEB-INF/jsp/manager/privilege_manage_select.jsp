<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>选择管理员</title>
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
            <a href="glxxgly.do?managerId=${managerId}&token=${Stoken}&sid=${schoolId}" class="icon icon-share pull-right external"></a>
            <h1 class="title">选择管理员</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="list-block media-list inset">
                <ul>
                    <c:forEach items="${managers}" var="manager">
                        <li>
                            <c:choose>
                                <c:when test="${manager.could_delete}">
                                <a href="privilege_manage.do?managerId=${managerId}&token=${Stoken}&mid=${manager.id}" class="item-link item-content external">
                                </c:when>
                                <c:otherwise>
                                    <a href="javascript:$.alert('超管不可操作')" class="item-link item-content">
                                </c:otherwise>
                            </c:choose>
                                <div class="item-inner">
                                    <div class="item-title-row">
                                        <div class="item-title">${manager.name}</div>
                                    </div>
                                    <div class="item-subtitle">${manager.phone}</div>
                                </div>
                            </a>
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