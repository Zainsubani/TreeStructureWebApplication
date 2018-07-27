package database;

public class TestClass {

    public static void main(String[] args){
        NodeDAO dao = new NodeDAO();
        Node root = new Node("root");
        Node node1 = new Node("node1");
        root.appendChild(node1);
        Node node2 = new Node("node2", root);
        try {
            dao.save(root);
            dao.save(node2);
        } catch (NodeDAO.DAOException e) {
            e.printStackTrace();
        }
        System.out.println(node2.getParentNode());
    }
}
