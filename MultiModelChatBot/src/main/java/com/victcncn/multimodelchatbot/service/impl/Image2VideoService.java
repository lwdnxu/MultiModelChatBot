package com.victcncn.multimodelchatbot.service.impl;

import com.victcncn.multimodelchatbot.api.image2video.AliImageToVideoClient;
import com.victcncn.multimodelchatbot.constants.ResponseTypeConstants;
import com.victcncn.multimodelchatbot.domain.AIResponse;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import com.victcncn.multimodelchatbot.service.ChatCompletionService;
import com.victcncn.multimodelchatbot.util.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 13:31
 * @Descreiption: com.victcncn.multimodelchatbot.service.impl
 * @Version: 1.0
 */
@Service
public class Image2VideoService implements ChatCompletionService {
    @Autowired
    private AliImageToVideoClient imageToVideoClient;
    @Override
    public AIResponse generate(ChatCompletionRequest chatCompletionRequest) {

//        ChatResponse chatResponse = imageToVideoClient.generate(chatCompletionRequest);

        AIResponse aiResponse = new AIResponse();
        aiResponse.setType(ResponseTypeConstants.IMAGE2VIDEO);
        aiResponse.setVideoUrl("/upload/b80c8914-5676-455a-adf2-b5c97f6df1eb.mp4");
//        aiResponse.setVideoUrl();
        return aiResponse;
    }
}
