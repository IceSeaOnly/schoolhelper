<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <link href="../css/css.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="format-detection" content="telephone=no"/>
    <meta charset="utf-8">
    <title>会员充值</title>
    <style>
        /*充值账户*/
        .column_box p{ position:absolute;right:4%;top:0; color:#191919;}
        /*充值金额*/
        .money_box{ height:127px; position:relative; background-color:#fff;}
        .money_box h1{ line-height:39px; font-size:14px; color:#65646b; margin-left:4%;}
        .money_box h1 span{font-size:12px; color:#ff7f00;}
        .money{ float:left; width:20%; height:33px; line-height:33px; text-align:center; color:#191919; margin-left:4%; border:1px solid #ccc; border-radius:3px; box-sizing:border-box; background-position:left top; background-size:19px 19px; background-repeat:no-repeat;}
        .money_box input{ width:20%; height:33px; text-align:center; color:#01aff0; border:1px solid #ccc; border-radius:3px; box-sizing:border-box; position:absolute;left:76%;top:39px;}
        input:placeholder{color:#191919;}
        .choose{ border:1px solid #01aff0; color:#01aff0;}
        .first{ height:23px; line-height:23px; width:20%; margin-top:7px; margin-left:auto; margin-right:auto; text-align:center; font-size:12px; color:#65646b; position:relative}
        .first .border_top{ top:11px; width:180%;}
        .money_box p{ text-align:center; font-size:12px; color:#ff7f00; line-height:12px;}

        /* all */
        .money_box input::-webkit-input-placeholder { color:#191919; }
        .money_box input::-moz-placeholder { color:#191919; } /* firefox 19+ */
        .money_box input:-ms-input-placeholder { color:#191919; } /* ie */
        .money_box input input:-moz-placeholder { color:#191919; }

        /*选择套餐*/
        .setmeal_box{ background-color:#fff; position:relative; padding-top:14px;}
        .setmeal_title{ line-height:13px; font-size:14px; color:#65646b; margin-left:4%;}
        .setmeal{ width:92%; margin-left:4%; position:relative}
        .setmeal > h1,.setmeal > h2{ line-height:42px; float:left; font-size:14px; color:#191919;}
        .setmeal > h2{ margin-left:10%; color:#c40000;}
        .setmeal img{ width:11px; height:auto; position:absolute; left:50%; top:20px;}
        .setmeal input{ float:right; width:16px; height:16px; margin-top:13px;}
        .text h1{ line-height:13px; font-size:14px; color:#191919; margin-bottom:13px;}
        .text h2{ line-height:11px; font-size:12px; color:#191919; margin-bottom:7px;}
        .text p{ line-height:17px; font-size:12px; color:#65646b;}
        /*推荐人手机号*/
        .column_box h1{ line-height:46px; float:left; color:#191919;}
        .column_box input{ width:130px; margin-left:27px;}
    </style>
    <script type="text/javascript">
        function check_form(){
            var st = document.myform.setmeal.value;
            if(st !=null && st > 0){
                return true;
            }
            alert('请选择套餐类型');
            return false;
        }
    </script>
</head>

<body style="background-color: #f9f9f9;">
<!--header顶部标题-->
<div class="header">成为会员
    <div class="header_left" onclick="window.history.go(-1)"><img src="../images/return.png"></div>
</div>
<!--充值账户-->
<div class="column_box" style="margin-top:14px;">充值帐号
    <div class="border_top"></div>
    <p>${user.username}</p>
    <div class="border_bottom"></div>
</div>

<!--选择套餐-->
<form action="/user/pay_charge_vip.do" name="myform" id="myform" method="post" style="width: 100%;height: auto;" onsubmit="return check_form();">
    <div class="setmeal_box">
        <div class="setmeal_title">选择套餐</div>

        <c:forEach items="${meals}" var="meal">
            <div class="setmeal">
                <h1>充值${meal.pay/100}</h1>
                <h2>送${meal.gift/100}</h2>
                <input type="radio" value="${meal.id}" name="setmeal">
                <div class="clearfix"></div>
                <div class="border_bottom"></div>
            </div>
        </c:forEach>
        <div class="border_bottom"></div>
    </div>

    <!--立即充值-->
    <c:if test="${fn:length(meals)>0}">
        <div class="confirm_box">
            <input type="submit" class="confirm" style="background-color:#01aff0" value="立即充值"/>
        </div>
    </c:if>
</form>
</body>
</html>

