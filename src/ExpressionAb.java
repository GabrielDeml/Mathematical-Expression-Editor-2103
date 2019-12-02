

import java.util.List;

abstract class ExpressionAb implements Expression {

    /**
     * Parent node
     */
    CompoundExpression parent;
    /**
     * Value
     */
    String value;

    /**
     * Children
     */
    List<Expression> children;

    /**
     * Returns the expression's parent.
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
     * Sets the parent be the specified expression.
     * @param children the CompoundExpression that should be the parent of the target object
     */
    public void setChildren(List<Expression> children) {
        this.children = children;
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
        //TODO implement me
        return null;
    }

    /**
     * Recursively flattens the expression as much as possible
     * throughout the entire tree. Specifically, in every multiplicative
     * or additive expression x whose first or last
     * child c is of the same type as x, the children of c will be added to x, and
     * c itself will be removed. This method modifies the expression itself.
     */
    @Override
    public void flatten() {
        //TODO implement me
    }

    /**
     * Returns the value associated with this expression
     * For multiplication, this would be *, for addition, this would be +, and for a literal, this would be toString()
     *
     * @return the value of this expression (excluding its children, if any)
     */
    @Override
    public String getValue() {
        return this.value;
    }

    /**
     * @return this expression's children. Empty list for none.
     */
    @Override
    public List<Expression> getChildren() {
        return this.children;
    }
}
