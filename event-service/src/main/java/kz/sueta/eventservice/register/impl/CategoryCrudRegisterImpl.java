package kz.sueta.eventservice.register.impl;

import com.google.common.base.Strings;
import kz.sueta.eventservice.dto.request.CreateCategoryRequest;
import kz.sueta.eventservice.dto.request.DetailRequest;
import kz.sueta.eventservice.dto.request.DictionaryFilter;
import kz.sueta.eventservice.dto.request.EditCategoryRequest;
import kz.sueta.eventservice.dto.response.CategoryDetailResponse;
import kz.sueta.eventservice.dto.response.CategoryListResponse;
import kz.sueta.eventservice.dto.response.CityDetailResponse;
import kz.sueta.eventservice.entity.CategoryDictionary;
import kz.sueta.eventservice.exception.NoDataByIdException;
import kz.sueta.eventservice.register.CategoryCrudRegister;
import kz.sueta.eventservice.repository.CategoryDictionaryDao;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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

    @SneakyThrows
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
                " select c.category_id         as categoryId, " +
                        " c.category_label     as label, " +
                        " c.actual            as actual " +
                        " from category_dictionary c " +
                        " where c.actual = ? " +
                        " limit " + filter.limit + " offset " + filter.offset + " ";

        List<CategoryDetailResponse> responseList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/event-service",
                "admin",
                "admin")) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setBoolean(1, filter.actual);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        CategoryDetailResponse detailResponse = new CategoryDetailResponse();
                        detailResponse.categoryId = rs.getString("categoryId");
                        detailResponse.label = rs.getString("label");
                        detailResponse.actual = rs.getBoolean("actual");
                        responseList.add(detailResponse);
                    }
                }
            }
        }

        return CategoryListResponse.of(responseList);
    }

    @SneakyThrows
    @Override
    public CategoryDetailResponse detailCategory(DetailRequest request) {

        String sql =
                " select c.category_id         as categoryId, " +
                        " c.category_label     as label, " +
                        " c.actual            as actual " +
                        " from category_dictionary c " +
                        " where c.actual = true and c.category_id = ? ";

        CategoryDetailResponse detailResponse = new CategoryDetailResponse();

        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/event-service",
                "admin",
                "admin")) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, request.id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        detailResponse.categoryId = rs.getString("categoryId");
                        detailResponse.label = rs.getString("label");
                        detailResponse.actual = rs.getBoolean("actual");
                    }
                }
            }
        }

        return detailResponse;
    }
}
