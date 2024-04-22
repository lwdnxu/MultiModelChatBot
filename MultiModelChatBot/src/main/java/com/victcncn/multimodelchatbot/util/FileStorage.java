package com.victcncn.multimodelchatbot.util;

import java.util.*;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/21 - 04 - 21 - 13:32
 * @Descreiption: com.victcncn.multimodelchatbot.util
 * @Version: 1.0
 */
public class FileStorage {
    public static  Map<String, String> fileMap = new HashMap<>();
    public static  Map<String, String> lastImageFile = new HashMap<>();
    public static  Map<String, String> lastVideoFile = new HashMap<>();

    public static Set<String> documentExtensions = new HashSet<>(Arrays.asList("doc", "docx", "txt"));
    public static Set<String> imageExtensions = new HashSet<>(Arrays.asList("png", "jpg", "jpeg"));
    public static Set<String> videoExtensions = new HashSet<>(Arrays.asList("mp4", "avi"));
//    public static void put(String ids,String path){
//        fileMap.put(ids,path);
//    }
}
