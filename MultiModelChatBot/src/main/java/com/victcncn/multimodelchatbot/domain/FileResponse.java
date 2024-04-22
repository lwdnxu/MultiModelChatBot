package com.victcncn.multimodelchatbot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/18 - 04 - 18 - 21:39
 * @Descreiption: com.victcncn.multimodelchatbot.domain
 * @Version: 1.0
 */@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileResponse {
    private Integer status;
    private String content;
}
