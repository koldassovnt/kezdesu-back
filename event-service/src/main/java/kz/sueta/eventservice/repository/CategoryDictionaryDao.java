package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.CategoryDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDictionaryDao extends JpaRepository<CategoryDictionary, Long> {

    long countCategoryDictionariesByCategoryIdAndActual(String categoryId, Boolean actual);

    CategoryDictionary findCategoryDictionaryByCategoryIdAndActual(String categoryId, Boolean actual);

    CategoryDictionary findCategoryDictionaryByCategoryId(String categoryId);
}
