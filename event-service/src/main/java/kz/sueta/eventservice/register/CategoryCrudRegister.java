package kz.sueta.eventservice.register;

import kz.sueta.eventservice.dto.request.CreateCategoryRequest;
import kz.sueta.eventservice.dto.request.DetailRequest;
import kz.sueta.eventservice.dto.request.DictionaryFilter;
import kz.sueta.eventservice.dto.request.EditCategoryRequest;
import kz.sueta.eventservice.dto.response.CategoryDetailResponse;
import kz.sueta.eventservice.dto.response.CategoryListResponse;

public interface CategoryCrudRegister {

    void saveCategory(CreateCategoryRequest request);

    void editCategory(EditCategoryRequest request);

    void deleteCategory(DetailRequest request);

    CategoryListResponse listCategory(DictionaryFilter filter);

    CategoryDetailResponse detailCategory(DetailRequest request);
}
