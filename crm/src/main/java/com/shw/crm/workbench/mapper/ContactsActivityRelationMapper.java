package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.ContactsActivityRelation;

import java.util.List;

public interface ContactsActivityRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContactsActivityRelation row);

    int insertSelective(ContactsActivityRelation row);

    ContactsActivityRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContactsActivityRelation row);

    int updateByPrimaryKey(ContactsActivityRelation row);

    /**
     * 批量保存创建的联系人和市场活动的关联关系
     * @param contactsActivityRelationList
     * @return
     */
    int insertContactsActivityRelationByList(List<ContactsActivityRelation> contactsActivityRelationList);

    void deleteContactsActivityRelationByContactsIds(String[] contactsIds);

    int deleteContactsActivityRelationByContactsIdAndActivityId(ContactsActivityRelation contactsActivityRelation);
}