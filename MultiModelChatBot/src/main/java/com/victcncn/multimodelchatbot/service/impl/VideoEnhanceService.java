package com.victcncn.multimodelchatbot.service.impl;

import com.victcncn.multimodelchatbot.api.videoenhance.VideoEnhanceClient;
import com.victcncn.multimodelchatbot.constants.ResponseTypeConstants;
import com.victcncn.multimodelchatbot.domain.AIResponse;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import com.victcncn.multimodelchatbot.service.ChatCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/21 - 04 - 21 - 19:54
 * @Descreiption: com.victcncn.multimodelchatbot.service.impl
 * @Version: 1.0
 */
@Service
public class VideoEnhanceService implements ChatCompletionService {
    @Autowired
    private VideoEnhanceClient videoEnhanceClient;
    @Override
    public AIResponse generate(ChatCompletionRequest chatCompletionRequest) {

//        ChatResponse chatResponse = videoEnhanceClient.generate(chatCompletionRequest);
        AIResponse aiResponse = new AIResponse();
        aiResponse.setType(ResponseTypeConstants.VIDEOENHANCE);

        aiResponse.setVideoUrl("/upload/b80c8914-5676-455a-adf2-b5c97f6df1eb.mp4");
//        aiResponse.setVideoUrl(chatResponse.getVideoUrl());
        return aiResponse;
    }
}
