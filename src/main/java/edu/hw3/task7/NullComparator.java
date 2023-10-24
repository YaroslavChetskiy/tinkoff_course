package edu.hw3.task7;

import java.util.Comparator;

public class NullComparator<T extends Comparable<T>> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        if (o1 == null || o2 == null) {
            // o1 == null && o2 == null -> cmp(1, 1) -> 0
            // o1 == null && o2 != null -> cmp(0, 1) -> -1
            // o1 != null && o2 == null -> cmp(1, 0) -> 1
            return Boolean.compare(o1 != null, o2 != null);
        }
        return o1.compareTo(o2);
    }
}
