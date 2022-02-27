package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.CategoryItem;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.function.Function;

import java.util.List;

public class DbStore {
    private StandardServiceRegistry registry;
    private SessionFactory sf;

    public DbStore() {
        this.registry = new StandardServiceRegistryBuilder().configure().build();
        this.sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    private static final class Lazy {
        private static final DbStore INST = new DbStore();
    }

    public static DbStore instOf() {
        return Lazy.INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        Session session = sf.openSession();
        session.beginTransaction();
        try {
            T rsl = command.apply(session);
            session.getTransaction().commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Item add(Item item, String[] ids) {
        tx(session -> {
                    for (String id : ids) {
                        CategoryItem categoryItem = session.find(CategoryItem.class, Integer.parseInt(id));
                        item.addCategory(categoryItem);
                    }
                    session.save(item);
                    return item;
                }
        );
        return item;
    }

    public User addUser(User user) {
        tx(session -> session.save(user));
        return user;
    }

    public User findUser(String key) {
        return tx(session -> {
            var query = session.createQuery("from ru.job4j.todo.model.User where email = :key");
            query.setParameter("key", key);
            return (User) query.uniqueResult();
        });
    }

    public boolean replace(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        try {
            session.update(item);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new IllegalStateException(e);
        } finally {
            session.close();
        }
    }

    public List<CategoryItem> categoryItemsAll() {
        return tx(session -> session.createQuery("from ru.job4j.todo.model.CategoryItem").list());
    }

    public Item findItem(int id) {
        return tx(session -> {
            var query = session.createQuery("select itm from Item itm join fetch itm.categoryItems where itm.id = :key", Item.class);
            query.setParameter("key", id);
            return query.uniqueResult();
        });
    }

    public List<Item> findAll(int key) {
        return tx(session -> {
            var query = session.createQuery("select DISTINCT itm from Item itm join fetch itm.categoryItems where user_id = :key");
            query.setParameter("key", key);
            return query.list();
        });
    }

    public List<Item> findAllNotCompleted(int key) {
        return tx(session -> {
            var query = session.createQuery("select DISTINCT itm from Item itm join fetch itm.categoryItems where done = false and user_id = :key");
            query.setParameter("key", key);
            return query.list();
        });
    }

    public Item findById(int id) {
        return tx(session -> session.get(Item.class, id));
    }
}