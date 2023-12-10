package edu.hw10.task1.entities;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;

public class MyClass {

    public static final int MAX_AGE = 100;
    public static final int MIN_AGE = 0;
    private String name;
    private String surName;
    private int age;

    private int height;
    private boolean hasJob;

    public MyClass(
        @NotNull String name,
        String surName,
        @Max(MAX_AGE) @Min(MIN_AGE) int age,
        int height,
        boolean hasJob
    ) {
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.height = height;
        this.hasJob = hasJob;
    }

    public static MyClass create(@NotNull String name, String surName, int height) {
        return new MyClass(name, surName, MIN_AGE, height, false);
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public int getAge() {
        return age;
    }

    public int getHeight() {
        return height;
    }

    public boolean isHasJob() {
        return hasJob;
    }
}
