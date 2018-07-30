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
    public boolean isEmpty;
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

    public Serializable save(Node node) throws DAOException {
        if (isEmpty && !(node.getParentNode()==null)){
            throw new DAOException("You have to save root node first!");
        } else if (node.getParentNode()==null){
            rootNodes.add(node);
        }
        isEmpty = false;
        return getCurrentSession().save(node);
    }

    public void update(Node node) {
        getCurrentSession().update(node);
    }

    public void delete(Node node) {
        if(node.getParentNode()==null){
            isEmpty = true; // last root node deleted
        }
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

    public NodeDAO(){
        currentSession = getSessionFactory().openSession();
        isEmpty = true;
    }

    public class DAOException extends Exception{
        public DAOException(String message)
        {
            super(message);
        }

    }
}
