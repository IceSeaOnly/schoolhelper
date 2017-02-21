<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>工资清算</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">

    <script>
        function start2pay(orderid) {
            $("#pay_card"+orderid).hide();
            icesea.start2pay(orderid);
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
            <h1 class="title">工资清算</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <c:forEach items="${logs}" var="log">

                <div class="card" id="pay_card${log.id}">
                    <div class="card-header">${log.mName} ${log.amount/100}</div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            ${log.pdesc}
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="javascript:start2pay(${log.id});" class="link"><span class="icon icon icon-share"></span>&nbsp支付</a>
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
    <c:if test="${fn:length(logs) == 0}">
    $.toast('每周日为结算期');
    </c:if>
</script>
<jsp:include page="replaceToken.jsp"/>
</body>
</html>