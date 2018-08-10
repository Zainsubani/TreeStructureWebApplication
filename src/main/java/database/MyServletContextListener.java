package database;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener{

    public void contextInitialized(ServletContextEvent arg0) {
        NodeDAO dao = DAOController.getDao();
        Node root3 = new Node("root1");
        Node root = new Node("root3");
        Node node1 = new Node("node1");
        root.appendChild(node1);
        Node node3 = new Node("node3", root);
        Node node2 = new Node("node2", root);
        Node root2 = new Node("root2");
        dao.save(root3);
        dao.save(root2);
        dao.save(root);
        dao.save(node2);
        dao.save(node3);
        dao.get(root2.getId());
    }
}