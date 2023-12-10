package edu.hw10.task2.calculator;

import edu.hw10.task2.annotation.Cache;

public interface FibonacciCalculator {

    @Cache(persist = true)
    long fib(int number);
}
