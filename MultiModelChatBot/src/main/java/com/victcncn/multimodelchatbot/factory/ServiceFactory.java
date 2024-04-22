package com.victcncn.multimodelchatbot.factory;

import com.victcncn.multimodelchatbot.constants.ResponseTypeConstants;
import com.victcncn.multimodelchatbot.service.ChatCompletionService;
import com.victcncn.multimodelchatbot.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 13:40
 * @Descreiption: com.victcncn.multimodelchatbot.factory
 * @Version: 1.0
 */

@Service
public class ServiceFactory {
    private Map<String, Class<? extends ChatCompletionService>> intentToServiceClass;
    private ApplicationContext applicationContext;

    @Autowired
    public void ChatCompletionServiceFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        this.intentToServiceClass = new HashMap<>();
        intentToServiceClass.put(ResponseTypeConstants.TEXT, LLMService.class);
        intentToServiceClass.put(ResponseTypeConstants.TEXT2IMAGE, Text2ImageService.class);
        intentToServiceClass.put(ResponseTypeConstants.IMAGE2TEXT, Image2TextService.class);
        intentToServiceClass.put(ResponseTypeConstants.IMAGE2VIDEO, Image2VideoService.class);
        intentToServiceClass.put(ResponseTypeConstants.TEXT2VIDEO, Text2VideoService.class);
        intentToServiceClass.put(ResponseTypeConstants.VIDEOENHANCE,VideoEnhanceService.class);
    }

    public ServiceFactory() {
    }

    public ChatCompletionService getBean(String intent) {
        Class<? extends ChatCompletionService> serviceClass = intentToServiceClass.get(intent);
        if (serviceClass != null) {
            return applicationContext.getBean(serviceClass);
        }
        return null;
    }
}
