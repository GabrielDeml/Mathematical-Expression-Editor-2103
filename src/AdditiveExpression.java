import java.util.List;

public class AdditiveExpression extends CompoundExpressionAb {
    /**
     * Constructor for a CompoundExpression
     *
     * @param parent   the parent of this Expression
     * @param children the children of this Expression
     */
    public AdditiveExpression(CompoundExpression parent, List<Expression> children) {
        super(parent, children);
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
     * Creates and returns a deep copy of the expression.
     * The entire tree rooted at the target node is copied, i.e.,
     * the copied Expression is as deep as possible.
     *
     * @return the deep copy
     */
    @Override
    public Expression deepCopy() {
        // fixme null as parent won't work -- perhaps go through tree at end of deepCopy and fix dependencies?
        //  (in all impls)
        return new AdditiveExpression(null, deepCopyChildren());
    }
}
