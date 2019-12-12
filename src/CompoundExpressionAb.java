import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CompoundExpressionAb extends ExpressionAb implements CompoundExpression {
    /**
     * Children expressions (ArrayList for remove support)
     */
    private ArrayList<Expression> children;

    /**
     * Constructor for a CompoundExpression
     *
     * @param children the children of this Expression
     */
    public CompoundExpressionAb(List<Expression> children) {
        super(new HBox());
        setChildren(children);
    }

    /**
     * Adds the specified expression as a child.
     *
     * @param subexpression the child expression to add
     */
    @Override
    public void addSubexpression(Expression subexpression) {
        children.add(subexpression);
        setChildren(children);
    }

    /**
     * @return this expression's children.
     */
    public List<Expression> getChildren() {
        return this.children;
    }

    /**
     * @param children the new children of this CompoundExpression
     */
    public void setChildren(List<Expression> children) {
        children.forEach(child -> child.setParent(this));
        this.children = new ArrayList<>(children);
        getHBox().getChildren().clear();
        getHBox().getChildren().addAll(getChildrenNodes(this.children.stream()
                .map(Expression::getNode).collect(Collectors.toList())));
    }

    /**
     * Adds appropriate dividers between the nodes to make the expression displayable
     * The default implementation will work for any operation (+, -, *, /) but not much else
     *
     * @param expressionNodes the children nodes that need to have dividers placed
     * @return a list of nodes ready to be displayed
     */
    List<Node> getChildrenNodes(List<Node> expressionNodes) {
        return getChildrenNodesFromDivider(expressionNodes, getValue());
    }

    /**
     * Helper function for getChildrenNodes that adds a divider between each of the expression nodes
     *
     * @param expressionNodes the expression Nodes to add dividers between
     * @param divider         the string to divide each expression node with
     * @return ready to display list of nodes
     */
    List<Node> getChildrenNodesFromDivider(List<Node> expressionNodes, String divider) {
        final LinkedList<Node> returnVal = new LinkedList<>();
        for (Node expression : expressionNodes) {
            returnVal.add(expression);
            returnVal.add(new Text(divider));
        }
        if (!returnVal.isEmpty()) returnVal.removeLast(); // To remove the last divider added
        return returnVal;
    }

    /**
     * Creates a String representation by recursively printing out (using indentation) the
     * tree represented by this expression, starting at the specified indentation level.
     *
     * @param stringBuilder the StringBuilder to use for building the String representation
     * @param indentLevel   the indentation level (number of tabs from the left margin) at which to start
     */
    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        // add self with super call
        super.convertToString(stringBuilder, indentLevel);
        // add children
        getChildren().forEach(e -> e.convertToString(stringBuilder, indentLevel + 1));
    }

    /**
     * Helper method for deepCopy that copies all of the Expression's children and returns the copies as a list
     *
     * @return a deep copy of the children of this expression
     */
    List<Expression> deepCopyChildren() {
        return children.stream().map(Expression::deepCopy).collect(Collectors.toList());
    }

    /**
     * Recursively flattens the expression as much as possible
     * throughout the entire tree. Specifically, in every multiplicative
     * or additive expression x whose first or last
     * child c is of the same type as x, the children of c will be added to x, and
     * c itself will be removed. This method modifies the expression itself.
     * NOTE: ALL OVERRIDES MUST CALL SUPER AT END!!!
     */
    @Override
    public void flatten() {
        getChildren().forEach(Expression::flatten);
    }

    /**
     * Convenience cast to get the HBox
     *
     * @return this expression's node as an HBox
     */
    HBox getHBox() {
        return (HBox) getNode();
    }
}
