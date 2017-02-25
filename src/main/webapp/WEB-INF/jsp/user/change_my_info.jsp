<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/8/30
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>我的信息</title>
    <link rel="stylesheet" href="../weixin_style/weui.css"/>
    <link rel="stylesheet" href="../weixin_style/example.css"/>
    <style>
        html { overflow-y:hidden;}
    </style>
</head>

<body>
<div style="overflow:hidden">
    <div class="hd">
        <h1 class="page_title">我的信息</h1>
        <p class="page_desc">为了更好地为您服务，请填写真实信息，您的所有信息均采用加密保存。</p>
    </div>
    <div class="odform">
        <form action="/user/update_info.do" method="post" >

            <div class="weui_cell ">
                <div class="weui_cell_hd"><label class="weui_label">姓名</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" name="name" type="text" placeholder="请输入真实姓名"
                           value="${user.username}" required="required"
                    />
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">手机号码</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" name="phone" type="number" pattern="[0-9]*" placeholder="请输入手机号码"
                           value="${user.phone}" required="required"/>
                </div>
            </div>

            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">宿舍区</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <select class="weui_select" name="part">
                        <c:forEach items="${parts}" var="part">
                            <c:if test="${part.id == user.part}">
                                <option value="${part.id}" selected="true">${part.partName}</option>
                            </c:if>
                            <c:if test="${part.id != user.part}">
                                <option value="${part.id}">${part.partName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">宿舍楼号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" name="building" type="text" placeholder="请输入宿舍楼号" required="required"
                           value="${user.building}"/>
                </div>
            </div>
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">宿舍号</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <input class="weui_input" name="dormitory" type="text" placeholder="请输入宿舍楼号" required="required"
                           value="${user.dormitory}"/>
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