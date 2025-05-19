package az.edu.turing.msstudent.service;

import az.edu.turing.msstudent.model.FileResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    FileResponseDto storeFile(MultipartFile file, Long ownerId);
}
