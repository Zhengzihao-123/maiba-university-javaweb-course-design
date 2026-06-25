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

@WebServlet("/logon/HandleMessageNew")
public class HandleMessageNew extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User sender = (User) request.getSession().getAttribute("user");
        if (sender == null) {
            response.sendRedirect(request.getContextPath() + "/logon/ArticleList");
            return;
        }
        
        String receiverIdStr = request.getParameter("receiverId");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        if (receiverIdStr != null && !receiverIdStr.isEmpty() 
            && title != null && !title.trim().isEmpty() 
            && content != null && !content.trim().isEmpty()) {
            
            try {
                int receiverId = Integer.parseInt(receiverIdStr);
                
                Message message = new Message();
                message.setSenderId(sender.getId());
                message.setReceiverId(receiverId);
                message.setTitle(title.trim());
                message.setContent(content.trim());
                message.setIsRead(0);
                
                MyDataBase.save(Message.TABLE_NAME, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/logon/MessageList");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}