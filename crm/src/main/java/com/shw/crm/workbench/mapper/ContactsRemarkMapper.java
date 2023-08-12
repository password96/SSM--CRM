package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.ContactsRemark;

import java.util.List;

public interface ContactsRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContactsRemark row);

    int insertSelective(ContactsRemark row);

    ContactsRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContactsRemark row);

    int updateByPrimaryKey(ContactsRemark row);

    /**
     * 批量插入联系人备注
     * @param contactsRemarkList 联系人备注集合
     * @return 插入条数
     */
    int insertContactsRemarkByList(List<ContactsRemark> contactsRemarkList);

    /**
     * 新增一条联系人备注
     * @param contactsRemark 联系人备注
     * @return 新增条数
     */
    int insertContactsRemark(ContactsRemark contactsRemark);

    /**
     * 通过联系人备注的id删除联系人备注
     * @param id 联系人备注的id
     * @return 删除的条数
     */
    int deleteContactsRemarkById(String id);
    /**
     * 通过联系人id删除备注（用于删除联系人时同时删除该联系人备注；因为可能一次性会删除多个联系人，所以传入的id是联系人id数组）
     * @param contactsIds 联系人id
     * @return 删除的条数
     */
    void deleteContactsRemarkByContactsId(String[] contactsIds);

    /**
     * 通过联系人id查询对应联系人的所有备注
     * @param contactsId 联系人id
     * @return 对应联系人备注集合
     */

    List<ContactsRemark> selectContactsRemarkForDetailByContactsId(String contactsId);

    /**
     * 更新联系人备注
     * @param contactsRemark 更新的联系人备注
     * @return 更新的条数
     */
    int updateContactsRemark(ContactsRemark contactsRemark);

}