package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.Contacts;
import com.shw.crm.workbench.pojo.FunnelVO;

import java.util.List;
import java.util.Map;

public interface ContactsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contacts row);

    int insertSelective(Contacts row);

    Contacts selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contacts row);

    int updateByPrimaryKey(Contacts row);

    /**
     * 保存创建联系人
     * @param contacts
     * @return
     */
    int insertContacts(Contacts contacts);

    List<Contacts> selectContactsByFuzzyName(String contactsName);

    List<Contacts> selectContactsByConditionForPage(Map<String, Object> map);

    int selectCountOfContactsByCondition(Map<String, Object> map);

    Contacts selectContactsById(String id);

    void updateContacts(Contacts contacts);

    void deleteContactsByIds(String[] contactsIds);

    Contacts selectContactsForDetailById(String id);

    List<FunnelVO> selectCountOfCustomerAndContactsGroupByCustomer();
}