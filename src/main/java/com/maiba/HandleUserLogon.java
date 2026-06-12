package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import cn.maiba.User;
import cn.maiba.MyDataBase;
import cn.maiba.DBOperator;

@WebServlet("/user/HandleUserLogon")
public class HandleUserLogon extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String errorMessage = "";
        boolean success = false;
        User loginUser = null;
        
        if (account == null || account.trim().equals("")) {
            errorMessage = "账号不能为空";
        } else if (password == null || password.trim().equals("")) {
            errorMessage = "密码不能为空";
        } else {
            try {
                List userList = MyDataBase.select(User.TABLE_NAME, "account", account, DBOperator.OP_EQUAL);
                if (userList == null || userList.size() == 0) {
                    errorMessage = "帐号不存在，登录失败。";
                } else {
                    User user = (User) userList.get(0);
                    if (user.getPassword().equals(password)) {
                        request.getSession().setAttribute("user", user);
                        loginUser = user;
                        success = true;
                    } else {
                        errorMessage = "密码错误，登录失败。";
                    }
                }
            } catch (Exception e) {
                errorMessage = "登录错误：" + e.getMessage();
            }
        }
        
        // 登录成功后，检查是否有需要跳转的URL
        if (success) {
            String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                // 清除session中的URL，然后重定向
                request.getSession().removeAttribute("redirectUrl");
                response.sendRedirect(redirectUrl);
                return;
            }
        }
        
        request.setAttribute("success", success);
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("loginUser", loginUser);
        
        List userList = null;
        if (success) {
            try {
                userList = MyDataBase.select(User.TABLE_NAME, "account", account, DBOperator.OP_EQUAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        request.setAttribute("userList", userList);
        
        request.getRequestDispatcher("/Handle-UserLogon.jsp").forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
