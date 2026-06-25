import urllib.request

# 获取页面内容
r = urllib.request.urlopen('http://localhost:8084/maiba/logon/ArticleList')
content_bytes = r.read()

print('Content length (bytes):', len(content_bytes))
print()

# 检查是否有非法字节
print('=== Checking for problematic bytes ===')
for i in range(len(content_bytes)):
    byte = content_bytes[i]
    # 检查是否是有效的UTF-8字节
    if byte == 0xBA:
        print(f'Found 0xBA at position {i}')
        # 显示周围的字节
        start = max(0, i - 10)
        end = min(len(content_bytes), i + 20)
        print(f'  Context bytes: {content_bytes[start:end].hex()}')
        # 尝试解码周围内容
        try:
            print(f'  Context string: {content_bytes[start:end].decode("utf-8", errors="replace")}')
        except:
            pass

# 尝试不同的编码解码
print()
print('=== Trying different encodings ===')
encodings = ['utf-8', 'gbk', 'gb2312', 'big5']
for enc in encodings:
    try:
        content = content_bytes.decode(enc)
        idx_maiba = content.find('麦吧')
        print(f'{enc}: 麦吧 found at {idx_maiba}')
    except Exception as e:
        print(f'{enc}: Error - {e}')