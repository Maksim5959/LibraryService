package com.chuyashkou.libraryservice.service;

import com.chuyashkou.libraryservice.dao.AccountingDao;
import com.chuyashkou.libraryservice.model.Accounting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountingService {

    @Autowired
    AccountingDao accountingDao = new AccountingDao();

    public boolean addNewAccounting(String date, String status, Long bookId, Long clientId, Long userId) {
        return accountingDao.addNewAccounting(date, status, bookId, clientId, userId);
    }

    public List<Accounting> getAllAccounting() {
        return accountingDao.getAllAccounting();
    }

}
