function validate_form() {
    var ex = document.myform.express.value;
    var sms_ = document.myform.sms.value;
    var part_ = document.myform.part.value;
    var otherinfo_ = document.myform.otherinfo.value;
    var time_validate_ = document.myform.time_validate;

	if(part_ == '家属区'){
		alert('对不起，因配送人员调整，今天暂时无法配送家属区，谢谢您的理解！');
		return false;
	}
    if (name_ == "" || phone_ == "" || area_ == "" || department_ == "" || sms_ == "") {
        alert("别着急，你还没填好呢！");
        return false;
    }
    if (phone_.length != 11) {
        alert("请仔细检查一下，手机号写的不对吧？");
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

    if (ex == '错误选择') {
        alert("别着急！你还没有选择快递呢！");
        return false;
    }

    var timestamp = Date.parse(new Date());
    if(timestamp - time_validate_ > 300000){
        alert("这个页面太久没有活动了，信息可能不准确了呢，请刷新一下再重新下单！")
        return false;
    }
    return true;
}
