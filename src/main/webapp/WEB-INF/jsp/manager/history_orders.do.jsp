<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${school.schoolName}历史订单</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">

    <script>
        var orderSum = ${fn:length(orders)};
        function empty_close() {
            $.toast("没有任何记录");
        }
        function refund(id) {
            $.confirm('你确定退款吗？', function () {
                $.showPreloader('正在退款操作...');
                $.get("/ajax/refund.do?managerId=${managerId}&schoolId=${schoolId}&token=${Stoken}&id="+id,function (data, status) {
                    $.hidePreloader();
                    $.alert(data);
                    $("#card"+id).hide();
                })
            });
        }
        function begin_search() {
            $.prompt('姓名/订单号/手机号', function (value) {
                if(value != "")
                    window.location.href="history_orders.do?managerId=${managerId}&schoolId=${schoolId}&yyyy_MM_dd=/&token=${Stoken}&search="+value;
            });
        }

        function add_additional(id) {
            $.prompt('附加信息', function (value) {
                if(value != "")
                    $.post("/ajax/add_additional.do?managerId=${managerId}&token=${Stoken}&content="+value+"&id="+id,function (data, status) {
                        $.toast(data);
                    });
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
            <a href="javascript:begin_search()" class="icon icon-search pull-right"></a>
            <h1 class="title">${school.schoolName}历史订单</h1>
        </header>


        <!-- 这里是页面内容区 begin-->
        <div class="content">

            <div align="center">
                <input type="text" id="bdate-picker" style="text-align:center;" value="${yyyy_MM_dd}"/>
            </div>

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
                            订单费用：￥<fmt:formatNumber value="${(order.shouldPay/100)}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber><br>
                            备注信息：${order.otherinfo }<br>
                            附加信息：<span style="color: red">${order.managerAdditional }</span><br>
                        </div>
                    </div>
                    <div class="card-footer">
                        <c:if test="${order.order_state == 0 || order.order_state == 1 || order.order_state == -2}">
                            <a href="javascript:refund(${order.id})" class="link"><span class="icon icon-refresh"></span>&nbsp退款</a>
                        </c:if>

                        <a href="javascript:add_additional(${order.id})" class="link"><span class="icon icon-edit"></span>&nbsp附加</a>
                        <a href="javascript:diff_call('${order.express_phone}','${order.receive_phone}')" class="link"><span class="icon icon-phone"></span>&nbsp致电</a>
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

    $("#bdate-picker").calendar({
        value: ['${yyyy_MM_dd}'],
        maxDate:['${max_date}'],
        onChange:function (p, values, displayValues) {
            if($.trim(values+"") != $.trim(displayValues))
                window.location.href="history_orders.do?managerId=${managerId}&schoolId=${schoolId}&yyyy_MM_dd="+displayValues+"&token=${Stoken}";
        }
    });
</script>
<jsp:include page="replaceToken.jsp"/>
</body>
</html>