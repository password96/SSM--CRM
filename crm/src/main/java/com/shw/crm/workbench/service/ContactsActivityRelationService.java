package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.ContactsActivityRelation;

import java.util.List;

public interface ContactsActivityRelationService {
    int saveCreateContactsActivityRelationByList(List<ContactsActivityRelation> contactsActivityRelationList);

    int deleteContactsActivityRelationByContactsIdAndActivityId(ContactsActivityRelation contactsActivityRelation);
}
