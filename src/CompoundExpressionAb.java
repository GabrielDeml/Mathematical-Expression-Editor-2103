import java.util.List;
import java.util.stream.Collectors;

public abstract class CompoundExpressionAb extends ExpressionAb implements CompoundExpression {
    /**
     * Children expressions
     */
    private List<Expression> children;

    /**
     * Constructor for a CompoundExpression
     *
     * @param parent   the parent of this Expression
     * @param children the children of this Expression
     */
    public CompoundExpressionAb(CompoundExpression parent, List<Expression> children) {
        setParent(parent);
        this.children = children;
    }

    /**
     * Adds the specified expression as a child.
     *
     * @param subexpression the child expression to add
     */
    @Override
    public void addSubexpression(Expression subexpression) {
        children.add(subexpression);
    }

    /**
     * @return this expression's children. Empty list for none.
     */
    @Override
    public List<Expression> getChildren() {
        return this.children;
    }

    /**
     * Helper method for deepCopy that copies all of the Expression's children and returns the copies as a list
     *
     * @return a deep copy of the children of this expression
     */
    List<Expression> deepCopyChildren() {
        return children.stream().map(Expression::deepCopy).collect(Collectors.toList());
    }
}
