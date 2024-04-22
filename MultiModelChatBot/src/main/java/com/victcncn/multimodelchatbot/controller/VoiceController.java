package com.victcncn.multimodelchatbot.controller;

import com.victcncn.multimodelchatbot.api.voice.WhisperClient;
import com.victcncn.multimodelchatbot.domain.ChatResponse;
import com.victcncn.multimodelchatbot.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/19 - 04 - 19 - 9:32
 * @Descreiption: com.victcncn.multimodelchatbot.controller
 * @Version: 1.0
 */
@RestController
@Slf4j
public class VoiceController {

    @Autowired
    private WhisperClient whisperClient;
    @PostMapping("/uploadVoice")
    @CrossOrigin
    public ChatResponse handleVoiceUpload(@RequestParam("audio") MultipartFile audioFile) {
        if (audioFile.isEmpty()) {
            return ChatResponse.builder().status(false).content("录音为空").build();
        }
        try {

            String fileName = UUID.randomUUID().toString() +".wav";
            String dir = FileUtils.uploadDir.get("voice");
            String path = dir + File.separator + fileName;
            log.info("保存路径为"+path);
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(audioFile.getBytes());
            fos.close();
            // 调用whisper服务
            ChatResponse chatResponse = whisperClient.generate(path);
            return chatResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return ChatResponse.builder().status(false).content("上传报错").build();
        }
    }
}
