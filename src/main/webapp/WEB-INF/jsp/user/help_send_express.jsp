<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>代寄快递</title>
    <link rel="stylesheet" href="../weixin_style/weui.css"/>
    <link rel="stylesheet" href="../weixin_style/example.css"/>
    <style>
        html {
            overflow-y: hidden;
        }
    </style>

</head>

<body>
<br>
<h1 class="odform-tit" align="center">
    代寄快递单
</h1>
<div class="weui_text_area" align="center">
    <p class="weui_msg_desc">· 服务说明 ·</p>
    <p class="weui_msg_desc">下单收取预约金,<span style="color: red">预约金不退，</span>寄件时减去预约金。</p>
    <p class="weui_msg_desc"><span style="color: red">付款后当天下午15~16点之间取件。</span></p>
</div>
<br>

<form action="/user/submit_help_sendexpress.do" method="post">
    <div class="weui_cell weui_cell_select weui_select_after">
        <div class="weui_cell_hd">
            <label class="weui_label">选择快递</label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
            <select class="weui_select" name="select_express">
                <c:forEach items="${expresses}" var="express">
                    <option value="${express.id}">${express.expressname}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="weui_cell ">
        <div class="weui_cell_hd"><label class="weui_label">联系人</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" name="name" type="text" placeholder="请输入真实姓名"
                   value="${user.username}"/>
        </div>
    </div>

    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">手机号码</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" name="phone" type="number" pattern="[0-9]*" placeholder="请输入手机号码"
                   value="${user.phone}"/>
        </div>
    </div>

    <div class="weui_cell">
        <div class="weui_cell_hd"><label class="weui_label">取件地址</label></div>
        <div class="weui_cell_bd weui_cell_primary">
            <input class="weui_input" name="address" type="text" value="${address}"/>
        </div>
    </div>
    <br>
    <div class="bd spacing">
        <a href="http://fast.bigdata8.xin/1.png" class="weui_btn weui_btn_plain_default">查看快递资费</a>
    </div>
    <br>
    <div class="bd spacing">
        <input type="submit" value="提交订单" class="weui_btn weui_btn_primary">
    </div>

</form>


</body>
</html>
