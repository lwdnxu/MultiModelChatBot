from flask import Flask, request, jsonify
from modelscope.pipelines import pipeline
from modelscope.outputs import OutputKeys

app = Flask(__name__)

@app.route('/video_to_video', methods=['POST'])
def convert_image_to_video():
    # 从请求中获取图像路径
    input_video_path = request.json.get('input_video_path')
    output_video_path = request.json.get('output_video_path')
    
    if not input_video_path or not output_video_path:
        return jsonify({'error': 'Image path is required'}), 400

    try:
        # 创建图像到视频的pipeline
        p_input = {'video_path': input_video_path}
        pipe2 = pipeline(task='video-to-video', model='damo/Video-to-Video', model_revision='v1.1.0', device='cuda:0')
        # 执行pipeline
        new_output_video_path = pipe2(p_input, output_video=output_video_path)[OutputKeys.OUTPUT_VIDEO]
        
        return jsonify({"status": True}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__': 
    app.run(debug=True, port=2200)
