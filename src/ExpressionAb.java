import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

abstract class ExpressionAb implements Expression {
    /**
     * Parent expression
     */
    private CompoundExpression parent = null;
    /**
     * This expression as a JavaFX Node
     */
    private Node self;

    /**
     * Creates an ExpressionAb that sets the self Node to whatever is passed in via this constructor
     * @param node the node that represents this Expression
     */
    public ExpressionAb(Node node) {
        this.self = node;
    }

    /**
     * Returns the expression's parent.
     *
     * @return the expression's parent
     */
    @Override
    public CompoundExpression getParent() {
        return this.parent;
    }

    /**
     * Sets the parent be the specified expression.
     *
     * @param parent the CompoundExpression that should be the parent of the target object
     */
    @Override
    public void setParent(CompoundExpression parent) {
        this.parent = parent;
    }

    /**
     * Returns the value associated with this expression
     * For multiplication, this would be *, for addition, this would be +, and for a literal, this would be toString()
     *
     * @return the value of this expression (excluding its children, if any)
     */
    public abstract String getValue();

    /**
     * Creates a String representation by recursively printing out (using indentation) the
     * tree represented by this expression, starting at the specified indentation level.
     * NOTE: ALL OVERRIDES MUST CALL SUPER FIRST (to add self)!!!
     *
     * @param stringBuilder the StringBuilder to use for building the String representation
     * @param indentLevel   the indentation level (number of tabs from the left margin) at which to start
     */
    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        // add self
        Expression.indent(stringBuilder, indentLevel);
        stringBuilder.append(getValue()).append('\n');
    }

    /**
     * Returns the JavaFX node associated with this expression.
     *
     * @return the JavaFX node associated with this expression.
     */
    @Override
    public Node getNode() {
        return self;
    }
}