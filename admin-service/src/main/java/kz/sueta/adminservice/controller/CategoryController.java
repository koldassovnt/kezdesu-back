package kz.sueta.adminservice.controller;

import kz.sueta.adminservice.dto.services.request.CreateCategoryRequest;
import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.DictionaryFilter;
import kz.sueta.adminservice.dto.services.request.EditCategoryRequest;
import kz.sueta.adminservice.dto.services.response.CategoryDetailResponse;
import kz.sueta.adminservice.dto.services.response.CategoryListResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.register.CategoryRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class CategoryController {

    private final CategoryRegister categoryRegister;

    @Autowired
    public CategoryController(CategoryRegister categoryRegister) {
        this.categoryRegister = categoryRegister;
    }

    @PostMapping("/action/save-category")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CreateCategoryRequest request) {
        MessageResponse response = categoryRegister.saveCategory(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/action/edit-category")
    public ResponseEntity<?> editCategory(@Valid @RequestBody EditCategoryRequest request) {
        MessageResponse response = categoryRegister.editCategory(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/action/delete-category")
    public ResponseEntity<?> deleteCategory(@Valid @RequestBody DetailRequest request) {
        MessageResponse response = categoryRegister.deleteCategory(request);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/action/list-category")
    public ResponseEntity<?> listCategory(@RequestParam(name = "limit", required = false) Integer limit,
                                          @RequestParam(name = "offset", required = false) Integer offset,
                                          @RequestParam(name = "actual", required = false) Boolean actual) {

        DictionaryFilter filter = new DictionaryFilter(limit, offset, actual);

        CategoryListResponse response = categoryRegister.listCategory(filter);
        return ResponseEntity.status(200).body(response);
    }


    @GetMapping("/action/detail-category")
    public ResponseEntity<?> detailCategory(@RequestParam(name = "id") String id) {
        CategoryDetailResponse response = categoryRegister.detailCategory(DetailRequest.of(id));
        return ResponseEntity.status(200).body(response);
    }
}
