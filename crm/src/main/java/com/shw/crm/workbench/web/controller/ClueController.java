package com.shw.crm.workbench.web.controller;

import com.shw.crm.commons.Constants;
import com.shw.crm.commons.domain.ReturnObject;
import com.shw.crm.commons.utils.DateUtils;
import com.shw.crm.commons.utils.UUIDUtils;
import com.shw.crm.settings.pojo.DicValue;
import com.shw.crm.settings.pojo.User;
import com.shw.crm.settings.service.DicValueService;
import com.shw.crm.settings.service.UserService;
import com.shw.crm.workbench.pojo.Activity;
import com.shw.crm.workbench.pojo.Clue;
import com.shw.crm.workbench.pojo.ClueActivityRelation;
import com.shw.crm.workbench.pojo.ClueRemark;
import com.shw.crm.workbench.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {
    @Autowired
    private UserService userService;
    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private ClueService clueService;

    @Autowired
    private ClueRemarkService clueRemarkService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClueActivityRelationService clueActivityRelationService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");

        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);

        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public @ResponseBody Object saveCreateClue(Clue clue, HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_USER);

        clue.setId(UUIDUtils.getUUID());
        clue.setCreateTime(DateUtils.formatDateTime(new Date()));
        clue.setCreateBy(user.getId());

        ReturnObject returnObject =new ReturnObject();
        try { // 新增线索
            int res = clueService.saveCreateClue(clue);
            if (res > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/queryClueByConditionForPage.do")
    public @ResponseBody Object queryClueByConditionForPage(String fullname, String company, String phone, String source,
                                                            String owner, String mphone, String state, int pageNo, int pageSize){

        Map<String,Object> map=new HashMap<>();
        map.put("fullname",fullname);
        map.put("company", company);
        map.put("phone", phone);
        map.put("source", source);
        map.put("owner", owner);
        map.put("mphone", mphone);
        map.put("state", state);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        // 查询数据
        List<Clue> clueList = clueService.queryClueByConditionForPage(map);
        int totalRows = clueService.queryCountOfClueByCondition(map);
        // 封装查询参数，传给前端操作
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("clueList", clueList);
        resultMap.put("totalRows", totalRows);
        return resultMap;
    }

    @RequestMapping("/workbench/clue/detailClue.do")
    public String detailClue(String id,HttpServletRequest request){

        Clue clue = clueService.queryClueForDetailById(id);
        List<ClueRemark> remarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);

        request.setAttribute("clue",clue);
        request.setAttribute("clueRemarkList",remarkList);
        request.setAttribute("activityList",activityList);
        return "workbench/clue/detail";
    }

    /**
     * 在线索详情页面绑定市场活动中通过市场活动名模糊查询市场活动
     * @param activityName 市场活动名
     * @param clueId 当前线索id
     * @return 查询到的市场活动集合
     */
    @RequestMapping("/workbench/clue/queryActivityForDetailByNameAndClueId.do")
    @ResponseBody
    public Object queryActivityForDetailByNameAndClueId(String activityName, String clueId) {
        // 封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        List<Activity> activityList = activityService.queryActivityForDetailByNameAndClueId(map);
        return activityList;
    }

    @RequestMapping("/workbench/clue/saveBound.do")
    public @ResponseBody Object saveBound(String []activityId,String clueId){
        //封装参数
        ClueActivityRelation car=null;
        List<ClueActivityRelation> relationList=new ArrayList<>();
        for(String ai:activityId){
             car = new ClueActivityRelation();
             car.setClueId(clueId);
             car.setActivityId(ai);
             car.setId(UUIDUtils.getUUID());
             relationList.add(car);
        }
        ReturnObject returnObject = new ReturnObject();
        try {
            int res = clueActivityRelationService.saveCreateClueActivityRelationByList(relationList);
            if (res > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                // 保存成功后查询所有市场活动id对应的市场活动，用于动态响应到前台页面
                List<Activity> activityList = activityService.queryActivityForDetailByIds(activityId);
                returnObject.setRetData(activityList);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试...");
            }
        } catch (Exception e) { // 发生了某些异常，捕获后返回信息
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }
        return returnObject;

    }
    @RequestMapping("workbench/clue/saveUnbound.do")
    @ResponseBody
        public Object saveUnBound(ClueActivityRelation clueActivityRelation){
        ReturnObject returnObject = new ReturnObject();
        try{
            int ret = clueActivityRelationService.deleteClueActivityRelationByClueIdAndActivityId(clueActivityRelation);
            if (ret > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试...");
            }
        } catch (Exception e) { // 发生了某些异常，捕获后返回信息
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }
        return returnObject;
    }

    /**
     * 跳转到转换界面
     * @param id 当前线索id
     * @param request 请求
     * @return 前端界面
     */
    @RequestMapping("/workbench/clue/toConvert.do")
    public String toConvert(String id, HttpServletRequest request) {
        // 查询convert页面所需的数据
        Clue clue = clueService.queryClueForDetailById(id);
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage"); // 查询stage的字典值
        // 存入request域中
        request.setAttribute("clue", clue);
        request.setAttribute("stageList", stageList);
        return "workbench/clue/convert";
    }

    @RequestMapping("/workbench/clue/queryActivityForConvertByNameAndClueId.do")
    public @ResponseBody Object queryActivityForConvertByNameAndClueId(String activityName, String clueId){
        // 封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        List<Activity> activityList = activityService.queryActivityForConvertByNameAndClueId(map);
        return activityList;
    }

    @RequestMapping("/workbench/clue/convertClue.do")
    public @ResponseBody Object convertClue(HttpSession session, String clueId, String money, String name, String expectedDate,
                                            String isCreateTran, String stage, String activityId){
        Map<String, Object> map = new HashMap<>();
        map.put(Constants.SESSION_USER,session.getAttribute(Constants.SESSION_USER));
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("expectedDate",expectedDate);
        map.put("stage", stage);
        map.put("name", name);
        map.put("activityId", activityId);
        map.put("isCreateTran", isCreateTran);

        ReturnObject returnObject = new ReturnObject();
        try {
            // 保存线索转换
            clueService.saveConvertClue(map);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }
        return returnObject;
    }

}
