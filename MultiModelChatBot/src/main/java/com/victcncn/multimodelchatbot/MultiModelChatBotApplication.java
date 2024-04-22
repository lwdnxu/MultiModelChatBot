package com.victcncn.multimodelchatbot;

import com.victcncn.multimodelchatbot.util.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.FileNotFoundException;
import java.util.Properties;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/16 - 04 - 16 - 10:39
 * @Descreiption: com.victcncn.multimodelchatbot
 * @Version: 1.0
 */
@SpringBootApplication
@CrossOrigin
public class MultiModelChatBotApplication {
    public static void main(String[] args)  {
        FileUtils.initCreateDir();
        SpringApplication.run(MultiModelChatBotApplication.class,args);
    }


}
