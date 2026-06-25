package com.maiba;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import cn.maiba.Article;
import cn.maiba.Board;
import cn.maiba.MyDataBase;

@WebServlet("/logon/ArticleList")
public class ArticleListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        String keyword = request.getParameter("keyword");
        String boardIdStr = request.getParameter("boardId");
        
        Integer boardId = null;
        if (boardIdStr != null && !boardIdStr.trim().isEmpty()) {
            try {
                boardId = Integer.parseInt(boardIdStr);
            } catch (NumberFormatException e) {
                boardId = null;
            }
        }
        
        List articleList;
        
        if (boardId != null && keyword != null && !keyword.trim().isEmpty()) {
            articleList = MyDataBase.searchArticlesByBoard(boardId, keyword.trim());
        } else if (boardId != null) {
            try {
                articleList = MyDataBase.select(Article.TABLE_NAME, "board_id", boardId, "=");
            } catch (Exception e) {
                articleList = null;
            }
        } else if (keyword != null && !keyword.trim().isEmpty()) {
            articleList = MyDataBase.searchArticles(keyword.trim());
        } else {
            articleList = MyDataBase.list(Article.TABLE_NAME);
        }
        
        MyDataBase.initBoardTable();
        
        List boardList = MyDataBase.list(Board.TABLE_NAME);
        
        if (boardList == null || boardList.isEmpty()) {
            Board defaultBoard = new Board();
            defaultBoard.setId(1);
            defaultBoard.setName("综合讨论");
            defaultBoard.setDescription("全站综合讨论区，畅所欲言");
            defaultBoard.setSortOrder(0);
            MyDataBase.save(Board.TABLE_NAME, defaultBoard);
            
            Board board2 = new Board();
            board2.setName("学习交流");
            board2.setDescription("学习资料分享、问题讨论");
            board2.setSortOrder(10);
            MyDataBase.save(Board.TABLE_NAME, board2);
            
            Board board3 = new Board();
            board3.setName("生活分享");
            board3.setDescription("记录生活点滴、分享日常");
            board3.setSortOrder(20);
            MyDataBase.save(Board.TABLE_NAME, board3);
            
            Board board4 = new Board();
            board4.setName("体育竞技");
            board4.setDescription("体育赛事讨论、运动健身");
            board4.setSortOrder(30);
            MyDataBase.save(Board.TABLE_NAME, board4);
            
            boardList = MyDataBase.list(Board.TABLE_NAME);
        }
        
        request.setAttribute("articleList", articleList);
        request.setAttribute("keyword", keyword);
        request.setAttribute("boardId", boardId);
        request.setAttribute("boardList", boardList);
        
        request.getRequestDispatcher("/logon/ArticleList.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
