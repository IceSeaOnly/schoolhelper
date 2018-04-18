<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/30
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>我的云币</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">

</head>
<body>

<div class="page-group">
    <div class="page">
        <div class="content">
            <div class="buttons-tab">
                <a href="#tab1" class="tab-link active button">我的资产</a>
                <a href="#tab2" class="tab-link button">兑换中心</a>
            </div>

            <div class="tabs">
                <div id="tab1" class="tab active">

                    <div align="center">
                        <h3>我的云币: <span style="color:red">${user.cloudCoin}</span></h3>
                    </div>
                    <div class="content-block-title">资产变动记录</div>
                    <div class="list-block">
                        <ul>
                            <c:forEach items="${records}" var="rec">
                                <li class="item-content">
                                    <div class="item-inner">
                                        <div class="item-title">${rec.remark}</div>
                                        <div class="item-after" style="color: ${rec.many>0?'green':'red'}">${rec.many>0?'+':'-'}${rec.many}</div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <div id="tab2" class="tab">
                    <div class="content-block">
                        <img src="http://cdn.binghai.site/o_1cbc3sgv8l591bpnir3qm01vkia.png" style="width: 100%"/>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<script type='text/javascript' src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/??sm.min.js,sm-extend.min.js' charset='utf-8'></script>
<script>
    $.init();
</script>
</body>
</html>

