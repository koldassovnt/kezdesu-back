package kz.sueta.clientservice.controller;

import kz.sueta.clientservice.dto.services.response.CategoryListResponse;
import kz.sueta.clientservice.register.EventRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/client")
public class CategoryController {

    final
    EventRegister eventRegister;

    @Autowired
    public CategoryController(EventRegister eventRegister) {
        this.eventRegister = eventRegister;
    }

    @GetMapping("/action/category-list")
    public ResponseEntity<?> categoryList() {
        CategoryListResponse response = eventRegister.categoryList();
        return ResponseEntity.status(200).body(response);
    }
}
