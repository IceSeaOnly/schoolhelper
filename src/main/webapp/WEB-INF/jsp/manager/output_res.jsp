<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>订单导出</title>
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
            <h1 class="title">订单导出</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="list-block  media-list">
                <ul>
                    <c:forEach items="${orders}" var="order">
                        <li>
                                <div class="item-media"><i class="icon icon-form-checkbox"></i></div>
                                <div class="item-inner">
                                    <div class="item-title-row">
                                        <div class="item-title">${order.express}
                                            【${order.express_number}】【${order.arrive}】
                                        </div>
                                    </div>
                                    <div class="item-subtitle">
                                        用户姓名：${order.express_name}<br>
                                        取件手机：${order.express_phone}<br>
                                        配送手机：${order.receive_phone}<br>
                                    </div>
                                    <div class="item-text">
                                        备注信息：${order.otherinfo }
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