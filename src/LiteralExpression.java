import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class LiteralExpression extends ExpressionAb {
    /**
     * The value of this LiteralExpression
     */
    private String value;

    /**
     * Constructs a LiteralExpression with the given parent and value
     *
     * @param value the value of this LiteralExpression
     */
    public LiteralExpression(String value) {
        super(new HBox(new Text(value)));
        this.value = value;
    }

    /**
     * Returns the value associated with this expression
     * For multiplication, this would be *, for addition, this would be +, and for a literal, this would be toString()
     *
     * @return the value of this expression (excluding its children, if any)
     */
    @Override
    public String getValue() {
        return value;
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
        return new LiteralExpression(value);
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
    }
}
