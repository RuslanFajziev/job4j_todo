package ru.job4j.todo;

import ru.job4j.todo.model.CategoryItem;
import ru.job4j.todo.store.DbStore;

import java.util.List;

public class TestDbStore {
    public static void main(String[] args) {
        List<CategoryItem>  lst = DbStore.instOf().categoryItemsAll();
        lst.forEach(System.out::println);
    }
}