<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>校园搬运</title>
    <link rel="stylesheet" href="../weixin_style/weui.css"/>
    <link rel="stylesheet" href="../weixin_style/example.css"/>
    <style>
        html { overflow-y:hidden; }
    </style>

</head>

<body>
<br>
<h1 class="odform-tit" align="center">
    校园搬运订单
</h1>
<div class="weui_text_area" align="center">
    <p class="weui_msg_desc">· 收费标准 ·</p>
    <p class="weui_msg_desc">使用三轮车: 5元/小时</p>
    <p class="weui_msg_desc">搬运小哥帮助 + 使用三轮车:15元/小时</p>
    <p class="weui_msg_desc">预约金:￥5,<span style="color: red">预约金不退</span></p>
</div>
<br>

<form action="/user/submit_schoolmove.do" method="post">
    <div class="weui_cell weui_cell_select weui_select_after">
        <div class="weui_cell_hd">
            <label for="" class="weui_label">用车时间</label>
        </div>
        <div class="weui_cell_bd weui_cell_primary">
            <select class="weui_select" name="movetime">
                <option value="上午9点到11点半">上午9点到11点半</option>
                <option value="晚上7点以后" selected="selected">晚上7点以后</option>
            </select>
        </div>
    </div>
    <div class="weui_cell ">
        <div class="weui_cell_hd"><label class="weui_label">联系人</label></div>
        <div class="weui_cell_bd weui_cell_primary" >
            <input class="weui_input" name = "name" type="text" placeholder="请输入真实姓名"
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
    <br>
    <div class="bd spacing">
        <input type="submit" value="提交订单" class="weui_btn weui_btn_primary">
    </div>

</form>



</body>
</html>
