package kz.sueta.fileservice.controller;

import kz.sueta.fileservice.dto.FileCreateRequest;
import kz.sueta.fileservice.dto.FileIdModel;
import kz.sueta.fileservice.dto.FileListRequest;
import kz.sueta.fileservice.dto.FileListResponse;
import kz.sueta.fileservice.register.FileRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileRegister fileRegister;

    @Autowired
    public FileController(FileRegister fileRegister) {
        this.fileRegister = fileRegister;
    }

    @PostMapping("/saveFile")
    public ResponseEntity<?> saveFile(@Valid @RequestBody FileCreateRequest fileCreateRequest) {
        FileIdModel response = fileRegister.saveFile(fileCreateRequest);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/getFiles")
    public ResponseEntity<?> getFiles(@Valid @RequestBody FileListRequest fileListRequest) {
        FileListResponse response = fileRegister.getFiles(fileListRequest);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/getFileType")
    public ResponseEntity<?> getFileType(@RequestParam(value = "id") String id) {
        String type = fileRegister.getFileType(id);
        return ResponseEntity.status(200).body(type);
    }
}
