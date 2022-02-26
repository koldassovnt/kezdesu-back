package kz.sueta.eventservice.controller;

import kz.sueta.eventservice.dto.request.*;
import kz.sueta.eventservice.dto.response.*;
import kz.sueta.eventservice.register.CategoryCrudRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryCrudController {

    private final CategoryCrudRegister categoryCrudRegister;

    @Autowired
    public CategoryCrudController(CategoryCrudRegister categoryCrudRegister) {
        this.categoryCrudRegister = categoryCrudRegister;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CreateCategoryRequest request) {
        categoryCrudRegister.saveCategory(request);
        return ResponseEntity.status(200).body(MessageResponse.of("Category successfully created!"));
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editCategory(@Valid @RequestBody EditCategoryRequest request) {
        categoryCrudRegister.editCategory(request);
        return ResponseEntity.status(200).body(MessageResponse.of("Category successfully edited!"));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteCategory(@Valid @RequestBody DetailRequest request) {
        categoryCrudRegister.deleteCategory(request);
        return ResponseEntity.status(200).body(MessageResponse.of("Category successfully deleted!"));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listCategory(@Valid @RequestBody DictionaryFilter filter) {
        CategoryListResponse response = categoryCrudRegister.listCategory(filter);
        return ResponseEntity.status(200).body(response);
    }


    @GetMapping("/detail")
    public ResponseEntity<?> detailCategory(@Valid @RequestBody DetailRequest request) {
        CategoryDetailResponse response = categoryCrudRegister.detailCategory(request);
        return ResponseEntity.status(200).body(response);
    }
}
