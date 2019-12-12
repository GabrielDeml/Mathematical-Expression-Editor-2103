import javafx.scene.Node;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ParentheticalExpression extends CompoundExpressionAb {
    /**
     * Constructs a ParentheticalExpression
     *
     * @param child the child of this ParentheticalExpression
     */
    public ParentheticalExpression(Expression child) {
        super(Collections.singletonList(child));
    }

    /**
     * An override to allow only one child
     *
     * @param subexpression the child expression to add
     */
    @Override
    public void addSubexpression(Expression subexpression) {
        super.setChildren(Collections.singletonList(subexpression));
    }

    /**
     * An override to allow only one child
     *
     * @param children the new children of this CompoundExpression
     */
    @Override
    public void setChildren(List<Expression> children) {
        // Ignore all children except for the first
        if (!children.isEmpty()) addSubexpression(children.get(0));
    }

    /**
     * Returns the value associated with this expression
     * For multiplication, this would be *, for addition, this would be +, and for a literal, this would be toString()
     *
     * @return the value of this expression (excluding its children, if any)
     */
    @Override
    public String getValue() {
        return "()";
    }

    /**
     * Adds parentheses around the child Expression's Node to make it displayable
     *
     * @param expressionNodes what to place parentheses around
     * @return a list of nodes ready to be displayed
     */
    @Override
    List<Node> getChildrenNodes(List<Node> expressionNodes) {
        return Arrays.asList(new Text("("), expressionNodes.get(0), new Text(")"));
    }

    /**
     * Creates and returns a deep copy of the expression.
     * The entire tree rooted at the target node is copied, i.e.,
     * the copied Expression is as deep as possible.
     *
     * @return the deep copy
     */
    @Override
    public Expression deepCopy() {
        return new ParentheticalExpression(deepCopyChildren().get(0));
    }
}
