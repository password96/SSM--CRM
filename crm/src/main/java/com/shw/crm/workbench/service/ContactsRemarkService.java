package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.ContactsRemark;

import java.util.List;

public interface ContactsRemarkService {
    List<ContactsRemark> queryContactsRemarkForDetailByContactsId(String id);
    int saveCreateContactsRemark(ContactsRemark contactsRemark);

    int deleteContactsRemarkById(String id);

    int saveEditContactsRemark(ContactsRemark contactsRemark);

}
