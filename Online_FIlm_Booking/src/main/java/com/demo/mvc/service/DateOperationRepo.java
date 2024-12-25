package com.demo.mvc.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.mvc.bean.CurrentDateOperation;

@Repository
public interface DateOperationRepo extends JpaRepository<CurrentDateOperation, Integer> {

}
