package com.services.core.intercept;

import org.apache.ibatis.binding.MapperMethod.ParamMap;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;

/**
 * 数据库时间更新拦截其
 * 新增或者更新数据时，自动更新时间
 */

@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class CreateTimeSetIntercept implements Interceptor {


    private static final String CREATE_TIME_SETTER = "setCreateTime";
    private static final String UPDATE_TIME_SETTER = "setModifyTime";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object param = invocation.getArgs()[1];
        if(ms.getSqlCommandType() == SqlCommandType.INSERT){
            setTimeIfNeccesary(param, CREATE_TIME_SETTER);
            setTimeIfNeccesary(param, UPDATE_TIME_SETTER);
        }else if(ms.getSqlCommandType() == SqlCommandType.UPDATE){
            setTimeIfNeccesary(param, UPDATE_TIME_SETTER);
        }
        return invocation.getMethod().invoke(invocation.getTarget(), invocation.getArgs());
    }

    private void setTimeIfNeccesary(Object param, String methodName){
        Class<?> cls = param.getClass();
        if(cls == ParamMap.class){
            @SuppressWarnings("unchecked")
            ParamMap<Object> map = (ParamMap<Object>) param;
            map.entrySet().forEach(entry -> {
                if(!entry.getKey().equals("et")){
                    setIfSetterExists(entry.getValue(), methodName);
                }
            });
        }else{
            setIfSetterExists(param, methodName);
        }
    }

    private void setIfSetterExists(Object param, String methodName)  {
        Class<?> cls = param.getClass();
        Method m = null;
        try{
            m = cls.getDeclaredMethod(methodName, Date.class);
            if(m != null){
                m.setAccessible(true);
                m.invoke(param, new Date());
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            //ignore
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
