import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.List;

abstract class ExpressionAb implements Expression {
    /**
     * Make it so we can call ourselves
     */
    CompoundExpressionAb us = null;
    /**
     * Parent expression
     */
    private CompoundExpression parent = null;

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
     * Returns the value associated with this expression
     * For multiplication, this would be *, for addition, this would be +, and for a literal, this would be toString()
     *
     * @return the value of this expression (excluding its children, if any)
     */
    public abstract String getValue();

    /**
     * Creates a String representation by recursively printing out (using indentation) the
     * tree represented by this expression, starting at the specified indentation level.
     * NOTE: ALL OVERRIDES MUST CALL SUPER FIRST (to add self)!!!
     *
     * @param stringBuilder the StringBuilder to use for building the String representation
     * @param indentLevel   the indentation level (number of tabs from the left margin) at which to start
     */
    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        // add self
        Expression.indent(stringBuilder, indentLevel);
        stringBuilder.append(getValue()).append('\n');
    }

    /**
     * Convenience method for convertToString(int) with no starting indent
     *
     * @return the String representation of this Expression with no starting indent
     */
    public String convertToString() {
        return convertToString(0);
    }

    Node treeToText(Expression parent) {
        StringBuilder tmp = new StringBuilder();
        CompoundExpressionAb parentCompound;
        HBox tmpHbox = new HBox();
        if (parent instanceof LiteralExpression) {
            LiteralExpression literalParent = (LiteralExpression) parent;
            tmpHbox.getChildren().setAll(new Text(literalParent.getValue()));
        }
        parentCompound = (CompoundExpressionAb) parent;
        for (Expression expression : parentCompound.getChildren()) {
            if (expression instanceof AdditiveExpression) {
                tmpHbox.getChildren().setAll(new Text(treeToText(expression) + "+"));
            }
            if (expression instanceof MultiplicativeExpression) {
                tmpHbox.getChildren().setAll(new Text(treeToText(expression) + "*"));
            }
        }
        return tmpHbox;
    }
}