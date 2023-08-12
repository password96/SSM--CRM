package com.shw.crm.workbench.web.controller;

import com.shw.crm.commons.Constants;
import com.shw.crm.commons.domain.ReturnObject;
import com.shw.crm.commons.utils.DateUtils;
import com.shw.crm.commons.utils.UUIDUtils;
import com.shw.crm.settings.pojo.User;
import com.shw.crm.workbench.pojo.TranRemark;
import com.shw.crm.workbench.service.TranRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class TranRemarkController {
    @Autowired
    private TranRemarkService tranRemarkService;

    @RequestMapping("workbench/transaction/saveCreateTranRemark.do")
    public  @ResponseBody Object saveCreateTranRemark(TranRemark tranRemark, HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        // 封装参数
        tranRemark.setId(UUIDUtils.getUUID());
        tranRemark.setCreateTime(DateUtils.formatDateTime(new Date()));
        tranRemark.setCreateBy(user.getId());
        tranRemark.setEditFlag(Constants.REMARK_EDIT_FLAG_FALSE);
        ReturnObject returnObject = new ReturnObject();
        // 插入操作
        try {
            // 保存线索备注
            int res = tranRemarkService.saveCreateTranRemark(tranRemark);
            if (res > 0) { // 插入成功
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(tranRemark); // 将备注也传到前端响应到页面
            } else { // 插入失败，服务器端出了问题，为了不影响顾客体验，最好不要直接说出问题
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

    @RequestMapping("/workbench/transaction/deleteTranRemarkById.do")
    @ResponseBody
    public Object deleteTranRemarkById(String id) {
        ReturnObject returnObject = new ReturnObject();
        // 删除操作
        try {
            // 删除备注
            int res = tranRemarkService.deleteTranRemarkById(id);
            if (res > 0) { // 删除成功
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            } else { // 删除失败，服务器端出了问题，为了不影响顾客体验，最好不要直接说出问题
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

    @RequestMapping("/workbench/transaction/saveEditTranRemark.do")
    @ResponseBody
    public Object saveEditTranRemark(TranRemark tranRemark, HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        // 封装参数
        tranRemark.setEditFlag(Constants.REMARK_EDIT_FLAG_TRUE);
        tranRemark.setEditBy(user.getId());
        tranRemark.setEditTime(DateUtils.formatDateTime(new Date()));
        ReturnObject returnObject = new ReturnObject();
        try {
            int res = tranRemarkService.saveEditTranRemark(tranRemark);
            if (res > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(tranRemark);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }
        return returnObject;
    }

}
