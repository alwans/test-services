package com.services.core;


import org.junit.Test;

import java.util.HashSet;

public class TestArr {

    @Test
    public void test1(){
        int[] arr = {1,2,3,4};
        HashSet set = new HashSet();
        System.out.println(set.add(1));
        System.out.println(set.add(1));
    }

    @Test
    public void test(){
        System.out.println(5%2);
        int[] nums = {1,2,3,4,5,6};
        int k =3;
        int len = nums.length;
        int prev =0;
        int pos = k -1;
        boolean flag = true;
        int count =0;
        int temp = 0;
        int cc = 1;
        while(cc<len){
            pos = len-1-count+k;
            pos = pos>= len? pos -len: pos;
            while(pos!=(len-1-count)){
                temp = prev;
                prev = nums[pos];
                if(flag){
                    nums[pos] = nums[len-1-count];
                    flag = false;
                }else{
                    nums[pos] = temp;
                }
                cc++;
                pos = pos +k;
                pos = pos >= len? pos-len: pos;
            }
            nums[pos] = prev;
            cc++;
            flag = true;
            count++;
        }

        while(count<len){
            count++;
            int temp1 = prev;
            prev = nums[pos];
            if(flag){
                nums[pos] = nums[len-1];
                flag = false;
            }else{
                nums[pos] = temp1;
            }
            pos = pos +k;
            pos = pos >= len? pos-len : pos;
        }
        System.out.println("123");
    }


}

class Solution {
    public void rotate(int[] nums, int k) {
        int len = nums.length;
        k = k % len;
        if(k==0 || nums.length==1){return;}
        int prev =0;
        int pos = k -1;
        boolean flag = true;
        int count =0;
        int temp = 0;
        int cc = 1;
        if(len%2 == 0){
            while(cc<len){
                pos = len-1-count+k;
                pos = pos>= len? pos -len: pos;
                while(pos!=(len-1-count)){
                    temp = prev;
                    prev = nums[pos];
                    if(flag){
                        nums[pos] = nums[len-1-count];
                        flag = false;
                    }else{
                        nums[pos] = temp;
                    }
                    cc++;
                    pos = pos +k;
                    pos = pos >= len? pos-len: pos;
                }
                nums[pos] = prev;
                cc++;
                flag = true;
                count++;
            }
        }else{
            while(count<len){
                count++;
                int temp1 = prev;
                prev = nums[pos];
                if(flag){
                    nums[pos] = nums[len-1];
                    flag = false;
                }else{
                    nums[pos] = temp1;
                }
                pos = pos +k;
                pos = pos >= len? pos-len : pos;
            }
        }
    }
}
