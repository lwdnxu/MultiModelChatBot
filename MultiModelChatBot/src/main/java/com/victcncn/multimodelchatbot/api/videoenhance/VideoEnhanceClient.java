package com.victcncn.multimodelchatbot.api.videoenhance;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.victcncn.multimodelchatbot.api.ClientApi;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/17 - 04 - 17 - 21:14
 * @Descreiption: com.victcncn.multimodelchatbot.api
 * @Version: 1.0
 */

/**
 * 视频增强
 */
@Service
public class VideoEnhanceClient implements ClientApi {
    @Value("${multi_model.video_enhance.url:null}")
    private String url;
    @Override
    public ChatResponse generate(ChatCompletionRequest chatCompletionRequest) {
        String content = chatCompletionRequest.getLastMessageContent();
        HashMap<String, Object> paramMap = new HashMap<>();
        // 读取图片，然后通过base64转换
        String input_video_path ="C:\\Users\\lwdnx\\Desktop\\1.mp4";
        String output_video_path ="C:\\Users\\lwdnx\\Desktop\\2.mp4";
        paramMap.put("input_video_path",input_video_path);
        paramMap.put("output_video_path",output_video_path);
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
            System.out.println("返回的结果为"+resStr);
            status =true;
        } catch (Exception e) {
            e.printStackTrace();
            resStr="视频增强请求错误";
        }
        return ChatResponse.builder().status(status).content(resStr).build();
    }
}
