package kz.sueta.eventservice.register;

import kz.sueta.eventservice.dto.request.CityDetailRequest;
import kz.sueta.eventservice.dto.request.CreateCityRequest;
import kz.sueta.eventservice.dto.request.DictionaryFilter;
import kz.sueta.eventservice.dto.request.EditCityRequest;
import kz.sueta.eventservice.dto.response.CityDetailResponse;
import kz.sueta.eventservice.dto.response.CityListResponse;

public interface CityCrudRegister {

    void saveCity(CreateCityRequest request);

    void editCity(EditCityRequest request);

    void deleteCity(CityDetailRequest request);

    CityListResponse listCity(DictionaryFilter filter);

    CityDetailResponse detailCity(CityDetailRequest request);
}
