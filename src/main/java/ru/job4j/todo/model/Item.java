package ru.job4j.todo.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    private LocalDateTime created = LocalDateTime.now();
    private boolean done = false;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<CategoryItem> categoryItems = new HashSet<>();

    public Item() {
    }

    public static Item of(String description, User user) {
        Item item = new Item();
        item.description = description;
        item.user = user;
        return item;
    }

    public void addCategory(CategoryItem category) {
        this.categoryItems.add(category);
    }

    public Set<CategoryItem> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(Set<CategoryItem> categoryItems) {
        this.categoryItems = categoryItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", created=" + created
                + ", done=" + done
                + ", user=" + user
                + ", categoryItems=" + categoryItems
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id && done == item.done && Objects.equals(description, item.description)
                && Objects.equals(created, item.created) && Objects.equals(user, item.user)
                && Objects.equals(categoryItems, item.categoryItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, created, done, user, categoryItems);
    }
}