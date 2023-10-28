package edu.hw3.task8;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BackwardIterator<T> implements Iterator<T> {
    private final List<T> list;
    private int currentIndex;

    public BackwardIterator(Collection<T> collection) {
        list = collection.stream().toList();
        currentIndex = list.size() - 1;
    }


    @Override
    public boolean hasNext() {
        return currentIndex >= 0;
    }

    @Override
    public T next() {
        if (currentIndex < 0) {
            throw new NoSuchElementException();
        }
        return list.get(currentIndex--);
    }
}
