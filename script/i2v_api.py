from flask import Flask, request, jsonify
from modelscope.pipelines import pipeline
from modelscope.outputs import OutputKeys

app = Flask(__name__)

@app.route('/image_to_video', methods=['POST'])
def convert_image_to_video():
    # 从请求中获取图像路径
    image_path = request.json.get('image_path')
    result_video_path = request.json.get('video_path')
    
    if ( not image_path )  or(not result_video_path):
        return jsonify({'error': 'Image path is required'}), 400

    try:
        # 创建图像到视频的pipeline
        pipe1 = pipeline(task='image-to-video', model='damo/Image-to-Video', model_revision='v1.1.0', device='cuda:0')
        
        # 指定输出视频的路径
        
        # 执行pipeline
        pipe1(image_path, output_video=result_video_path)
        
        return jsonify({"status": True}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    app.run(debug=True, port=4396)
