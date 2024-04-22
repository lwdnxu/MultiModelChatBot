package com.victcncn.multimodelchatbot.service.impl;


import com.victcncn.multimodelchatbot.api.voice.Text2AudioClient;
import com.victcncn.multimodelchatbot.api.voice.WhisperClient;
import com.victcncn.multimodelchatbot.api.image2text.VisualGLMClient;
import com.victcncn.multimodelchatbot.api.llm.LLMClient;
import com.victcncn.multimodelchatbot.api.text2image.SDClient;
import com.victcncn.multimodelchatbot.constants.ResponseTypeConstants;
import com.victcncn.multimodelchatbot.domain.AIResponse;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import com.victcncn.multimodelchatbot.service.ChatCompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 12:23
 * @Descreiption: com.victcncn.multimodelchatbot.service.impl
 * @Version: 1.0
 */
@Service
public class LLMService implements ChatCompletionService {
    @Autowired
    private LLMClient llmClient;
    @Autowired
    private SDClient sdClient;

    @Autowired
    private VisualGLMClient visualGLMClient;
    @Autowired
    private WhisperClient whisperClient;
    @Autowired
    private Text2AudioClient text2AudioClient;

    @Autowired
    private ApplicationContext context;
    public AIResponse generate(ChatCompletionRequest chatCompletionRequest) {

        ChatResponse chatResponse = llmClient.generate(chatCompletionRequest);
        AIResponse aiResponse = new AIResponse();
        aiResponse.setType(ResponseTypeConstants.TEXT);
        aiResponse.setContent(chatResponse.getContent());
        return aiResponse;
    }
}
