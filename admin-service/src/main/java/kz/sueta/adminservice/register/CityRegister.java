package kz.sueta.adminservice.register;

import kz.sueta.adminservice.dto.services.request.CreateCityRequest;
import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.DictionaryFilter;
import kz.sueta.adminservice.dto.services.request.EditCityRequest;
import kz.sueta.adminservice.dto.services.response.CityDetailResponse;
import kz.sueta.adminservice.dto.services.response.CityListResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;

public interface CityRegister {

    MessageResponse saveCity(CreateCityRequest request);

    MessageResponse editCity(EditCityRequest request);

    MessageResponse deleteCity(DetailRequest request);

    CityListResponse listCity(DictionaryFilter filter);

    CityDetailResponse detailCity(DetailRequest request);
}
