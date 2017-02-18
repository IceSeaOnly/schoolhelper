<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${school.schoolName}配送订单</title>
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
            $.toast('没有订单，换一下过滤条件试试');
        }

        var tmp_orderid = -1;
        function can_not_send_success(orderId) {
            var buttons1 = [
                {
                    text: '请选择失败原因',
                    label: true
                },
                <c:forEach items="${reasons}" var="reason" varStatus="stat">
                {
                    text: '${reason.why}',
                    onClick: function () {
                        can_not_send_success_ajax(orderId, ${reason.id});
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
        function can_not_send_success_ajax(orderId, reasonId) {
            $.get("/ajax/express_order_send_result.do?token=${Stoken}&managerId=${managerId}&schoolId=${schoolId}&orderId=" + orderId + "&result=false&reasonId=" + reasonId, function (data, status) {
                if (status == "success") {
                    if (data != "true")
                        $.toast("操作失败", 1000);
                    else
                        $("#card" + orderId).hide();
                } else {
                    $.toast("操作失败", 1000);
                }
            });
        }

        function send_success(orderId) {
            if (tmp_orderid == orderId) {
                $.get("/ajax/express_order_send_result.do?token=${Stoken}&managerId=${managerId}&schoolId=${schoolId}&orderId=" + orderId + "&result=true&reasonId=0", function (data, status) {
                    if (status == "success") {
                        if (data != "true")
                            $.toast("操作失败", 1000);
                        else{
                            $.toast("配送成功,辛苦了");
                            $("#card" + orderId).hide();
                        }
                    } else {
                        $.toast("操作失败", 1000);
                    }
                });

            } else{
                $.toast("防止误操作，请再点一次", 1000);
                tmp_orderid = orderId;
            }
        }
    </script>
</head>
<body>

<div class="page-group">
    <div class="page page-current">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="javascript:icesea.finish()" class="icon icon-left pull-left"></a>
            <a href="javascript:$('#set_picker').trigger('click');" class="icon icon-settings pull-right"></a>
            <h1 class="title">${school.schoolName}配送订单</h1>
        </header>

        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <input name="set_picker" id="set_picker" type="hidden"/>
            <c:forEach items="${orders}" var="order">
                <div class="card" id="card${order.id}">
                    <div class="card-header">${order.express} 【${order.express_number}】【${order.arrive}】</div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            下单时间：${order.orderTime}<br>
                            用户姓名：${order.express_name}<br>
                            取件手机：${order.express_phone}<br>
                            配送手机：${order.receive_phone}<br>
                            宿舍位置：${order.sendTo}<br>
                            备注信息：${order.otherinfo }<br>
                            <c:if test="${order.order_state != 2}"><span style="color: red">${order.reason2String()}</span></c:if>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="javascript:can_not_send_success(${order.id});" class="link"><span
                                class="icon icon-caret"></span>&nbsp无法派送</a>
                        <a href="javascript:send_success(${order.id});" class="link"><span
                                class="icon icon-check"></span>&nbsp已送达</a>
                        <a href="javascript:diff_call('${order.express_phone}','${order.receive_phone}')"
                           class="link"><span class="icon icon-phone"></span>&nbsp致电</a>
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
    if (orderSum == 0)
        empty_close();

    var cur_config = '${cur_config}';
    $("#set_picker").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
  <button class="button button-link pull-right close-picker">确定</button>\
  <h1 class="title">【过滤条件】</h1>\
  </header>',
        onClose:function(){
            if($("#set_picker").val() != cur_config)
                icesea.loadUrl('send_express_order.do?managerId=MANAGERID&token=TOKEN&schoolId=SCHOOLID&config='+$("#set_picker").val());
        },
        cols: [
            {
                textAlign: 'center',
                values: ['everyone', 'mine'],
                displayValues: ['不限', '我的']
            },
            {
                textAlign: 'center',
                values: ['new', 'all'],
                displayValues: ['不限', '新件']
            },
            {
                textAlign: 'center',
                values: ['-1'<c:forEach items="${parts}" var="part">,'${part.id}'</c:forEach>],
                displayValues: ['不限'<c:forEach items="${parts}" var="part">,'${part.partName}'</c:forEach>]
            },
            {
                textAlign: 'center',
                values: ['-1'<c:forEach items="${stimes}" var="stm">,'${stm.id}'</c:forEach>],
                displayValues: ['不限'<c:forEach items="${stimes}" var="stm">,'${stm.shortName()}'</c:forEach>]
            }
        ]
    });
</script>
<jsp:include page="replaceToken.jsp"/>
</body>
</html>
