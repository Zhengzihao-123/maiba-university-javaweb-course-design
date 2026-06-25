package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import cn.maiba.User;
import cn.maiba.MyDataBase;
import cn.maiba.DBOperator;

@WebServlet("/user/HandleUserLogon")
public class HandleUserLogon extends HttpServlet {
    
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_DURATION_MINUTES = 15;
    
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
                    
                    Timestamp lockedTime = user.getLockedTime();
                    if (lockedTime != null) {
                        long lockEndTime = lockedTime.getTime() + LOCK_DURATION_MINUTES * 60 * 1000;
                        if (System.currentTimeMillis() < lockEndTime) {
                            long remainingMinutes = (lockEndTime - System.currentTimeMillis()) / (60 * 1000);
                            errorMessage = "账号已被锁定，请" + remainingMinutes + "分钟后再试。";
                        } else {
                            MyDataBase.updateUserUnlock(User.TABLE_NAME, user.getId());
                            user.setLockedTime(null);
                            user.setFailedAttempts(0);
                        }
                    }
                    
                    if (errorMessage.isEmpty()) {
                        if (user.getPassword().equals(password)) {
                            MyDataBase.updateUserFailedAttempts(User.TABLE_NAME, user.getId(), 0);
                            request.getSession().setAttribute("user", user);
                            OnlineUserListener.registerOnlineUser(request.getSession(), user);
                            loginUser = user;
                            success = true;
                        } else {
                            int newFailedAttempts = user.getFailedAttempts() + 1;
                            MyDataBase.updateUserFailedAttempts(User.TABLE_NAME, user.getId(), newFailedAttempts);
                            
                            if (newFailedAttempts >= MAX_FAILED_ATTEMPTS) {
                                MyDataBase.updateUserLockedTime(User.TABLE_NAME, user.getId(), new Timestamp(System.currentTimeMillis()));
                                errorMessage = "登录失败次数过多，账号已被锁定15分钟。";
                            } else {
                                int remainingAttempts = MAX_FAILED_ATTEMPTS - newFailedAttempts;
                                errorMessage = "密码错误，登录失败。还剩" + remainingAttempts + "次尝试机会。";
                            }
                        }
                    }
                }
            } catch (Exception e) {
                errorMessage = "登录错误：" + e.getMessage();
            }
        }
        
        if (success) {
            String redirectUrl = (String) request.getSession().getAttribute("redirectUrl");
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                request.getSession().removeAttribute("redirectUrl");
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect(request.getContextPath() + "/logon/ArticleList");
            }
            return;
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