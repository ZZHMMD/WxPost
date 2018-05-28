<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>测试</title>
</head>
<body>
    <table>
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
    </table>


</body>
</html>