<%@ page import="com.jd.account.Account" %>
<%@ page import="com.jd.entity.user.User" %>
<html>
<body>
<center>
<h2>Hello World!</h2>
<%
 User account = (User) session.getAttribute(Account.SESSION_KEY);
%>
 HI , <%= account.getUserName() %>
</center>
</body>
</html>
