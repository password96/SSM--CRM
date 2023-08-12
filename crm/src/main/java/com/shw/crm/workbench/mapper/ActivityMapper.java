package com.shw.crm.workbench.mapper;

import com.shw.crm.workbench.pojo.Activity;
import com.shw.crm.workbench.pojo.FunnelVO;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    int deleteByPrimaryKey(String id);

    int insertActivity(Activity row);

    int insertSelective(Activity row);

    Activity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Activity row);

    int updateByPrimaryKey(Activity row);

    List<Activity> selectActivityByConditionForPage(Map<String,Object> map);

    int selectCountOfActivityByCondition(Map<String,Object> map);

    int deleteActivityByIds(String[] ids);

    Activity selectActivityById(String id);

    int updateActivity(Activity activity);

    List<Activity> selectAllActivity();

    int insertActivityByList(List<Activity> activityList);

    /**
     * 根据id查询市场活动明细信息
     * @param id
     * @return
     */
    Activity selectActivityForDetailById(String id);

    List<Activity> selectActivityForDetailByClueId(String clueId);

    /**
     * 通过市场活动名和线索id模糊查询市场活动（线索转换查询未绑定的市场活动）
     * @param map 封装的线索id和市场活动名参数
     * @return 市场活动集合
     */
    List<Activity> selectActivityForDetailByNameAndClueId(Map<String, Object> map);

    List<Activity> selectActivityForDetailByIds(String[] ids);

    /**
     * 通过市场活动名和线索id模糊查询市场活动
     * @param map 封装的线索id和市场活动名参数
     * @return 市场活动集合
     */
    List<Activity> selectActivityForConvertByNameAndClueId(Map<String,Object>map);

    List<Activity> selectActivityByFuzzyName(String name);

    List<Activity> selectActivityForDetailByContactsId(String contactsId);

    List<Activity> selectActivityForDetailByNameAndContactsId(Map<String, Object> map);

    List<FunnelVO> selectCountOfActivityGroupByOwner();

    List<Activity> selectCheckedActivity(String[] id);
}