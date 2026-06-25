package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.maiba.Message;
import cn.maiba.MyDataBase;
import cn.maiba.User;

@WebServlet("/logon/MessageList")
public class MessageListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/logon/ArticleList");
            return;
        }
        
        List messageList = null;
        Map<Integer, String> senderNames = new HashMap<>();
        try {
            messageList = MyDataBase.select(Message.TABLE_NAME, "receiver_id", user.getId(), "=");
            for (Object obj : messageList) {
                Message msg = (Message) obj;
                if (!senderNames.containsKey(msg.getSenderId())) {
                    User sender = (User) MyDataBase.load(User.TABLE_NAME, msg.getSenderId());
                    senderNames.put(msg.getSenderId(), sender != null ? sender.getUserName() : "未知用户");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("messageList", messageList);
        request.setAttribute("senderNames", senderNames);
        
        request.getRequestDispatcher("/logon/MessageList.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
