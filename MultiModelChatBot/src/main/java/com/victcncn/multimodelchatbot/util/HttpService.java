package com.victcncn.multimodelchatbot.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatMessages;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import javafx.scene.web.PromptData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class HttpService {
    @Value("${multi_model.llm.url:null}")
    private String url;
    @Value("${multi_model.llm.api_key:null}")
    public String apiKey;
    @Value("${multi_model.llm.model_name:null}")
    private String modelName;

    /**
     * openai风格接口的LLM调用
     * @param chatMessages
     *
     * @return
     */
    public ChatResponse openAIStylePost(List<ChatMessages> chatMessages) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", modelName);
        paramMap.put("messages", chatMessages);
        paramMap.put("stream", false);
        String resStr = "";
        Boolean status = false;
        log.info("当前请求LLM的参数为" + paramMap);
        try {
            // 需要设置代理访问
            String response = HttpRequest.post(url).header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr(paramMap)).timeout(10000).setHttpProxy("127.0.0.1", 7890)
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(response);
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject result = choices.get(0, JSONObject.class, Boolean.TRUE);
            JSONObject message = result.getJSONObject("message");
            resStr = message.getStr("content");
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
            resStr = "大模型请求错误";
        }
        return ChatResponse.builder().content(resStr).status(status).build();
    }

    /**
     * openai风格的流式化接口
     * @param chatMessages
     * @return
     */
    public Flux<String>  openAIStylePostStream(ChatCompletionRequest chatCompletionRequest){
        String prompt= chatCompletionRequest.getLastMessageContent();
        WebClient webClient = WebClient.builder().baseUrl("https://api.openai.com/v1/chat")
                .filter(logRequest())
                .clientConnector( new ReactorClientHttpConnector(
                        HttpClient.create()
                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 连接超时时间
                                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))) // 读取超时时间
                                .proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP).address(new InetSocketAddress("127.0.0.1", 7890)))
                ))
                .build();
        HashMap<String, Object> paramMap = new HashMap<>();
        ArrayList<Map> list = new ArrayList<>();
        Map<String, String> msg = new HashMap<>();
        msg.put("role","user");
        msg.put("content",prompt);
        list.add(msg);
        paramMap.put("model","gpt-3.5-turbo");
        paramMap.put("messages",list);
        paramMap.put("stream", true);

        Mono<ClientResponse> responseMono = webClient.post()
                .uri("/completions")
                .header("Authorization", "Bearer sk-")
                .header("Content-Type","application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(paramMap)

                .exchange();

        return responseMono.flatMapMany(response -> {
            if (response.statusCode().is2xxSuccessful()) {
                // 将响应体转换为字符串流
                return response.bodyToFlux(String.class);
            } else {
                // 处理错误情况
                return Flux.error(new RuntimeException("Error occurred during streaming"));
            }
        }).map(message -> {
            // 构造SSE消息格式
            return message;
        });
    }
    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: " + clientRequest.method() + " " + clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> System.out.println(name + ": " + value)));
            return Mono.just(clientRequest);
        });
    }


    /**
     * 非openai风格接口的LLM调用，只解析以下json：
     * {"content": ""}
     * @param chatMessages
     * @param stream
     * @return
     */
    public ChatResponse nonOpenAIStylePost(List<ChatMessages> chatMessages, Boolean stream) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("model", modelName);
        paramMap.put("messages", chatMessages);
        paramMap.put("stream", stream);
        String resStr = "";
        Boolean status = false;
        log.info("当前请求LLM的参数为" + paramMap);
        try {
            // 需要设置代理访问
            String response = HttpRequest.post(url).header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .body(JSONUtil.toJsonStr(paramMap)).timeout(10000).setHttpProxy("127.0.0.1", 7890)
                    .execute().body();
            JSONObject jsonObject = JSONUtil.parseObj(response);
            resStr = jsonObject.getStr("content");
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
            resStr = "大模型请求错误";
        }
        return ChatResponse.builder().content(resStr).status(status).build();
    }


}