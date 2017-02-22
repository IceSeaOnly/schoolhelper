<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>编辑权限</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
    <script>
        function privilege_changed(pid){
            $.get("/ajax/privilege_changed.do?managerId=${managerId}&mid=${mid}&token=${Stoken}&privilegeId="+pid, function (data, status) {
                if (status == "success") {
                    if(data == "true")
                        $.toast("修改成功", 1000);
                    else
                        $.toast("修改失败，请刷新重试", 1000);
                } else {
                    $.toast("修改失败，请刷新重试", 1000);
                }
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
            <jsp:include page="right_reload.jsp"/>
            <h1 class="title">编辑权限</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">

            <div class="list-block">
                <ul>
                    <c:forEach items="${privileges}" var="privilege">
                    <li class="item-content">
                        <div class="item-media"><img src="${privilege.imageUrl}" style='width: 2rem;'></div>
                        <div class="item-inner">
                            <div class="item-title">${privilege.name}</div>
                            <div class="item-after">
                                <label class="label-switch">
                                    <input type="checkbox" <c:if test="${privilege.tmp_status}">checked="checked"</c:if> onchange="privilege_changed(${privilege.id})">
                                    <div class="checkbox"></div>
                                </label>
                            </div>
                        </div>
                    </li>
                    </c:forEach>
                </ul>
            </div>
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
</script>
<jsp:include page="replaceToken.jsp"/>
</body>
</html>