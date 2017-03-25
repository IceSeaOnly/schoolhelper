<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<title>新建小骨头订单</title>
<style>
body,input,button,select {
	font: normal 14px "Microsoft Yahei";
	margin: 0;
	padding: 0
}

.odform-tit {
	font-weight: normal;
	font-size: 25px;
	color: #595757;
	line-height: 40px;
	text-align: center;
	border-bottom: 1px solid #c9cacb;
	margin: 0;
	padding: 5% 0
}

.odform-tit img {
	height: 40px;
	vertical-align: middle;
	margin-right: 15px
}

.odform {
	padding: 5%
}

.input-group {
	margin-bottom: 5%;
	position: relative
}

.input-group label {
	padding: 2% 0;
	position: absolute;
	color: #595757
}

.input-group input {
	margin-left: 5em;
	padding: 3% 5%;
	box-sizing: border-box;
	background: #efeff0;
	border: 0;
	border-radius: 5px;
	color: #595757;
	width: 75%
}

.input-group select {
	margin-left: 5em;
	padding: 3% 5%;
	box-sizing: border-box;
	background: #efeff0;
	border: 0;
	border-radius: 5px;
	color: #595757;
	width: 75%
}

.odform button {
	background: #8ec31f;
	color: #fff;
	text-align: center;
	border: 0;
	border-radius: 10px;
	padding: 3%;
	width: 100%;
	font-size: 16px
}

.odform .cal {
	background-image: url(http://xiaogutou.qdxiaogutou.com/images/daetixian-cal.png);
	background-repeat: no-repeat;
	background-position: 95% center;
	background-size: auto 50%
}

.odform .xl {
	background-image: url(http://xiaogutou.qdxiaogutou.com/images/daetixian-xl.png);
	background-repeat: no-repeat;
	background-position: 95% center;
	background-size: auto 20%
}
</style>
<script type="text/javascript">
function validate_form() {
    var ex = document.myform.express.value;
    var sms_ = document.myform.sms.value;
    var otherinfo_ = document.myform.otherinfo.value;
	var part_ = document.myform.part.value;
	var bd = document.myform.building.value;
	var sid = document.myform.sendtime_id.value;

	if(sid == -1){
		alert('该时段配送已达上限，请选择其他时间，谢谢.');
		return false;
	}
	if(part_ == 4){
		alert('对不起，因配送人员调整，今天暂时无法配送家属区，谢谢您的理解！');
		return false;
	}

    if (sms_ == "") {
        alert("别着急，你还没填好呢！");
        return false;
    }
	if(sms_.length>9){
		alert("快递编号是短信中取件序号哦，请检查输入！");
		return false;
	}
    if (otherinfo_.length > 20) {
        alert("你的备注好长啊，短一点好吗？");
        return false;
    }
    if (ex < 0) {
        alert("哎呀,这家快递需要在12点前下单哦！现在不可以啦！");
		return false;
    }

    if (ex > 999) {
        alert("别着急！你还没有选择快递呢！");
        return false;
    }
	

    return true;
}

</script>
</head>

<body>
	<h1 class="odform-tit">
		新建小骨头订单
	</h1>
	<div class="odform">
		<form action="/user/take_help_express_order.do" method="post" name="myform" id="myform"
              onsubmit="return validate_form();">
			<div class="input-group">
				<label>快递名称</label> <select  name="express">
					<option value="9999">请选择</option>
					<c:forEach items="${expresses}" var="express">
                            <option value="${express.id}">${express.expressName}</option>
                        </c:forEach>
				</select>
			</div>
			<div class="input-group">
				<label >取件号</label> <input type="text"
					name="sms" placeholder="短信通知中的快递序号">
			</div>
			<div class="input-group">
				<label>配送时间</label> <select  name="sendtime_id">
				<c:forEach items="${sendtimes}" var="stime">
					<!--<option value="${stime.id}">${stime.name} ${stime.curSum}/${stime.s_limit}</option>-->
					<option value="${stime.id}">${stime.name}</option>
				</c:forEach>
			</select>
			</div>
			<div class="input-group">
				<label >到达时间</label>
				<select  name="arrive">
					<option value="今日件">今日件</option>
					<option value="昨日件">昨日件</option>
				</select>
			</div>
			<div class="input-group">
				<label >收件人</label> <input type="text" 
					name="express_name" placeholder="请输入快递单上的姓名" value="${user.username}" required="required">
			</div>
			<div class="input-group">
				<label >手机号码</label> <input type="text" 
					name="express_phone" placeholder="请输入快递单上的手机号码" pattern="[0-9]*" value="${user.phone}" required="required">
			</div>
			
			<div class="input-group">
				<label>配送至</label> <select  name="part">
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
			
			<div class="input-group">
				<label >宿舍信息</label> <input type="text"
					name="building" placeholder="楼号-宿舍号" value="${user.building}-${user.dormitory}" required="required">
			</div>
			<div class="input-group">
				<label >配送给</label> <input type="text" 
					name="sendto_name" placeholder="请输入真实姓名" value="${user.username}" required="required">
			</div>
			<div class="input-group">
				<label >手机号码</label> <input type="text" 
					name="sendto_phone" pattern="[0-9]*" placeholder="请输入手机号码" required="required"
                           value="${user.phone}">
			</div>
			<div class="input-group">
				<label >附加备注</label> <input type="text" 
					name="otherinfo" placeholder="附加备注，勿超过20字">
			</div>
			<a href="javascript:this.form.submit();"><button>${user.freeSum>0?"使用免单券下单":"马上下单"}</button></a>
		</form>
	</div>

	<div
		style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
		<p>青岛小骨头·版权所有</p>
		<p>
			<a href="http://www.binghai.site" target="_blank">Powered by 冰海</a>
		</p>
	</div>
</body>
</html>
