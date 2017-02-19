<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${schoolName}学校概况</title>
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
            <a href="" class="icon icon-menu pull-right"></a>
            <h1 class="title">${schoolName}学校概况</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="list-block media-list">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><img src="http://image.binghai.site/data/f_43214620.jpg" style='width: 2.2rem;'></div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">${school.schoolName} ${school.tag}</div>
                                    <div class="item-after">NO.${schoolId}</div>
                                </div>
                                <div class="item-subtitle">${config.servicePhone}</div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><img src="http://image.binghai.site/data/f_26496006.jpg" style='width: 2.2rem;'></div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">今日收入</div>
                                    <div class="item-after">￥${todayIncome/100}</div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><img src="http://image.binghai.site/data/f_74544532.jpg" style='width: 2.2rem;'></div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">今日订单</div>
                                    <div class="item-after">${todaySum}</div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><img src="http://image.binghai.site/data/f_21545552.jpg" style='width: 2.2rem;'></div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">总收入</div>
                                    <div class="item-after">￥${config.sumIncome/100}</div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><img src="http://image.binghai.site/data/f_98565668.jpg" style='width: 2.2rem;'></div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">代取总数</div>
                                    <div class="item-after">${dqSum}单</div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><img src="http://image.binghai.site/data/f_35380204.jpg" style='width: 2.2rem;'></div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">代寄总数</div>
                                    <div class="item-after">${djSum}单</div>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-media"><img src="http://image.binghai.site/data/f_88539884.jpg" style='width: 2.2rem;'></div>
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">校园搬运</div>
                                    <div class="item-after">${moveSum}单</div>
                                </div>
                            </div>
                        </div>
                    </li>
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