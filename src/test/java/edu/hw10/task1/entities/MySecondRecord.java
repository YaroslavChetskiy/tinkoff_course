package edu.hw10.task1.entities;

public record MySecondRecord(String name, int age, boolean hasJob) {

    public static final String DEFAULT_NAME = "NAME";
    public static final int DEFAULT_AGE = 10;
    public static final boolean DEFAULT_HAS_JOB = false;

    public MySecondRecord() {
        this(DEFAULT_NAME, DEFAULT_AGE, DEFAULT_HAS_JOB);
    }

    public static MySecondRecord create() {
        return new MySecondRecord();
    }
}
