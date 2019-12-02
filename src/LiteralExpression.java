import java.util.List;

public class LiteralExpression extends ExpressionAb {
    public LiteralExpression(CompoundExpression parent, List<Expression> children, String value){
        this.parent = parent;
        this.value = value;
        this.children = children;
    }
}
