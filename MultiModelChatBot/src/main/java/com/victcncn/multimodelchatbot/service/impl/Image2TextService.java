package com.victcncn.multimodelchatbot.service.impl;

import com.victcncn.multimodelchatbot.api.image2text.VisualGLMClient;
import com.victcncn.multimodelchatbot.constants.ResponseTypeConstants;
import com.victcncn.multimodelchatbot.domain.AIResponse;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import com.victcncn.multimodelchatbot.domain.aiReponseDomain.ImageResponse;
import com.victcncn.multimodelchatbot.service.ChatCompletionService;
import com.victcncn.multimodelchatbot.util.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.FileDataSource;


/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 13:31
 * @Descreiption: com.victcncn.multimodelchatbot.service.impl
 * @Version: 1.0
 */
@Service
public class Image2TextService implements ChatCompletionService {

    @Autowired
    private  VisualGLMClient visualGLMClient;
    @Override
    public AIResponse generate(ChatCompletionRequest chatCompletionRequest) {
        String path = FileStorage.lastImageFile.get(chatCompletionRequest.getUuid());
        System.out.println("路径为"+path);
        ChatResponse chatResponse = visualGLMClient.generate(chatCompletionRequest, path);
        AIResponse aiResponse = null;
        if(chatResponse.getStatus()){

            aiResponse = new AIResponse();
            aiResponse.setType(ResponseTypeConstants.IMAGE2TEXT);
            aiResponse.setContent(chatResponse.getContent());
            aiResponse.setImageUrl(chatResponse.getImageUrl());
        }
        return aiResponse;
    }
}
