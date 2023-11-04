<%--
  Created by IntelliJ IDEA.
  User: 24033
  Date: 2023/11/4
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
</head>
<body>
<h1>欢迎的登录:亲爱的${username}</h1>
<p><a href="${pageContext.request.contextPath}/user/goOut">退出登录</a></p>
</body>
</html>
