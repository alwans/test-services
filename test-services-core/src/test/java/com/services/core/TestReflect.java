package com.services.core;


import com.services.core.entity.SandboxInfo;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

public class TestReflect {

    @Test
    public void test4refelctMethod(){
        Class clz = SandboxInfo.class;
        try {
            Method method = clz.getDeclaredMethod("setModifyTime", Date.class);
            System.out.println(method.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4field() throws Exception{
        SandboxInfo sandboxInfo = new SandboxInfo();
        Class cls = SandboxInfo.class;
        Field[] fields = cls.getDeclaredFields();
        for(Field field: fields){
            field.setAccessible(true);
            Object value = field.get(sandboxInfo);
            if(value != null && !(value instanceof Date) && !value.equals("")){
                System.out.println(field.getName()+": "+value);
            }
        }
    }
}
