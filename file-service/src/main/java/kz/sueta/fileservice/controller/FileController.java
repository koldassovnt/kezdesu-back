package kz.sueta.fileservice.controller;

import kz.sueta.fileservice.dto.FileIdModel;
import kz.sueta.fileservice.dto.FileListRequest;
import kz.sueta.fileservice.dto.FileListResponse;
import kz.sueta.fileservice.register.FileRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileRegister fileRegister;

    @Autowired
    public FileController(FileRegister fileRegister) {
        this.fileRegister = fileRegister;
    }

    @PostMapping(value = "/save-file",
            consumes = {"multipart/form-data"})
    public ResponseEntity<?> saveFile(@RequestParam("file") MultipartFile file) {
        FileIdModel response = fileRegister.saveFile(file);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/getFiles")
    public ResponseEntity<?> getFiles(@Valid @RequestBody FileListRequest fileListRequest) {
        FileListResponse response = fileRegister.getFiles(fileListRequest);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/get-file-type")
    public ResponseEntity<?> getFileType(@RequestParam(value = "id") String id) {
        String type = fileRegister.getFileType(id);
        return ResponseEntity.status(200).body(type);
    }
}
