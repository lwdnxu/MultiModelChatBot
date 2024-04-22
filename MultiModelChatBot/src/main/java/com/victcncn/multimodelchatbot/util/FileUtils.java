package com.victcncn.multimodelchatbot.util;

import cn.hutool.core.lang.hash.Hash;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/19 - 04 - 19 - 10:35
 * @Descreiption: com.victcncn.multimodelchatbot.util
 * @Version: 1.0
 */
@Slf4j
public class FileUtils {
    public static Map<String, String> uploadDir = new HashMap<>();
    static{
        File staticDir = null;
        try {
            staticDir = ResourceUtils.getFile("classpath:static");
            String[] strs = {"doc","voice","image","video"};
            for (String str : strs) {
                String dir = staticDir.getAbsolutePath() + File.separator + "upload" + File.separator + str;
                uploadDir.put(str,dir);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

    }
    // 初始化创建各种文件夹 ,也可以不保存在本地，用oss也可
    public static void initCreateDir() {
        for (String key : uploadDir.keySet()) {
            String dir = uploadDir.get(key);
            doCreate(dir);
        }

        log.info("文件夹初始化成功");
    }
    private static void doCreate(String dir){
        if (!Files.exists(Paths.get(dir))) {
            try {
                Files.createDirectories(Paths.get(dir));
            } catch (IOException e) {
                System.err.println("Failed to create directory: " + e.getMessage());
            }
        }
    }

    public static String generateFile(String extensionType ,String file)  {
        String fileName = UUID.randomUUID().toString() + extensionType;
        // 获取保存目录
        File staticDir = null;
        try {
            staticDir = ResourceUtils.getFile("classpath:static");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String dir = staticDir.getAbsolutePath() + File.separator + "upload";
        String path = dir + File.separator +file +File.separator+ fileName;
        return path;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(FileUtils.generateFile(".doc","voice"));
    }
}
