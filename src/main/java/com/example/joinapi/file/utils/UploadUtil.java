package com.example.joinapi.file.utils;

import com.example.joinapi.file.domain.UploadFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.*;

public class UploadUtil {

    public static Map<String, List<UploadFile>> upload(String targetDirectory, MultipartHttpServletRequest request) {
        java.io.File directory = new java.io.File(targetDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        List<UploadFile> files = new ArrayList<>();

        request.getMultiFileMap().forEach((key, fileList) -> {
            fileList.forEach(v -> {
                UploadFile uploadFile = UploadFile.builder()
                        .fileName((new Date()).getTime() + "_" + v.getOriginalFilename())
                        .filePath(targetDirectory)
                        .fileType(v.getContentType())
                        .build();
                //fileInfos.add(fileInfo);
                files.add(uploadFile);
                try {
                    v.transferTo(new java.io.File(targetDirectory + uploadFile.getFileName()));
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

        Map<String, List<UploadFile>> result = new HashMap<>();
        result.put("data", files);

        return result;
    }
}
