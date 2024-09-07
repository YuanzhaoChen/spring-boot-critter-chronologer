package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer getCustomer(long id);

    List<Customer> getAllCustomers();

    Customer saveCustomer(Customer customer);
}
