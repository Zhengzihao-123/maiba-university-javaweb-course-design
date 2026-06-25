import urllib.request

pages = ['/logon/ArticleList', '/logon/MyRemarkList', '/logon/MyArticleList', '/logon/UserList']

for page in pages:
    try:
        r = urllib.request.urlopen(f'http://localhost:8084/maiba{page}')
        content = r.read().decode('utf-8')
        has_maiba = '麦吧' in content
        print(f'{page}: Status {r.status}, has header: {has_maiba}')
    except Exception as e:
        print(f'{page}: Error - {e}')