<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <title>E_milk 数字奶业平台</title>
    <link rel="icon" href="/favicon.ico"/>
</head>
<body id="login" class="login">
<center>
    <div id="header">
        <div class="logo">
        </div>
    </div>
    <input type="button" onclick="scoreQuery()" value="测试登陆"/>
    <script type="text/javascript">

        function scoreQuery() {
            var url = "/wap/curio/query";
            var certCode = $("#certCode").val();
            var data = {
                "param": {
                    "status": "1",
                    "tagId": "",
                    "key": "古玩",
                    "cityCode": ""
                }
            };
            $.ajax({
                type: 'post',
                url: url,
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (data) {
                    pingppPc.createPayment(data.bizData, function (result, err) {
                        // 处理错误信息
                    });
                },
                error: function () {
                    // view("异常！");
                    alert("异常！");
                }
            });
        }
    </script>
    <script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#codePic").bind('click', function () {
                remove();
                getPic();
            })
        })
        function getPic() {
            $("#codePic").attr("src", "http://127.0.0.1:8081/image/code?id=" + new Date().getTime());
        }
        ;
        function remove() {
            $("#codePic").attr("src", "");
        }
        ;
    </script>
    </div>

    <center>
        <form action="" method="post"/>
        用户名：
        <input type="text" id="username"/>
        密&nbsp;&nbsp;码：
        <input type="password" id="password"/>
        &nbsp;验证码：
        <input type="text" id="certCode"/>
        <img id="codePic"><a href="javascript:getPic()">看不清，换一张 </a></img>
        <input type="submit" value="登录"/>
        </form>
    </center>

    <div id="footer">
        阿里巴巴 | Copyright &copy; 2016 All rights reserved.
    </div>
</center>
</body>
</html>
