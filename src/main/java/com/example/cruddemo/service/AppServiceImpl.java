package com.example.cruddemo.service;

import com.example.cruddemo.dao_old.AppDAO;
import com.example.cruddemo.entity.Instructor;
import com.example.cruddemo.repository_new.AppRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppServiceImpl implements AppService{

    // old DAO
  //  private final AppDAO appDAO;

    private final AppRepository appRepository;

    public AppServiceImpl( AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    @Transactional
    @Override
    public void save(Instructor instructor) {
        appRepository.save(instructor);
    }

}
