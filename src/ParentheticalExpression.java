import java.util.Collections;
import java.util.List;

public class ParentheticalExpression extends ExpressionAb {
    private Expression child;

    public ParentheticalExpression(CompoundExpression parent, Expression child) {
        setParent(parent);
        this.child = child;
    }

    /**
     * @return this expression's children. Empty list for none.
     */
    @Override
    public List<Expression> getChildren() {
        return Collections.singletonList(child);
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
        return new ParentheticalExpression(null, child.deepCopy());
    }
}
