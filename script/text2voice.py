from flask import Flask, request, send_file
from modelscope.outputs import OutputKeys
from modelscope.pipelines import pipeline
from modelscope.utils.constant import Tasks
import io
import uuid
import os


app = Flask(__name__)

model_id = 'damo/speech_sambert-hifigan_tts_zh-cn_16k'
sambert_hifigan_tts = pipeline(task=Tasks.text_to_speech, model=model_id)

@app.route('/text2voice', methods=['POST'])
def generate_audio():
    # 获取请求中的text和voice参数
    data = request.json
    text = data.get('text', '')
    voice = data.get('voice', 'zhitian_emo')


    # 使用sambert_hifigan_tts生成音频
    output = sambert_hifigan_tts(input=text, voice=voice)
    wav = output[OutputKeys.OUTPUT_WAV]

    # 将音频数据保存到内存中
    buffer = io.BytesIO()
    buffer.write(wav)
    buffer.seek(0)

    filename = str(uuid.uuid4()).replace('-', '') + '.wav'
        # 文件保存路径
    base_dir = os.path.dirname(os.path.abspath(__file__))
    file_path = os.path.join(base_dir+"/uploads/voice", filename)
    with open(file_path, 'wb') as f:
        f.write(wav)
    # 返回音频文件
    return send_file(file_path, mimetype='audio/wav')

if __name__ == '__main__':
    if not os.path.exists('./uploads/voice'):
        os.makedirs('./uploads/voice')
    app.run(debug=True,port=5678)
