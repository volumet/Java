<%-- 
    Document   : show
    Created on : Oct 29, 2020, 12:44:44 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show</title>
    </head>
    <body>
        <c:set var="listUser" value="${requestScope.LIST_USER}"></c:set>

        <c:if test="${not empty listUser}">

            <table border="1">
                <thead>
                    <tr>
                        <th>UserId</th>
                        <th>Username</th>
                        <th>StatusId</th>
                        <th>RoleId</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${listUser}">
                        <tr>
                            <td>${user.userId}</td>
                            <td>${user.name}</td>
                            <td>${user.statusId}</td>
                            <td>${user.roleId}</td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>


        </c:if>
    </body>
</html>
