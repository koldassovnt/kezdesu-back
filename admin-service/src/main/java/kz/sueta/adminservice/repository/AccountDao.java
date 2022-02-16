package kz.sueta.adminservice.repository;

import kz.sueta.adminservice.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends CrudRepository<Account, Long> {

    Account findAccountByEmailAndActual(String email, boolean b);
}
