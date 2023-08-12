package com.shw.crm.commons.domain;

public class ReturnObject {
    private String code;//成功或失败标记
    private String message;//提示信息

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }

    private Object retData;//其他信息
}
