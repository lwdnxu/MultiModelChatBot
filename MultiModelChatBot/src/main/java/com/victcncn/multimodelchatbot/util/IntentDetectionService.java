package com.victcncn.multimodelchatbot.util;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 13:29
 * @Descreiption: com.victcncn.multimodelchatbot.util
 * @Version: 1.0
 */

import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatMessages;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * 判断用户的意图 text、文生图、图生文、文生视频、图生视频等等
 */
@Service
public class IntentDetectionService {
    @Autowired
    private HttpService httpService;
    public  String intent(ChatCompletionRequest chatCompletionRequest){
        String systemContent = "请根据用户的输入判断，和文生图相关则返回 text2image，和图生文相关则返回 image2text，和图生视频相关则返回 image2video，和视频增强相关则返回 videoenhance，其他的则返回 llm，以下是例子：\\n\\n生成雨后彩虹图 –>  text2image \\n 这个图片讲的什么？  –>  image2text  \\n用这个图片来生成视频   –>  image2video  \\n增强这个视频的帧数   –> videoenhance  \\n北京的景点有哪些   –> llm";
        // 目前只意图识别最后一个的content
        String content = chatCompletionRequest.getLastMessageContent();

        ArrayList<ChatMessages> list = new ArrayList<>();
        list.add(new ChatMessages("system",systemContent));
        list.add(new ChatMessages("user",content));
        ChatResponse chatResponse = httpService.openAIStylePost(list);
        return chatResponse.getContent();
    }
}
