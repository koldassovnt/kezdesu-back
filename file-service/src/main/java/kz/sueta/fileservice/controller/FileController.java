package kz.sueta.fileservice.controller;

import io.swagger.annotations.ApiOperation;
import kz.sueta.fileservice.dto.FileCreateRequest;
import kz.sueta.fileservice.dto.FileIdModel;
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

    @ApiOperation("Сохранение файла")
    @PostMapping(value = "/save-file")
    public ResponseEntity<?> saveFile(@Valid @RequestBody FileCreateRequest request) {
        FileIdModel response = fileRegister.saveFile(request);
        return ResponseEntity.status(200).body(response);
    }

    @ApiOperation("Получения файла")
    @GetMapping("/get-file")
    public ResponseEntity<?> getFiles(@RequestParam(value = "id") String id) {
        String response = fileRegister.getFileById(id);
        return ResponseEntity.status(200).body(response);
    }

    @ApiOperation("Получения типа файла")
    @GetMapping("/get-file-type")
    public ResponseEntity<?> getFileType(@RequestParam(value = "id") String id) {
        String type = fileRegister.getFileType(id);
        return ResponseEntity.status(200).body(type);
    }
}
