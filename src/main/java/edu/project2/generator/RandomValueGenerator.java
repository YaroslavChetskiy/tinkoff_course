package edu.project2.generator;

// Сделал через Generic, т.к вдруг понадобится
// не только целочисленные значения генерировать
public interface RandomValueGenerator<T> {

    T getRandomValue(T leftBound, T rightBound);
}
