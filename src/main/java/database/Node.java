package database;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
@Table(name = "NODE")
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parentNode")
    private Node parentNode;

    @OneToMany(mappedBy = "parentNode", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Node> childrenNodes = new HashSet<Node>();

    public long getId() {
        return id;
    }

    public Set<Node> getChildrenNodes() {
        return childrenNodes;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void appendChild(Node node){
        node.setParentNode(this);
        childrenNodes.add(node);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node(String name, Node parentNode){
        this.name = name;
        this.parentNode = parentNode;
        parentNode.appendChild(this);
    }

    public Node(){
        // do nothing
    }

    public Node(String name){
        this.name = name;
    }

    public boolean hasChildren(){
        return !childrenNodes.isEmpty();
    }

    public boolean isRoot(){
        return parentNode==null;
    }

    public void deleteChildren(Node node){
        childrenNodes.remove(node);
    }

    public String toString(){
        return  "\t{\n" +
                "\t\t\"id\"          : \"" + id + "\",\n" +
                "\t\t\"parent\"      : \"" + (isRoot() ? "#" : parentNode.id) + "\",\n" +
                "\t\t\"text\"        : \"" + name + "\",\n" +
                "\t\t\"children\"    : " + hasChildren() + "\n" +
                "\t\t}\n";
    }
}
