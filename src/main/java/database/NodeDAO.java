package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NodeDAO {
    private Session currentSession;
    Set<Node> rootNodes = new HashSet<Node>();

    private static SessionFactory getSessionFactory() {
        try {
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            return sessionFactory;
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private Session getCurrentSession() {
        return currentSession;
    }

    // CRUD-methods
    public Node get(Long id) {
        return getCurrentSession().get(Node.class, id);
    }

    public Serializable save(Node node) {
        if (node.getParentNode()==null) {
            rootNodes.add(node);
        }
        return getCurrentSession().save(node);
    }

    public void update(Node node) {
        getCurrentSession().update(node);
    }

    public void delete(Node node) {
        getCurrentSession().delete(node);
    }

    public List<Node> query(String hsql, Map<String, Object> params) {
        Session session = getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery(hsql);
        if (params != null) {
            for (String i : params.keySet()) {
                query.setParameter(i, params.get(i));
            }
        }

        List<Node> result = null;
        if ((hsql.toUpperCase().indexOf("DELETE") == -1)
                && (hsql.toUpperCase().indexOf("UPDATE") == -1)
                && (hsql.toUpperCase().indexOf("INSERT") == -1)) {
            result = query.list();
        }
        session.getTransaction().commit();
        return result;
    }

    public Set<Node> getRootNodes(){
        return rootNodes;
    }

    public void setRootNode(Node node){
        rootNodes.add(node);
    }

    public void deleteRootNode(Node node){
        rootNodes.remove(node);
    }

    public NodeDAO(){
        currentSession = getSessionFactory().openSession();
    }
}
