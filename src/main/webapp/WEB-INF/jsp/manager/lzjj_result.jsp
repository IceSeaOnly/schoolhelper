<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${school.schoolName}楼长交接结果</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">

    <script>
        var orderSum = ${fn:length(orders)};
        function empty_close() {
            $.alert("二维码失效 或 无权接收该订单",":(",function () {
                icesea.finish();
            });
        }
    </script>
</head>
<body>

<div class="page-group">
    <div class="page page-current">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="javascript:icesea.finish()" class="icon icon-left pull-left"></a>
            <h1 class="title">${school.schoolName}楼长交接结果</h1>
        </header>


        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <c:forEach items="${orders}" var="order">

                <div class="card" id="card${order.id}">
                    <div class="card-header">${order.express} 【${order.express_number}】【${order.arrive}】</div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            用户姓名：${order.express_name}<br>
                            用户标示：${order.user_id}<br>
                            取件手机：${order.express_phone}<br>
                            配送手机：${order.receive_phone}<br>
                            配&nbsp&nbsp送&nbsp&nbsp员：${order.rider_name}<br>
                            下单时间：${order.orderTime}<br>
                            宿舍位置：${order.sendTo}<br>
                            当前状态：${order.state_toString()}<br>
                            是否异常：${order.reason2String()}<br>
                            备注信息：${order.otherinfo }<br>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="javascript:diff_call('${order.express_phone}','${order.receive_phone}')" class="link"><span class="icon icon-message"></span>&nbsp短信通知ta</a>
                    </div>

                </div>
            </c:forEach>
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

    if(orderSum == 0)
        empty_close();
</script>
<jsp:include page="replaceToken.jsp"/>
</body>
</html>