package cn.maiba;

/**
 * 角色常量定义
 * 用于标识用户角色等级
 */
public class RoleConstants {
    
    // 角色定义（数值越大权限越高）
    public static final int ROLE_SUPER_ADMIN = 0;  // 超级管理员：拥有全站所有权限
    public static final int ROLE_USER = 1;         // 普通用户：仅拥有个人操作权限
    public static final int ROLE_MODERATOR = 2;    // 版主：绑定单个或多个板块，拥有所属板块的帖子管理权限
    
    /**
     * 判断是否为超级管理员
     */
    public static boolean isSuperAdmin(int role) {
        return role == ROLE_SUPER_ADMIN;
    }
    
    /**
     * 判断是否为普通用户
     */
    public static boolean isNormalUser(int role) {
        return role == ROLE_USER;
    }
    
    /**
     * 判断是否为版主
     */
    public static boolean isModerator(int role) {
        return role == ROLE_MODERATOR;
    }
    
    /**
     * 判断是否为已登录用户（包括普通用户、版主、管理员）
     */
    public static boolean isLoggedIn(int role) {
        return role >= ROLE_USER;
    }
    
    /**
     * 获取角色名称
     */
    public static String getRoleName(int role) {
        switch (role) {
            case ROLE_SUPER_ADMIN:
                return "超级管理员";
            case ROLE_USER:
                return "普通用户";
            case ROLE_MODERATOR:
                return "版主";
            default:
                return "未知角色";
        }
    }
}
