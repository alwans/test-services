package com.services.core;


import com.services.core.entity.Record;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class TestSet {
    @Test
    public void duplicateRemove(){
        Set<Integer> s1 = new HashSet<>();
        Set<Integer> s2 = new HashSet<>();
        s1.add(1);
        s1.add(2);
        s2.add(2);
        s2.add(3);
        s1.addAll(s2);
        System.out.println(s1);
    }

    @Test
    public void addDuplicate(){
        Set<Integer> set = new HashSet<>();
        set.add(1);
        System.out.println(set.add(1));
    }

    @Test
    public void addDuplicateRecord(){
        Set<Record> set = new HashSet<>();
        Record r1 = new Record();
        r1.setId(1L);
        Record r2 = new Record();
        r2.setId(1L);
        System.out.println(set.add(r1));
        System.out.println(set.add(r2));
    }


}
