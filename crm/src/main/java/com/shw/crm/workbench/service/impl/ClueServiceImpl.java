package com.shw.crm.workbench.service.impl;

import com.shw.crm.commons.Constants;
import com.shw.crm.commons.utils.DateUtils;
import com.shw.crm.commons.utils.UUIDUtils;
import com.shw.crm.settings.pojo.User;
import com.shw.crm.workbench.mapper.*;
import com.shw.crm.workbench.pojo.*;
import com.shw.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("ClueService")
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private ContactsMapper contactsMapper;

    @Autowired
    private ClueRemarkMapper clueRemarkMapper;

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;

    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;

    @Autowired
    private ClueActivityRelationMapper clueActivityRelationMapper;

    @Autowired
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Autowired
    private TranMapper tranMapper;

    @Autowired
    private TranRemarkMapper tranRemarkMapper;


    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    public List<Clue> queryClueByConditionForPage(Map<String, Object> map) {
        return clueMapper.selectClueByConditionForPage(map);
    }

    @Override
    public int queryCountOfClueByCondition(Map<String, Object> map) {
        return clueMapper.selectCountOfClueByCondition(map);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public void saveConvertClue(Map<String, Object> map) {
        String clueId = (String) map.get("clueId");
        User user = (User) map.get(Constants.SESSION_USER);
        String isCreateTran = (String) map.get("isCreateTran");
        // 根据id查询线索的信息
        Clue clue = clueMapper.selectClueById(clueId);
        // 把该线索中有关公司的信息转换到客户表中
        Customer customer = new Customer();
        customer.setId(UUIDUtils.getUUID());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formatDateTime(new Date()));
        customer.setAddress(clue.getAddress());
        customer.setContactSummary(clue.getContactSummary());
        customer.setDescription(clue.getDescription());
        customer.setWebsite(clue.getWebsite());
        customer.setName(clue.getCompany());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setPhone(clue.getPhone());
        customer.setOwner(user.getId());
        customerMapper.insertCustomer(customer); // 新增客户

        // 把该线索中有关个人的信息转换到联系人表中
        Contacts contacts = new Contacts();
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtils.formatDateTime(new Date()));
        contacts.setCustomerId(customer.getId());
        contacts.setDescription(clue.getDescription());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setId(UUIDUtils.getUUID());
        contacts.setJob(clue.getJob());
        contacts.setMphone(clue.getMphone());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contactsMapper.insertContacts(contacts);
        // 根据clueId查询该线索下所有的备注
        List<ClueRemark> clueRemarkList = clueRemarkMapper.selectClueRemarkByClueId(clueId);
        // 如果线索备注非空，给客户和联系人添加该线索的所有备注
        if(clueRemarkList!=null&&clueRemarkList.size()>0){
            // 遍历线索备注，封装客户备注和联系人备注
            CustomerRemark cur=null;
            ContactsRemark cor =null;
            List<CustomerRemark> curList=new ArrayList<>();
            List<ContactsRemark> corList=new ArrayList<>();
            for(ClueRemark cr:clueRemarkList){
                cur= new CustomerRemark();
                cur.setId(UUIDUtils.getUUID());
                cur.setCustomerId(customer.getId());
                cur.setCreateBy(cr.getCreateBy());
                cur.setCreateTime(cr.getCreateTime());
                cur.setEditBy(cr.getEditBy());
                cur.setEditFlag(cr.getEditFlag());
                cur.setEditTime(cr.getEditTime());
                cur.setNoteContent(cr.getNoteContent());
                curList.add(cur);

                cor=new ContactsRemark();
                cor.setId(UUIDUtils.getUUID());
                cor.setContactsId(contacts.getId());
                cor.setCreateBy(cr.getCreateBy());
                cor.setCreateTime(cr.getCreateTime());
                cor.setEditBy(cr.getEditBy());
                cor.setEditFlag(cr.getEditFlag());
                cor.setEditTime(cr.getEditTime());
                cor.setNoteContent(cr.getNoteContent());
                corList.add(cor);
            }

            customerRemarkMapper.insertCustomerRemarkByList(curList);
            contactsRemarkMapper.insertContactsRemarkByList(corList);
        }
        // 查询所有线索和市场活动关联关系
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
        // 遍历线索和市场活动关联关系集合，封装到联系人和市场活动关系中
        if (clueActivityRelationList != null && clueActivityRelationList.size() > 0) {
            ContactsActivityRelation contactsActivityRelation = null;
            List<ContactsActivityRelation> contactsActivityRelationList = new ArrayList<>();
            for (ClueActivityRelation clueActivityRelation : clueActivityRelationList) {
                contactsActivityRelation = new ContactsActivityRelation();
                // 封装参数
                contactsActivityRelation.setContactsId(contacts.getId());
                contactsActivityRelation.setId(UUIDUtils.getUUID());
                contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());
                // 存入集合
                contactsActivityRelationList.add(contactsActivityRelation);
            }
            // 向联系人和市场活动关系表中插入数据
            contactsActivityRelationMapper.insertContactsActivityRelationByList(contactsActivityRelationList);
        }
        // 如果需要创建交易
        if(isCreateTran.equals("true")){
            // 往交易表中添加一条记录
            Tran tran = new Tran();
            tran.setActivityId((String) map.get("activityId"));
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formatDateTime(new Date()));
            tran.setCustomerId(customer.getId());
            tran.setExpectedDate(((String) map.get("expectedDate")));
            tran.setId(UUIDUtils.getUUID());
            tran.setMoney((String) map.get("money"));
            tran.setName(((String) map.get("name")));
            tran.setStage(((String) map.get("stage")));
            tran.setOwner(user.getId());
            // 添加记录
            tranMapper.insertTran(tran);
            // 把线索的备注信息转换到交易备注表中一份
            if (clueRemarkList!=null&&clueRemarkList.size()> 0){
                TranRemark tr=null;
                ArrayList<TranRemark> tranRemarkList = new ArrayList<>();
                for(ClueRemark cr:clueRemarkList){
                    tr = new TranRemark();
                    tr.setId(UUIDUtils.getUUID());
                    tr.setCreateBy(cr.getCreateBy());
                    tr.setCreateTime(cr.getCreateTime());
                    tr.setTranId(tran.getId());
                    tr.setEditBy(cr.getEditBy());
                    tr.setEditFlag(cr.getEditFlag());
                    tr.setEditTime(cr.getEditTime());
                    tr.setNoteContent(cr.getNoteContent());
                    tranRemarkList.add(tr);
                }
                // 新增交易备注
                tranRemarkMapper.insertTranRemarkByList(tranRemarkList);
            }
            clueRemarkMapper.deleteClueRemarkByClueId(clueId);
            clueActivityRelationMapper.deleteClueActivityRelationByClueIds(clueId);
            clueMapper.deleteClueByIds(clueId);
        }
    }

    @Override
    public List<String> queryClueStageOfClueGroupByClueStage() {
        return clueMapper.selectClueStageOfClueGroupByClueStage();
    }

    @Override
    public List<Integer> queryCountOfClueGroupByClueStage() {
        return clueMapper.selectCountOfClueGroupByClueStage();
    }
}
