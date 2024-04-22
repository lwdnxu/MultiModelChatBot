package com.victcncn.multimodelchatbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 19:07
 * @Descreiption: com.victcncn.multimodelchatbot.domain
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatResponse {
    private Boolean status;
    private String content;
    private String imageUrl;
    private String videoUrl;
}
