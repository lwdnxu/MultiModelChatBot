package com.victcncn.multimodelchatbot.api.voice;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.victcncn.multimodelchatbot.api.ClientApi;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import sun.net.www.content.audio.wav;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/17 - 04 - 17 - 22:05
 * @Descreiption: com.victcncn.multimodelchatbot.api
 * @Version: 1.0
 */
@Service
public class Text2AudioClient implements ClientApi {
    @Value("${multi_model.text2voice.url:null}")
    private String url;
    @Override
    public ChatResponse generate(ChatCompletionRequest chatCompletionRequest) {
        String content = chatCompletionRequest.getLastMessageContent();
        HashMap<String, Object> paramMap = new HashMap<>();
        // 读取图片，然后通过base64转换
        String text ="前端模块我们采用模型结合规则的方式灵活处理各种场景下的文本，后端模块则采";
        // 这里可以用作用户的输入，这里先设置为  请描述图片
        paramMap.put("text",text);
        String resStr = "";
        Boolean status = false;

        try {

            byte[] bytes = HttpUtil.createPost(url)
                    .body(JSONUtil.toJsonStr(paramMap))
                    .execute().bodyBytes();
            File staticDir = ResourceUtils.getFile("classpath:static");
            UUID uuid = UUID.randomUUID();
            String uuidStr = uuid.toString().replace("-", "");
            String path =staticDir.getAbsolutePath() + File.separator + "voice" + File.separator + uuidStr +".wav";
            FileUtil.writeBytes(bytes, path);
            status =true;
            resStr= path;
        } catch (Exception e) {
            e.printStackTrace();
            resStr="文字转音频请求错误";
        }
        return ChatResponse.builder().status(status).content(resStr).build();
    }
}
