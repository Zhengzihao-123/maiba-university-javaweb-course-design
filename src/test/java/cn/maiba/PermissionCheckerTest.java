package cn.maiba;

import org.junit.Test;
import static org.junit.Assert.*;

public class PermissionCheckerTest {

    private User createUser(int role) {
        User user = new User();
        user.setId(1);
        user.setRole(role);
        return user;
    }

    private Article createArticle(Integer userId, Integer boardId) {
        Article article = new Article();
        article.setId(1);
        article.setUserId(userId);
        article.setBoardId(boardId);
        return article;
    }

    @Test
    public void testIsLoggedIn() {
        User loggedInUser = createUser(RoleConstants.ROLE_USER);
        assertTrue("已登录用户应返回true", PermissionChecker.isLoggedIn(loggedInUser));
        assertTrue("版主用户应返回true", PermissionChecker.isLoggedIn(createUser(RoleConstants.ROLE_MODERATOR)));
        assertTrue("管理员用户应返回true", PermissionChecker.isLoggedIn(createUser(RoleConstants.ROLE_SUPER_ADMIN)));
        assertFalse("未登录用户应返回false", PermissionChecker.isLoggedIn(null));
    }

    @Test
    public void testIsSuperAdmin() {
        User admin = createUser(RoleConstants.ROLE_SUPER_ADMIN);
        assertTrue("管理员应返回true", PermissionChecker.isSuperAdmin(admin));
        
        User normalUser = createUser(RoleConstants.ROLE_USER);
        assertFalse("普通用户应返回false", PermissionChecker.isSuperAdmin(normalUser));
        
        User moderator = createUser(RoleConstants.ROLE_MODERATOR);
        assertFalse("版主应返回false", PermissionChecker.isSuperAdmin(moderator));
        
        assertFalse("null用户应返回false", PermissionChecker.isSuperAdmin(null));
    }

    @Test
    public void testIsNormalUser() {
        User normalUser = createUser(RoleConstants.ROLE_USER);
        assertTrue("普通用户应返回true", PermissionChecker.isNormalUser(normalUser));
        
        User admin = createUser(RoleConstants.ROLE_SUPER_ADMIN);
        assertFalse("管理员应返回false", PermissionChecker.isNormalUser(admin));
        
        User moderator = createUser(RoleConstants.ROLE_MODERATOR);
        assertFalse("版主应返回false", PermissionChecker.isNormalUser(moderator));
        
        assertFalse("null用户应返回false", PermissionChecker.isNormalUser(null));
    }

    @Test
    public void testIsModerator() {
        User moderator = createUser(RoleConstants.ROLE_MODERATOR);
        assertTrue("版主应返回true", PermissionChecker.isModerator(moderator));
        
        User admin = createUser(RoleConstants.ROLE_SUPER_ADMIN);
        assertFalse("管理员应返回false", PermissionChecker.isModerator(admin));
        
        User normalUser = createUser(RoleConstants.ROLE_USER);
        assertFalse("普通用户应返回false", PermissionChecker.isModerator(normalUser));
        
        assertFalse("null用户应返回false", PermissionChecker.isModerator(null));
    }

    @Test
    public void testHasManagementRole() {
        User admin = createUser(RoleConstants.ROLE_SUPER_ADMIN);
        assertTrue("管理员应返回true", PermissionChecker.hasManagementRole(admin));
        
        User moderator = createUser(RoleConstants.ROLE_MODERATOR);
        assertTrue("版主应返回true", PermissionChecker.hasManagementRole(moderator));
        
        User normalUser = createUser(RoleConstants.ROLE_USER);
        assertFalse("普通用户应返回false", PermissionChecker.hasManagementRole(normalUser));
        
        assertFalse("null用户应返回false", PermissionChecker.hasManagementRole(null));
    }

    @Test
    public void testCanManageBoard() {
        User admin = createUser(RoleConstants.ROLE_SUPER_ADMIN);
        assertTrue("管理员可以管理任何板块", PermissionChecker.canManageBoard(admin, 1));
        
        assertFalse("null用户不能管理板块", PermissionChecker.canManageBoard(null, 1));
        assertFalse("用户不能管理null板块", PermissionChecker.canManageBoard(admin, null));
    }

    @Test
    public void testCanModifyArticle() {
        User admin = createUser(RoleConstants.ROLE_SUPER_ADMIN);
        User normalUser = createUser(RoleConstants.ROLE_USER);
        User moderator = createUser(RoleConstants.ROLE_MODERATOR);
        
        Article ownArticle = createArticle(1, 1);
        Article otherArticle = createArticle(2, 1);
        
        assertTrue("管理员可以修改任何帖子", PermissionChecker.canModifyArticle(admin, ownArticle));
        assertTrue("管理员可以修改他人帖子", PermissionChecker.canModifyArticle(admin, otherArticle));
        
        assertTrue("普通用户可以修改自己的帖子", PermissionChecker.canModifyArticle(normalUser, ownArticle));
        assertFalse("普通用户不能修改他人帖子", PermissionChecker.canModifyArticle(normalUser, otherArticle));
        
        assertTrue("版主可以修改自己的帖子", PermissionChecker.canModifyArticle(moderator, ownArticle));
        
        assertFalse("null用户不能修改帖子", PermissionChecker.canModifyArticle(null, ownArticle));
        assertFalse("用户不能修改null帖子", PermissionChecker.canModifyArticle(admin, null));
    }

