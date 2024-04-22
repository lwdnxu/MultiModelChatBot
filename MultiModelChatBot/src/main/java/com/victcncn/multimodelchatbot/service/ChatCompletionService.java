package com.victcncn.multimodelchatbot.service;

import com.victcncn.multimodelchatbot.domain.AIResponse;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 12:07
 * @Descreiption: com.victcncn.multimodelchatbot.service
 * @Version: 1.0
 */
public interface ChatCompletionService {
    public AIResponse generate(ChatCompletionRequest chatCompletionRequest);
}
