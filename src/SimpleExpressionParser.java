import java.util.ArrayList;
import java.util.Arrays;
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

    private Expression parE(String str) {
        List<Expression> A = parA(str);
        if (A != null) {
            return new AdditiveExpression(A);
        }
        List<Expression> X = parX(str);
        if (X != null) {
            return new ParentheticalExpression(X.get(0));
        }
        return null;
    }

    private List<Expression> parA(String str) {
        ArrayList<Expression> node = parseHelper(str, '+', this::parA, this::parM);
        if (node != null && node.get(0) != null) {
            //TODO Get rid tmp after it works
            Expression tmp1 = node.get(0);
            Expression tmp2 = node.get(1);
            return makeAdditiveExpression(new ArrayList<Expression>(Arrays.asList(tmp1, tmp2)));
        }
        List<Expression> M = parM(str);
        if (M != null && M.get(0) != null) {
            return M;
        }
        return null;
    }

    private List<Expression> parM(String str) {
        List<Expression> node = parseHelper(str, '*', this::parM, this::parM);
        if (node != null && node.get(0) != null) {
            //TODO Get rid tmp after it works
            Expression tmp1 = node.get(0);
            Expression tmp2 = node.get(1);
             return makeMultiplicativeExpression(new ArrayList<Expression>(Arrays.asList(tmp1, tmp2)));
        }
        List<Expression> X = parX(str);
        if (X != null && X.get(0) != null) {
            return X;
        }
        return null;
    }

    private List<Expression> parX(String str) {
        if ( str.length() > 2 && str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') {
            Expression E = parE(str.substring(1, str.length() - 1));
            if (E != null) {
                return makeParentheticalExpression(E);
            }
        }
        List<Expression> L = parL(str);
        if (L != null && L.get(0) != null) {
            return makeParentheticalExpression(L.get(0));
        }
        return null;
    }

    private List<Expression> parL(String str) {
        for(int i = 0; i < str.length(); i++){
            if (!(Character.isLetter(str.charAt(i)) || Character.isDigit(str.charAt(i)))) {
                return null;
            }
        }
        return makeLiteralExpression(str);
    }

    private ArrayList parseHelper(String str, char op, Function<String, List<Expression>> f1, Function<String, List<Expression>> f2) {
        for (int i = 1; i < str.length() - 1; i++) {
            List<Expression> f1Node = f1.apply(str.substring(0, i));
            List<Expression> f2Node = f2.apply(str.substring(i + 1));
            if (str.charAt(i) == op && f1Node != null && f2Node != null && f1Node.get(0) != null && f2Node.get(0) != null) {
                return new ArrayList(Arrays.asList(f1Node, f2Node));

            }
        }
        return null;
    }

    private List<Expression>makeLiteralExpression(String value){
        return new ArrayList<Expression>(Arrays.asList( new LiteralExpression(value)));
    }

    private List<Expression>makeAdditiveExpression(List<Expression> children){
        return new ArrayList<Expression>(Arrays.asList( new AdditiveExpression(children)));
    }

    private List<Expression>makeMultiplicativeExpression(List<Expression> children){
        return new ArrayList<Expression>(Arrays.asList( new MultiplicativeExpression(children)));
    }

    private List<Expression>makeParentheticalExpression(Expression child){
        return new ArrayList<Expression>(Arrays.asList( new ParentheticalExpression(child)));
    }


}
