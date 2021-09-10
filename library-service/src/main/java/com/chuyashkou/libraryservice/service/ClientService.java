package com.chuyashkou.libraryservice.service;

import com.chuyashkou.libraryservice.dao.ClientDao;
import com.chuyashkou.libraryservice.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    ClientDao clientDao = new ClientDao();

    public boolean addClient(Client client) {
        try {
            clientDao.getClient(client);
            return false;
        } catch (EmptyResultDataAccessException e) {
            clientDao.addClient(client);
            return true;
        }
    }

    public Client getClient(Client client) {
        try {
            return clientDao.getClient(client);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Client> getAllClients() {
        return clientDao.getAllClients();
    }

    public Client getClientById(Long id) {
        return clientDao.getClientById(id);
    }

    public boolean deleteClientById(Long id) {
        return clientDao.deleteClientById(id);

    }

    public boolean updateClientById(Client client) {
        return clientDao.updateClientById(client);
    }

    public List<Client> getClientsByName(Client client) {
        return clientDao.getClientsByName(client);
    }

    public List<Client> getClientsByBirthday(Client client) {
        return clientDao.getClientsByBirthday(client);
    }
}
