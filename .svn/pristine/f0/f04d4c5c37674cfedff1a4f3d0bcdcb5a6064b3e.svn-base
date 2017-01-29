<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/9
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    icesea.updateToken('${Stoken}');
    function diff_call(phone_a, phone_b) {
        if ($.trim(phone_a) == $.trim(phone_b)) {
            icesea.call(phone_a);
        }
        else {
            var buttons1 = [
                {
                    text: '请选择拨打：',
                    label: true
                },
                {
                    text: '取件手机号',
                    onClick: function () {
                        icesea.call(phone_a);
                    }
                },
                {
                    text: '配送手机号',
                    onClick: function () {
                        icesea.call(phone_b);
                    }
                }
            ];
            var buttons2 = [
                {
                    text: '取消',
                    bg: 'danger'
                }
            ];
            var groups = [buttons1, buttons2];
            $.actions(groups);
        }
    }
</script>
