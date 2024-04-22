package com.victcncn.multimodelchatbot.controller;

import com.victcncn.multimodelchatbot.domain.ChatCompletionRequest;
import com.victcncn.multimodelchatbot.domain.ChatMessages;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import com.victcncn.multimodelchatbot.util.HttpService;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.server.HttpServer;
import reactor.netty.transport.ProxyProvider;

import javax.xml.bind.annotation.XmlRegistry;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 14:09
 * @Descreiption: com.victcncn.multimodelchatbot.controller
 * @Version: 1.0
 */
@RestController
@Slf4j
public class TestController {
    @GetMapping("/test")
    public String test(){
        return "";
    }
//    @GetMapping("/api/test/sss")
//    public void test(String prompt, HttpServletResponse res) throws IOException, InterruptedException {
//        log.info("【prompt内容】：{}", prompt);
//        String str = "       什么是爱而不得? \n" +
//                "东边日出西边雨，道是无晴却有晴。\n" +
//                "他朝若是同淋雪，此生也算共白头。\n" +
//                "我本将心向明月，奈何明月照沟渠。\n" +
//                "此时相望不相闻，愿逐月华流照君。\n" +
//                "衣带渐宽终不悔，为伊消得人憔悴。\n" +
//                "此情可待成追忆，只是当时已惘然。\n" +
//                "人生若只如初见，何事西风悲画扇。\n" +
//                "曾经沧海难为水，除却巫山不是云。\n" +
//                "何当共剪西窗烛，却话巴山夜雨时。\n" +
//                "天长地久有时尽，此恨绵绵无绝期。\n" +
//                "\n";
//        // 响应流
//        res.setHeader("Content-Type", "text/event-stream");
//        res.setContentType("text/event-stream");
//        res.setCharacterEncoding("UTF-8");
//        res.setHeader("Pragma", "no-cache");
//        ServletOutputStream out = null;
//        try {
//            out = res.getOutputStream();
//            for (int i = 0; i < str.length(); i++) {
//                out.write(String.valueOf(str.charAt(i)).getBytes());
//                // 更新数据流
//                out.flush();
//                Thread.sleep(100);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    // 实现流式传输
    @GetMapping(value = "/ttt", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> hello(){
//        return Flux.interval(Duration.ofMillis(100))
//                .map(sequence -> "Data " + sequence)
//                // 执行50次
//                .take(50)
//                .log();
        String text = "```python\n" +
                "def quick_sort(arr):\n" +
                "    if len(arr) <= 1:\n" +
                "        return arr\n" +
                "    pivot = arr[len(arr) // 2]\n" +
                "    left = [x for x in arr if x < pivot]\n" +
                "    middle = [x for x in arr if x == pivot]\n" +
                "    right = [x for x in arr if x > pivot]\n" +
                "    return quick_sort(left) + middle + quick_sort(right)\n" +
                "\n" +
                "# 测试代码\n" +
                "arr = [3, 7, 1, 9, 2, 6, 5]\n" +
                "sorted_arr = quick_sort(arr)\n" +
                "print(sorted_arr)\n" +
                "```\n" +
                "\n" +
                "在这个示例代码中，快速排序使用递归的方法，从原始数组中选择一个基准元素，然后将数组划分为小于、等于和大于基准元素的三个子数组。然后，对左右子数组分别进行递归调用，最后将排序后的左子数组、基准元素和排序后的右子数组进行合并，形成最终的排序结果。\n" +
                "\n" +
                "注意：这个示例代码是使用Python语言编写的，如果你使用的是其他编程语言，可能需要进行一些语法上的更改。";
//        String text = "注意：这个示例代码是使用Python语言编写的，如果你使用的是其他";
        return Flux.fromIterable(splitString(text, 5)) // 将字符串拆分为字符数组
                .delayElements(Duration.ofMillis(10)) // 每100毫秒发射一个字符
                .doOnNext(character -> System.out.print(character));
    }
    private List<String> splitString(String text, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        for (int i = 0; i < text.length(); i += chunkSize) {
            int end = Math.min(text.length(), i + chunkSize);
            chunks.add(text.substring(i, end));
        }
        return chunks;
    }

//    @GetMapping("haha", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> haha(){
////        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(options -> options.httpProxy(addressSpec -> addressSpec
////                        .address(new InetSocketAddress("127.0.0.1", 1080)))
////        );
//        WebClient webClient = WebClient.builder().baseUrl("https://api.openai.com/v1/chat")
//                .filter(logRequest())
//                .clientConnector( new ReactorClientHttpConnector(
//                        HttpClient.create()
//                                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // 连接超时时间
//                                .doOnConnected(conn -> conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))) // 读取超时时间
//                                .proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP).address(new InetSocketAddress("127.0.0.1", 7890)))
//                ))
//                .build();
//
//        String prompt = "你好";
//        HashMap<String, Object> paramMap = new HashMap<>();
//        ArrayList<Map> list = new ArrayList<>();
//
//        Map<String, String> msg = new HashMap<>();
//        msg.put("role","user");
//        msg.put("content","你好");
//        list.add(msg);
//        paramMap.put("model","gpt-3.5-turbo");
//        paramMap.put("messages",list);
//        paramMap.put("stream", true);
//
//        webClient.post()
//
//                .uri("/completions")
//                .header("Authorization", "Bearer sk-")
//                .header("Content-Type","application/json")
//                .bodyValue(paramMap)
//
//                .retrieve()
//                .bodyToFlux(String.class)
//                .doOnRequest((response) -> {
//                    System.out.println("Request body: " + paramMap.toString());
//
//                })
//                .subscribe(System.out::println);
//    }

    @GetMapping(value = "/haha", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> haha(@RequestParam String prompt) {
//        ReactorClientHttpConnector connector = new ReactorClientHttpConnector(options -> options.httpProxy(addressSpec -> addressSpec
//                        .address(new InetSocketAddress("127.0.0.1", 1080)))
//        );
        System.out.println("问题为"+prompt);
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

    @Autowired
    private HttpService httpService;
    @PostMapping("/sse")
    public Flux<String> sse(@RequestBody ChatCompletionRequest chatCompletionRequest){
        if(chatCompletionRequest.getStream()){
            return httpService.openAIStylePostStream(chatCompletionRequest);
        }
        else{
            ChatResponse chatResponse = httpService.openAIStylePost(chatCompletionRequest.getMessages());
            Flux<String> just = Flux.just(chatResponse.toString());
            return just;
        }
    }

}
