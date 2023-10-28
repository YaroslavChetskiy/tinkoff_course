package edu.hw3.task5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static edu.hw3.task5.SortOrder.ASC;

public final class Task5 {

    private static final String BLANK = " ";

    private Task5() {
    }

    public static List<PersonProfile> parseContacts(List<String> fullNames, SortOrder order) {
        if (fullNames == null || fullNames.size() == 0) {
            return List.of();
        }
        List<PersonProfile> resultList = getPersonProfilesList(fullNames);

        if (order == ASC) {
            resultList.sort(Comparator.naturalOrder());
        } else {
            resultList.sort(Comparator.reverseOrder());
        }
        return resultList;
    }

    private static List<PersonProfile> getPersonProfilesList(List<String> fullNames) {
        return new ArrayList<>(fullNames.stream()
            .map(it -> it.split(BLANK))
            .map(it -> new PersonProfile(it[0], it.length == 2 ? it[1] : null))
            .toList());
    }
}
