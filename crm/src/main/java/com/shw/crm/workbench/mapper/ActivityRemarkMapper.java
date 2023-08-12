package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.Activity;
import com.shw.crm.workbench.pojo.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ActivityRemark row);

    int insertSelective(ActivityRemark row);

    ActivityRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActivityRemark row);

    int updateByPrimaryKey(ActivityRemark row);

    /**
     * 根据ActivityId查询该市场活动的详细信息
     * @param activityId
     * @return
     */
    List<ActivityRemark> selectActivityRemarkForDetailByActivityId(String activityId);

    int insertActivityRemark(ActivityRemark remark);

    int deleteActivityRemark(String id);

    int updateActivityRemark(ActivityRemark remark);
}