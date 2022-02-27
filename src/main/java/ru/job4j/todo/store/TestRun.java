package ru.job4j.todo.store;

import ru.job4j.todo.model.Item;

public class TestRun {
    public static void main(String[] args) {
        Item item = DbStore.instOf().findItem(74);
        System.out.println(item);
    }
}