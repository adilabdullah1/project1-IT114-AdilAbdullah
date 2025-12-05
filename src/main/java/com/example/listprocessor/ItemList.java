package com.example.listprocessor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemList implements Iterable<Employee> {
    private final List<Employee> employees;
    private static ItemList instance = null;

    private ItemList() {
        employees = new ArrayList<>();
    }

    public static ItemList getInstance() {
        if (instance == null) {
            instance = new ItemList();
        }
        return instance;
    }

    public int size() {
        return employees.size();
    }

    public void clear() {
        employees.clear();
    }

    public boolean isEmpty() {
        return employees.isEmpty();
    }

    public void add(Employee e) {
        employees.add(e);
    }

    public Employee get(int i) {
        return employees.get(i);
    }

    public Employee findById(String id) {
        for (Employee e : employees) {
            if (e.getId().equalsIgnoreCase(id.trim()))
                return e;
        }
        return null;
    }

    public boolean removeById(String id) {
        Iterator<Employee> it = employees.iterator();
        while (it.hasNext()) {
            Employee e = it.next();
            if (e.getId().equalsIgnoreCase(id.trim())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<Employee> iterator() {
        return employees.iterator();
    }

    public double geometricMeanSalary() {
        if (employees.isEmpty()) return 0.0;
        double logSum = 0.0;
        for (Employee e : employees) {
            double s = Math.max(e.getSalary(), 0.0001);
            logSum += Math.log(s);
        }
        return Math.exp(logSum / employees.size());
    }

    public int countHighPaid(double threshold) {
        int cnt = 0;
        for (Employee e : employees) {
            if (e.getSalary() >= threshold) cnt++;
        }
        return cnt;
    }
}
