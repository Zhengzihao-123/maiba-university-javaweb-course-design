<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改用户信息</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css?v=1.0.2">
    <script type="text/javascript">
        function submitform() {
            var account = document.getElementById("account").value;
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            var userName = document.getElementById("userName").value;
            var age = document.getElementById("age").value;
            var email = document.getElementById("email").value;
            var errorMessage = "";
            
            // 账号校验
            if (account == "") {
                errorMessage += "账号不能为空\n";
            }
            
            // 密码校验
            if (password == "") {
                errorMessage += "密码不能为空\n";
            } else if (password.length < 8) {
                errorMessage += "密码长度必须8位或以上\n";
            } else if (!/[a-zA-Z]/.test(password) || !/[0-9]/.test(password)) {
                errorMessage += "密码必须包含字母和数字\n";
            }
            
            // 确认密码校验
            if (confirmPassword == "") {
                errorMessage += "确认密码不能为空\n";
            } else if (password != confirmPassword) {
                errorMessage += "两次输入的密码不一致\n";
            }
            
            // 昵称校验
            if (userName == "") {
                errorMessage += "昵称不能为空\n";
            }
            
            // 年龄校验
            if (age == "") {
                errorMessage += "年龄不能为空\n";
            } else if (isNaN(age) || age < 0 || age > 150) {
                errorMessage += "年龄必须是0-150之间的数字\n";
            }
            
            // 电子邮箱校验
            if (email == "") {
                errorMessage += "电子邮箱不能为空\n";
            } else if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
                errorMessage += "电子邮箱格式不正确\n";
            }
            
            if (errorMessage != "") {
                alert(errorMessage);
                return false;
            }
            
            return true;
        }
    </script>
</head>
<body>
    <%@ include file="/include/header.jsp" %>
    
    <div class="container">
        <h1>修改用户信息</h1>
        <form action="${pageContext.request.contextPath}/logon/HandleUserModify" method="post" onsubmit="return submitform()">
            <input type="hidden" name="id" value="${user.id}">
            <div class="form-group">
                <label for="account">账号：</label>
                <input type="text" id="account" name="account" value="${user.account}">
            </div>
            <div class="form-group">
                <label for="password">密码：</label>
                <input type="password" id="password" name="password" value="${user.password}">
            </div>
            <div class="form-group">
                <label for="confirmPassword">确认密码：</label>
                <input type="password" id="confirmPassword" name="confirmPassword" value="${user.password}">
            </div>
            <div class="form-group">
                <label for="userName">昵称：</label>
                <input type="text" id="userName" name="userName" value="${user.userName}">
            </div>
            <div class="form-group">
                <label for="age">年龄：</label>
                <input type="text" id="age" name="age" value="${user.age}">
            </div>
            <div class="form-group">
                <label for="email">电子邮箱：</label>
                <input type="text" id="email" name="email" value="${user.email}">
            </div>
            <p style="text-align: center;">
                <input type="submit" value="修改">
                <input type="button" value="取消" onclick="window.location.href='${pageContext.request.contextPath}/logon/UserDetail?userId=${user.id}'">
            </p>
        </form>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
