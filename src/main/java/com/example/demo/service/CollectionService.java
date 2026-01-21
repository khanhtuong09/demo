package com.example.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

public class CollectionService {

    public List<Integer> uniqueSorted(List<Integer> input) {
        if (input == null) return null;
        Set<Integer> set = new LinkedHashSet<>(input);
        List<Integer> result = new ArrayList<>(set);
        Collections.sort(result);
        return result;
    }

    public int sum(List<Integer> input) {
        if (input == null) return 0;
        int s = 0;
        for (Integer i : input) {
            if (i != null) s += i;
        }
        return s;
    }
}

