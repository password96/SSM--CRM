package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.CustomerRemark;

import java.util.List;

public interface CustomerRemarkService {
    List<CustomerRemark> queryCustomerRemarkForDetailByCustomerId(String id);

    int deleteCustomerRemarkById(String id);

    int saveCreateCustomerRemark(CustomerRemark customerRemark);

    int saveEditCustomerRemark(CustomerRemark customerRemark);
}
