package com.victcncn.multimodelchatbot.domain;

import com.victcncn.multimodelchatbot.domain.aiReponseDomain.DocResponse;
import com.victcncn.multimodelchatbot.domain.aiReponseDomain.ImageResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 12:08
 * @Descreiption: com.victcncn.multimodelchatbot.domain
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIResponse {
    /**
     * 返回的类型， 是纯文本、文生图、图生文、文生视频、图生视频等等。
     */
    private String type;

    /**
     * 大模型返回的文本，（前提是有文本需要返回）
     */
    private String content;

    /**
     * 如果包含图片，则当前的不为空  （可能有多个）
     */
    private String imageUrl;

    /**
     * 如果包含文档，则当前的不为空 （可能有多个）
     */
    private String  docUrl;

    /**
     * 如果包含视频，则当前的不为空 （只可能有一个）
     */
    private String videoUrl;

}
