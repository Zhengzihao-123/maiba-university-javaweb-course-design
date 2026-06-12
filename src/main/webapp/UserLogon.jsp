<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script type="text/javascript">
        function validateForm() {
            var account = document.getElementById("account").value;
            var password = document.getElementById("password").value;
            var errorMessage = "";
            
            // 账号校验
            if (account === "") {
                errorMessage += "账号不能为空\n";
            }
            
            // 密码校验
            if (password === "") {
                errorMessage += "密码不能为空\n";
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
        <h1>用户登录</h1>
        <form action="${pageContext.request.contextPath}/user/HandleUserLogon" method="post" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="account">账号：</label>
                <input type="text" id="account" name="account">
                <span style="color: red;">*</span>
            </div>
            <div class="form-group">
                <label for="password">密码：</label>
                <input type="password" id="password" name="password">
                <span style="color: red;">*</span>
            </div>
            <button type="submit">登录</button>
        </form>
        <p style="text-align: center; margin-top: 15px;">
            还没有账号？<a href="UserRegister.jsp">去注册</a>
        </p>
    </div>
    
    <%@ include file="/include/footer.jsp" %>
</body>
</html>
