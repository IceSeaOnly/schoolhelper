<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>选择优惠券使用的学校</title>
    <link rel="stylesheet" href="../weixin_style/weui.css"/>
    <link rel="stylesheet" href="../weixin_style/example.css"/>
    <script src="../js/jquery.js"></script>
    <style>
        html {
            overflow-y: hidden;
        }
    </style>
    <script>
        function validation() {
            if($("#schoolvar").val() != -1){
                //$("#form_chsc").submit();
                return true;
            }else{
                alert("你还没有选择正确的学校哦");
                return false;
            }
            return false;
        }
    </script>
</head>

<body>
<div style="overflow:hidden">
    <div class="hd">
        <h1 class="page_title">选择优惠券使用的学校</h1>
    </div>
    <div class="odform">
        <form action="/user/free_activity.do" method="get" id="form_chsc" onsubmit="return validation();">
            <input name="gid" value="${gid}" hidden="hidden">
            <div class="weui_cell">
                <div class="weui_cell_hd"><label class="weui_label">选择学校</label></div>
                <div class="weui_cell_bd weui_cell_primary">
                    <select class="weui_select" id="schoolvar" name="schoolId">
                        <option value="-1" selected="true">点击选择</option>
                        <c:forEach items="${schools}" var="school">
                            <%--<c:if test="${user.schoolId == school.id}">--%>
                            <%--<option value="${school.id}" selected="true">${school.schoolName}</option>--%>
                            <%--</c:if>--%>
                            <%--<c:if test="${user.schoolId != school.id}">--%>
                            <option value="${school.id}">${school.schoolName}</option>
                            <%--</c:if>--%>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="bd spacing">
                <button class="weui_btn weui_btn_primary">选好了</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>