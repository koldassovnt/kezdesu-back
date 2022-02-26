package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.CategoryDictionary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDictionaryDao extends CrudRepository<CategoryDictionary, Long> {

    long countCategoryDictionariesByCategoryIdAndActual(String categoryId, Boolean actual);

    CategoryDictionary findCategoryDictionaryByCategoryIdAndActual(String categoryId, Boolean actual);

    @Modifying
    @Query("update CategoryDictionary c set c.actual = :actual where c.categoryId = :categoryId")
    void updateCategoryActual(@Param("actual") Boolean actual, @Param("categoryId") String categoryId);
}
