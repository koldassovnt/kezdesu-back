package kz.sueta.adminservice.register.impl;

import kz.sueta.adminservice.dto.services.request.CreateCategoryRequest;
import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.DictionaryFilter;
import kz.sueta.adminservice.dto.services.request.EditCategoryRequest;
import kz.sueta.adminservice.dto.services.response.CategoryDetailResponse;
import kz.sueta.adminservice.dto.services.response.CategoryListResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;
import kz.sueta.adminservice.register.CategoryRegister;
import kz.sueta.adminservice.service_messaging.EventServiceClient;
import kz.sueta.adminservice.util.ServiceFallbackStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryRegisterImpl implements CategoryRegister {

    private final EventServiceClient eventServiceClient;

    @Autowired
    public CategoryRegisterImpl(EventServiceClient eventServiceClient) {
        this.eventServiceClient = eventServiceClient;
    }

    @Override
    public MessageResponse saveCategory(CreateCategoryRequest request) {

        MessageResponse messageResponse = eventServiceClient.saveCategory(request);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("KTqNsVzogG :: event service calling returned error for SAVE");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse editCategory(EditCategoryRequest request) {
        MessageResponse messageResponse = eventServiceClient.editCategory(request);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("SbKg1X1m91 :: event service calling returned error for EDIT");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse deleteCategory(DetailRequest request) {
        MessageResponse messageResponse = eventServiceClient.deleteCategory(request);

        if (ServiceFallbackStatic.SERVICE_CALL_ERROR_MESSAGE.equals(messageResponse.message)) {
            throw new RuntimeException("N0cx294ae6 :: event service calling returned error for DELETE");
        }

        return messageResponse;
    }

    @Override
    public CategoryListResponse listCategory(DictionaryFilter filter) {
        return eventServiceClient.listCategory(filter.limit, filter.offset, filter.actual);
    }

    @Override
    public CategoryDetailResponse detailCategory(DetailRequest request) {

        CategoryDetailResponse detailResponse = eventServiceClient.detailCategory(request.id);

        if (detailResponse == null) {
            throw new RuntimeException("443014svY0 :: event service calling returned error for DETAIL");
        }

        return detailResponse;
    }
}
