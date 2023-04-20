package com.example.coursej.helpers;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortOrderUtil {
    public static List<Sort.Order> createSortOrder(List<String> sortList, String sortDirection) {

        List<Sort.Order> sorts = new ArrayList<>();

        Sort.Direction direction;

        for (String sort : sortList) {
            if (sortDirection != null) {
                direction = Sort.Direction.fromString(sortDirection);
            } else {
                direction = Sort.Direction.ASC;
            }
            sorts.add(new Sort.Order(direction, sort));
        }
        return sorts;
    }
}
