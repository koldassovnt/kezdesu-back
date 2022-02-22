package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.CityDictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityDictionaryDao extends CrudRepository<CityDictionary, Long> {
}
