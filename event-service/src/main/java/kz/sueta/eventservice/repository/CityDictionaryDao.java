package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.CityDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDictionaryDao extends JpaRepository<CityDictionary, Long> {

    CityDictionary findCityDictionaryByCityIdAndActual(String dictId, Boolean actual);

    CityDictionary findCityDictionaryByCityId(String dictId);
}
