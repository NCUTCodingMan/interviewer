package com.rain.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author rain
 * created on 2018/4/5
 */
public class Constructor {
    public static void main(String[] args) {
        List<Mutable> list = new ArrayList<>(Collections.nCopies(4, new Mutable(1)));

        List<Mutable> alias = new ArrayList<>(list);
        alias.get(0).setValue(2);

        System.out.println(list.get(0).getValue());
    }

    static class Mutable {
        int value;

        Mutable(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        void setValue(int value) {
            this.value = value;
        }
    }
}
