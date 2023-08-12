package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.Contacts;
import com.shw.crm.workbench.pojo.FunnelVO;

import java.util.List;
import java.util.Map;

public interface ContactsService {
    List<Contacts> queryContactsByFuzzyName(String contactsName);

    List<Contacts> queryContactsByConditionForPage(Map<String, Object> map);

    int queryCountOfContactsByCondition(Map<String, Object> map);

    void saveCreateContacts(Contacts contacts);

    Contacts  queryContactsById(String id);

    void saveEditContacts(Contacts contacts);

    void deleteContacts(String[] id);

    Contacts queryContactsForDetailById(String id);

    List<FunnelVO> queryCountOfCustomerAndContactsGroupByCustomer();
}
