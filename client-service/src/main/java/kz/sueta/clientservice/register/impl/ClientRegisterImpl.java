package kz.sueta.clientservice.register.impl;

import kz.sueta.clientservice.dto.ui.request.PhoneSmsRequest;
import kz.sueta.clientservice.entity.Client;
import kz.sueta.clientservice.register.ClientRegister;
import kz.sueta.clientservice.repository.ClientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Component
public class ClientRegisterImpl implements ClientRegister {

    private final PasswordEncoder passwordEncoder;

    private final ClientDao clientDao;

    @Autowired
    public ClientRegisterImpl(PasswordEncoder passwordEncoder, ClientDao clientDao) {
        this.passwordEncoder = passwordEncoder;
        this.clientDao = clientDao;
    }

    @Override
    public Client saveClient(PhoneSmsRequest phoneSmsRequest) {
        Client client = new Client();
        client.clientId = UUID.randomUUID().toString();
        client.phone = phoneSmsRequest.phoneNumber;
        client.createdAt = new Timestamp(new Date().getTime());
        client.password = passwordEncoder.encode(phoneSmsRequest.phoneNumber + client.clientId);
        client.actual = true;

        clientDao.save(client);

        return client;
    }

    @Override
    public Client getClient(PhoneSmsRequest phoneSmsRequest) {
        return clientDao.findClientByPhoneAndActual(phoneSmsRequest.phoneNumber, true);
    }

}
