package kz.sueta.clientservice.repository;

import kz.sueta.clientservice.entity.SmsForAuth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsForAuthDao extends CrudRepository<SmsForAuth, String> {

}
