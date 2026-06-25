# 检查 header.jsp 文件的编码
with open('d:/JavaWeb/Project/maiba/src/main/webapp/include/header.jsp', 'rb') as f:
    content_bytes = f.read()

# 检查是否有非法字节
print('文件长度:', len(content_bytes))
print('前100字节:', content_bytes[:100].hex())
print()

# 查找中文"首页"的字节
maiba_bytes = '麦吧'.encode('utf-8')
shouye_bytes = '首页'.encode('utf-8')
print('麦吧 UTF-8:', maiba_bytes.hex())
print('首页 UTF-8:', shouye_bytes.hex())
print()

# 查找是否存在
print('麦吧位置:', content_bytes.find(maiba_bytes))
print('首页位置:', content_bytes.find(shouye_bytes))
print()

# 尝试解码
try:
    content = content_bytes.decode('utf-8')
    print('UTF-8解码成功')
    print('内容长度:', len(content))
    print('首页在内容中:', '首页' in content)
except Exception as e:
    print('解码失败:', e)