<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <title>登录测试</title>
    <link rel="icon" href="/favicon.ico"/>
</head>
<body id="login" class="login">
<center>
    <div id="header">
        <div class="logo">
        </div>
    </div>
    <input type="button" onclick="scoreQuery()" value="测试"/>
    <script src="http://libs.baidu.com/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript">

        function scoreQuery() {
//                var url = "/pigs/basic/retail/9";
//                var url = "/basic/retail/add";
            var base = "http://127.0.0.1:8081";
            var url = "/shop/add-update";
//            var url = "/login";
            var certCode = $("#certCode").val();
//                var data = {"userName": '李四', "password": '123456',"issuingUnit":certCode,
//                    "address":"萧山","detailAddress":"李家村","familyName":"汉族"
//                "userCode":"123456789963","cardNumber":"1010123456456","mobile":"18867514504"
//                };//upacp_pc
//            var data = {
//                "param": {
//                    "username": 'abcabc',
//                    "password": 'e10adc3949ba59abbe56e057f20f883e',
//                    "imageCode": certCode
//                }};
            var data = {
                "param": {
                    "name": "",
                    "slogans": "hahsdghahdg",
                    "banner": "http://786.342.23.4242/877.png",
                    "contact": "18878127812",
                    "address":"萬福里"
                }
            };
            $.ajax({
                type: 'post',
                url: url,
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function (data) {
                    alert(data);
                },
                error: function () {
                    // view("异常！");
                    alert("异常！");
                }
            });
        }
        ;

        $(function () {
            $("#codePic").bind('click', function () {
                remove();
                getPic();
            })
        })
        function getPic() {
            $("#codePic").attr("src", "http://localhost:8081/image/code?id=" + new Date().getTime());
        }
        ;
        function remove() {
            $("#codePic").attr("src", "");
        }
        ;

        function fileloadon() {
            $("#msg").html("");
            $("#_fileForm").submit(function () {
                $("#_fileForm").ajaxSubmit({
                    type: "post",
                    url: "/image/upload",
                    success: function (data1) {
                        $('#timgUrl').html(data1);
                        var reg = new RegExp('"', "g");
                        var imageUrl = $('#timgUrl').text().replace(reg, "");
                        $('#backImgUrl').attr("src", imageUrl);
                        if ($('#timgUrl').html() != null) {
                            $('#timgok').html("文件上传成功");
                        } else {
                            $('#timgok').html("文件上传失败");
                        }
                    },
                    error: function (msg) {
                        alert("文件上传失败");
                    }
                });
                return false;
            });
            $("#_fileForm").submit();
        }
    </script>
    </div>
    <%--<script src="project/js/jquery.form.js" type="text/javascript"></script>--%>
    <%--<script src="project/js/fileload.js" type="text/javascript"></script>--%>
    <script type="text/javascript">
        $(document).ready(function() {
            createHtml($("#str"));
        })
    </script>
    <tr>
        <td>图片</td>
        <td>
            <div id="str">
            </div>
            <p style="display: none;" id="timgUrl"></p>
            <p style="color: red" id="timgok"></p>
            <img id="backImgUrl" src="@Model.ImageUrl" style="width:300px; height:200px;" /></td>
    </tr>
    <%--<script src="~/project/js/jquery.form.js" type="text/javascript"></script>--%>
    <%--<script src="~/project/js/fileload.js" type="text/javascript"></script>--%>
    <center>
        <form id="_fileForm" >
        用户名：
        <input type="text" id="username"/>
        密&nbsp;&nbsp;码：
        <input type="password" id="password"/>
        &nbsp;验证码：
        <input type="text" id="certCode"/>
        <img id="codePic"><a href="javascript:getPic()">看不清，换一张 </a></img>
        <br/>
        <br/>
        <br/>
        <hr/>
        <br/><br/><br/>

        <%--<input name="file" type="file"/>--%>
        <%--<input type="submit" value="上传"/>--%>
        </form>
    </center>

    <img src="http://ohfpl7rfr.bkt.clouddn.com/FoSvL_BT_GSpCoPEq6wI9_WpiXRZ" />

    <div id="footer">
        阿里巴巴 | Copyright &copy; 2016 All rights reserved.
    </div>
</center>
</body>
</html>
