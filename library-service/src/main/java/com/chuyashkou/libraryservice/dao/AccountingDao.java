package com.chuyashkou.libraryservice.dao;

import com.chuyashkou.libraryservice.mapper.AccountingRowMapper;
import com.chuyashkou.libraryservice.model.Accounting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountingDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean addNewAccounting(String date, String status, Long bookId, Long clientId, Long userId) {
        return jdbcTemplate.update("INSERT INTO ACCOUNTING (accounting_date, accounting_status, book_id, client_id, user_id) " +
                "VALUES (?,?,?,?,?)", date, status, bookId, clientId, userId) > 0;
    }

    public List<Accounting> getAllAccounting() {
        return jdbcTemplate.query("SELECT * from accounting", new AccountingRowMapper());
    }

}
