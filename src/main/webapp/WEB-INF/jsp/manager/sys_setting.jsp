<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>${schoolName}-系统设置</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1">
    <link rel="shortcut icon" href="/favicon.ico">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
    <link rel="stylesheet" href="http://g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
    <script>

        function update_salary_config(pid) {
            var nt = $("#salary_conf_" + pid).val()
            $.get("/ajax/update_salary_conf.do?token=${Stoken}&managerId=${managerId}&pid=" + pid + "&val=" + nt, function (data, status) {
                if (status == "success")
                    $.alert(data);
                else
                    $.alert("输入不合法，请参看说明");
            });
        }

        function common_change(where) {
            $.get("/ajax/" + where + ".do?token=${Stoken}&managerId=${managerId}&schoolId=${schoolId}", function (data, status) {
                if (status == "success")
                    $.alert(data);
                else
                    $.alert("输入不合法，请参看说明");
            });
        }
        function delete_vip(id) {
            $.get("/ajax/deleteVip.do?token=${Stoken}&managerId=${managerId}&schoolId=${schoolId}&id="+id, function (data, status) {
                if (status == "success")
                    $.alert(data);
                else
                    $.alert("输入不合法，请参看说明");
            });
            $("#vipls_"+id).hide();
        }

        function exp_status_change(id) {
            $.get("/ajax/exp_status_change.do?managerId=${managerId}&token=${Stoken}&eid="+id,function (data, status) {
                if(status="success"){
                    $.toast(data);
                }else $.toast('参数错误');
            })
        }
        function sendtime_changed(id) {
            $.get("/ajax/sendtime_changed.do?managerId=${managerId}&token=${Stoken}&id="+id,function (data, status) {
                if(status="success"){
                    $.toast(data);
                }else $.toast('参数错误');
            })
        }

        function reset_exp(id) {
            $.prompt('重设价格', function (value) {
                $.get("/ajax/reset_exp_price.do?managerId=${managerId}&token=${Stoken}&eid="+id+"&price="+value,function (data, status) {
                    if(status="success"){
                        $.toast(data);
                        setTimeout("icesea.reload()",1000);
                    } else $.toast('参数错误');
                })
            });
        }
    </script>

</head>
<body>

