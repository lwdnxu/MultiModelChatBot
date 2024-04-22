from flask import Flask, request, jsonify
import whisper
import os
app = Flask(__name__)
import uuid

model = None
def load_whisper_model():
    global model
    if model is None:
        model = whisper.load_model("/home/user/data/large-v3.pt")
        print("模型加载成功")

# 在应用启动时加载模型
load_whisper_model()

@app.route('/voice2text', methods=['POST'])
def transcribe():
    voice_path = request.json.get('voice_path')

    if not voice_path:
        return jsonify({'error': 'voice_path is required'}), 400

    try:
        
        result = model.transcribe(voice_path)
        transcribed_text = result["text"]
        return jsonify({'result': transcribed_text}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500

@app.route('/upload', methods=['POST'])
def upload_file():
    # 检查请求是否包含文件
    if 'file' not in request.files:
        return jsonify({'error': 'No file provided'}), 400

    file = request.files['file']

    # 检查文件是否为空
    if file.filename == '':
        return jsonify({'error': 'No selected file'}), 400

    # 检查文件格式
    if file and file.filename.endswith('.wav'):
        # 保存文件
        filename = str(uuid.uuid4()).replace('-', '') + '.wav'
        # 文件保存路径
        base_dir = os.path.dirname(os.path.abspath(__file__))
        file_path = os.path.join(base_dir+"/uploads", filename)
        file.save(file_path)
        #  这里可以返回保存的位置
        return jsonify({'message': 'File uploaded successfully', 'file_path': file_path}), 200
    else:
        return jsonify({'error': 'Invalid file format. Please upload a .wav file'}), 400


if __name__ == '__main__':
    
    if not os.path.exists('./uploads'):
        os.makedirs('./uploads')
    app.run(debug=True, port=5000)
