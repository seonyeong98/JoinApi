package com.example.joinapi.file.model.dto;

import com.example.joinapi.file.domain.UploadFile;
import lombok.Getter;

@Getter
public class FileResponseDto {
    private Integer id;
    private String fileName;
    private String filePath;
    private String fileType;

    public UploadFile toEntity() {
        return UploadFile.builder()
                .fileName(fileName)
                .filePath(filePath)
                .fileType(fileType)
                .build();
    }


    public FileResponseDto(UploadFile entity) {
        this.id = entity.getId();
        this.fileName = entity.getFileName();
        this.filePath = entity.getFilePath();
        this.fileType = entity.getFileType();
    }
}
