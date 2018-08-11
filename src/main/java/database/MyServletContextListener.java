package database;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener{

    public void contextInitialized(ServletContextEvent arg0) {
        NodeDAO dao = DAOController.getDao();
        Node nodeA = new Node("Node A");
        Node nodeB = new Node("Node B");
        Node nodeC = new Node("Node C");
        Node nodeD = new Node("Node D");
        nodeA.appendChild(new Node("Node A1"));
        nodeA.appendChild(new Node("Node A2"));
        nodeA.appendChild(new Node("Node A3"));
        nodeB.appendChild(new Node("Node B1"));
        Node nodeD1 = new Node("Node D1", nodeD);
        Node nodeD2 = new Node("Node D2", nodeD);
        nodeD1.appendChild(new Node("Node D1.1"));
        nodeD1.appendChild(new Node("Node D1.2"));
        nodeD1.appendChild(new Node("Node D1.3"));
        dao.save(nodeA);
        dao.save(nodeB);
        dao.save(nodeC);
        dao.save(nodeD);
        dao.save(nodeD1);
        dao.save(nodeD2);
    }
}