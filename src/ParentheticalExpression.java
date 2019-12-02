import java.util.List;

public class ParentheticalExpression extends ExpressionAb {
    public ParentheticalExpression(CompoundExpression parent, List<Expression> children){
        this.parent = parent;
        this.value = "()";
        this.children = children;
    }
}
