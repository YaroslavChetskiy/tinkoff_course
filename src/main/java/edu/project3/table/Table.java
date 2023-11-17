package edu.project3.table;

import edu.project3.exception.TableException;
import java.util.ArrayList;
import java.util.List;

public record Table(String header, List<String> titles, List<List<String>> rows) {

    public Table(String header, List<String> titles) {
        this(header, titles, new ArrayList<>());
    }

    public void addRow(List<String> row) {
        if (titles.size() == row.size()) {
            rows.add(row);
        } else {
            throw new TableException("The number of row columns does not match the number of table columns");
        }
    }
}
