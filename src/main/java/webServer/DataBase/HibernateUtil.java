package webServer.DataBase;
import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil<T> {

    private final Class<T> clazz;
    private Session currentSession;
    private Transaction currentTransaction;

    public HibernateUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }
    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }
    public void closeCurrentSession() {
        currentSession.close();
    }
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }
    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure(new File("C:\\Users\\David\\IdeaProjects\\ScoutWebService\\src\\main\\java\\hibernate.cfg.xml"));
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }

    public Session getCurrentSession() {
        return currentSession;
    }
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }
    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    public void persist(T t) {
        openCurrentSessionwithTransaction();
        getCurrentSession().save(t);
        closeCurrentSessionwithTransaction();
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        openCurrentSession();
        List<T> list = (List<T>) getCurrentSession().createQuery("from " + clazz.getName()).list();
        closeCurrentSession();
        return list;
    }

    public void update(T t) {
        openCurrentSessionwithTransaction();
        getCurrentSession().update(t);
        closeCurrentSessionwithTransaction();
    }

    public void delete(T t) {
        openCurrentSessionwithTransaction();
        getCurrentSession().delete(t);
        closeCurrentSessionwithTransaction();
    }

    public void deleteAll() {
        openCurrentSessionwithTransaction();
        List<T> people = getAll();
        for (T t : people) {
            delete(t);
        }
        closeCurrentSessionwithTransaction();
    }
}
