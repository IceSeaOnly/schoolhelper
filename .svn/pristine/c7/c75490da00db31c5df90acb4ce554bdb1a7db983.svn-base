<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>原因管理</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
    <script>
        function delete_it(id) {
            $.confirm('删除后不可恢复，确认删除?', function () {
                $.get("/ajax/delete_reason.do?managerId=${managerId}&token=${Stoken}&rid=" + id, function (data, status) {
                    if (status == "success") {
                        if (data == "true") {
                            $("#reason_" + id).hide();
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
            var rtext = document.reason_form.reason.value;
            if(rtext == ""){
                $.alert("描述不能为空！");
            }else{
                document.getElementById("reason_form").submit();
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
            <h1 class="title">原因管理</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="list-block  media-list">
                <ul>
                    <c:forEach items="${reasons_s}" var="rs">
                        <li class="item-content" id="reason_${rs.id}">
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">配送异常</div>
                                    <div class="item-after"><a href="javascript:delete_it(${rs.id})"
                                                               class="button button-danger">x</a></div>
                                </div>
                                <div class="item-text">${rs.why}</div>
                            </div>
                        </li>
                    </c:forEach>
                    <c:forEach items="${reasons_f}" var="rf">
                        <li class="item-content" id="reason_${rf.id}">
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">取件异常</div>
                                    <div class="item-after"><a href="javascript:delete_it(${rf.id})"
                                                               class="button button-danger">x</a></div>
                                </div>
                                <div class="item-text">${rf.why}</div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <!-- 这里是页面内容区 end-->
        <!-- 你的html代码 -->
    </div>
    <div class="page" id='router_addone'>
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left back" href="#">
                <span class="icon icon-left"></span>
            </a>
            <h1 class='title'>添加原因</h1>
        </header>
        <div class="content">
            <form action="reason_manage_add.do" method="post" id="reason_form" name="reason_form">
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="managerId" value="${managerId}" type="hidden"/>
                <div class="list-block">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-gender"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">类别</div>
                                    <div class="item-input">
                                        <select name="type">
                                            <option value="send">配送异常</option>
                                            <option value="fetch">取件异常</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">描述</div>
                                    <div class="item-input">
                                        <input type="text" name="reason">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                    </ul>
                </div>
                <div class="content-block">
                    <p><a href="javascript:add_one()" class="button">提交</a></p>
                </div>
            </form>
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