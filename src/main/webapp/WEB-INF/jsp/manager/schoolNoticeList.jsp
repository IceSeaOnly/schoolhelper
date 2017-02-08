<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>本校消息列表</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
</head>
<body>

<div class="page-group">
    <div class="page page-current">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="javascript:icesea.finish()" class="icon icon-left pull-left"></a>
            <a href="#router_addone" class="icon icon-edit pull-right"></a>
            <h1 class="title">本校消息列表</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <c:forEach items="${msgs}" var="ls">
                <div class="card">
                    <div class="card-header">#${ls.id} ${ls.title}</div>
                    <div class="card-content">
                        <div class="card-content-inner">
                            ${ls.content}<br>
                            ${ls.managerName} ${ls.timeStr}
                        </div>
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
            <h1 class='title'>发布新通知</h1>
        </header>
        <div class="content">
            <div class="content-padded">
                <p>· 发布后<span style="color:red">所有用户</span>都能收到此通知</p>
                <p>· 发布后<span style="color:red">暂不可撤销</span></p>
            </div>
            <form action="publish_notice.do" method="post">
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="managerId" value="${managerId}" type="hidden"/>
                <input name="schoolId" value="${schoolId}" type="hidden"/>
                <div class="list-block">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">标题</div>
                                    <div class="item-input">
                                        <input type="text" name="title">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">内容</div>
                                    <div class="item-input">
                                        <textarea name="content"></textarea>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <p><a href="javascript:this.form.submit()" class="button button-big">发布</a></p>
                </div>
            </form>
        </div>
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