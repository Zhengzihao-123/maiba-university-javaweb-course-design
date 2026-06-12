package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;
import cn.maiba.User;
import cn.maiba.MyDataBase;
import cn.maiba.DBOperator;

@WebServlet("/HandleUserRegister")
public class HandleUserRegister extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 获取用户输入的注册信息
        String account = request.getParameter("userName");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String ageStr = request.getParameter("age");
        String email = request.getParameter("email");
        
        // 数据校验
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        
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
        
        // 昵称校验
        if (nickname == null || nickname.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("昵称不能为空<br>");
        }
        
        // 年龄校验
        if (ageStr == null || ageStr.trim().isEmpty()) {
            isValid = false;
            errorMessage.append("年龄不能为空<br>");
        } else {
            try {
                int age = Integer.parseInt(ageStr);
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
        
        // 设置属性用于EL表达式
        request.setAttribute("account", account);
        request.setAttribute("nickname", nickname);
        request.setAttribute("email", email);
        
        // 如果数据校验通过，进行账号查重
        if (isValid) {
            try {
                // 查询数据库中是否已存在该账号
                List userList = MyDataBase.select(User.TABLE_NAME, "account", account, DBOperator.OP_EQUAL);
                
                if (userList == null || userList.size() == 0) {
                    // 账号未被注册，创建用户对象并保存到数据库
                    User user = new User();
                    user.setAccount(account);
                    user.setPassword(password);
                    user.setUserName(nickname);
                    user.setAge(Integer.parseInt(ageStr));
                    user.setEmail(email);
                    
                    // 保存到数据库
                    MyDataBase.save(User.TABLE_NAME, user);
                    
                    request.setAttribute("user", user);
                    request.setAttribute("success", true);
                    request.getRequestDispatcher("Success-UserRegister.jsp").forward(request, response);
                } else {
                    request.setAttribute("success", false);
                    request.setAttribute("errorMessage", "账号已存在");
                    request.getRequestDispatcher("Failure-UserRegister.jsp").forward(request, response);
                }
            } catch (Exception e) {
                request.setAttribute("success", false);
                request.setAttribute("errorMessage", "数据库操作失败：" + e.getMessage());
                request.getRequestDispatcher("Failure-UserRegister.jsp").forward(request, response);
            }
        } else {
            // 数据校验失败，显示错误信息
            request.setAttribute("success", false);
            request.setAttribute("errorMessage", errorMessage.toString());
            request.getRequestDispatcher("Failure-UserRegister.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}