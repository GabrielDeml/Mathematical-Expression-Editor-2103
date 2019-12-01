import java.util.List;

public class node {
    /**
     * Parent of node
     */
    private node parent;
    /**
     * Value of the node
     */
    private String value;
    /**
     * Children of the node
     */
    private List<node> children;

    public node(node p, String v, List<node> c){
        this.parent = p;
        this.value = v;
        this.children = c;
    }

    /**
     * Gets parent
     * @return parent node
     */
    public node getParent() {
        return parent;
    }

    /**
     * Gets the value of node
     * @return Value of node
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets children
     * @return list of children
     */
    public List<node> getChildren() {
        return children;
    }


}
