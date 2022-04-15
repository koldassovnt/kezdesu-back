package kz.sueta.adminservice.register.impl;

import kz.sueta.adminservice.dto.services.request.CreateCityRequest;
import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.DictionaryFilter;
import kz.sueta.adminservice.dto.services.request.EditCityRequest;
import kz.sueta.adminservice.dto.services.response.CityDetailResponse;
import kz.sueta.adminservice.dto.services.response.CityListResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.register.CityRegister;
import kz.sueta.adminservice.service_messaging.EventServiceClient;
import kz.sueta.adminservice.util.ServiceFallbackStatic;
import org.springframework.stereotype.Component;

@Component
public class CityRegisterImpl implements CityRegister {

    private final EventServiceClient eventServiceClient;

    public CityRegisterImpl(EventServiceClient eventServiceClient) {
        this.eventServiceClient = eventServiceClient;
    }

    @Override
    public MessageResponse saveCity(CreateCityRequest request) {
        MessageResponse response = eventServiceClient.saveCity(request);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(response.message)) {
            throw new RuntimeException("ue4lF2fAo9 :: event service calling returned error for SAVE");
        }

        return response;
    }

    @Override
    public MessageResponse editCity(EditCityRequest request) {
        MessageResponse response = eventServiceClient.editCity(request);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(response.message)) {
            throw new RuntimeException("M8wIWyfKdI :: event service calling returned error for EDIT");
        }

        return response;
    }

    @Override
    public MessageResponse deleteCity(DetailRequest request) {
        MessageResponse response = eventServiceClient.deleteCity(request);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(response.message)) {
            throw new RuntimeException("3C3g9QqwsQ :: event service calling returned error for DELETE");
        }

        return response;
    }

    @Override
    public CityListResponse listCity(DictionaryFilter filter) {
        return eventServiceClient.listCity(filter.limit, filter.offset, filter.actual);
    }

    @Override
    public CityDetailResponse detailCity(DetailRequest request) {

        CityDetailResponse response = eventServiceClient.detailCity(request.id);

        if (response == null) {
            throw new RuntimeException("qW3jJU1OGK :: event service calling returned error for DETAIL");
        }

        return response;
    }
}
