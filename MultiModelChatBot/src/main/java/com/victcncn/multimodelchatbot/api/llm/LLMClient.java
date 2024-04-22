package com.victcncn.multimodelchatbot.api.llm;

import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import com.victcncn.multimodelchatbot.util.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/20 - 04 - 20 - 18:40
 * @Descreiption: com.victcncn.multimodelchatbot.api.llm
 * @Version: 1.0
 */
@Service
public class LLMClient{
    @Autowired
    private HttpService httpService;
    public ChatResponse generate(ChatCompletionRequest chatCompletionRequest){
        ChatResponse chatResponse = httpService.openAIStylePost(chatCompletionRequest.getMessages());
        // 需要流式接口
//        ChatResponse chatResponse = httpService.nonOpenAIStylePost(chatCompletionRequest.getMessages(), false);
        return  chatResponse;
    }
}
