import urllib.request

r = urllib.request.urlopen('http://localhost:8084/maiba/logon/ArticleList')
content = r.read().decode('utf-8')

# 查找导航栏部分
nav_start = content.find('nav-items')
if nav_start > 0:
    nav_section = content[nav_start:nav_start+1000]
    print('Navigation section:')
    print(nav_section)
    print()
    print('Has 首页:', '首页' in nav_section)
    print('Has 发帖:', '发帖' in nav_section)
    print('Has 用户列表:', '用户列表' in nav_section)
    print('Has 登录:', '登录' in nav_section)
    print('Has 注册:', '注册' in nav_section)
else:
    print('nav-items not found')