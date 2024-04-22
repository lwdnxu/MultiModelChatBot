package com.victcncn.multimodelchatbot.domain.aiReponseDomain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 12:17
 * @Descreiption: com.victcncn.multimodelchatbot.domain.aiReponseDomain
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocResponse {
    /**
     * 是否包含文档
     */
    private String isDoc;

    /**
     * 文档的id
     */
    private List<String> docIds;

    /**
     * 文档的地址
     */
    private List<String> docUrls;
}
