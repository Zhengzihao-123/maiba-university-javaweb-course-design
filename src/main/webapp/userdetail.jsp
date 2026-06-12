<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户详情</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h1>用户详情</h1>
        <table border="1" cellpadding="10" cellspacing="0" style="width: 60%; margin: 20px auto;">
            <tr>
                <td style="background-color: #f0f0f0; font-weight: bold; width: 30%;">ID</td>
                <td>${user.id}</td>
            </tr>
            <tr>
                <td style="background-color: #f0f0f0; font-weight: bold;">账号</td>
                <td>${user.account}</td>
            </tr>
            <tr>
                <td style="background-color: #f0f0f0; font-weight: bold;">密码</td>
                <td>${user.password}</td>
            </tr>
            <tr>
                <td style="background-color: #f0f0f0; font-weight: bold;">昵称</td>
                <td>${user.userName}</td>
            </tr>
            <tr>
                <td style="background-color: #f0f0f0; font-weight: bold;">年龄</td>
                <td>${user.age}</td>
            </tr>
            <tr>
                <td style="background-color: #f0f0f0; font-weight: bold;">邮箱</td>
                <td>${user.email}</td>
            </tr>
        </table>
        
        <p style="text-align: center; margin-top: 20px;">
            <input type="button" value="修改" onclick="handle_modify()" style="padding: 8px 20px; margin-right: 10px; cursor: pointer;">
            <input type="button" value="删除" onclick="handle_delete()" style="padding: 8px 20px; margin-right: 10px; cursor: pointer;">
            <input type="button" value="返回" onclick="handle_list_user()" style="padding: 8px 20px; cursor: pointer;">
        </p>
    </div>
    
    <script type="text/javascript">
        function handle_modify(){
            window.location.href="${pageContext.request.contextPath}/UserModify?userId=${user.id}";
        }
        function handle_delete(){
            window.location.href="${pageContext.request.contextPath}/HandleUserDelete?userId=${user.id}";
        }
        function handle_list_user(){
            window.location.href="${pageContext.request.contextPath}/UserList";
        }
    </script>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
