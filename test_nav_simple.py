import urllib.request

r = urllib.request.urlopen('http://localhost:8084/maiba/logon/ArticleList')
content = r.read().decode('utf-8')

print('=== 导航栏中文检查 ===')
print('首页:', '首页' in content)
print('发帖:', '发帖' in content)
print('用户列表:', '用户列表' in content)
print('登录:', '登录' in content)
print('注册:', '注册' in content)
print('麦吧:', '麦吧' in content)
print('分享生活:', '分享生活' in content)
print('我的帖子:', '我的帖子' in content)
print('我的评论:', '我的评论' in content)