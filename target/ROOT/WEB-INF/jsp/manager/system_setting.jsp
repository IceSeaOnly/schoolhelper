<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/2
  Time: 0:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>

    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,maximum-scale=3,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no, minimal-ui"/>
    <title>系统设置</title>
    <link rel="stylesheet" href="../manager_style/css/common.css">
    <link rel="stylesheet" href="../manager_style/css/font.css">
    <link rel="stylesheet" href="../manager_style/css/header.css">
    <link rel="stylesheet" href="../manager_style/css/footer.css">
    <link rel="stylesheet" href="../manager_style/css/index.css">
    <link rel="stylesheet" href="../manager_style/css/responsive.css">
</head>

<body ontouchstart="return true;">


<!--内容区-->

<div align="center">
    <form action="/manager/update_system_setting.do" method="post">
        首单费用(单位：分)<br><input type="text" name="fc" value="${fc}"/><br>
        配送工资(单位：分/每单)<br><input type="text" name="riderpayset" value="${riderpayset}"/><br>
        自动接单(0~24)<br><input type="text" name="as" value="${as}"/><br>
        自动停止(0~24)<br><input type="text" name="ae" value="${ae}"/><br>
        休息公告(自动停止时)<br><textarea  name="aci" style="width:100%;height:200px;font-size:30px">${aci}</textarea><br>
        停服公告(手动关闭时)<br><textarea  name="hci" style="width:100%;height:200px;font-size:30px">${hci}</textarea><br>
        <input type="submit" style="font-size: large;width: 100%;" value="保存配置">
    </form>
<br><br>
<a href="/manager/change_system_state.do?sid=${sid}">
    <button style="font-size: large;width: 300px;height: 100px">${handstop == "0"?"自动接单服务运行中":"自动接单服务已关闭"}</button>
</a>
</div>

</body>
</html>
<script type="text/javascript" src="../manager_style/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="../manager_style/js/swipe-min.js"></script>
<script type="text/javascript" src="../manager_style/js/common.js"></script>
