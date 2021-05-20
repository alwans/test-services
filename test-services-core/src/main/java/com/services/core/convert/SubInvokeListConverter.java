package com.services.core.convert;

import com.services.core.domain.InvocationBO;
import com.services.core.domain.SubInvokeBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("subInvokeListConverter")
@Slf4j
public class SubInvokeListConverter implements ModelConverter<List<InvocationBO>, List<SubInvokeBO>>{

    @Override
    public List<SubInvokeBO> convert(List<InvocationBO> source) {
        Map<String,List> map = new HashMap<>();
        for(InvocationBO invocationBO: source){
            String temp = invocationBO.getIdentity().split("~")[0];
            temp = temp.split("//")[1];
            String className = temp.split("/")[0];
            String methodName = temp.split("/")[1];
            if(className != null && map.containsKey(className)) {
                map.get(className).add(methodName);
            }else{
                List<String> list = new ArrayList<>();
                list.add(methodName);
                map.put(className, list);
            }
        }
        return subInvokeBOConverter(map);
    }

    private List<SubInvokeBO> subInvokeBOConverter(Map<String,List> map){
        List<SubInvokeBO> list = new ArrayList<>(map.size());
        for(Map.Entry<String,List> entry: map.entrySet()){
            SubInvokeBO temp = new SubInvokeBO();
            temp.setClassName(entry.getKey());
            temp.setMethods(entry.getValue());
            list.add(temp);
        }
        return list;
    }

    @Override
    public List<InvocationBO> reconvert(List<SubInvokeBO> target) {
        return null;
    }
}
