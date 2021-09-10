package com.chuyashkou.libraryservice.dao;

import com.chuyashkou.libraryservice.mapper.ClientRowMapper;
import com.chuyashkou.libraryservice.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean addClient(Client client) throws EmptyResultDataAccessException {
        return jdbcTemplate.update("INSERT INTO CLIENTS (clients.firstname, clients.lastname, clients.birthday) " +
                "VALUES (?,?,?)", client.getFirstname(), client.getLastname(), client.getBirthday()) > 0;
    }

    public Client getClient(Client client) throws EmptyResultDataAccessException {
        return jdbcTemplate.queryForObject("SELECT id FROM clients where clients.lastname = ? and clients.birthday = ? and clients.firstname = ? ",
                new BeanPropertyRowMapper<>(Client.class), client.getLastname(), client.getBirthday(), client.getFirstname());
    }


    public Client getClientById(Long id) {
        return jdbcTemplate.queryForObject("SELECT firstname, lastname, birthday FROM clients where clients.id = ?  ",
                new BeanPropertyRowMapper<>(Client.class), id);
    }

    public List<Client> getAllClients() {
        return jdbcTemplate.query("SELECT * FROM clients", new ClientRowMapper());
    }

    public boolean deleteClientById(Long id) {
        return jdbcTemplate.update("DELETE FROM clients where clients.id = ? ", id) > 0;
    }

    public boolean updateClientById(Client client) {
        return jdbcTemplate.update("UPDATE clients SET clients.firstname = ?, clients.lastname = ?, " +
                        "clients.birthday = ? where clients.id = ?",
                client.getFirstname(), client.getLastname(), client.getBirthday(), client.getId()) > 0;
    }

    public List<Client> getClientsByName(Client client) {
        return jdbcTemplate.query("SELECT * FROM clients where clients.firstname = ? and clients.lastname = ?",
                new ClientRowMapper(), client.getFirstname(), client.getLastname());
    }

    public List<Client> getClientsByBirthday(Client client) {
        return jdbcTemplate.query("SELECT * FROM clients where clients.birthday = ?",
                new ClientRowMapper(), client.getBirthday());
    }
}
