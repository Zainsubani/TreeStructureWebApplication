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
        parentNode.appendChild(this);
    }

    public Node(String name){
        this.name = name;
    }

    // just for testing
    public String toString(){
        Iterator<Node> iterator = childrenNodes.iterator();
        String result = "{ \"" + name + "\" : [ " + (iterator.hasNext()? iterator.next() : "");
        while (iterator.hasNext()){
            result += ", " + iterator.next();
        }
        result += " ] }";
        return result;
    }
}
