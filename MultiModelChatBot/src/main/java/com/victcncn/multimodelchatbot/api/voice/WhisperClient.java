package com.victcncn.multimodelchatbot.api.voice;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/18 - 04 - 18 - 0:04
 * @Descreiption: com.victcncn.multimodelchatbot.api
 * @Version: 1.0
 */
@Service
@Slf4j
public class WhisperClient{
    @Value("${multi_model.voice2text.url:null}")
    private String url;

    @Value("${multi_model.voice2text.upload_url:null}")
    private String uploadUrl;
    public ChatResponse generate(String path) {
        HashMap<String, Object> paramMap = new HashMap<>();
        // 读取音频，然后通过base64转换
        String ttPath = upload(uploadUrl,path);
        // 固定为可描述图片
        paramMap.put("voice_path",ttPath);
        String resStr = "";
        Boolean status = false;

        try {
            System.out.println(paramMap);
            String response = HttpRequest.post(url)
                    .header("Content-Type","application/json")
                    .body(JSONUtil.toJsonStr(paramMap)).timeout(10000)
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(response);
            resStr = jsonObject.getStr("result");
            System.out.println("返回的结果为"+resStr);
            status =true;
        } catch (Exception e) {
            e.printStackTrace();
            resStr="音频转文字请求错误";
        }
        return ChatResponse.builder().status(status).content(resStr).build();
    }

    private static String upload(String url,String path){
        // 要上传的WAV文件路径
        File file = FileUtil.file(path);
        // 发送POST请求
        String response = HttpRequest.post(url)
                .form("file", file)
                .execute().body();
        JSONObject jsonObject = JSONUtil.parseObj(response);
        String resPath = jsonObject.getStr("file_path");
        log.info("保存音频在服务器上的地址为："+resPath);
        return resPath;
    }
}
