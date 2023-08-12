package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Customer row);

    int insertSelective(Customer row);

    Customer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Customer row);

    int updateByPrimaryKey(Customer row);

    int insertCustomer(Customer customer);

    String selectCustomerIdByName(String customerName);

    List<String> selectCustomerNameByFuzzyName(String customerName);

    List<Customer> selectCustomerByConditionForPage(Map<String, Object> map);

    int selectCountOfCustomerByCondition(Map<String, Object> map);

    Customer selectCustomerById(String id);

    int updateCustomer(Customer customer);

    void deleteCustomerByIds(String[] ids);

    Customer selectCustomerForDetailById(String id);
}