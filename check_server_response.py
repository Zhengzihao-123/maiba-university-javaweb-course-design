import urllib.request

# 获取服务器返回的内容
r = urllib.request.urlopen('http://localhost:8084/maiba/logon/ArticleList')
content_bytes = r.read()

# 检查是否包含 header 的关键元素
content = content_bytes.decode('utf-8', errors='replace')

print('=== 服务器返回内容检查 ===')
print('内容长度:', len(content))
print('Has <div class="nav-items">:', '<div class="nav-items">' in content)
print('Has <div class="header">:', '<div class="header">' in content)
print('Has 麦吧:', '麦吧' in content)
print('Has 首页:', '首页' in content)

# 查找 header 的位置
header_start = content.find('<div class="header">')
if header_start > 0:
    print(f'\nHeader 开始位置: {header_start}')
    # 显示 header 部分的内容
    header_end = content.find('</div>', header_start)
    if header_end > 0:
        header_content = content[header_start:header_end+6]
        print('Header 内容长度:', len(header_content))
        # 保存到文件查看
        with open('d:/JavaWeb/Project/maiba/server_header.html', 'w', encoding='utf-8') as f:
            f.write(header_content)
        print('Header 内容已保存到 server_header.html')