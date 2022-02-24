package kz.sueta.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileListResponse {
    public List<FileDetailResponse> fileDetailResponses = new ArrayList<>();

    public void addFileDetail(FileDetailResponse detailResponse) {
        fileDetailResponses.add(detailResponse);
    }
}
