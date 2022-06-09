package com.example.joinapi.file.repository;

import com.example.joinapi.file.domain.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile, Integer> {
}
