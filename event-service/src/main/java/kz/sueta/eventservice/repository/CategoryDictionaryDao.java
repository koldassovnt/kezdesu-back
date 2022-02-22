package kz.sueta.eventservice.repository;

import kz.sueta.eventservice.entity.CityDictionary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDictionaryDao extends CrudRepository<CityDictionary, Long> {
}
