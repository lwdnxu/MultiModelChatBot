package com.victcncn.multimodelchatbot.domain;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.annotation.security.DenyAll;
import java.util.List;
import java.util.Objects;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 10:54
 * @Descreiption: com.victcncn.multimodelchatbot.domain
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionRequest {
    /**
     * 模型名称
     */
    @JsonAlias({"modelName","model_name"})
    private String modelName;

    /**
     * 消息列表
     */
    private List<ChatMessages> messages;

    /**
     * 最大的token
     */
    @JsonAlias({"maxTokens","max_tokens"})
    private Integer maxTokens = 1024;

    /**
     * 温度值 [0,1]之前
     */
    @JsonProperty(value = "temperature")
    private Double temperature = 0.0;

     /**
     * 取前K个结果
     */
    @JsonAlias({"topK","top_k"})
    private Integer topK =1;

    /**
     * 大模型回答默认使用流式
     */
    private Boolean stream = true;

    private String uuid;

    public String getLastMessageContent( ){
        List<ChatMessages> messages = this.getMessages();
        String content =  messages.get(messages.size()-1).getContent();
        return content;
    }

}
