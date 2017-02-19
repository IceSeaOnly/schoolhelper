<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${school.schoolName}楼长交接</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">

    <script>
        var orderSum = ${fn:length(orders)};
        function empty_close() {
            $.alert("空空如也");
        }
    </script>
</head>
<body>

<div class="page-group">
    <div class="page page-current">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="javascript:icesea.finish()" class="icon icon-left pull-left"></a>
            <h1 class="title">${school.schoolName}楼长交接</h1>
        </header>


        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <form action="lzjjQRmake.do" method="post" id="form_select" name="form_select">
                <input name="managerId" value="${managerId}" type="hidden"/>
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="schoolId" value="${schoolId}" type="hidden"/>
                <div class="list-block  media-list">
                    <ul>
                        <c:forEach items="${orders}" var="order">
                            <li>
                                <label class="label-checkbox item-content">
                                    <input type="checkbox" value="${order.id}" name="checked_orders">
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
                                            配&nbsp&nbsp送&nbsp&nbsp员：${order.rider_name}<br>
                                            下单时间：${order.orderTime}<br>
                                            <span style="color: red">宿舍位置：${order.sendTo}</span><br>
                                            当前状态：${order.state_toString()}<br>
                                            是否异常：${order.reason2String()}<br>
                                        </div>
                                        <div class="item-text">
                                            备注信息：${order.otherinfo }
                                        </div>
                                    </div>
                                </label>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="content-block">
                    <p><a href="javascript:$('#form_select').submit()" class="button button-big">选好了</a></p>
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

    if(orderSum == 0)
        empty_close();

</script>
<jsp:include page="replaceToken.jsp"/>
</body>
</html>