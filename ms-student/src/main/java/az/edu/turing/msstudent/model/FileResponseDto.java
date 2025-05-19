package az.edu.turing.msstudent.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileResponseDto {
    private String fileUrl;
    private String fileName;
    private String fileType;
    private long size;
}
