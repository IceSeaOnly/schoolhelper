<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/4
  Time: 11:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>服务说明</title>
    <link rel="stylesheet" href="./weixin_style/weui.css"/>
    <link rel="stylesheet" href="./weixin_style/example.css"/>
    <script type="text/javascript">
        function closeit(){
            WeixinJSBridge.invoke('closeWindow',{},function(res){
                //alert(res.err_msg);
            });
        }
    </script>
</head>
<body>
<br>
<h1 class="page_title">服务说明</h1>
<div class="weui_msg">
    <div class="weui_text_area" align="left">
        <p class="weui_msg_desc">* 请填写当日手机短信上的提货码或编号</p>
        <p class="weui_msg_desc">* 填写快递单上的姓名电话</p>
        <p class="weui_msg_desc">* 因编号姓名电话填写不正确导致未能取件的，概不退款,可改为次日取件。</p>
        <p class="weui_msg_desc">* 如付款后想自己去领，请<span style="color:red">发短信给客服</span>：17806272551，我们将给予退款。</p>
        <p class="weui_msg_desc">* 付款后没有在微信及时说明而自己领取的，和已经领取还未送达宿舍楼的不予退款，谢谢合作。</p>
        <p class="weui_msg_desc">* 客服微信:yueshangshao1993</p>
        <p class="weui_msg_desc">* 配送时间：16：30~18：00</p>
    </div>
    <div class="weui_opr_area">
        <p class="weui_btn_area">
            <a href="/user/help_express.do" class="weui_btn weui_btn_primary">我已阅读并同意</a>
            <a href="javascirpt:void(0)" onclick="closeit()" class="weui_btn weui_btn_warn">我再想想</a>
        </p>
    </div>
</div>

</body>
</html>