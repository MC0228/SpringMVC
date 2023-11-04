<%--
  Created by IntelliJ IDEA.
  User: 24033
  Date: 2023/11/4
  Time: 11:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
    <script type="text/javascript">
        function check() {
            let name = document.getElementById("name").value;
            let file = document.getElementById("file").value;
            if (name == "") {
                alert("请填写上传为");
                return false;
            }
            if (file.length == 0 || file == "") {
                alert("请选择上传的文件")
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<form action="${pageContext.request.contextPath}/file/upload"
      method="post" enctype="multipart/form-data" onsubmit="return check()">
    上传人：<input id="name" type="text" name="name"><br>
    <%--多文件上传 multiple="multiple--%>
    请选择文件: <input id="file" type="file" name="files" multiple="multiple"><br>
    <input type="submit" value="上传">
</form>
</body>
</html>
