package com.chuyashkou.libraryservice.service;

import com.chuyashkou.libraryservice.dao.PublishingHouseDao;
import com.chuyashkou.libraryservice.model.PublishingHouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublishingHouseService {
    @Autowired
    PublishingHouseDao publishingHouseDao = new PublishingHouseDao();

    public boolean addPublishingHouse(PublishingHouse publishingHouse) {
        try {
            publishingHouseDao.getPublishingHouse(publishingHouse);
            return false;
        } catch (EmptyResultDataAccessException e) {
            publishingHouseDao.addPublishingHouse(publishingHouse);
            return true;
        }
    }

    public PublishingHouse getPublishingHouse(PublishingHouse publishingHouse) {
        try {
            return publishingHouseDao.getPublishingHouse(publishingHouse);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<PublishingHouse> getAllPublishingHouses() {
        return publishingHouseDao.getAllPublishingHouses();
    }

    public PublishingHouse getPublishingHouseById(Long id) {
        return publishingHouseDao.getPublishingHouseById(id);
    }

    public boolean deletePublishingHouseById(Long id) {
        return publishingHouseDao.deletePublishingHouseById(id);
    }

    public boolean updatePublishingHouseById(PublishingHouse publishingHouse) {
        return publishingHouseDao.updatePublishingHouseById(publishingHouse);
    }

}
