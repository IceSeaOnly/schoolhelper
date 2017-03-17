<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${school.schoolName}待接订单</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
    <script type='text/javascript' src='localhost/jquery-3.1.1.min' charset='utf-8'></script>
    <script type='text/javascript'>
        var orderSum = ${fn:length(orders)};
        function empty_close() {
            $.alert("空空如也",":)",function () {
                icesea.finish();
            });
        }

        function takeorder(id) {
            $("#card" + id).hide();
            orderSum--;
            $.get("/ajax/take_order.do?token=${Stoken}&managerId=${managerId}&schoolId=${schoolId}&orderId="+id, function (data, status) {
                if (status == "success") {
                    if(data == "true")
                        $.toast("抢单成功", 1000);
                    else
                        $.toast("没抢到！", 1000);
                } else {
                    $.toast("操作失败", 1000);
                }
            });

            if(orderSum == 0)
                empty_close();
        }
    </script>
</head>
<body>

<div class="page-group">
    <div class="page page-current">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="javascript:icesea.finish()" class="icon icon-left pull-left"></a>
            <jsp:include page="right_reload.jsp"/>
            <h1 class="title">${school.schoolName}待接订单</h1>
        </header>

        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <c:forEach items="${orders}" var="order">

                <div class="card" id="card${order.id}">
                    <div class="card-header">${order.express}</div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            下单时间：${order.orderTime}<br>
                            <span style="color:red"><b>配送时间：${order.sendTime2String()}</b></span><br>
                            宿舍位置：${order.sendTo}<br>
                            备注信息：${order.otherinfo }<br>
                            附加信息：<span style="color: red">${order.managerAdditional }</span><br>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="javascript:diff_call('${order.express_phone}','${order.receive_phone}')" class="link"><span class="icon icon-phone"></span>&nbsp致电</a>
                        <a href="javascript:takeorder(${order.id});" class="link"><span class="icon icon-check"></span>&nbsp接单</a>
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
