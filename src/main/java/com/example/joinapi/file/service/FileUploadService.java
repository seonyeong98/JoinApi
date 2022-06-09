package com.example.joinapi.file.service;

import com.example.joinapi.file.domain.UploadFile;
import com.example.joinapi.file.model.dto.FileResponseDto;
import com.example.joinapi.file.repository.UploadFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


@Service
@RequiredArgsConstructor

public class FileUploadService {

    private final UploadFileRepository uploadFileRepository;

    public FileResponseDto findById(Integer id) {
        UploadFile entity = uploadFileRepository.findById(id).orElseThrow(RuntimeException::new);
        return new FileResponseDto(entity);
    }

    @Transactional
    public void delete(Integer id) {
        UploadFile entity = uploadFileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (entity.getId().equals(id)) {
            java.io.File file = new java.io.File(entity.getFilePath() + entity.getFileName());
            if(file.exists()) {
                file.delete();
            }
            uploadFileRepository.delete(entity);
        }
    }
}
