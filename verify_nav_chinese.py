import urllib.request

# 获取页面内容
r = urllib.request.urlopen('http://localhost:8084/maiba/logon/ArticleList')
content = r.read().decode('utf-8')

# 检查导航栏中文
print('=== 导航栏中文内容检查 ===')
print('首页:', '首页' in content)
print('发帖:', '发帖' in content)
print('用户列表:', '用户列表' in content)
print('登录:', '登录' in content)
print('注册:', '注册' in content)
print('麦吧:', '麦吧' in content)
print('分享生活:', '分享生活' in content)

# 查找导航栏部分
nav_start = content.find('<div class="nav-items">')
if nav_start > 0:
    nav_end = content.find('</div>', nav_start)
    if nav_end > 0:
        nav_html = content[nav_start:nav_end+6]
        print('\n=== 导航栏HTML内容 ===')
        print(nav_html.encode('utf-8').decode('utf-8'))