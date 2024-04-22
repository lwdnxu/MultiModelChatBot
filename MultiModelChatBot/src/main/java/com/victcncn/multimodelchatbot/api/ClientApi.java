package com.victcncn.multimodelchatbot.api;

import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;

import java.io.FileNotFoundException;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/21 - 04 - 21 - 12:29
 * @Descreiption: com.victcncn.multimodelchatbot.api
 * @Version: 1.0
 */

public interface ClientApi {
    public ChatResponse generate(ChatCompletionRequest chatCompletionRequest) throws FileNotFoundException;
}
