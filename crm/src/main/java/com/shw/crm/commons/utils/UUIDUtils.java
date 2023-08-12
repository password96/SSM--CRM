package com.shw.crm.commons.utils;

import java.util.UUID;

public class UUIDUtils {
    /**
     * 获取UUID32位
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
