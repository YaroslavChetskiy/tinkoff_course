package edu.hw7.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractPersonDatabase implements PersonDatabase {

    protected final Map<Integer, Person> database = new HashMap<>();
    protected final Map<String, List<Person>> personsByName = new HashMap<>();
    protected final Map<String, List<Person>> personsByAddress = new HashMap<>();
    protected final Map<String, List<Person>> personsByPhone = new HashMap<>();

    @Override
    public void add(Person person) {
        delete(person.id());
        database.put(person.id(), person);
        personsByName.computeIfAbsent(person.name(), o -> new ArrayList<>()).add(person);
        personsByAddress.computeIfAbsent(person.address(), o -> new ArrayList<>()).add(person);
        personsByPhone.computeIfAbsent(person.phoneNumber(), o -> new ArrayList<>()).add(person);
    }

    @Override
    public void delete(int id) {
        var removed = database.remove(id);
        if (removed != null) {
            personsByName.get(removed.name()).remove(removed);
            personsByAddress.get(removed.address()).remove(removed);
            personsByPhone.get(removed.phoneNumber()).remove(removed);
        }
    }

    @Override
    public List<Person> findByName(String name) {
        return personsByName.getOrDefault(name, List.of());
    }

    @Override
    public List<Person> findByAddress(String address) {
        return personsByAddress.getOrDefault(address, List.of());
    }

    @Override
    public List<Person> findByPhone(String phone) {
        return personsByPhone.getOrDefault(phone, List.of());
    }
}
