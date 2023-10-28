package edu.hw3.task5;

import org.jetbrains.annotations.NotNull;

public record PersonProfile(String name, String lastName) implements Comparable<PersonProfile> {
    @Override
    public int compareTo(@NotNull PersonProfile o) {
        if (lastName == null || o.lastName == null) {
            return name.compareTo(o.name);
        }
        return lastName.compareTo(o.lastName);
    }
}
