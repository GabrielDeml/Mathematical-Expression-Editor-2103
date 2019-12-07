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
     * Implements the E grammar
     *
     * @param str this is the string we are parsing
     * @return an expression
     */
    private Expression parE(String str) {
        // Try to parse using A
        List<Expression> A = parA(str);
        // If it works return it
        if (A != null) {
            return A.get(0);
        }
        // Try parsing using X
        List<Expression> X = parX(str);
        // If it works return it
        if (X != null) {
            return X.get(0);
        }
        return null;
    }

    /**
     * Implements the A grammar
     *
     * @param str this is the string we are parsing
     * @return an expression
     */
    private List<Expression> parA(String str) {
        //Try parsing using the A+M part of the A grammar
        List<Expression> node = parseHelper(str, '+', this::parA, this::parM);
        //If parsing using the A+M works then make a new node an return it
        if (node != null && node.get(0) != null) {
            return makeAdditiveExpression(new ArrayList<Expression>(Arrays.asList(node.get(0), node.get(1))));
        }
        // If the A+M part does not work try using the M part
        List<Expression> M = parM(str);
        // If it works return it
        if (M != null && M.get(0) != null) {
            return M;
        }
        //Only return null if nothing else works
        return null;
    }

    /**
     * Implements the M grammar
     *
     * @param str this is the string we are parsing
     * @return an expression
     */
    private List<Expression> parM(String str) {
        //Try parsing using the M+M part of the M grammar
        List<Expression> node = parseHelper(str, '*', this::parM, this::parM);

        if (node != null && node.get(0) != null) {
            return makeMultiplicativeExpression(node);
        }
        List<Expression> X = parX(str);
        if (X != null && X.get(0) != null) {
            return X;
        }
        return null;
    }

    private List<Expression> parX(String str) {
        if (str.length() > 2 && str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
            Expression E = parE(str.substring(1, str.length() - 1));
            if (E != null) {
                return makeParentheticalExpression(E);
            }
        }
        List<Expression> L = parL(str);
        if (L != null && L.get(0) != null) {
            return L;
        }
        return null;
    }

    private List<Expression> parL(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!(Character.isLetter(str.charAt(i)) || Character.isDigit(str.charAt(i)))) {
                return null;
            }
        }
        return makeLiteralExpression(str);
    }

    private List<Expression> parseHelper(String str, char op, Function<String, List<Expression>> f1, Function<String, List<Expression>> f2) {
        for (int i = 1; i < str.length() - 1; i++) {
            List<Expression> f1Node = f1.apply(str.substring(0, i));
            List<Expression> f2Node = f2.apply(str.substring(i + 1));
            if (str.charAt(i) == op && f1Node != null && f2Node != null && f1Node.get(0) != null && f2Node.get(0) != null) {
                return new ArrayList<Expression>(Arrays.asList(f1Node.get(0), f2Node.get(0)));
            }
        }
        return null;
    }

    private List<Expression> makeLiteralExpression(String value) {
        return new ArrayList<Expression>(Arrays.asList(new LiteralExpression(value)));
    }

    private List<Expression> makeAdditiveExpression(List<Expression> children) {
        return new ArrayList<Expression>(Arrays.asList(new AdditiveExpression(children)));
    }

    private List<Expression> makeMultiplicativeExpression(List<Expression> children) {
        return new ArrayList<Expression>(Arrays.asList(new MultiplicativeExpression(children)));
    }

    private List<Expression> makeParentheticalExpression(Expression child) {
        return new ArrayList<Expression>(Arrays.asList(new ParentheticalExpression(child)));
    }


}
