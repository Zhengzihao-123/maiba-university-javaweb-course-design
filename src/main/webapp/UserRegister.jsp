<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type="text/javascript">
        function validateForm() {
            var username = document.getElementById("userName").value;
            var password = document.getElementById("password").value;
            var confirmPassword = document.getElementById("confirmPassword").value;
            var nickname = document.getElementById("nickname").value;
            var age = document.getElementById("age").value;
            var email = document.getElementById("email").value;
            var errorMessage = "";
            
            // 账号校验
            if (username === "") {
                errorMessage += "账号不能为空\n";
            }
            
            // 密码校验
            if (password === "") {
                errorMessage += "密码不能为空\n";
            } else if (password.length < 6) {
                errorMessage += "密码长度需要6位或以上\n";
            }
            
            // 确认密码校验
            if (confirmPassword === "") {
                errorMessage += "确认密码不能为空\n";
            } else if (password !== confirmPassword) {
                errorMessage += "两次输入的密码不一致\n";
            }
            
            // 昵称校验
            if (nickname === "") {
                errorMessage += "昵称不能为空\n";
            }
            
            // 年龄校验
            if (age === "") {
                errorMessage += "年龄不能为空\n";
            } else if (isNaN(age) || age < 0 || age > 150) {
                errorMessage += "年龄必须是0-150之间的数字\n";
            }
            
            // 电子邮件校验
            if (email === "") {
                errorMessage += "电子邮件不能为空\n";
            } else if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
                errorMessage += "电子邮件格式不正确\n";
            }
            
            if (errorMessage !== "") {
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
        <h1>用户注册</h1>
        <form action="${pageContext.request.contextPath}/HandleUserRegister" method="post" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="userName">账号：</label>
                <input type="text" id="userName" name="userName">
                <span style="color: red;">*</span>
            </div>
            <div class="form-group">
                <label for="password">密码：</label>
                <input type="password" id="password" name="password">
                <span style="color: red;">*</span>
            </div>
            <div class="form-group">
                <label for="confirmPassword">确认密码：</label>
                <input type="password" id="confirmPassword" name="confirmPassword">
                <span style="color: red;">*</span>
            </div>
            <div class="form-group">
                <label for="nickname">昵称：</label>
                <input type="text" id="nickname" name="nickname">
                <span style="color: red;">*</span>
            </div>
            <div class="form-group">
                <label for="age">年龄：</label>
                <input type="text" id="age" name="age">
                <span style="color: red;">*</span>
            </div>
            <div class="form-group">
                <label for="email">电子邮件：</label>
                <input type="text" id="email" name="email">
                <span style="color: red;">*</span>
            </div>
            <button type="submit">注册</button>
        </form>
        <p style="text-align: center; margin-top: 15px;">
            已有账号？<a href="UserLogon.jsp">去登录</a>
        </p>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>