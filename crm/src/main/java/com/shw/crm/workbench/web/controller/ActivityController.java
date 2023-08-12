package com.shw.crm.workbench.web.controller;

import com.shw.crm.commons.Constants;
import com.shw.crm.commons.domain.ReturnObject;
import com.shw.crm.commons.utils.DateUtils;
import com.shw.crm.commons.utils.HSSFUtils;
import com.shw.crm.commons.utils.UUIDUtils;
import com.shw.crm.settings.pojo.User;
import com.shw.crm.settings.service.UserService;
import com.shw.crm.workbench.pojo.Activity;
import com.shw.crm.workbench.pojo.ActivityRemark;
import com.shw.crm.workbench.service.ActivityRemarkService;
import com.shw.crm.workbench.service.ActivityService;
import com.shw.crm.workbench.service.impl.ActivityRemarkServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request){
        List<User> users = userService.queryAllUsers();
        request.setAttribute("userList",users);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity, HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());

        ReturnObject object=new ReturnObject();
        try {
            int ret = activityService.saveCreateActivity(activity);
            if(ret>0){
                object.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                object.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                object.setMessage("系统忙，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;

    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    public @ResponseBody Object queryActivityByConditionForPage(String name,
    String owner,String startDate,String endDate,int pageNo,int pageSize){
        Map<String,Object> map =new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("beginNo", (pageNo - 1) * pageSize); // 分页计算起始数据的位置
        map.put("pageSize", pageSize);
        // 由前端传来的条件查询数据
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map); // 查询当前分页要显示数据集合
        int totalRows = activityService.queryCountOfActivityByCondition(map); // 查询总条数
        // 封装查询参数，传给前端操作
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("activityList", activityList);
        resultMap.put("totalRows", totalRows);
        return resultMap;

    }

    @RequestMapping("/workbench/activity/deleteActivityIds.do")
    public @ResponseBody Object deleteActivityIds(String[] id){
        ReturnObject returnObject =new ReturnObject();
        try {
            int ret = activityService.deleteActivityByIds(id);
            System.out.println(ret);
            if(ret>0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，联系管理员");
        }
        return  returnObject;
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    public @ResponseBody Object queryActivityById(String id){
        Activity activity = activityService.queryActivityById(id);
        return  activity;
    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity,HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setEditBy(user.getId());

        ReturnObject returnObject =new ReturnObject();
        try {
            int ret=activityService.saveEditActivity(activity);
            if(ret>0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，修改失败");
        }
        return returnObject;
    }
    /**
     * 批量导出市场活动到excel表格
     * 该方法是跳转浏览器下载页面，所以不需要给前端返回信息
     * @param response 响应
     * @throws Exception 输出流异常
     */
    @RequestMapping("/workbench/activity/exportAllActivity.do")
    public void fileDownload(HttpServletResponse response) throws Exception{
        //调用service层方法，查询所有的市场活动
        List<Activity> activityList = activityService.queryAllActivity();
        //创建exel文件，并且把activityList写入到excel文件中
        HSSFUtils.createExcelByActivityList(activityList, Constants.FILE_NAME_ACTIVITY, response);
    }

    /**
     * 选择导出市场活动excel表格
     * @param id 选择的市场活动id
     * @param response 响应
     * @throws Exception 输出流异常
     */
    @RequestMapping("/workbench/activity/exportCheckedActivity.do")
    public void exportCheckedActivity(String[] id, HttpServletResponse response) throws Exception {
        // 调用service层方法，查询所有的市场活动
        List<Activity> activityList = activityService.queryCheckedActivity(id);
        // 创建excel文件，并且把activityList写入到excel文件中
        HSSFUtils.createExcelByActivityList(activityList, Constants.FILE_NAME_ACTIVITY, response);
    }

    /**
     * 实现文件导入市场活动功能
     * @param activityFile 导入的文件
     * @param session 当前用户的session对象
     * @return 后端响应给前端的信息
     */
    @RequestMapping("workbench/activity/importActivity.do")
    public @ResponseBody Object importActivity(MultipartFile activityFile,HttpSession session){

        User user = (User) session.getAttribute(Constants.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        try {
            InputStream is = activityFile.getInputStream();
            HSSFWorkbook wb = new HSSFWorkbook(is);
            // 根据wb获取HSSFSheet对象，封装了一页的所有信息
            HSSFSheet sheet = wb.getSheetAt(0); // 页的下标，下标从0开始，依次增加
            // 根据sheet获取HSSFRow对象，封装了一行的所有信息
            HSSFRow row = null;
            HSSFCell cell = null;
            Activity activity = null;
            List<Activity> activityList = new ArrayList<>();
            for(int i = 1; i <= sheet.getLastRowNum(); i++) { // sheet.getLastRowNum()：最后一行的下标
                row = sheet.getRow(i); // 行的下标，下标从0开始，依次增加
                activity = new Activity();
                // 补充部分参数
                activity.setId(UUIDUtils.getUUID());
                activity.setOwner(user.getId());
                activity.setCreateTime(DateUtils.formatDateTime(new Date()));
                activity.setCreateBy(user.getId());
                for(int j = 0; j < row.getLastCellNum(); j++) { // row.getLastCellNum():最后一列的下标+1
                    // 根据row获取HSSFCell对象，封装了一列的所有信息
                    cell=row.getCell(j); // 列的下标，下标从0开始，依次增加
                    // 获取列中的数据
                    String cellValue = HSSFUtils.getCellValueForStr(cell);
                    if(j == 1) {
                        activity.setName(cellValue);
                    } else if(j == 3){
                        activity.setStartDate(cellValue);
                    } else if(j == 4){
                        activity.setEndDate(cellValue);
                    } else if(j == 5){
                        activity.setCost(cellValue);
                    } else if(j == 6){
                        activity.setDescription(cellValue);
                    }
                }
                //每一行中所有列都封装完成之后，把activity保存到list中
                activityList.add(activity);
            }
            // 调用service层方法，保存市场活动
            int res = activityService.saveActivityByList(activityList);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            returnObject.setRetData(res);
        } catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("workbench/activity/detailActivity")
    public String detailActivity(String id,HttpServletRequest request){
        //调service 查询数据
        Activity activity = activityService.queryActivityForDetailById(id);
        List<ActivityRemark> remarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        request.setAttribute("activity",activity);
        request.setAttribute("remarkList",remarkList);

        return "workbench/activity/detail";
    }
}