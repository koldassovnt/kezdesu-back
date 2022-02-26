package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.CategoryDictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDictionaryDao extends CrudRepository<CategoryDictionary, Long> {

    long countCategoryDictionariesByCategoryIdAndActual(String categoryId, Boolean actual);
}
