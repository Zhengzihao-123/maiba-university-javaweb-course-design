package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import cn.maiba.Message;
import cn.maiba.MyDataBase;
import cn.maiba.User;
import cn.maiba.PermissionChecker;

@WebServlet("/logon/MessageDetail")
public class MessageDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/result/user-non-logon.jsp");
            return;
        }
        
        String messageIdStr = request.getParameter("messageId");
        
        try {
            int messageId = Integer.parseInt(messageIdStr);
            Message message = (Message) MyDataBase.load(Message.TABLE_NAME, messageId);
            
            if (message != null) {
                if (!PermissionChecker.isSuperAdmin(currentUser) 
                    && !message.getReceiverId().equals(currentUser.getId())) {
                    request.setAttribute("errorMessage", "您没有权限查看这条消息");
                    request.getRequestDispatcher("/result/operation-result.jsp").forward(request, response);
                    return;
                }
                
                MyDataBase.markMessageAsRead(messageId);
                
                User sender = (User) MyDataBase.load(User.TABLE_NAME, message.getSenderId());
                request.setAttribute("message", message);
                request.setAttribute("sender", sender);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("/logon/MessageDetail.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}