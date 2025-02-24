import java.util.List;

public class AdditiveExpression extends CompoundExpressionAb {
    /**
     * Constructor for a CompoundExpression
     *
     * @param children the children of this Expression
     */
    public AdditiveExpression(List<Expression> children) {
        super(children);
    }

    /**
     * Returns the value associated with this expression
     * For multiplication, this would be *, for addition, this would be +, and for a literal, this would be toString()
     *
     * @return the value of this expression (excluding its children, if any)
     */
    @Override
    public String getValue() {
        return "+";
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
        for (int i = 0; i < getChildren().size(); ++i) {
            if (getChildren().get(i) instanceof AdditiveExpression) {
                final AdditiveExpression child = (AdditiveExpression) getChildren().remove(i--);
                child.getChildren().forEach(this::addSubexpression);
            }
        }
        super.flatten();
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
        return new AdditiveExpression(deepCopyChildren());
    }
}
