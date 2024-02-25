package kz.sueta.clientservice.beans.services;

import kz.sueta.clientservice.entity.Client;
import kz.sueta.clientservice.repository.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientDao clientDao;

    @Autowired
    public CustomUserDetailsService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {
        Client client = clientDao.findClientByClientIdAndActual(clientId, true);

        if (client == null) {
            throw new UsernameNotFoundException("kPjy3wQCks :: Client not found with clientId: " + clientId);
        }

        return CustomUserDetails.of(client);
    }
}
