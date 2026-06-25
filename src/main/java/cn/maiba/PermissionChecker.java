package cn.maiba;

import java.util.List;

/**
 * 权限校验工具类
 * 统一处理所有权限相关的校验逻辑
 */
public class PermissionChecker {
    
    /**
     * 判断用户是否已登录
     */
    public static boolean isLoggedIn(User user) {
        return user != null;
    }
    
    /**
     * 判断是否为超级管理员
     */
    public static boolean isSuperAdmin(User user) {
        return user != null && user.getRole() == RoleConstants.ROLE_SUPER_ADMIN;
    }
    
    /**
     * 判断是否为普通用户
     */
    public static boolean isNormalUser(User user) {
        return user != null && user.getRole() == RoleConstants.ROLE_USER;
    }
    
    /**
     * 判断是否为版主
     */
    public static boolean isModerator(User user) {
        return user != null && user.getRole() == RoleConstants.ROLE_MODERATOR;
    }
    
    /**
     * 判断用户是否拥有任意管理权限（版主或管理员）
     */
    public static boolean hasManagementRole(User user) {
        return user != null && (user.getRole() == RoleConstants.ROLE_SUPER_ADMIN 
                || user.getRole() == RoleConstants.ROLE_MODERATOR);
    }
    
    /**
     * 判断用户是否为某板块的版主
     */
    public static boolean isModeratorOfBoard(User user, Integer boardId) {
        if (user == null || boardId == null) {
            return false;
        }
        if (user.getRole() != RoleConstants.ROLE_MODERATOR) {
            return false;
        }
        
        // 查询用户是否为该板块的版主
        try {
            List boardList = MyDataBase.select(Board.TABLE_NAME, "moderator_id", user.getId(), DBOperator.OP_EQUAL);
            if (boardList != null) {
                for (Object obj : boardList) {
                    Board board = (Board) obj;
                    if (board.getId().equals(boardId)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 判断用户是否为某板块的版主（包括管理员）
     * 管理员可以管理所有板块
     */
    public static boolean canManageBoard(User user, Integer boardId) {
        if (user == null || boardId == null) {
            return false;
        }
        // 超级管理员可以管理所有板块
        if (isSuperAdmin(user)) {
            return true;
        }
        // 版主只能管理自己关联的板块
        return isModeratorOfBoard(user, boardId);
    }
    
    /**
     * 判断是否可以修改帖子
     * - 超级管理员：可修改任何帖子
     * - 版主：可修改自己发的帖子，或本版所有帖子
     * - 普通用户：只能修改自己的帖子
     */
    public static boolean canModifyArticle(User user, Article article) {
        if (user == null || article == null) {
            return false;
        }
        
        // 超级管理员可修改任何帖子
        if (isSuperAdmin(user)) {
            return true;
        }
        
        // 普通用户和版主只能修改自己的帖子
        if (article.getUserId().equals(user.getId())) {
            return true;
        }
        
        // 版主可以修改本版的帖子
        if (isModerator(user) && article.getBoardId() != null) {
            return isModeratorOfBoard(user, article.getBoardId());
        }
        
        return false;
    }
    
    /**
     * 判断是否可以删除帖子
     * - 超级管理员：可删除任何帖子
     * - 版主：可删除自己发的帖子，或本版所有帖子
     * - 普通用户：只能删除自己的帖子
     */
    public static boolean canDeleteArticle(User user, Article article) {
        if (user == null || article == null) {
            return false;
        }
        
        // 超级管理员可删除任何帖子
        if (isSuperAdmin(user)) {
            return true;
        }
        
        // 普通用户只能删除自己的帖子
        if (user.getRole() == RoleConstants.ROLE_USER) {
            return article.getUserId().equals(user.getId());
        }
        
        // 版主可以删除自己发的帖子
        if (isModerator(user) && article.getUserId().equals(user.getId())) {
            return true;
        }
        
        // 版主可以删除本版的帖子
        if (isModerator(user) && article.getBoardId() != null) {
            return isModeratorOfBoard(user, article.getBoardId());
        }
        
        return false;
    }
    
    /**
     * 判断是否可以管理用户（查看、修改、删除）
     * - 超级管理员：可管理所有用户
     * - 其他角色：只能管理自己的信息
     */
    public static boolean canManageUser(User currentUser, User targetUser) {
        if (currentUser == null || targetUser == null) {
            return false;
        }
        
        // 超级管理员可管理所有用户
        if (isSuperAdmin(currentUser)) {
            return true;
        }
        
        // 其他角色只能管理自己的信息
        return currentUser.getId().equals(targetUser.getId());
    }
    
    /**
     * 判断是否可以删除用户
     * 只有超级管理员可以删除用户，且不能删除自己
     */
    public static boolean canDeleteUser(User currentUser, User targetUser) {
        if (currentUser == null || targetUser == null) {
            return false;
        }
        
        // 超级管理员可以删除其他用户
        if (isSuperAdmin(currentUser) && !currentUser.getId().equals(targetUser.getId())) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 判断是否可以管理板块
     * 只有超级管理员可以管理板块信息
     */
    public static boolean canManageBoardInfo(User user) {
        return isSuperAdmin(user);
    }
    
    /**
     * 判断是否可以发表帖子
     * 已登录用户都可以发帖
     */
    public static boolean canPostArticle(User user) {
        return isLoggedIn(user);
    }
    
    /**
     * 判断是否可以发表评论
     * 已登录用户都可以评论
     */
    public static boolean canPostComment(User user) {
        return isLoggedIn(user);
    }
}
