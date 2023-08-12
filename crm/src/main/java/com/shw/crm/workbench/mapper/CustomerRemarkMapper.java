package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.CustomerRemark;

import java.util.List;

public interface CustomerRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerRemark row);

    int insertSelective(CustomerRemark row);

    CustomerRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerRemark row);

    int updateByPrimaryKey(CustomerRemark row);

    /**
     * 批量保存客户备注
     * @return
     */
    int insertCustomerRemarkByList(List<CustomerRemark> customerRemarks);

    void deleteCustomerRemarkByCustomerIds(String[] ids);

    List<CustomerRemark> selectCustomerRemarkForDetailByCustomerId(String customerId);

    int deleteCustomerRemarkById(String id);

    int insertCustomerRemark(CustomerRemark customerRemark);

    int updateCustomerRemark(CustomerRemark customerRemark);
}