<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath %>"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>测试</title>
</head>
<body>
<%-- <table>
  <th>
  <c:forEach items="${result.obj.list}" var="item">
  <tr>
       <td>${item.id}</td>
       <td>${item.orderId}</td>
       <td>${item.serviceEfficiency}</td>
       <td>${item.serviceAttitude}</td>
       <td>${item.desc}</td>
       <td>${item.enable}</td>
       <td>${item.createTime}</td>
  </tr>
  </c:forEach>
  </th>
</table> --%>

<form action="${pageContext.request.contextPath}/test/split" method="get">
    请输入：<input type="text" name="content"/>
    <input type="submit" value="提交">
</form>


</body>
</html>