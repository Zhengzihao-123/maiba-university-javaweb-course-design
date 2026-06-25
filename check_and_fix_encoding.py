import os

def check_and_fix_file(file_path):
    try:
        with open(file_path, 'rb') as f:
            content_bytes = f.read()
        
        try:
            content_utf8 = content_bytes.decode('utf-8')
            if '麦吧' in content_utf8:
                print("OK: " + file_path + " - UTF-8")
                return True
            else:
                print("INFO: " + file_path + " - UTF-8 but no Chinese")
        except UnicodeDecodeError:
            try:
                content_gbk = content_bytes.decode('gbk')
                print("FIX: " + file_path + " - GBK -> UTF-8")
                with open(file_path, 'w', encoding='utf-8') as f:
                    f.write(content_gbk)
                print("DONE: " + file_path)
                return True
            except UnicodeDecodeError:
                print("ERR: " + file_path + " - Unknown encoding")
                return False
    except Exception as e:
        print("ERR: " + file_path + " - Read error: " + str(e))
        return False

def main():
    base_path = r'd:\JavaWeb\Project\maiba\src\main\webapp'
    
    for root, dirs, files in os.walk(base_path):
        for file in files:
            if file.endswith('.jsp') or file.endswith('.css'):
                file_path = os.path.join(root, file)
                check_and_fix_file(file_path)

if __name__ == '__main__':
    main()