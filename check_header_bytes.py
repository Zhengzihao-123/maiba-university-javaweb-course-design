# 检查 header.jsp 文件中中文字符的字节
content = open('d:/JavaWeb/Project/maiba/src/main/webapp/include/header.jsp', 'rb').read()

# 查找 "麦吧" 的字节位置
maiba_bytes = '麦吧'.encode('utf-8')
idx = content.find(maiba_bytes)
if idx > 0:
    print(f'Found "麦吧" bytes at position {idx}')
    print(f'"麦吧" UTF-8 bytes: {maiba_bytes.hex()}')
    print(f'Context bytes: {content[idx-10:idx+20].hex()}')
else:
    print('"麦吧" not found in file')
    
# 查找非法字节 0xA6
for i in range(len(content)):
    if content[i] == 0xA6:
        print(f'\nFound 0xA6 at position {i}')
        print(f'Context: {content[i-20:i+20].hex()}')
        break