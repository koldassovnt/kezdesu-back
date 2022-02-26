package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.CityDictionary;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDictionaryDao extends CrudRepository<CityDictionary, Long> {

    CityDictionary findCityDictionaryByCityIdAndActual(String dictId, Boolean actual);

    @Modifying
    @Query("update CityDictionary c set c.actual = :actual where c.cityId = :cityId")
    void updateCityActual(@Param("actual") Boolean actual, @Param("cityId") String categoryId);
}
