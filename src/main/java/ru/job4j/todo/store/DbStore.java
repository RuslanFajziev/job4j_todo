package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

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

    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        try {
            session.save(item);
            session.getTransaction().commit();
            return item;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new IllegalStateException(e);
        } finally {
            session.close();
        }
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

    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        try {
            List<Item> lst = session.createQuery("from ru.job4j.todo.model.Item").list();
            session.getTransaction().commit();
            return lst;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new IllegalStateException(e);
        } finally {
            session.close();
        }
    }

    public List<Item> findAllNotCompleted() {
        Session session = sf.openSession();
        session.beginTransaction();
        try {
            List<Item> lst = session.createQuery("from ru.job4j.todo.model.Item where done = false").list();
            session.getTransaction().commit();
            return lst;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new IllegalStateException(e);
        } finally {
            session.close();
        }
    }

    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        try {
            Item item = session.get(Item.class, id);
            session.getTransaction().commit();
            return item;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new IllegalStateException(e);
        } finally {
            session.close();
        }
    }
}