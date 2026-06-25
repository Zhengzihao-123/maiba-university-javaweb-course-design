import urllib.request

r = urllib.request.urlopen('http://localhost:8084/maiba/logon/ArticleList')
content = r.read().decode('utf-8')

print('Has header div:', 'class="header"' in content)
print('Has nav-items:', 'nav-items' in content)
print('First 500 chars:')
print(repr(content[:500]))