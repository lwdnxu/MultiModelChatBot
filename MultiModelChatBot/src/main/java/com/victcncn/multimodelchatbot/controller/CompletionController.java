package com.victcncn.multimodelchatbot.controller;

import cn.hutool.json.JSONUtil;
import com.victcncn.multimodelchatbot.domain.AIResponse;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatMessages;
import com.victcncn.multimodelchatbot.factory.ServiceFactory;
import com.victcncn.multimodelchatbot.service.ChatCompletionService;
import com.victcncn.multimodelchatbot.util.HttpService;
import com.victcncn.multimodelchatbot.util.IntentDetectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.ArrayList;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 12:03
 * @Descreiption: com.victcncn.multimodelchatbot.controller
 * @Version: 1.0
 */
@RestController
@Slf4j
public class CompletionController {
    @Autowired
    ServiceFactory serviceFactory;
    @Autowired
    private IntentDetectionService intentDetectionService;
    @Autowired
    private HttpService httpService;
    @PostMapping("/chatCompletion")
    @CrossOrigin
    public Flux<String> ChatCompletion(@RequestBody ChatCompletionRequest chatCompletionRequest){
        log.info("请求参数为"+chatCompletionRequest);
        // 只对最后输入的进行意图识别 (以后可以增加内容，因此这里将chatCompletionRequest交给IntentDetectionUtils 识别意图)
        String intent = intentDetectionService.intent(chatCompletionRequest);
        log.info("当前的意图为"+intent);
        // 根据不同的意图调用不同的函数
        if("llm".equals(intent)) { // 如果当前的的意图是llm，默认为流式操作
            return httpService.openAIStylePostStream(chatCompletionRequest);
        }
        else{
            ChatCompletionService chatCompletionService = serviceFactory.getBean(intent);
            AIResponse aiResponse = chatCompletionService.generate(chatCompletionRequest);
            Flux<String> just = Flux.just(aiResponse.toString());
            return just;
        }
    }

    /**
     * 专门用于前端页面的getmapping，默认只支持一个消息，后序可以修改
     * @param prompt
     * @return
     */
    @GetMapping("/chatCompletion")
    @CrossOrigin
    public Flux<String> ChatCompletionByPrompt(@RequestParam("prompt") String prompt,@RequestParam("uuid") String  uuid){

        ChatCompletionRequest chatCompletionRequest = new ChatCompletionRequest();
        chatCompletionRequest.setStream(true);
        chatCompletionRequest.setUuid(uuid);
        ArrayList<ChatMessages> list = new ArrayList<>();
        list.add(new ChatMessages("user",prompt));
        chatCompletionRequest.setMessages(list);
        log.info("请求参数为"+chatCompletionRequest);
        // 只对最后输入的进行意图识别 (以后可以增加内容，因此这里将chatCompletionRequest交给IntentDetectionUtils 识别意图)
        String intent = intentDetectionService.intent(chatCompletionRequest);
//        String intent="videoenhance";
        log.info("当前的意图为"+intent);
        // 根据不同的意图调用不同的函数
        if("llm".equals(intent)) { // 如果当前的的意图是llm，默认为流式操作
            return httpService.openAIStylePostStream(chatCompletionRequest);
        }
        else{
            ChatCompletionService chatCompletionService = serviceFactory.getBean(intent);
            AIResponse aiResponse = chatCompletionService.generate(chatCompletionRequest);
            Flux<String> just = Flux.just(JSONUtil.toJsonStr(aiResponse));
            return just;
        }
    }

    @GetMapping(value = "/chatStream", produces = "text/event-stream")
    public Flux<ServerSentEvent<String>> streamChat() {
        // 这里只是一个示例，你可以根据实际需求进行更改
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("message")
                        .data("Message " + sequence)
                        .build())
                .take(5);  // 限制返回的事件数量为5
    }
}
