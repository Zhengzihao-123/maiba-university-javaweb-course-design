import urllib.request

# 获取服务器返回的内容
r = urllib.request.urlopen('http://localhost:8084/maiba/logon/ArticleList')
content_bytes = r.read()

# 查找非法字节
print('Searching for invalid bytes...')
for i in range(len(content_bytes)):
    byte = content_bytes[i]
    # 检查是否是有效的UTF-8字节
    if byte == 0xA6:
        print(f'Found 0xA6 at position {i}')
        # 显示周围的字节
        start = max(0, i - 20)
        end = min(len(content_bytes), i + 30)
        print(f'Context bytes: {content_bytes[start:end].hex()}')
        break

# 尝试解码
try:
    content = content_bytes.decode('utf-8')
    print('\nSuccessfully decoded as UTF-8')
except Exception as e:
    print(f'\nDecode error: {e}')