<div class="page-group">
    <div class="page page-current">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="javascript:icesea.finish()" class="icon icon-left pull-left"></a>
            <jsp:include page="right_reload.jsp"/>
            <h1 class="title">${schoolName}-系统设置</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <br><br><br>
            <div class="content-block">
                <p><a href="#salary_config" class="button button-big">配置分红比例</a></p>
                <p><a href="#vip_config" class="button button-big ">配置VIP充值方案</a></p>
                <p><a href="#part_config" class="button button-big ">配置本校区域分配</a></p>
                <p><a href="#express_config" class="button button-big ">管理本校快递</a></p>
                <p><a href="#sendtime_config" class="button button-big ">配送时段设置</a></p>
                <p><a href="#other_config" class="button button-big ">配置本校其他内容</a></p>
            </div>
        </div>
        <!-- 这里是页面内容区 end-->
        <!-- 你的html代码 -->
    </div>
    <div class="page" id="salary_config">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="#" class="icon icon-left pull-left back"></a>
            <h1 class="title">配置分红比例</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="content-padded">
                <p>· 此处配置本校所有管理员的分红比例，如某人不参与本校利润分红，<span style="color:red">则应设置成0</span></p>
                <p>· 此处设置的分红比例是以<span style="color:red">收入为底</span>进行分红，分红所得=本校收入*分红比例</p>
                <p>· 应输入<span style="color:red">大于或等于0 且 小于1</span>的小数，最多保留两位小数</p>
            </div>
            <div class="list-block">
                <ul>
                    <c:forEach items="${ms}" var="per">
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">${per.name}</div>
                                    <div class="item-input">
                                        <input id="salary_conf_${per.id}" value="${per.dividendRatio}" type="text"
                                               placeholder="0 <= 输入 < 1">
                                    </div>
                                    <div class="item-after">
                                        <a href="javascript:update_salary_config(${per.id})" class="button">更新</a>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <!-- 这里是页面内容区 end-->
        <!-- 你的html代码 -->
    </div>


    <div class="page" id="vip_config">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="#" class="icon icon-left pull-left back"></a>
            <h1 class="title">配置VIP充值方案</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="list-block media-list">
                <ul>
                    <c:forEach items="${vips}" var="vip">
                    <li id="vipls_${vip.id}">
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">充${vip.pay/100}送${vip.gift/100}</div>
                                    <div class="item-after"><a href="javascript:delete_vip(${vip.id})" class="button button-danger">删除</a></div>
                                </div>
                            </div>
                        </div>
                    </li>
                    </c:forEach>
                </ul>
            </div>
            <form action="addVipMeal.do" method="post" id="form_vip">
                <input name="managerId" value="${managerId}" type="hidden"/>
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="schoolId" value="${schoolId}" type="hidden"/>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">充(单位:分)</div>
                                <div class="item-input">
                                    <input type="text" name="pay" placeholder="大于0的整数">
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">送(单位:分)</div>
                                <div class="item-input">
                                    <input type="text" name="gift" placeholder="赠送比例不大于10%,整数">
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <p><a href="javascript:$('#form_vip').submit()" class="button button-big">添加</a></p>
            </div>
            </form>
        </div>
        <!-- 这里是页面内容区 end-->
        <!-- 你的html代码 -->
    </div>

    <div class="page" id="sendtime_config">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="#" class="icon icon-left pull-left back"></a>
            <h1 class="title">配送时间设置</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="content-padded">
                <p>· 这里的说明是给用户看的，<span style="color:red">并不区分时间</span>，例如，08:00 ~ 12:00时间段上尚未选满，用户在13:00仍可选择该时间段</p>
                <p>· 一但添加<span style="color:red">将无法删除，</span>但可停用</p>
            </div>
            <div class="list-block media-list">
                <ul>
                    <c:forEach items="${stimes}" var="stm">
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">${stm.name} ${stm.s_limit}个</div>
                                    <div class="item-after">
                                        <label class="label-switch">
                                            <input type="checkbox" onchange="sendtime_changed(${stm.id})" <c:if test="${stm.available}">checked="checked"</c:if> />
                                            <div class="checkbox"></div>
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </li>
                    </c:forEach>
                </ul>
            </div>
            <form action="addSendTime.do" method="post" id="form_stm">
                <input name="managerId" value="${managerId}" type="hidden"/>
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="schoolId" value="${schoolId}" type="hidden"/>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">说明</div>
                                <div class="item-input">
                                    <input type="text" name="notice" placeholder="如:8:00 ~ 12:00">
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">订单限制</div>
                                <div class="item-input">
                                    <input type="text" name="limit" placeholder="如不设限应尽量大">
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <p><a href="javascript:$('#form_stm').submit()" class="button button-big">添加</a></p>
            </div>
            </form>
        </div>
        <!-- 这里是页面内容区 end-->
        <!-- 你的html代码 -->
    </div>


    <div class="page" id="express_config">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="#" class="icon icon-left pull-left back"></a>
            <h1 class="title">管理本校快递</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="content-padded">
                <p>· 此处配置费用<span style="color:red">单位为分</span></p>
                <p>· 快递点一但添加<span style="color:red">将无法删除，</span>但可停用</p>
            </div>
            <div class="list-block media-list">
                <ul>
                    <c:forEach items="${exps}" var="exp">
                        <li id="express_${exp.id}">
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title-row">
                                        <div class="item-title">${exp.expressName} ￥${exp.sendPrice/100}</div>
                                        <div class="item-after">
                                            <a href="javascript:reset_exp(${exp.id})" class="button">设置</a>&nbsp&nbsp
                                            <label class="label-switch">
                                                <input type="checkbox" onchange="exp_status_change(${exp.id})" <c:if test="${exp.available}">checked="checked"</c:if> />
                                                <div class="checkbox"></div>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <form action="addExpress.do" method="post" id="form_exps">
                <input name="managerId" value="${managerId}" type="hidden"/>
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="schoolId" value="${schoolId}" type="hidden"/>
                <div class="list-block">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">快递名</div>
                                    <div class="item-input">
                                        <input type="text" name="exp_name" >
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">配送费</div>
                                    <div class="item-input">
                                        <input type="text" name="price" placeholder="大于0的整数">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">停止接单时间</div>
                                    <div class="item-input">
                                        <input type="text" name="exp_end" id="exp_end" placeholder="点击选择">
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <p><a href="javascript:$('#form_exps').submit()" class="button button-big">添加</a></p>
                </div>
            </form>
        </div>
        <!-- 这里是页面内容区 end-->
        <!-- 你的html代码 -->
    </div>

    <div class="page" id="other_config">
        <!-- 你的html代码 -->
        <header class="bar bar-nav">
            <a href="#" class="icon icon-left pull-left back"></a>
            <h1 class="title">配置本校其他内容</h1>
        </header>
        <!-- 这里是页面内容区 begin-->
        <div class="content">
            <div class="content-padded">
                <p>· 此处出现的金额单位<span style="color:red">均为分</span></p>
                <p>· 此处配置的工资项<span style="color:red">均是每件标准</span></p>
                <p>· 此处所填必须<span style="color:red">均为大于0的整数</span></p>
                <p>· 此处所配置时间<span style="color:red">均为24小时制的整点</span></p>
                <p>· 校园微店地址<span style="color:red">非</span>必填项</p>
                <p>· 手动控制优先级<span style="color:red">高于</span>自动控制</p>
                <p>· 首单优惠、满十减一<span style="color:red">开启后立即生效</span></p>
            </div>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">手动总控</div>
                                <div class="item-input">
                                    <label class="label-switch">
                                        <input type="checkbox"
                                               <c:if test="${!config.hand_close}">
                                                checked="checked"
                                        </c:if>
                                               onchange="javascript:common_change('hand_controll_change')">
                                        <div class="checkbox"></div>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">首单优惠</div>
                                <div class="item-input">
                                    <label class="label-switch">
                                        <input type="checkbox"
                                        <c:if test="${config.firstDiscount}">
                                               checked="checked"
                                        </c:if>
                                               onchange="javascript:common_change('firstDiscountChange')">
                                        <div class="checkbox"></div>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">满十减一</div>
                                <div class="item-input">
                                    <label class="label-switch">
                                        <input type="checkbox"
                                        <c:if test="${config.ifTenThenFree}">
                                               checked="checked"
                                        </c:if>
                                               onchange="javascript:common_change('ifTenThenFree')">
                                        <div class="checkbox"></div>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">校园搬运</div>
                                <div class="item-input">
                                    <label class="label-switch">
                                        <input type="checkbox"
                                        <c:if test="${config.schoolMove}">
                                               checked="checked"
                                        </c:if>
                                               onchange="javascript:common_change('schoolMove')">
                                        <div class="checkbox"></div>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">代寄快递</div>
                                <div class="item-input">
                                    <label class="label-switch">
                                        <input type="checkbox"
                                        <c:if test="${config.ifTenThenFree}">
                                               checked="checked"
                                        </c:if>
                                               onchange="javascript:common_change('helpSend')">
                                        <div class="checkbox"></div>
                                    </label>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <form action="update_sys_setting.do" method="post" id="form_set">
                <input name="managerId" value="${managerId}" type="hidden"/>
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="schoolId" value="${schoolId}" type="hidden"/>
                <div class="list-block">
                    <ul>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">负责人手机号</div>
                                    <div class="item-input">
                                        <input type="text" name="phone" placeholder="必填" value="${config.servicePhone}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">首单金额</div>
                                    <div class="item-input">
                                        <input type="text" name="fristCost" placeholder="1<=整数" value="${config.first_cost}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">校园微店</div>
                                    <div class="item-input">
                                        <input type="text" name="shopUrl" placeholder="url地址" value="${config.shop_url}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">取件工资</div>
                                    <div class="item-input">
                                        <input type="text" name="qs" placeholder="1<=整数" value="${config.each_fetch}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">送件工资</div>
                                    <div class="item-input">
                                        <input type="text" name="ss" placeholder="1<=整数" value="${config.each_send}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">转交楼长工资</div>
                                    <div class="item-input">
                                        <input type="text" name="zjs" placeholder="1<=整数" value="${config.each_give}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">楼长接收工资</div>
                                    <div class="item-input">
                                        <input type="text" name="jss" placeholder="1<=整数" value="${config.each_receive}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">自动开始接单</div>
                                    <div class="item-input">
                                        <input type="text" name="aus" id="auto_start_select" value="${config.auto_start}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">自动停止接单</div>
                                    <div class="item-input">
                                        <input type="text" name="ausd" id="auto_stop_select" value="${config.auto_close}">
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">自动停止告知</div>
                                    <div class="item-input">
                                        <textarea name="ausn" placeholder="系统处于自动停止接单时提示的内容">${config.auto_close_info}</textarea>
                                    </div>
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="item-content">
                                <div class="item-inner">
                                    <div class="item-title label">手动停止告知</div>
                                    <div class="item-input">
                                        <textarea name="ausdn" placeholder="系统处于手动停止接单时提示的内容">${config.hand_close_info}</textarea>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>
                </div>
                <div class="content-block">
                    <p><a href="javascript:$('#form_set').submit()" class="button button-big">更新</a></p>
                </div>
            </form>
        </div>
        <!-- 这里是页面内容区 end-->
        <!-- 你的html代码 -->
    </div>
    <div class="page" id='part_config'>
        <header class="bar bar-nav">
            <a class="button button-link button-nav pull-left back">
                <span class="icon icon-left"></span>
                返回
            </a>
            <h1 class='title'>配置本校区域分配</h1>
        </header>
        <div class="content">
            <div class="content-padded">
                <p>· 一旦添加<span style="color:red">暂不可删除</span></p>
            </div>
            <div class="list-block media-list">
                <ul>
                    <c:forEach items="${parts}" var="part">
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title-row">
                                    <div class="item-title">${part.partName}</div>
                                </div>
                            </div>
                        </div>
                    </li>
                    </c:forEach>
                </ul>
            </div>
            <form action="addSchoolPart.do" method="post" id="form_part">
                <input name="managerId" value="${managerId}" type="hidden"/>
                <input name="token" value="${Stoken}" type="hidden"/>
                <input name="schoolId" value="${schoolId}" type="hidden"/>
            <div class="list-block">
                <ul>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">区域名</div>
                                <div class="item-input">
                                    <input type="text" name="pname" placeholder="如：A区">
                                </div>
                            </div>
                        </div>
                    </li>
                    <li>
                        <div class="item-content">
                            <div class="item-inner">
                                <div class="item-title label">配送费</div>
                                <div class="item-input">
                                    <input type="text" name="ppay" placeholder="(单位:分)">
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="content-block">
                <p><a href="javascript:$('#form_part').submit()" class="button button-big">添加</a></p>
            </div>
            </form>
        </div>
    </div>
</div>

<script type='text/javascript'
        src='http://g.alicdn.com/sj/lib/zepto/zepto.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm.min.js' charset='utf-8'></script>
<script type='text/javascript' src='http://g.alicdn.com/msui/sm/0.6.2/js/sm-extend.min.js' charset='utf-8'></script>
<script>
    $.init();
</script>
<script>
    $("#auto_start_select").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
  <button class="button button-link pull-right close-picker">确定</button>\
  <h1 class="title">自动接单(24小时制)</h1>\
  </header>',
        cols: [
            {
                textAlign: 'center',
                values: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
            }
        ]
    });

    $("#auto_stop_select").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
  <button class="button button-link pull-right close-picker">确定</button>\
  <h1 class="title">自动停止接单(24小时制)</h1>\
  </header>',
        cols: [
            {
                textAlign: 'center',
                values: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
            }
        ]
    });


    $("#exp_end").picker({
        toolbarTemplate: '<header class="bar bar-nav">\
  <button class="button button-link pull-right close-picker">确定</button>\
  <h1 class="title">24小时制</h1>\
  </header>',
        cols: [
            {
                textAlign: 'center',
                values: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]
            }
        ]
    });
</script>
<jsp:include page="replaceToken.jsp"/>
</body>
</html>