package kz.sueta.eventservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.eventservice.dto.request.CreateCategoryRequest;
import kz.sueta.eventservice.dto.request.DetailRequest;
import kz.sueta.eventservice.dto.request.DictionaryFilter;
import kz.sueta.eventservice.dto.request.EditCategoryRequest;
import kz.sueta.eventservice.dto.response.CategoryDetailResponse;
import kz.sueta.eventservice.dto.response.CategoryListResponse;
import kz.sueta.eventservice.entity.CategoryDictionary;
import kz.sueta.eventservice.exception.NoDataByIdException;
import kz.sueta.eventservice.register.CategoryCrudRegister;
import kz.sueta.eventservice.repository.CategoryDictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.UUID;

@Component
public class CategoryCrudRegisterImpl implements CategoryCrudRegister {

    private final CategoryDictionaryDao categoryDictionaryDao;
    private final EntityManager entityManager;

    @Autowired
    public CategoryCrudRegisterImpl(CategoryDictionaryDao categoryDictionaryDao,
                                    EntityManager entityManager) {
        this.categoryDictionaryDao = categoryDictionaryDao;
        this.entityManager = entityManager;
    }

    @Override
    public void saveCategory(CreateCategoryRequest request) {
        CategoryDictionary category = new CategoryDictionary();
        category.categoryId = UUID.randomUUID().toString();
        category.actual = true;
        category.categoryLabel = request.categoryLabel;
        categoryDictionaryDao.save(category);
    }

    @Override
    public void editCategory(EditCategoryRequest request) {
        CategoryDictionary category = categoryDictionaryDao
                .findCategoryDictionaryByCategoryIdAndActual(request.categoryId, true);

        if (category == null) {
            throw new NoDataByIdException();
        }

        if (!Strings.isNullOrEmpty(request.categoryLabel)) {
            category.categoryLabel = request.categoryLabel;
        }

        categoryDictionaryDao.save(category);
    }

    @Override
    public void deleteCategory(DetailRequest request) {
        categoryDictionaryDao.updateCategoryActual(false, request.id);
    }

    @Override
    public CategoryListResponse listCategory(DictionaryFilter filter) {

        if (filter.limit == null) {
            filter.limit = 10;
        }

        if (filter.offset == null) {
            filter.offset = 0;
        }

        if (filter.actual == null) {
            filter.actual = true;
        }

        String sql =
                " select c.categoryId         as categoryId, " +
                        " c.categoryLabel     as label, " +
                        " c.actual            as actual " +
                        " from CategoryDictionary c " +
                        " where c.actual = :actual " +
                        " limit " + filter.limit + " offset " + filter.offset + " ";

        TypedQuery<CategoryDetailResponse> query = entityManager.createQuery(sql, CategoryDetailResponse.class);
        query.setParameter("actual", filter.actual);

        List<CategoryDetailResponse> responseList = query.getResultList();

        return CategoryListResponse.of(responseList);
    }

    @Override
    public CategoryDetailResponse detailCategory(DetailRequest request) {

        String sql =
                " select c.categoryId         as categoryId, " +
                        " c.categoryLabel     as label, " +
                        " c.actual            as actual " +
                        " from CategoryDictionary c " +
                        " where c.actual = true and c.categoryId = :categoryId ";

        TypedQuery<CategoryDetailResponse> query = entityManager.createQuery(sql, CategoryDetailResponse.class);
        query.setParameter("categoryId", request.id);

        return query.getSingleResult();
    }
}
