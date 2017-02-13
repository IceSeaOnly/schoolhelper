<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>队伍管理</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
    <script>
        function delete_it(id) {
            $.confirm('删除后不可恢复，确认删除?', function () {
                $.get("/ajax/delete_manager.do?managerId=${managerId}&token=${Stoken}&mid=" + id, function (data, status) {
                    if (status == "success") {
                        if (data == "true") {
                            $("#manager_" + id).hide();
                            $.toast("已删除", 1000);
                        } else
                            $.toast("删除失败，请刷新重试", 1000);
                    } else {
                        $.toast("删除失败，请刷新重试", 1000);
                    }
                });
            });
        }

        function add_one() {
            var na = document.manager_form.name.value;
            var ph = document.manager_form.phone.value;
            var al = document.manager_form.wxpay.value;
            var wx = document.manager_form.alipay.value;
            if (na == "" || ph == "" || al == "" || wx == "") {
                $.alert("信息不能为空！");
            } else if (ph.length != 11) {
                $.alert("手机号不合法！");
            } else {
                document.getElementById("manager_form").submit();
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
            <a href="#router_addone" class="icon icon-edit pull-right"></a>
            <h1 class="title">队伍管理</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <c:forEach items="${managers}" var="mr">
                <div class="card" id="manager_${mr.id}">
                    <div class="card-header">${mr.name}</div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            手机号：${mr.phone}<br>
                            微信号：${mr.wxpay}<br>
                            支付宝：${mr.alipay}<br>
                        </div>
                    </div>
                    <div class="card-footer">
                        <a href="javascript:delete_it(${mr.id})" class="link"><span class="icon icon-check"></span>&nbsp删除</a>
                        <a href="javascript:diff_call('${mr.phone}','${mr.phone}')" class="link"><span
                                class="icon icon-phone"></span>&nbsp致电</a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <!-- 这里是页面内容区 end-->
        <!-- 你的html代码 -->
    </div>
    <div class="page" id='router_addone'>
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left back" href="#">
                <span class="icon icon-left"></span>
            </a>
            <h1 class='title'>添加管理员</h1>
        </header>
        <div class="content">
            <form action="manager_add.do" method="post" id="manager_form" name="manager_form">
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="managerId" value="${managerId}" type="hidden"/>
                <div class="list-block">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">姓名</div>
                                    <div class="item-input">
                                        <input type="text" name="name">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">手机</div>
                                    <div class="item-input">
                                        <input type="text" name="phone">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">微信号</div>
                                    <div class="item-input">
                                        <input type="text" name="wxpay">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">支付宝</div>
                                    <div class="item-input">
                                        <input type="text" name="alipay">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">OpenId</div>
                                    <div class="item-input">
                                        <input type="text" name="openid">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">描述信息</div>
                                    <div class="item-input">
                                        <input type="text" name="pdesc">
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <p><a href="javascript:add_one()" class="button button-big">提交</a></p>
                </div>
            </form>
        </div>
    </div>
    <script type='text/javascript'
            src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
    <script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js'
            charset='utf-8'></script>
    <script>
        $.init();
    </script>
    <jsp:include page="replaceToken.jsp"/>
</body>
</html>