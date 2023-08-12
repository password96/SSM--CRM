package com.shw.crm.workbench.service;

import com.shw.crm.workbench.pojo.Activity;
import com.shw.crm.workbench.pojo.FunnelVO;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int saveCreateActivity(Activity activity);

    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);

    int queryCountOfActivityByCondition(Map<String,Object> map);

    int deleteActivityByIds(String[] ids);

    Activity queryActivityById(String id);

    int saveEditActivity(Activity activity);

    List<Activity> queryAllActivity();

    int saveActivityByList(List<Activity>activityList);

    Activity queryActivityForDetailById(String id);

    List<Activity> queryActivityForDetailByClueId(String clueId);

    List<Activity> queryActivityForDetailByNameAndClueId(Map<String,Object> map);
    List<Activity> queryActivityForDetailByIds(String [] ids);
    List<Activity> queryActivityForConvertByNameAndClueId(Map<String,Object> map);

    List<Activity> queryActivityByFuzzyName(String id);

    List<Activity> queryActivityForDetailByContactsId(String id);

    List<Activity> queryActivityForDetailByNameAndContactsId(Map<String, Object> map);

    List<FunnelVO> queryCountOfActivityGroupByOwner();

    List<Activity> queryCheckedActivity(String[] id);

}
