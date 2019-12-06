import javafx.beans.binding.ListExpression;
import javafx.scene.Parent;

import javax.swing.text.html.parser.Parser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


/**
 * Starter code to implement an ExpressionParser. Your parser methods should use the following grammar:
 * E := A | X
 * A := A+M | M
 * M := M*M | X
 * X := (E) | L
 * L := [0-9]+ | [a-z]
 */
public class SimpleExpressionParser implements ExpressionParser {
    /**
     * Attempts to create an expression tree -- flattened as much as possible -- from the specified String.
     * Throws a ExpressionParseException if the specified string cannot be parsed.
     *
     * @param str                the string to parse into an expression tree
     * @param withJavaFXControls you can just ignore this variable for R1
     * @return the Expression object representing the parsed expression tree
     */
    public Expression parse(String str, boolean withJavaFXControls) throws ExpressionParseException {
        // Remove spaces -- this simplifies the parsing logic
        str = str.replaceAll(" ", "");
        Expression expression = parseExpression(str);
        if (expression == null) {
            // If we couldn't parse the string, then raise an error
            throw new ExpressionParseException("Cannot parse expression: " + str);
        }

        // Flatten the expression before returning
        expression.flatten();
        return expression;
    }

    /**
     * Attempts to parse the specified string into an Expression
     *
     * @param str the string to attempt to parse
     * @return null if could not parse str, the associated Expression otherwise
     */
    protected Expression parseExpression(String str) {
        Expression expression;
        parE(str);
        /**
         * Grammar:
         * E → A | X
         * A → A+M | M
         * M → M*M | X
         * X → (E) | L
         * L → [a-z] | [0-9]+
         */
        return null;
    }

    private boolean parE(String str) {
        if (parA(str)) {
            return true;
        }
        return parX(str);
    }

    private boolean parA(String str) {
        if (parseHelper(str, '+', this::parA, this::parM)) {
            return true;
        }
        return parM(str);
    }

    private boolean parM(String str) {
        if (parseHelper(str, '*', this::parM, this::parM)) {
            return true;
        }
        return parX(str);
    }

    private boolean parX(String str) {
        //noinspection ConstantConditions
        if (str.charAt(0) == '(' &&
                str.charAt(str.length()) == ')' &&
                parE(str.substring(1, str.length() - 1))) {
            return true;
        }
        return parL(str);
    }

    private boolean parL(String str){
        return Character.isLetter(str.charAt(0)) || Character.isDigit(str.charAt(0));
    }

    private boolean parseHelper(String str, char op, Function<String, Boolean> f1, Function<String, Boolean> f2) {
        for (int i = 1; i < str.length() - 1; i++) {
            if (str.charAt(i) == op && f1.apply(str.substring(0, i)) && f2.apply(str.substring(i + 1))) {
                return true;
            }
        }
        return false;
    }

}
