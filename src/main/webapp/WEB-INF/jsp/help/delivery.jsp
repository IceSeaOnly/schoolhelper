<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/3
  Time: 21:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>快递信息</title>
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
            <a class="icon icon-me pull-left open-panel"></a>
            <h1 class="title">快递系统</h1>
        </header>
        <div class="content">
            <div class="content-block">
                <form action="/help/test.do" method ="post">
                <div class="searchbar row">

                    <div class="search-input col-80">
                        <label class="icon icon-search" for="search"></label>
                        <input type="search" id='search' name ="search" placeholder='输入关键字...'/>
                    </div>
                   <%--<a class="button button-fill button-primary col-20" ><input type="submit" value="搜索"></a>--%>
                    <input type="submit" value="搜索">

                </div>
                </form>

                <div class="list-block">
                    <ul>
                        <li class="item-content">
                            <div class="item-media"><i class="icon icon-f7"></i></div>
                            <div class="item-inner">
                                <div class="item-title">快递点名称</div>
                                <div class="item-after">request:${requestScope.message}${requestScope.message}</div>
                            </div>
                        </li>
                        <li class="item-content">
                            <div class="item-media"><i class="icon icon-f7"></i></div>
                            <div class="item-inner">
                                <div class="item-title">联系人姓名</div>
                                <div class="item-after">${kuaidilist}</div>
                            </div>
                        </li>
                        <li class="item-content">
                            <div class="item-media"><i class="icon icon-f7"></i></div>
                            <div class="item-inner">
                                <div class="item-title">手机号</div>
                                <div class="item-after">17854258991</div>
                            </div>
                        </li>
                        <li class="item-content">
                            <div class="item-media"><i class="icon icon-f7"></i></div>
                            <div class="item-inner">
                                <div class="item-title">gps</div>
                                <div class="item-after">山东科技大学</div>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

    </div>
</div>

<script type='text/javascript' src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>

</body>

</html>
