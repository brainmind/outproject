package com.wechat.client.utils;

import java.util.UUID;

/**
 * Created by ydd on 2014/4/29.
 */
public class UUIDUtil {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
