package kz.sueta.adminservice.register;

import kz.sueta.adminservice.dto.services.request.CreateCategoryRequest;
import kz.sueta.adminservice.dto.services.request.DetailRequest;
import kz.sueta.adminservice.dto.services.request.DictionaryFilter;
import kz.sueta.adminservice.dto.services.request.EditCategoryRequest;
import kz.sueta.adminservice.dto.services.response.CategoryDetailResponse;
import kz.sueta.adminservice.dto.services.response.CategoryListResponse;
import kz.sueta.adminservice.dto.ui.response.MessageResponse;

public interface CategoryRegister {

    MessageResponse saveCategory(CreateCategoryRequest request);

    MessageResponse editCategory(EditCategoryRequest request);

    MessageResponse deleteCategory(DetailRequest request);

    CategoryListResponse listCategory(DictionaryFilter filter);

    CategoryDetailResponse detailCategory(DetailRequest request);
}