    @Test
    public void testCanDeleteArticle() {
        User admin = createUser(RoleConstants.ROLE_SUPER_ADMIN);
        User normalUser = createUser(RoleConstants.ROLE_USER);
        User moderator = createUser(RoleConstants.ROLE_MODERATOR);
        
        Article ownArticle = createArticle(1, 1);
        Article otherArticle = createArticle(2, 1);
        
        assertTrue("管理员可以删除任何帖子", PermissionChecker.canDeleteArticle(admin, ownArticle));
        assertTrue("管理员可以删除他人帖子", PermissionChecker.canDeleteArticle(admin, otherArticle));
        
        assertTrue("普通用户可以删除自己的帖子", PermissionChecker.canDeleteArticle(normalUser, ownArticle));
        assertFalse("普通用户不能删除他人帖子", PermissionChecker.canDeleteArticle(normalUser, otherArticle));
        
        assertTrue("版主可以删除自己的帖子", PermissionChecker.canDeleteArticle(moderator, ownArticle));
        
        assertFalse("null用户不能删除帖子", PermissionChecker.canDeleteArticle(null, ownArticle));
        assertFalse("用户不能删除null帖子", PermissionChecker.canDeleteArticle(admin, null));
    }

    @Test
    public void testCanManageUser() {
        User admin = createUser(RoleConstants.ROLE_SUPER_ADMIN);
        User normalUser = createUser(RoleConstants.ROLE_USER);
        
        User targetSelf = new User();
        targetSelf.setId(1);
        
        User targetOther = new User();
        targetOther.setId(2);
        
        assertTrue("管理员可以管理任何用户", PermissionChecker.canManageUser(admin, targetOther));
        assertTrue("管理员可以管理自己", PermissionChecker.canManageUser(admin, targetSelf));
        
        assertTrue("普通用户可以管理自己", PermissionChecker.canManageUser(normalUser, targetSelf));
        assertFalse("普通用户不能管理他人", PermissionChecker.canManageUser(normalUser, targetOther));
        
        assertFalse("null用户不能管理他人", PermissionChecker.canManageUser(null, targetSelf));
        assertFalse("用户不能管理null目标", PermissionChecker.canManageUser(admin, null));
    }

    @Test
    public void testCanDeleteUser() {
        User admin = createUser(RoleConstants.ROLE_SUPER_ADMIN);
        User normalUser = createUser(RoleConstants.ROLE_USER);
        
        User targetSelf = new User();
        targetSelf.setId(1);
        
        User targetOther = new User();
        targetOther.setId(2);
        
        assertTrue("管理员可以删除其他用户", PermissionChecker.canDeleteUser(admin, targetOther));
        assertFalse("管理员不能删除自己", PermissionChecker.canDeleteUser(admin, targetSelf));
        
        assertFalse("普通用户不能删除任何用户", PermissionChecker.canDeleteUser(normalUser, targetOther));
        assertFalse("普通用户不能删除自己", PermissionChecker.canDeleteUser(normalUser, targetSelf));
        
        assertFalse("null用户不能删除他人", PermissionChecker.canDeleteUser(null, targetOther));
        assertFalse("用户不能删除null目标", PermissionChecker.canDeleteUser(admin, null));
    }

    @Test
    public void testCanManageBoardInfo() {
        User admin = createUser(RoleConstants.ROLE_SUPER_ADMIN);
        User normalUser = createUser(RoleConstants.ROLE_USER);
        User moderator = createUser(RoleConstants.ROLE_MODERATOR);
        
        assertTrue("管理员可以管理板块", PermissionChecker.canManageBoardInfo(admin));
        assertFalse("普通用户不能管理板块", PermissionChecker.canManageBoardInfo(normalUser));
        assertFalse("版主不能管理板块", PermissionChecker.canManageBoardInfo(moderator));
        assertFalse("null用户不能管理板块", PermissionChecker.canManageBoardInfo(null));
    }

    @Test
    public void testCanPostArticle() {
        User loggedInUser = createUser(RoleConstants.ROLE_USER);
        assertTrue("已登录用户可以发帖", PermissionChecker.canPostArticle(loggedInUser));
        assertTrue("版主可以发帖", PermissionChecker.canPostArticle(createUser(RoleConstants.ROLE_MODERATOR)));
        assertTrue("管理员可以发帖", PermissionChecker.canPostArticle(createUser(RoleConstants.ROLE_SUPER_ADMIN)));
        assertFalse("未登录用户不能发帖", PermissionChecker.canPostArticle(null));
    }

    @Test
    public void testCanPostComment() {
        User loggedInUser = createUser(RoleConstants.ROLE_USER);
        assertTrue("已登录用户可以评论", PermissionChecker.canPostComment(loggedInUser));
        assertTrue("版主可以评论", PermissionChecker.canPostComment(createUser(RoleConstants.ROLE_MODERATOR)));
        assertTrue("管理员可以评论", PermissionChecker.canPostComment(createUser(RoleConstants.ROLE_SUPER_ADMIN)));
        assertFalse("未登录用户不能评论", PermissionChecker.canPostComment(null));
    }
}