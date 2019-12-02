import java.util.List;

public class AddOrMultiExpression extends ExpressionAb implements CompoundExpression {
    public AddOrMultiExpression(CompoundExpression parent, List<Expression> children, String value){
        this.parent = parent;
        this.value = value;
        this.children = children;
    }
    /**
     * Adds the specified expression as a child.
     *
     * @param subexpression the child expression to add
     */
    @Override
    public void addSubexpression(Expression subexpression) {

    }
}
