<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>邀您关注筋斗云校园服务</title>
<head>
    <style>body{background-image: url("http://cdn.binghai.site/${tag?'o_1cb7cacjs1d5v164g1cjb1abm12ba':'o_1cb7cit4v1j5s112l1qsd1bij6via'}.png");background-position: center center; background-repeat: no-repeat;  background-size: 100% ;width:100%;}
    </style>
    <script type="text/javascript">
        function showQr(){
            console.log('FUCK');
            var div = document.getElementById("qrCode");
            div.style.display = "block";
            console.log('FUCK END');
        }
    </script>
</head>
<body >
<div style="width: 100%;height: 100%" onclick="showQr()">
    <div align="center" style="padding-top: 10%;display: none;" id="qrCode" >
        <img src="http://wx.nanayun.cn/qrCode?w=800&t=${ctx}">
    </div>
</div>
</body>
</html>