<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/30
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>我的信息</title>
    <link rel="stylesheet" href="../weixin_style/weui.css"/>
    <link rel="stylesheet" href="../weixin_style/example.css"/>
    <style>
        html {
            overflow-y: hidden;
        }
    </style>
</head>

<body>
<div style="overflow:hidden">
    <div class="hd">
        <h1 class="page_title">切换学校</h1>
        <p class="page_desc">切换学校后请及时更新宿舍信息</p>
    </div>
    <div class="odform">
        <form action="/user/update_school.do" method="post">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">选择学校</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <select class="weui_select" name="school">
                        <c:forEach items="${schools}" var="school">
                            <c:if test="${user.schoolId == school.id}">
                                <option value="${school.id}" selected="true">${school.schoolName}</option>
                            </c:if>
                            <c:if test="${user.schoolId != school.id}">
                                <option value="${school.id}">${school.schoolName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="bd spacing">
                <button onclick="form.submit()" class="weui_btn weui_btn_primary">提交更新</button>
            </div>
        </form>
    </div>

    <div
            style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
        <p>青岛小骨头·版权所有·V5.0</p>
        <p>
            <a href="http://www.binghai.site" target="_blank">Powered by 冰海</a>
        </p>
    </div>
</div>
</body>
</html>