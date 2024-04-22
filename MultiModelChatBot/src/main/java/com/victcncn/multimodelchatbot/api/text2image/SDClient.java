package com.victcncn.multimodelchatbot.api.text2image;

import cn.hutool.Hutool;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.victcncn.multimodelchatbot.api.ClientApi;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 22:30
 * @Descreiption: com.victcncn.multimodelchatbot.api.text2image
 * @Version: 1.0
 */
@Service
public class SDClient implements ClientApi {
    @Value("${multi_model.text2image.url:null}")
    private String url;
    @Override
    public ChatResponse generate(ChatCompletionRequest chatCompletionRequest) {
        String content = chatCompletionRequest.getLastMessageContent();
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("prompt",content);
        paramMap.put("steps",5);
        String resStr = "";
        Boolean status = false;
        try {
            // 需要设置代理访问
            String response = HttpRequest.post(url)
                    .header("Content-Type","application/json")
                    .body(JSONUtil.toJsonStr(paramMap)).timeout(10000)
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(response);
            JSONArray images = jsonObject.getJSONArray("images");
            String path = writePng(images.getStr(0));
            resStr = path;
            status =true;
        } catch (Exception e) {
            e.printStackTrace();
            resStr="文生图请求错误";
        }
        return ChatResponse.builder().status(status).imageUrl(resStr).build();
    }

    private String writePng(String base64Code){
        String path ="";
        try {
            // Decode Base64 to byte array
            byte[] imageBytes = Base64.getDecoder().decode(base64Code);

            // Read byte array into BufferedImage
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));

            // Save BufferedImage to static directory
            File staticDir = ResourceUtils.getFile("classpath:static");
            path =staticDir.getAbsolutePath() + File.separator + "123.png";
            File imageFile = new File(staticDir.getAbsolutePath() + File.separator + "123.png");

            ImageIO.write(bufferedImage, "png", imageFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return  path;
    }
}
