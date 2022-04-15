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
    public ResponseEntity<?> listCategory(@RequestParam(name = "limit", required = false) Integer limit,
                                          @RequestParam(name = "offset", required = false) Integer offset,
                                          @RequestParam(name = "actual", required = false) Boolean actual) {

        DictionaryFilter filter = new DictionaryFilter(limit, offset, actual);

        CategoryListResponse response = categoryCrudRegister.listCategory(filter);
        return ResponseEntity.status(200).body(response);
    }


    @GetMapping("/detail")
    public ResponseEntity<?> detailCategory(@RequestParam(name = "id") String id) {
        CategoryDetailResponse response = categoryCrudRegister.detailCategory(DetailRequest.of(id));
        return ResponseEntity.status(200).body(response);
    }
}
