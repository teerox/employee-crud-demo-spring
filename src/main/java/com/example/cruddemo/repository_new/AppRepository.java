package com.example.cruddemo.repository_new;

import com.example.cruddemo.entity.Employee;
import com.example.cruddemo.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface AppRepository extends JpaRepository<Instructor, Integer> {

}