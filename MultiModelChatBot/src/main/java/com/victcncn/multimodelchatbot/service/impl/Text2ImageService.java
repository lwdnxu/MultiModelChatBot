package com.victcncn.multimodelchatbot.service.impl;

import com.victcncn.multimodelchatbot.api.text2image.SDClient;
import com.victcncn.multimodelchatbot.constants.ResponseTypeConstants;
import com.victcncn.multimodelchatbot.domain.AIResponse;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import com.victcncn.multimodelchatbot.service.ChatCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.stereotype.Service;

/**
 *
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 13:30
 * @Descreiption: com.victcncn.multimodelchatbot.service.impl
 * @Version: 1.0
 */
@Service
public class Text2ImageService implements ChatCompletionService {
    @Autowired
    private SDClient sdClient;
    @Override
    public AIResponse generate(ChatCompletionRequest chatCompletionRequest) {
//        ChatResponse chatResponse = sdClient.generate(chatCompletionRequest);

        AIResponse aiResponse = new AIResponse();
        aiResponse.setType(ResponseTypeConstants.TEXT2IMAGE);
//        aiResponse.setImageUrl(chatResponse.getImageUrl());
        aiResponse.setImageUrl("upload/d5b1bf10-8be5-4254-9d39-48a4463deb93.png");
        return aiResponse;
    }
}
