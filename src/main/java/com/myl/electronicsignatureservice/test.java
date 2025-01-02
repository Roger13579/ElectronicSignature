package com.myl.electronicsignatureservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        String a = "ABCD";
        List<String> list = new ArrayList (List.of(a));
        LinkedList<String> strings = new LinkedList<>(list);
        Collections.shuffle(strings);
        System.out.println(strings);
        String s = "";
        for (int i = 0; i < 2; i++) {
            s += strings.poll();
        }
        System.out.println(s);
        System.out.println(strings);
    }
}
