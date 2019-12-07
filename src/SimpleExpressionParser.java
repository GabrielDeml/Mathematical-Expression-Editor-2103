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
     * This is to test the boolean tester
     *
     * @param str the string to parse into an expression tree
     * @throws ExpressionParseException
     */
    public boolean parseBoolean(String str) {
        // Remove spaces -- this simplifies the parsing logic
        str = str.replaceAll(" ", "");
        boolean expression = parseExpressionBoolean(str);
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

        System.out.println();
        /**
         * Grammar:
         * E → A | X
         * A → A+M | M
         * M → M*M | X
         * X → (E) | L
         * L → [a-z] | [0-9]+
         */
        return parE(str);
    }

    /**
     * This is just here to test the boolean parser
     *
     * @param str
     * @return
     */
//    protected boolean parseExpressionBoolean(String str) {
//        Expression expression;
//        return parE(str);
//        /**
//         * Grammar:
//         * E → A | X
//         * A → A+M | M
//         * M → M*M | X
//         * X → (E) | L
//         * L → [a-z] | [0-9]+
//         */
//    }
    private Expression parE(String str) throws ExpressionParseException {
        Expression A = new AdditiveExpression(parA(str));
        if (A != null) {
            return A;
        }
        Expression X = new ParentheticalExpression(parX(str));
        if (X != null) {
            return X;
        }
        throw new ExpressionParseException("Isn't a valid string");
    }

    private List<Expression> parA(String str) {
        List<Expression> node = parseHelper(str, '+', this::parA, this::parM);
        if (node.get(0) != null) {
            return new ArrayList<>(Arrays.asList(new AdditiveExpression(new ArrayList<Expression>(Arrays.asList(node.get(0)))),
                    new MultiplicativeExpression(new ArrayList<Expression>(Arrays.asList(node.get(1))))));
        }
        List<Expression> M = parM(str);
        if (M.get(0) != null) {
            return M;
        }
        return null;
    }

    private List<Expression> parM(String str) {
        List<Expression> node = parseHelper(str, '*', this::parM, this::parM);
        if (node.get(0) != null) {
            return new ArrayList<>(Arrays.asList(new MultiplicativeExpression(new ArrayList<Expression>(Arrays.asList(node.get(0)))),
                    new MultiplicativeExpression(new ArrayList<Expression>(Arrays.asList(node.get(1))))));
        }
        List<Expression> X = parX(str);
        if (X.get(0) != null) {
            return X;
        }
        return null;
    }

    private List<Expression> parX(String str){

        if (str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
            Expression E = parE(str.substring(1, str.length() - 1));
            if (E != null) {
                return new ArrayList<Expression>(Arrays.asList(new ParentheticalExpression(E)));
            }
        }
        return null;
    }

    private List<Expression> parL(String str) {
        if(str.length() == 1 && (Character.isLetter(str.charAt(0)) || Character.isDigit(str.charAt(0)))){
            return new ArrayList<Expression>(Arrays.asList(new LiteralExpression(str)));
        }
        return null;
    }

    private List<Expression> parseHelper(String str, char op, Function<String, List<Expression>> f1, Function<String, List<Expression>> f2) {
        for (int i = 1; i < str.length() - 1; i++) {
            List<Expression> f1Node = f1.apply(str.substring(0, i));
            List<Expression> f2Node = f2.apply(str.substring(i + 1));
            if (str.charAt(i) == op && f1Node.get(0) != null && f2Node.get(0) != null) {
                return new ArrayList(Arrays.asList(f1Node, f2Node));

            }
        }
        return null;
    }
}
