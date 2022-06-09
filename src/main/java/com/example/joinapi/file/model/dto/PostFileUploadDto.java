package com.example.joinapi.file.model.dto;

import com.example.joinapi.file.domain.UploadFile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostFileUploadDto {
    private Integer id;
    private String fileName;
    private String filePath;
    private String fileType;


    @Builder
    public PostFileUploadDto(Integer id, String fileName, String filePath , String fileType) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }

    public UploadFile toEntity() {
        return UploadFile.builder()
                .fileName(fileName)
                .filePath(filePath)
                .fileType(fileType)
                .build();
    }

}
