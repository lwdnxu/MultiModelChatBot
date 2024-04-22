package com.victcncn.multimodelchatbot.domain.aiReponseDomain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 12:15
 * @Descreiption: com.victcncn.multimodelchatbot.domain
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {
    /**
     * 是否包含图片
     */
    private String isImage;

    /**
     * 图片的id
     */
    private List<String> imageIds;

    /**
     * 图片的地址
     */
    private List<String> imageUrls;

}
