package com.cognizant.service;

import com.cognizant.model.Customer;

import java.util.HashMap;
import java.util.Map;

/**
 * Customer Service class
 */
public class CustomerService {
    private static final CustomerService SINGLETON = new CustomerService();
    private final Map<String, Customer> customerMap = new HashMap<>();

    private CustomerService() {
    }

    /**
     * Gets instance
     *
     * @return SINGLETON
     */
    public static CustomerService getInstance() {
        return SINGLETON;
    }

    /**
     * Adds customer
     *
     * @param customer customer
     */
    public void addCustomer(Customer customer) {
        customerMap.put(customer.getEmail(), customer);
    }

    /**
     * Gets customer
     *
     * @param customerEmail customer email
     * @return customer
     */
    public Customer getCustomer(String customerEmail) {
        return customerMap.get(customerEmail);
    }

    /**
     * Gets all customers
     *
     * @return Map of customers
     */
    public Map<String, Customer> getAllCustomers() {
        return customerMap;
    }

}
