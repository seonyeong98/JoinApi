package com.example.joinapi.file.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.FileCopyUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
public class DownloadUtil {

    public static void download(HttpServletRequest request, HttpServletResponse response,String filename,
                                String saveFilepath, String fileType)
        throws FileNotFoundException, UnsupportedEncodingException {
        File file = new File(saveFilepath);
        if (!file.exists()) throw new FileNotFoundException();

        response.setContentLength((int)file.length());

        response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20") + "\"");
        //setDisposition(request, response, filename);
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        if (fileType != null && fileType.startsWith("image")) {
            response.setHeader("Content-Type", fileType);
        }else {
            response.setHeader("Content-Type", "application/octet-stream");
        }

        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(file));
             BufferedOutputStream bos = new BufferedOutputStream(
                     response.getOutputStream())) {
            FileCopyUtils.copy(bis, bos);
            bos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
