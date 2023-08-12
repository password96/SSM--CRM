package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    List<String> queryCustomerNameByFuzzyName(String customerName);

    List<Customer> queryCustomerByConditionForPage(Map<String, Object> map);

    int queryCountOfCustomerByCondition(Map<String, Object> map);

    int saveCreateCustomer(Customer customer);

    Object queryCustomerById(String id);

    int saveEditCustomer(Customer customer);

    void deleteCustomer(String[] id);

    Customer queryCustomerForDetailById(String id);
}
