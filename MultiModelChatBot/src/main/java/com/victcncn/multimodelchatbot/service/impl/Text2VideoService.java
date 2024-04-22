package com.victcncn.multimodelchatbot.service.impl;

import com.victcncn.multimodelchatbot.constants.ResponseTypeConstants;
import com.victcncn.multimodelchatbot.domain.AIResponse;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.service.ChatCompletionService;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 13:30
 * @Descreiption: com.victcncn.multimodelchatbot.service.impl
 * @Version: 1.0
 */
public class Text2VideoService implements ChatCompletionService {
    @Override
    public AIResponse generate(ChatCompletionRequest chatCompletionRequest) {
        AIResponse aiResponse = new AIResponse();
        aiResponse.setType(ResponseTypeConstants.TEXT2VIDEO);
        return aiResponse;
    }
}
