package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;
import cn.maiba.User;
import cn.maiba.MyDataBase;

@WebServlet("/logon/HandleUserModify")
public class HandleUserModifyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 获取用户输入的信息
        String idStr = request.getParameter("id");
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String nickname = request.getParameter("userName");
        String ageStr = request.getParameter("age");
        String email = request.getParameter("email");
        
        // 数据校验
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        
        // ID校验
        Integer id = null;
        if (idStr == null || idStr.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("ID不能为空<br>");
        } else {
            try {
                id = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                isValid = false;
                errorMessage.append("ID格式不正确<br>");
            }
        }
        
        // 账号校验
        if (account == null || account.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("账号不能为空<br>");
        }
        
        // 密码校验
        if (password == null || password.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("密码不能为空<br>");
        } else if (password.length() < 8) {
            isValid = false;
            errorMessage.append("密码长度必须8位或以上<br>");
        } else if (!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", password)) {
            isValid = false;
            errorMessage.append("密码必须包含字母和数字<br>");
        }
        
        // 确认密码校验
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("确认密码不能为空<br>");
        } else if (!password.equals(confirmPassword)) {
            isValid = false;
            errorMessage.append("两次输入的密码不一致<br>");
        }
        
        // 昵称校验
        if (nickname == null || nickname.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("昵称不能为空<br>");
        }
        
        // 年龄校验
        int age = 0;
        if (ageStr == null || ageStr.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("年龄不能为空<br>");
        } else {
            try {
                age = Integer.parseInt(ageStr);
                if (age < 0 || age > 150) {
                    isValid = false;
                    errorMessage.append("年龄必须是0-150之间的数字<br>");
                }
            } catch (NumberFormatException e) {
                isValid = false;
                errorMessage.append("年龄必须是数字<br>");
            }
        }
        
        // 电子邮件校验
        if (email == null || email.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("电子邮件不能为空<br>");
        } else if (!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", email)) {
            isValid = false;
            errorMessage.append("电子邮件格式不正确<br>");
        }
        
        if (isValid) {
            try {
                // 创建用户对象
                User user = new User();
                user.setId(id);
                user.setAccount(account);
                user.setPassword(password);
                user.setUserName(nickname);
                user.setAge(age);
                user.setEmail(email);
                
                // 更新数据库
                boolean success = MyDataBase.update(User.TABLE_NAME, user);
                
                if (success) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/logon/Success-UserModify.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "更新用户信息失败");
                    request.getRequestDispatcher("/logon/Failure-UserModify.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "数据库操作失败：" + e.getMessage());
                request.getRequestDispatcher("/logon/Failure-UserModify.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", errorMessage.toString());
            request.getRequestDispatcher("/logon/Failure-UserModify.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
