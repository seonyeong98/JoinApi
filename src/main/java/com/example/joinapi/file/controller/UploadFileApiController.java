package com.example.joinapi.file.controller;

import com.example.joinapi.file.domain.UploadFile;
import com.example.joinapi.file.model.dto.FileResponseDto;
import com.example.joinapi.file.service.FileUploadService;
import com.example.joinapi.notice.model.dto.GetInfoDto;
import com.example.joinapi.notice.repository.UserTableRepository;
import com.example.joinapi.notice.service.UserTableService;
import com.example.joinapi.file.utils.DownloadUtil;
import com.example.joinapi.file.utils.UploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@ResponseBody
@RestController
@ToString
@RequestMapping("/api")
public class UploadFileApiController {
    //컨트롤러에서 서비스를 부르고 서비스에서 레포지터리 불러오기

    @Value("${file.path}")
    private String file_path;

    private final UserTableService userTableService;
    private final UserTableRepository userTableRepository;
    private final FileUploadService fileUploadService;



    @GetMapping("/file/{id}")
    public void download(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
         FileResponseDto fileResponseDto = fileUploadService.findById(id);
         DownloadUtil.download(
                 request,
                 response,
                 fileResponseDto.getFileName(),
                 file_path + File.separator + fileResponseDto.getFileName(),
                 fileResponseDto.getFileType()
         );
    }


    /**파일첨부 API*/
    @PostMapping("/upload-file")
    public ResponseEntity<Map<String, List<UploadFile>>> upload(MultipartHttpServletRequest request) {
        Map<String, List<UploadFile>> result = UploadUtil.upload(file_path, request);
        return ResponseEntity.ok(result);
    }

}
