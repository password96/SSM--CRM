package com.shw.crm.workbench.web.controller;

import com.shw.crm.commons.Constants;
import com.shw.crm.commons.domain.ReturnObject;
import com.shw.crm.commons.utils.DateUtils;
import com.shw.crm.commons.utils.UUIDUtils;
import com.shw.crm.settings.pojo.DicValue;
import com.shw.crm.settings.pojo.User;
import com.shw.crm.settings.service.DicValueService;
import com.shw.crm.settings.service.UserService;
import com.shw.crm.workbench.mapper.TranHistoryMapper;
import com.shw.crm.workbench.mapper.TranRemarkMapper;
import com.shw.crm.workbench.pojo.*;
import com.shw.crm.workbench.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class TranController {

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private TranService tranService;

    @Autowired
    private TranRemarkService tranRemarkService;

    @Autowired
    private TranHistoryService tranHistoryService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/workbench/transaction/index.do")
    public String index(HttpServletRequest request){
        //调用service 查询动态数据
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //把数据保存到作用域
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("stageList",stageList);
        //请求转发
        return "workbench/transaction/index";
    }

    @RequestMapping("/workbench/transaction/queryTransactionByConditionForPage.do")
    public @ResponseBody Object queryTransactionByConditionForPage(String owner, String name, String customerId, String stage, String type,
                                                                   String source, String contactsId, int pageNo, int pageSize) {
        // 封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("owner", owner);
        map.put("name", name);
        map.put("customerId", customerId);
        map.put("source", source);
        map.put("stage", stage);
        map.put("type", type);
        map.put("contactsId", contactsId);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        // 查询
        List<Tran> tranList = tranService.queryTransactionByConditionForPage(map);
        int totalRows = tranService.queryCountOfTransactionByCondition(map);
        // 封装查询参数，传给前端操作
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("tranList", tranList);
        resultMap.put("totalRows", totalRows);
        return resultMap;
    }

    @RequestMapping("workbench/transaction/toSavePage.do")
    public String toSavePage(HttpServletRequest request){
        // 调用service层方法，查询数据
        List<User> userList = userService.queryAllUsers();
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //把数据保存到作用域
        request.setAttribute("userList",userList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("stageList",stageList);
        //请求转发
        return "workbench/transaction/save";
    }

    @RequestMapping("workbench/transaction/queryActivityByFuzzyName.do")
    public @ResponseBody Object queryActivityByFuzzyName(String activityName){
        List<Activity> activityList = activityService.queryActivityByFuzzyName(activityName);
        return activityList;
    }

    @RequestMapping("/workbench/transaction/queryContactsByFuzzyName.do")
    public @ResponseBody Object queryContactsByFuzzyName(String contactsName) {
        List<Contacts> contactsList = contactsService.queryContactsByFuzzyName(contactsName);
        return contactsList;
    }

    @RequestMapping("/workbench/transaction/getPossibilityByStage.do")
    @ResponseBody
    public Object getPossibilityByStage(String stageValue){
        // 解析properties配置文件，根据阶段获取可能性
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(stageValue);
        // 返回响应信息
        return possibility;
    }


    @RequestMapping("workbench/transaction/queryCustomerNameByFuzzyName.do")
    public @ResponseBody Object queryCustomerNameByFuzzyName(String customerName){
        List<String> customerNames = customerService.queryCustomerNameByFuzzyName(customerName);
        return customerNames;
    }

    @RequestMapping("workbench/transaction/saveCreateTransaction.do")
    public @ResponseBody Object  saveCreateTransaction(Tran tran, HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        // 封装参数
        tran.setId(UUIDUtils.getUUID());
        tran.setCreateTime(DateUtils.formatDateTime(new Date()));
        tran.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            tranService.saveCreateTransaction(tran);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }



    /**
     * 详细信息页
     * @return
     */
    @RequestMapping("workbench/transaction/toDetailPage.do")
    public String toDetailPage(String id,HttpServletRequest request){

        Tran tran = tranService.queryTranForDetailById(id);
        List<TranRemark> remarkList = tranRemarkService.queryTranRemarkForDetailByTranId(id);
        List<TranHistory> historyList=tranHistoryService.queryTranHistoryForDetailByTranId(id);

        System.out.println(tran.getSource()+tran.getNextContactTime());
        //根据tran所处阶段名称查询可能性
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility=bundle.getString(tran.getStage());
        // 获取当前阶段阶段的stageNo
        String stageOrderNo =dicValueService.queryDicValueById(tranService.queryTransactionById(id).getStage()).getOrderNo();
        request.setAttribute("stageOrderNo", stageOrderNo);
        //把数据保存到request中
        request.setAttribute("tran",tran);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("historyList",historyList);
        request.setAttribute("possibility",possibility);
        // 调用service方法，查询交易所有的阶段
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("stageList",stageList);
        //请求转发
        return "workbench/transaction/detail";
    }

    @RequestMapping("workbench/transaction/toEditPage.do")
    public String toEditPage(String id, HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        Tran tran = tranService.queryTransactionById(id);
        // 解析properties配置文件，根据阶段获取可能性
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(dicValueService.queryDicValueById(tran.getStage()).getValue());
        // 存入request域中
        request.setAttribute("userList",userList);
        request.setAttribute("stageList",stageList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("tran", tran);
        request.setAttribute("possibility", possibility);

        return "workbench/transaction/edit";
    }


    @RequestMapping("/workbench/transaction/saveEditTransaction.do")
    public @ResponseBody Object saveEditTransaction(Tran tran, HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        // 封装参数
        tran.setEditTime(DateUtils.formatDateTime(new Date()));
        tran.setEditBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            tranService.saveEditTransaction(tran);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }


    @RequestMapping("workbench/transaction/deleteTranByIds.do")
    public  @ResponseBody Object deleteTranByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        try {
            tranService.deleteTranByIds(id); // 通过联系人id数组删除所有对应的线索以及该线索的所有信息
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) { // 发生了某些异常，捕获后返回信息
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }
        return returnObject;
    }

}
