package com.victcncn.multimodelchatbot.api.image2video;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.victcncn.multimodelchatbot.api.ClientApi;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import com.victcncn.multimodelchatbot.util.FileStorage;
import com.victcncn.multimodelchatbot.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/17 - 04 - 17 - 21:17
 * @Descreiption: com.victcncn.multimodelchatbot.api.text2video
 * @Version: 1.0
 */
@Service
public class AliImageToVideoClient implements ClientApi {
    @Value("${multi_model.image2video.url:null}")
    private String url;
    @Override
    public ChatResponse generate(ChatCompletionRequest chatCompletionRequest)  {
        String content = chatCompletionRequest.getLastMessageContent();
        HashMap<String, Object> paramMap = new HashMap<>();
        // 读取图片，然后通过base64转换
        String path = FileStorage.lastImageFile.get(chatCompletionRequest.getUuid());
        System.out.println("图片路径为"+path);
        String imagePath =path;
        String videoPath = FileUtils.generateFile(".mp4", "video");
        System.out.println("视频路径为"+path);
        paramMap.put("image_path",imagePath);
        paramMap.put("video_path",videoPath);
        String resStr = "";
        Boolean status = false;
        try {
            // 需要设置代理访问
            String response = HttpRequest.post(url)
                    .header("Content-Type","application/json")
                    .body(JSONUtil.toJsonStr(paramMap)).timeout(10000)
                    .execute().body();
            System.out.println(response);
            JSONObject jsonObject = JSONUtil.parseObj(response);
            resStr = jsonObject.getStr("status");
            // 获取状态
            System.out.println("返回的结果为"+resStr);
            status =true;
        } catch (Exception e) {
            e.printStackTrace();
            resStr="图生视频请求错误";
        }
        return ChatResponse.builder().status(status).videoUrl(videoPath).build();
    }


}
