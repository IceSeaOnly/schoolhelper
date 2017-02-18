<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>增加学校</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
    <script>
        function check_and_submit() {
            var ch = document.myform.name_ch.value;
            var en = document.myform.name_en.value;
            var ph = document.myform.servicePhone.value;
            var parent=/^[A-Za-z]+$/;
            if(ch == "" || en == "" || ph == ""){
                $.alert("不能为空！");
            }else if(!parent.test(en)){
                $.alert("英文缩写中不得出现除英文以外的字符！");
            }else if(ph.length != 11){
                $.alert("请输入正确的手机号");
            }else{
                $.confirm('学校只能增加不能删除，你确定添加一个新的学校吗？',
                        function () {
                            document.getElementById("myform").submit();
                        },
                        function () {
                            icesea.finish();
                        }
                );
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
            <h1 class="title">增加学校</h1>
        </header>


        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <form method="post" action="/app/zjxx_result.do" id="myform" name="myform">
                <input name="managerId" value="${managerId}" type="hidden"/>
                <input name="token" value="${Stoken}" type="hidden">
                <div class="list-block">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">中文校名</div>
                                    <div class="item-input">
                                        <input type="text" name="name_ch" placeholder="如：山东科技大学">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">英文缩写</div>
                                    <div class="item-input">
                                        <input type="text" name="name_en" placeholder="如：SDUST">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-media"><i class="icon icon-form-name"></i></div>
                                <div class="item-inner">
                                    <div class="item-title label">负责人手机号</div>
                                    <div class="item-input">
                                        <input type="text" name="servicePhone" placeholder="必填">
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <p><a onclick="check_and_submit()" class="button button-big">添加该校</a></p>
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
</script>
<jsp:include page="replaceToken.jsp"/>
</body>
</html>