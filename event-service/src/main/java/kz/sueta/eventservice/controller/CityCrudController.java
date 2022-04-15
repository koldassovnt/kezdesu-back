package kz.sueta.eventservice.controller;

import kz.sueta.eventservice.dto.request.*;
import kz.sueta.eventservice.dto.response.CityDetailResponse;
import kz.sueta.eventservice.dto.response.CityListResponse;
import kz.sueta.eventservice.dto.response.MessageResponse;
import kz.sueta.eventservice.register.CityCrudRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/city")
public class CityCrudController {

    private final CityCrudRegister cityCrudRegister;

    @Autowired
    public CityCrudController(CityCrudRegister cityCrudRegister) {
        this.cityCrudRegister = cityCrudRegister;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCity(@Valid @RequestBody CreateCityRequest request) {
        cityCrudRegister.saveCity(request);
        return ResponseEntity.status(200).body(MessageResponse.of("City successfully created!"));
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editCity(@Valid @RequestBody EditCityRequest request) {
        cityCrudRegister.editCity(request);
        return ResponseEntity.status(200).body(MessageResponse.of("City successfully edited!"));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteCity(@Valid @RequestBody DetailRequest request) {
        cityCrudRegister.deleteCity(request);
        return ResponseEntity.status(200).body(MessageResponse.of("City successfully deleted!"));
    }

    @GetMapping("/list")
    public ResponseEntity<?> listCity(@RequestParam(name = "limit", required = false) Integer limit,
                                      @RequestParam(name = "offset", required = false) Integer offset,
                                      @RequestParam(name = "actual", required = false) Boolean actual) {

        DictionaryFilter filter = new DictionaryFilter(limit, offset, actual);

        CityListResponse response = cityCrudRegister.listCity(filter);
        return ResponseEntity.status(200).body(response);
    }


    @GetMapping("/detail")
    public ResponseEntity<?> detailCity(@RequestParam(name = "id") String id) {
        CityDetailResponse response = cityCrudRegister.detailCity(DetailRequest.of(id));
        return ResponseEntity.status(200).body(response);
    }
}
