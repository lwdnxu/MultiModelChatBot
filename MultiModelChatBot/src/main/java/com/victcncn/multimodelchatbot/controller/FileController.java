package com.victcncn.multimodelchatbot.controller;

import com.victcncn.multimodelchatbot.domain.FileResponse;
import com.victcncn.multimodelchatbot.util.FileStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.net.www.content.image.png;

import javax.security.sasl.SaslServer;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.CollationKey;
import java.util.Arrays;
import java.util.UUID;

/**
 * @Author: lwdnxu
 * @Date: 2024/4/18 - 04 - 18 - 21:33
 * @Descreiption: com.victcncn.multimodelchatbot.controller
 * @Version: 1.0
 */
@RestController
public class FileController {


    @GetMapping("/generateUUID")
    @CrossOrigin
    public String generateUUID(HttpServletRequest request) {

        HttpSession session = request.getSession();
        UUID uuid = UUID.randomUUID();
        session.setAttribute("uuid", uuid.toString());
        System.out.println("存入值成功");
        return uuid.toString();
    }
    @PostMapping("/uploadFile")
    @CrossOrigin
    public FileResponse fileUpload(@RequestParam("file") MultipartFile file,@RequestParam("uuid")String uuid,HttpServletRequest request,HttpSession session) {
        session.setAttribute("uuid",uuid);
//        System.out.println(request.getAttribute("uuid"));
        if (!file.isEmpty()) {
            try (InputStream inputStream = file.getInputStream()) {
                // 生成UUID作为文件名
                String extensionType = getFileExtension(file.getOriginalFilename());
                String fileName = UUID.randomUUID().toString() + extensionType;
                // 获取保存目录
                File staticDir = ResourceUtils.getFile("classpath:static");
                String dir = staticDir.getAbsolutePath() + File.separator + "upload";
                // 文件保存路径
                String path = dir + File.separator + fileName;

                try (OutputStream outputStream = new FileOutputStream(new File(path))) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                System.out.println("保存文件路径为" + path);
                extensionType = extensionType.substring(1);
                if (FileStorage.documentExtensions.contains(extensionType)) {
                    // 后接入向量数据库
                } else if (FileStorage.imageExtensions.contains(extensionType)) {
                    // 将<cookieId,path> 存储最后一个图片的位置
                    FileStorage.lastImageFile.put(uuid,path);
                } else if (FileStorage.videoExtensions.contains(extensionType)) {
                    // 视频处理逻辑
                    FileStorage.lastVideoFile.put(uuid,path);
                }
                return new FileResponse(1, path);
            } catch (IOException e) {
                e.printStackTrace();
                return new FileResponse(0, "上传失败");
            }
        } else {
            return new FileResponse(0, "文件为空");
        }
    }

    // 获取文件扩展名
    private String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }
}
