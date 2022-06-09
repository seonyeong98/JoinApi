package com.example.joinapi.notice.model.dto;

import com.example.joinapi.file.model.dto.PostFileUploadDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PostUpdateRequestDto {
    private String name;
    private String email;
    private String pnu;
    private String gender;
    private LocalDate birth;
    private Integer fileId;
    private PostFileUploadDto fileInfo;

}

