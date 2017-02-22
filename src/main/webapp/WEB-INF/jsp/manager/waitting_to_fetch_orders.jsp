<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${school.schoolName}待取订单</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
    <script type='text/javascript'>
        var orderSum = ${fn:length(orders)};
        function empty_close() {
            $.alert("空空如也",":)",function () {
                icesea.finish();
            });
        }

        var tmp_orderid = -1;

        function takeorder(orderId,reasonid,res){
            if(tmp_orderid == orderId){
                $("#card" + orderId).hide();
                $.showPreloader("正在通讯，请稍后...");
                $.get("/ajax/fetch_order.do?token=${Stoken}&managerId=${managerId}&schoolId=${schoolId}&orderId="+orderId+"&reasonId="+reasonid+"&res="+res, function (data, status) {
                    if (status == "success") {
                        if(data == "true" && res == true){
                            $.hidePreloader();
                            $.toast("请上传快递条码", 1000);
                            icesea.uploadCourierNumber(orderId);
                        }else if(data == "true" && res == false){
                            $.hidePreloader();
                            $.toast("已记录", 1000);
                        }

                    } else {
                        $.hidePreloader();
                        $.toast("操作失败", 1000);
                    }
                });

            }else{
                $.toast("防止误操作，请再点一次", 1000);
                tmp_orderid = orderId;
            }
        }

        function can_not_fetch_success(id) {
            var buttons1 = [
                {
                    text: '请选择失败原因',
                    label: true
                },
                <c:forEach items="${reasons}" var="reason" varStatus="stat">
                {
                    text: '${reason.why}',
                    onClick: function () {
                        takeorder(id, ${reason.id},false);
                    }
                }
                <c:if test="${!stat.last}">, </c:if>
                </c:forEach>
            ];
            var buttons2 = [
                {
                    text: '取消',
                    bg: 'danger'
                }
            ];
            var groups = [buttons1, buttons2];
            $.actions(groups);
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
            <h1 class="title">${school.schoolName}待取订单</h1>
        </header>

        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <c:forEach items="${orders}" var="order">

                <div class="card" id="card${order.id}">
                    <div class="card-header">${order.express} 【${order.express_number}】【${order.arrive}】</div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            下单时间：${order.orderTime}<br>
                            用户姓名：${order.express_name}<br>
                            宿舍位置：${order.sendTo}<br>
                            取件手机：${order.express_phone}<br>
                            配送手机：${order.receive_phone}<br>
                            备注信息：${order.otherinfo }<br>
                            附加信息：<span style="color: red">${order.managerAdditional }</span><br>
                            <c:if test="${order.order_state == -2}"><span style="color: red">${order.reason2String()}</span></c:if>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="javascript:can_not_fetch_success(${order.id});" class="link"><span
                                class="icon icon-caret"></span>&nbsp取件失败</a>
                        <a href="javascript:diff_call('${order.express_phone}','${order.receive_phone}')" class="link"><span class="icon icon-phone"></span>&nbsp致电</a>
                        <a href="javascript:takeorder(${order.id},0,true)" class="link"><span class="icon icon-check"></span>&nbsp已取件</a>
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
