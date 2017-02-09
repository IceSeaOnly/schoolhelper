<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>代寄快递&设置</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
    <script>
        function delete_es(id) {
            $.get("/ajax/deleteSendExpress.do?managerId=${managerId}&token=${token}&id="+id,function (data, status) {
                alert("data");
            })
        }
    </script>

</head>
<body>

<div class="page-group">
    <div class="page page-current">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="javascript:icesea.finish()" class="icon icon-left pull-left"></a>
            <a href="#settings" class="icon icon-settings pull-right"></a>
            <h1 class="title">代寄快递&设置</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <c:forEach items="${orders}" var="order">

                <div class="card">
                    <div class="card-header">#${order.id} ${order.express}</div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            用户姓名：${order.name}<br>
                            预留手机：${order.phone}<br>
                            下单时间：${order.orderTime}<br>
                            宿舍位置：${order.address}<br>
                            订单费用：￥<fmt:formatNumber value="${(order.shouldPay/100)}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber><br>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="javascript:diff_call('${order.phone}','${order.phone}')" class="link"><span class="icon icon-phone"></span>&nbsp致电</a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <!-- 这里是页面内容区 end-->
        <!-- 你的html代码 -->
    </div>
    <div class="page" id="settings">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="#" class="icon icon-left pull-left back"></a>
            <h1 class="title">配置快递点</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="list-block media-list">
                <ul>
                    <c:forEach items="${ess}" var="es">
                        <li id="vipls_${es.id}">
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title-row">
                                        <div class="item-title">${es.expressname} ${es.phone}</div>
                                        <div class="item-after"><a href="javascript:delete_es(${es.id})" class="button button-danger">删除</a></div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <form action="addSendExpress.do" method="post" id="form_send">
                <input name="managerId" value="${managerId}" type="hidden"/>
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="schoolId" value="${schoolId}" type="hidden"/>
                <div class="list-block">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">快递名</div>
                                    <div class="item-input">
                                        <input type="text" name="ename">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">联系手机号</div>
                                    <div class="item-input">
                                        <input type="text" name="ephone">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">费用(单位:分)</div>
                                    <div class="item-input">
                                        <input type="text" name="eprice" placeholder="大于0的整数">
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <p><a href="javascript:$('#form_send').submit()" class="button button-big">添加</a></p>
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
    $.toast("仅显示最近50条记录");
</script>
<jsp:include page="replaceToken.jsp"/>
</body>
</html>