package kz.sueta.adminservice.controller;

import kz.sueta.adminservice.dto.services.request.CreateCityRequest;
import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.DictionaryFilter;
import kz.sueta.adminservice.dto.services.request.EditCityRequest;
import kz.sueta.adminservice.dto.services.response.CityDetailResponse;
import kz.sueta.adminservice.dto.services.response.CityListResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.register.CityRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class CityController {

    private final CityRegister cityRegister;

    @Autowired
    public CityController(CityRegister cityRegister) {
        this.cityRegister = cityRegister;
    }

    @PostMapping("/action/save-city")
    public ResponseEntity<?> saveCity(@Valid @RequestBody CreateCityRequest request) {
        MessageResponse response = cityRegister.saveCity(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/action/edit-city")
    public ResponseEntity<?> editCity(@Valid @RequestBody EditCityRequest request) {
        MessageResponse response = cityRegister.editCity(request);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/action/delete-city")
    public ResponseEntity<?> deleteCity(@Valid @RequestBody DetailRequest request) {
        MessageResponse response = cityRegister.deleteCity(request);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/action/list-city")
    public ResponseEntity<?> listCity(@RequestParam(name = "limit", required = false) Integer limit,
                                      @RequestParam(name = "offset", required = false) Integer offset,
                                      @RequestParam(name = "actual", required = false) Boolean actual) {

        DictionaryFilter filter = new DictionaryFilter(limit, offset, actual);

        CityListResponse response = cityRegister.listCity(filter);
        return ResponseEntity.status(200).body(response);
    }


    @GetMapping("/action/detail-city")
    public ResponseEntity<?> detailCity(@RequestParam(name = "id") String id) {
        CityDetailResponse response = cityRegister.detailCity(DetailRequest.of(id));
        return ResponseEntity.status(200).body(response);
    }
}
