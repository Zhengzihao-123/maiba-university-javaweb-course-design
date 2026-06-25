import os
import chardet

def check_encoding(file_path):
    with open(file_path, 'rb') as f:
        raw_data = f.read()
        result = chardet.detect(raw_data)
        encoding = result['encoding']
        confidence = result['confidence']
        return encoding, confidence

def main():
    base_path = r'd:\JavaWeb\Project\maiba\src\main\webapp'
    problematic_files = []
    
    for root, dirs, files in os.walk(base_path):
        for file in files:
            if file.endswith('.jsp') or file.endswith('.css'):
                file_path = os.path.join(root, file)
                encoding, confidence = check_encoding(file_path)
                
                if encoding != 'utf-8' and encoding != 'UTF-8':
                    problematic_files.append((file_path, encoding, confidence))
    
    if problematic_files:
        print("发现以下文件编码不是 UTF-8:")
        for fp, enc, conf in problematic_files:
            print(f"  {fp} - 检测编码: {enc} (置信度: {conf:.2f})")
    else:
        print("所有文件都是 UTF-8 编码")

if __name__ == '__main__':
    main()