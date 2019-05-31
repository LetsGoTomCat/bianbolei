<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/29
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
    <script src="boot/js/jquery-2.2.1.min.js"></script>
    <script src="boot/js/bootstrap.js"></script>
<%--goeasy--%>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js​"></script>
</head>
<body>


<script>
    var goEasy = new GoEasy({
        appkey: "BS-fc8bfbc77ebf47199876061cef12b256"
    });
    goEasy.subscribe({
        channel: "talk1_channel",
        onMessage: function (message) {
            $("#requset").text(message.content)
        }
    });


    var goEasy1 = new GoEasy({
        appkey: "BC-748b758122044aaa9c8f14c5ff9a8a5e"
    });
    function sendmassage() {
        var value=$("#requset").val()
        goEasy1.publish({
            channel: "talk_channel",
            message: value
        });
    }
</script>

<textarea type="text" id="requset"></textarea><br/>
    <input type="button" id="submit" onclick="sendmassage()" value="发送">
</body>
</html>
