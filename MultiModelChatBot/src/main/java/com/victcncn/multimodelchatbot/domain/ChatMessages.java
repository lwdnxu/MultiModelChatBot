package com.victcncn.multimodelchatbot.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 10:55
 * @Descreiption: com.victcncn.multimodelchatbot.domain
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessages {
    /**
     * role，角色
     * 按照惯例，应该包含了：system、user、assistant
     */
    private String role;
    private String content;
}
