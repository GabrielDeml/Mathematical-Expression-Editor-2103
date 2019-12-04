import java.util.List;

abstract class ExpressionAb implements Expression {
    /**
     * Parent expression
     */
    private CompoundExpression parent;

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
     * @return this expression's children. Empty list for none.
     */
    public abstract List<Expression> getChildren();

    /**
     * Returns the value associated with this expression
     * For multiplication, this would be *, for addition, this would be +, and for a literal, this would be toString()
     *
     * @return the value of this expression (excluding its children, if any)
     */
    public abstract String getValue();

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
        for (Expression expression : getChildren()) expression.flatten();
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
        // add self
        Expression.indent(stringBuilder, indentLevel);
        stringBuilder.append(getValue()).append('\n');
        // add children
        for (Expression e : getChildren()) e.convertToString(stringBuilder, indentLevel + 1);
    }

    /**
     * Convenience method for convertToString(int) with no starting indent
     *
     * @return the String representation of this Expression with no starting indent
     */
    public String convertToString() {
        return convertToString(0);
    }
}
