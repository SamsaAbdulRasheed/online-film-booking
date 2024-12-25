package com.demo.mvc.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.mvc.bean.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

	Customer findByEmailAndPassword(String email, String pwd);

}
