package com.victcncn.multimodelchatbot.api.image2text;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.victcncn.multimodelchatbot.api.ClientApi;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/17 - 04 - 17 - 14:32
 * @Descreiption: com.victcncn.multimodelchatbot.api.image2text
 * @Version: 1.0
 */
@Service
public class VisualGLMClient  {
    @Value("${multi_model.image2text.url:null}")
    private String url;

    /**
     * path + messages的最后一个content
     * @param path
     * @return
     */
    public ChatResponse generate(ChatCompletionRequest chatCompletionRequest,String path) {
        String content = chatCompletionRequest.getLastMessageContent();
        HashMap<String, Object> paramMap = new HashMap<>();
        // 读取图片，然后通过base64转换
        String base64String = convertImageToBase64(path);
        paramMap.put("image",base64String);
        // 这里可以用作用户的输入，这里先设置为  请描述图片
        paramMap.put("text",content);
        String resStr = "";
        Boolean status = false;

        try {
            String response = HttpRequest.post(url)
                    .header("Content-Type","application/json")
                    .body(JSONUtil.toJsonStr(paramMap)).timeout(10000)
                    .execute().body();
            System.out.println(response);
            JSONObject jsonObject = JSONUtil.parseObj(response);
            resStr = jsonObject.getStr("result");
            System.out.println("返回的结果为"+resStr);
            status =true;
        } catch (Exception e) {
            e.printStackTrace();
            resStr="图生文请求错误";
        }
        return ChatResponse.builder().status(status).content(resStr).imageUrl(path).build();
//        return null;
    }

    public static String convertImageToBase64(String imagePath) {
        String base64String = "";
        try {
            // 读取图像文件
            File file = new File(imagePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

            byte[] imageBytes = bos.toByteArray();

            // 将图像字节数组转换为Base64字符串
            base64String = Base64.getEncoder().encodeToString(imageBytes);

            fis.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }

    public static void main(String[] args) {
        String path ="C:\\Users\\lwdnx\\Desktop\\1.png";
        String base64String = convertImageToBase64(path);
        System.out.println("Base64 String: " + base64String);
    }
}
