package kz.sueta.eventservice.register;

import kz.sueta.eventservice.dto.request.*;
import kz.sueta.eventservice.dto.response.CityDetailResponse;
import kz.sueta.eventservice.dto.response.CityListResponse;

public interface CityCrudRegister {

    void saveCity(CreateCityRequest request);

    void editCity(EditCityRequest request);

    void deleteCity(DetailRequest request);

    CityListResponse listCity(DictionaryFilter filter);

    CityDetailResponse detailCity(DetailRequest request);
}
