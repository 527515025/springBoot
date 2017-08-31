package com.us.example.springel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author:Dave
 * @Description:
 * @Date: 2017/8/31 14:17
 */
public class MyUtils {
    public static <T> T copy(Collection<? super T> dest,Collection<T> src){
        T last = null;
        for(T ele : src){
            last = ele;
            dest.add(ele);
        }
        return last;
    }

    public static void main(String[] args){
        List<Number> ln = new ArrayList<>();
        List<Integer> li = new ArrayList<>();
        li.add(5);

        Integer last = copy(ln,li);
        last.byteValue();
    }
}
