import urllib.request

r = urllib.request.urlopen('http://localhost:8084/maiba/logon/ArticleList')
content = r.read().decode('utf-8')

# 查找完整的导航栏HTML部分
div_start = content.find('<div class="nav-items">')
if div_start > 0:
    div_end = content.find('</div>', div_start)
    if div_end > 0:
        nav_html = content[div_start:div_end+6]
        print('Navigation HTML:')
        print(nav_html)
    else:
        print('</div> not found after nav-items')
else:
    print('<div class="nav-items"> not found')