<%@ page import="com.jd.common.security.Account" %>
<html>
<body>
<center>
<h2>Hello World!</h2>
<%
 Account account = (Account) session.getAttribute(Account.SESSION_KEY);
%>
 HI , <%= account.getUsername() %>
</center>
</body>
</html>
