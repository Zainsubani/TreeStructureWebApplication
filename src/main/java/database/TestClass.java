package database;

public class TestClass {

    public static void main(String[] args){
        NodeDAO dao = new NodeDAO();
        Node root = new Node("root");
        Node node1 = new Node("node1");
        root.appendChild(node1);
        Node node2 = new Node("node2", root);
        Node root2 = new Node("root2");
        dao.save(root2);
        dao.save(root);
        dao.save(node2);
        Long id = node1.getId();
        System.out.println(id);
        System.out.println(node1);
        dao.delete(node1);
        System.out.println(dao.get(id));
    }
}
