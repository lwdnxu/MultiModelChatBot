package com.victcncn.multimodelchatbot.api.image2video;

import com.victcncn.multimodelchatbot.api.ClientApi;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/17 - 04 - 17 - 10:34
 * @Descreiption: com.victcncn.multimodelchatbot.api.image2video
 * @Version: 1.0
 */
@Service
public class SVDClient implements ClientApi {
    @Value("${multi_model.text2video.url:null}")
    private String url;
    @Override
    public ChatResponse generate(ChatCompletionRequest chatCompletionRequest) {
        return null;
    }
}
