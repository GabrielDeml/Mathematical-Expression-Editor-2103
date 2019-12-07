import java.util.ArrayList;
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
        setChildren(children);
    }

    /**
     * Adds the specified expression as a child.
     *
     * @param subexpression the child expression to add
     */
    @Override
    public void addSubexpression(Expression subexpression) {
        subexpression.setParent(this);
        children.add(subexpression);
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
}
